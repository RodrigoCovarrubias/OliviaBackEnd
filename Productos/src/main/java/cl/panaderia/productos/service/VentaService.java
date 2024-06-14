package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.dominio.TransBank;
import cl.panaderia.productos.dominio.VentaRequest;
import cl.panaderia.productos.dto.ServiceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class VentaService {

    @Autowired
    private TransBank transBank;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ServiceDto ventaDto;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String TBANK_URL = "https://webpay3gint.transbank.cl/rswebpaytransaction/api/webpay/v1.3/transactions";

    public Map<String, Object> createTransaction(List<VentaRequest> productos) throws IOException {

       Map<String, Object> stockValidation = validateProduct(productos);

        if (stockValidation.isEmpty()) {
            int amount = calculateAmount(productos);
            try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
                HttpPost httpPost = new HttpPost(TBANK_URL);

                httpPost.setHeader("Authorization", "Token");
                httpPost.setHeader("Tbk-Api-Key-Id", transBank.getApiKey());
                httpPost.setHeader("Tbk-Api-Key-Secret", transBank.getCommerceCode());
                httpPost.setHeader("Content-Type", "application/json");

                Map<String, Object> body = new HashMap<>();
                body.put("buy_order", String.format("buyOrder_%s", Instant.now().getEpochSecond()));
                body.put("session_id", String.format("sessionId_%s", Instant.now().getEpochSecond()));
                body.put("amount", amount);
                body.put("return_url", transBank.getReturnUrl());
                String json = mapper.writeValueAsString(body);

                StringEntity entity = new StringEntity(json);
                httpPost.setEntity(entity);

                try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                    if (response.getStatusLine().getStatusCode() == 200) {
                        String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                        return mapper.readValue(responseString, HashMap.class);
                    } else {
                        Map<String, Object> errorResponse = new HashMap<>();
                        errorResponse.put("error", "Failed to create transaction");
                        errorResponse.put("status", response.getStatusLine().getStatusCode());
                        return errorResponse;
                    }
                }
            }
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("error", "Stock insuficiente");
            response.put("productos", stockValidation);
            return response;
        }
    }

    public Map<String, Object> getTransactionStatus(String token) throws IOException {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(TBANK_URL + "/" + token);

            httpGet.setHeader("Authorization", "Token");
            httpGet.setHeader("Tbk-Api-Key-Id", transBank.getApiKey());
            httpGet.setHeader("Tbk-Api-Key-Secret", transBank.getCommerceCode());
            httpGet.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(httpGet)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                    return mapper.readValue(responseString, HashMap.class);
                } else {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("error", "Failed to get transaction status");
                    errorResponse.put("status", response.getStatusLine().getStatusCode());
                    return errorResponse;
                }
            }
        }
    }
    
    public boolean confirmarVenta(List<Producto> productos) {

        SecureRandom secureRandom = new SecureRandom();
        int idBoleta = secureRandom.nextInt(1000);
        Integer idVenta = ventaDto.insertVenta(idBoleta);
        return productoService.updateProductoStock(idVenta,productos);
    }

    public int calculateAmount(List<VentaRequest> productos) {
        return productos.stream()
                .mapToInt(producto ->{
                    Producto producto1 = productoService.getProductoById(producto.getId());
                    return producto1.getPrecio() * producto.getQuantity();
                }).sum();
    }


    public Map<String, Object> validateProduct(List<VentaRequest> ventas) {
        return ventas.stream()
                .map(venta -> {
                    Producto producto = productoService.getProductoById(venta.getId());
                    Map<String, Object> response = new HashMap<>();
                    if (producto == null) {
                        response.put("error", "Product con ID " + venta.getId() + " no existe.");
                        response.put("id", venta.getId());
                        return new AbstractMap.SimpleEntry<>("error", response);
                    } else {
                        response.put("id", producto.getId());
                        response.put("stockRequerido", venta.getQuantity());
                        response.put("stockDisponible", producto.getStock());
                        if (producto.getStock() < venta.getQuantity()) {
                            return new AbstractMap.SimpleEntry<>(producto.getNombre(), response);
                        } else {
                            return null;
                        }
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}