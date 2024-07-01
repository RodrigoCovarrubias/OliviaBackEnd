package cl.panaderia.productos.rest;

import lombok.Data;

@Data
public class ProductSellDetail {

    private String name;
    private Integer quantity;
    private Integer price;
    private String url;
}
