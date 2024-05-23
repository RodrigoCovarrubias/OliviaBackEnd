package cl.panaderia.productos.controller;

import cl.panaderia.productos.dominio.Usuario;
import cl.panaderia.productos.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("usuario")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("getAllUsuarios")
    public ResponseEntity<List<Usuario>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("usuarioById")
    public ResponseEntity<Usuario> getUser(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @PostMapping("createUsuario")
    public ResponseEntity<Boolean> createUser(@RequestBody Usuario usuario) {
        return ResponseEntity.ok(userService.createUser(usuario));
    }

    @DeleteMapping("usuarioById")
    public ResponseEntity<Boolean> deleteUser(@RequestParam("id") Integer id) {
        return ResponseEntity.ok(userService.deleteUser(id));
    }

    @PutMapping("usuarioById")
    public ResponseEntity<Boolean> updateUser(@RequestParam("id") Integer id, @RequestBody Usuario usuario) {
        return ResponseEntity.ok(userService.updateUser(id, usuario));
    }

}
