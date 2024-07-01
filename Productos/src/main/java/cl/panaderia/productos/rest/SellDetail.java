package cl.panaderia.productos.rest;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class SellDetail {

    private List<ProductSellDetail> productos;
    private Integer type;
    private Integer total;

    public SellDetail() {
        this.total = 0;
        this.productos = new ArrayList<>();
    }

}
