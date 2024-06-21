package cl.panaderia.productos.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductFilter {

    private String nombre;
    private Integer idCategoria;
    private Integer minPrecio;
    private Integer maxPrecio;
    private Integer precio;


}
