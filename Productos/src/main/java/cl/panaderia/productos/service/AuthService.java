package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.User;
import cl.panaderia.productos.dto.ServiceDto;
import cl.panaderia.productos.rest.AuthRequest;
import cl.panaderia.productos.rest.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.UUID;

@Service
public class AuthService {

    @Autowired
    private ServiceDto authDto;

    public AuthResponse getAuthDto(AuthRequest authRequest) {
        String password = Base64.getEncoder().encodeToString(authRequest.getPassword().getBytes());
        User user = authDto.getLogin(authRequest.getEmail(), password);
        if (user != null) {
            return new AuthResponse(true, UUID.randomUUID().toString().replace("-", "").substring(0, 20),user.getIdRol(), user.getNombre());
        } else {
            return new AuthResponse(false,null, null, null);
        }
    }
}
