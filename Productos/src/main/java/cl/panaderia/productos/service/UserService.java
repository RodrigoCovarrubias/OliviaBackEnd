package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.Usuario;
import cl.panaderia.productos.dto.ServiceDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private ServiceDao userDto;

    public List<Usuario> getAllUsers() {
        return  userDto.getAllUsers();
    }

    public Usuario getUser(Integer id) {
        return userDto.getUser(id);
    }

    public boolean createUser(Usuario usuario) {
        String encriptedPassword = Base64.getEncoder().encodeToString(usuario.getContrasena().getBytes());
        usuario.setContrasena(encriptedPassword);
        return userDto.createUser(usuario);
    }

    public boolean deleteUser(Integer id) {
        return userDto.deleteUser(id);
    }

    public boolean updateUser(Integer id, Usuario usuario) {
        if (usuario.getContrasena() != null) {
            String encriptedPassword = Base64.getEncoder().encodeToString(usuario.getContrasena().getBytes());
            usuario.setContrasena(encriptedPassword);
        }
        return userDto.updateUser(id, usuario);
    }
}
