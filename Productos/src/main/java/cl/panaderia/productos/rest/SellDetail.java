package cl.panaderia.productos.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class SellDetail {

    private List<ProductSellDetail> productos;
    private Integer type;
    private Integer total;

    public SellDetail() {
        this.total = 0;
        this.productos = new ArrayList<>();
    }

}
