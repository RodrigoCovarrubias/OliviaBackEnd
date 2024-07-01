package cl.panaderia.productos.controller;

import cl.panaderia.productos.rest.AuthRequest;
import cl.panaderia.productos.rest.AuthResponse;
import cl.panaderia.productos.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public AuthResponse login(@RequestBody AuthRequest authRequest) {
        return authService.getAuthDto(authRequest);

    }

}
