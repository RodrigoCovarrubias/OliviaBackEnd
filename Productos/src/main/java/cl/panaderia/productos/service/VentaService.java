package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.dominio.TransBank;
import cl.panaderia.productos.dto.ServiceDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class VentaService {

    @Autowired
    private TransBank transBank;

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ServiceDto ventaDto;

    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String CREATE_TRANSACTION_URL = "https://webpay3gint.transbank.cl/rswebpaytransaction/api/webpay/v1.2/transactions";

    public Map<String, Object> createTransaction(List<Producto> productos) throws IOException {
        int amount = calculateAmount(productos);

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost(CREATE_TRANSACTION_URL);

            httpPost.setHeader("Authorization", "Token");
            httpPost.setHeader("Tbk-Api-Key-Id", transBank.getApiKey());
            httpPost.setHeader("Tbk-Api-Key-Secret", transBank.getCommerceCode());
            httpPost.setHeader("Content-Type", "application/json");

            Map<String, Object> body = new HashMap<>();
            body.put("buy_order", "ordenCompra123456");
            body.put("session_id", "sesion1234557545");
            body.put("amount", amount);
            body.put("return_url", transBank.getReturnUrl());
            String json = mapper.writeValueAsString(body);

            StringEntity entity = new StringEntity(json);
            httpPost.setEntity(entity);

            try (CloseableHttpResponse response = httpClient.execute(httpPost)) {
                String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                return mapper.readValue(responseString, HashMap.class);
            }
        }
    }

    public boolean confirmarVenta(List<Producto> productos) {

        SecureRandom secureRandom = new SecureRandom();
        int idBoleta = secureRandom.nextInt(1000);
        Integer idVenta = ventaDto.insertVenta(idBoleta);
        return productoService.updateProductoStock(idVenta,productos);
    }

    public int calculateAmount(List<Producto> productos) {
        return productos.stream().mapToInt(Producto::getPrecio).sum();
    }





}