package cl.panaderia.productos.dominio;

import lombok.Data;

@Data
public class TransBank {

    private String apiKey;
    private String commerceCode;
    private String environment;
    private String returnUrl;

}
