package cl.panaderia.productos.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User {

    private String nombre;
    private Integer idRol;
}
