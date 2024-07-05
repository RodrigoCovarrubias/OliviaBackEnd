package cl.panaderia.productos.dominio;

import cl.panaderia.productos.rest.SellDetail;
import lombok.Data;

import java.util.Date;

@Data
public class SellDetailWithStatus extends SellDetail {

    private String status;
    private Date statusDate;
    private Integer id;
    private Despacho despacho;


}
