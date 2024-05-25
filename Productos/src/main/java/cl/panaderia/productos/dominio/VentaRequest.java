package cl.panaderia.productos.dominio;

import lombok.Data;

import java.util.List;

@Data
public class VentaRequest {

    private List<Producto> productos;
    private String fecha;

}
