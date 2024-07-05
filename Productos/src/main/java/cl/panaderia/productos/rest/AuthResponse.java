package cl.panaderia.productos.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AuthResponse {

    private Boolean success;
    private String token;
    private Integer idRol;
    private String nombre;
}
