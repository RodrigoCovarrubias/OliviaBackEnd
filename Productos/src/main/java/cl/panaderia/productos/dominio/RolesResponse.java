package cl.panaderia.productos.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class RolesResponse {

    private Integer id;
    private String nombre;
}
