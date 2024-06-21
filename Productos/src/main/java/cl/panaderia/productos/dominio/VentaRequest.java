package cl.panaderia.productos.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequest {

    private List<ProductoVentaRequest> productos;
    private Despacho despacho;

}
