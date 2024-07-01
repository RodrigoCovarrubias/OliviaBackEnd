package cl.panaderia.productos.rest;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;
}
