package cl.panaderia.productos.service;

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
        Integer idRol = authDto.getLogin(authRequest.getEmail(), password);
        if (idRol == 1 || idRol == 2) {
            return new AuthResponse(true, UUID.randomUUID().toString().replace("-", "").substring(0, 20),idRol);
        } else {
            return new AuthResponse(false,null, null);
        }
    }
}
