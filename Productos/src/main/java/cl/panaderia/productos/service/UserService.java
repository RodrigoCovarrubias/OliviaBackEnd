package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.RolesResponse;
import cl.panaderia.productos.dominio.Usuario;
import cl.panaderia.productos.dto.ServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private ServiceDto userDto;

    //Validacion espacios contraseña
    private static final String REGEXP = ".*\\s+.*";
    private static final String REGEX_EMAIL = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
    private static final String REGEX_NAME = "[a-zA-Z]";

    public List<Usuario> getAllUsers() {
        return  userDto.getAllUsers();
    }

    public Usuario getUser(Integer id) {
        return userDto.getUser(id);
    }

    public boolean createUser(Usuario usuario) {
        if (isInvalidUser(usuario)) {
            return false;
        }
        String encriptedPassword = Base64.getEncoder().encodeToString(usuario.getContrasena().getBytes());
        usuario.setContrasena(encriptedPassword);
        return userDto.createUser(usuario);
    }

    public boolean deleteUser(Integer id) {
        return userDto.deleteUser(id);
    }

    public boolean updateUser(Integer id, Usuario usuario) {
        if (usuario.getCorreo() != null && !usuario.getCorreo().matches(REGEX_EMAIL)) {
            return false;
        }
        if (usuario.getContrasena() != null) {
            String encriptedPassword = Base64.getEncoder().encodeToString(usuario.getContrasena().getBytes());
            usuario.setContrasena(encriptedPassword);
        }
        return userDto.updateUser(id, usuario);
    }

    private boolean isInvalidUser(Usuario usuario) {
        return usuario.getNombre() == null ||
                usuario.getApaterno() == null ||
                usuario.getAmaterno() == null ||
                usuario.getCorreo() == null ||
                usuario.getIdRol() == 0 ||
                usuario.getContrasena() == null ||
                usuario.getContrasena().length() < 8 ||
                usuario.getContrasena().matches(REGEXP) ||
                !usuario.getCorreo().matches(REGEX_EMAIL)
                || !usuario.getNombre().matches(REGEX_NAME);
    }

    public List<RolesResponse> getRoles() {
        return userDto.getRoles();
    }
}
