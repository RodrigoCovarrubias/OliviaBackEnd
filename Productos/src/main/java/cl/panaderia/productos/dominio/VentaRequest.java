package cl.panaderia.productos.dominio;

import lombok.Data;

import java.util.List;

@Data
public class VentaRequest {

    private String id;
    private List<Producto> productos;
    private String fecha;
}
