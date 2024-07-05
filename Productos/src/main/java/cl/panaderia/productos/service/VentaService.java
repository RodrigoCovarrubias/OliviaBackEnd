package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.*;
import cl.panaderia.productos.dto.ServiceDto;
import cl.panaderia.productos.rest.ProductSellDetail;
import cl.panaderia.productos.rest.SellDetail;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    private static final String SUCCESS_URL = "http://localhost:5173/venta/exito";

    private static final String FAIL_URL = "http://localhost:5173/venta/fallida";

    private Map<String, VentaRequest> ventas = new HashMap<>();

    public Map<String, Object> createTransaction(VentaRequest ventaRequests) throws IOException {
        List<ProductoVentaRequest> productos = ventaRequests.getProductos();
        Map<String, Object> stockValidation = validateProduct(productos);
        if (stockValidation.isEmpty()) {
            int amount = calculateAmount(productos);
            if (ventaRequests.getDespacho().getIdTipoDespacho() == 1) {
                amount += 5000;
            }
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
                        ventas.put(body.get("buy_order").toString(), ventaRequests);
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

    public String confirmTransaction(String token, boolean isClientAnnulment) throws IOException {
        if (isClientAnnulment == true) {
            return FAIL_URL;
        }
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPut httpPut = new HttpPut(TBANK_URL + "/" + token);

            httpPut.setHeader("Authorization", "Token");
            httpPut.setHeader("Tbk-Api-Key-Id", transBank.getApiKey());
            httpPut.setHeader("Tbk-Api-Key-Secret", transBank.getCommerceCode());
            httpPut.setHeader("Content-Type", "application/json");


            try (CloseableHttpResponse response = httpClient.execute(httpPut)) {
                if (response.getStatusLine().getStatusCode() == 200) {
                    String responseString = EntityUtils.toString(response.getEntity(), "UTF-8");
                    Map<String, Object> responseMap = mapper.readValue(responseString, Map.class);
                    Integer responseCode = (Integer) responseMap.get("response_code");
                    if (responseCode.equals(0)) {
                        Integer ammount = (Integer) responseMap.get("amount");
                        String date = (String) responseMap.get("transaction_date");
                        String auth = (String) responseMap.get("authorization_code");
                        Integer idVenta = saveSell();
                        saveDespacho(idVenta, ventas.get(responseMap.get("buy_order")));
                        handleStock(idVenta, ventas.get(responseMap.get("buy_order")).getProductos());
                        ventaDto.saveSellStatus(idVenta,1);
                        ventas.remove(responseMap.get("buy_order"));
                        return SUCCESS_URL+"?monto="+ammount+"&fechaTransaccion="+date+"&codigoAutorizacion="+auth+"&ventaId="+idVenta;
                    } else {
                        return FAIL_URL;
                    }
                } else {
                    return FAIL_URL;
                }
            }
        }
    }
    
    public Integer saveSell() {

        SecureRandom secureRandom = new SecureRandom();
        int idBoleta = secureRandom.nextInt(1000);
        return ventaDto.insertVenta(idBoleta);

    }

    public void saveDespacho(Integer idVenta, VentaRequest ventaRequest) {
        ventaDto.insertDespacho(idVenta, ventaRequest.getDespacho());
    }



    public int calculateAmount(List<ProductoVentaRequest> productos) {
        return productos.stream()
                .mapToInt(producto ->{
                    Producto producto1 = productoService.getProductoById(producto.getId());
                    return producto1.getPrecio() * producto.getQuantity();
                }).sum();
    }


    public Map<String, Object> validateProduct(List<ProductoVentaRequest> ventas) {
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

    private void handleStock(Integer idVenta, List<ProductoVentaRequest> req) {
        ventaDto.batcgInsertVentaProducto(idVenta, req);
        req.forEach(producto -> {
            productoService.updateStock(producto.getId(), producto.getQuantity());
        });
    }

    public SellDetail getSell(Integer idVenta) {
        SellDetail sellDetail = new SellDetail();
        List<Sell> sells = ventaDto.getSell(idVenta);
        sells.forEach(sell -> {
            Producto producto = productoService.getProductoById(sell.getIdProducto());
            ProductSellDetail productSellDetail = new ProductSellDetail();
            productSellDetail.setName(producto.getNombre());
            productSellDetail.setPrice(producto.getPrecio());
            productSellDetail.setQuantity(sell.getCantidad());
            productSellDetail.setUrl(producto.getImagenUrl());
            sellDetail.getProductos().add(productSellDetail);
            sellDetail.setTotal(sellDetail.getTotal() + productSellDetail.getQuantity() * producto.getPrecio());
        });
        sellDetail.setType(ventaDto.getSellType(idVenta));
        if (sellDetail.getType() == 1) {
            sellDetail.setTotal(sellDetail.getTotal() + 5000);
        }
        return sellDetail;
    }

    public List<SellDetailWithStatus> getSellWithStatus() {
        List<SellDetailWithStatus> sellDetails = new ArrayList<>();
        ventaDto.getAllSells().forEach(id -> {
            SellDetailWithStatus sellDetailWithStatus = converToSellDetailWithStatus(getSell(id));
            StatusDetail statusDetail = ventaDto.getSellStatus(id);
            sellDetailWithStatus.setStatus(statusDetail.getNombre());
            sellDetailWithStatus.setStatusDate(statusDetail.getFecha());
            sellDetailWithStatus.setId(id);
            sellDetails.add(sellDetailWithStatus);
        });
        return sellDetails;
    }

    private SellDetailWithStatus converToSellDetailWithStatus(SellDetail sellDetail) {
        SellDetailWithStatus sellDetailWithStatus = new SellDetailWithStatus();
        sellDetailWithStatus.setProductos(sellDetail.getProductos());
        sellDetailWithStatus.setTotal(sellDetail.getTotal());
        sellDetailWithStatus.setType(sellDetail.getType());
        return sellDetailWithStatus;
    }

    public Boolean updateStatus(Integer idVenta, Integer status) {
        return ventaDto.saveSellStatus(idVenta, status);
    }

    public List<Status> getStatus() {
        return ventaDto.getStatus();
    }
}