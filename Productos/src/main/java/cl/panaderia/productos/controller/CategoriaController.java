package cl.panaderia.productos.controller;

import cl.panaderia.productos.dominio.Categoria;
import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.rest.CrearCategoriaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import cl.panaderia.productos.service.CategoriaService;

import java.util.List;

@RestController
@RequestMapping("/categoria")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @PostMapping("/crearCategoria")
    public ResponseEntity<Boolean> createCategoria(@RequestBody CrearCategoriaRequest categoriaRequest) {
        return ResponseEntity.ok(categoriaService.createCategoria(categoriaRequest));
    }

    @GetMapping("/getAllCategoria")
    public ResponseEntity<List<Categoria>> getCategoria() {
        return ResponseEntity.ok(categoriaService.getAllCategoria());
    }

    @GetMapping("/getAllProductosFromCategoria")
    public ResponseEntity<List<Producto>> getAllProductosFromCategoria(@RequestParam Integer idCategoria) {
        return ResponseEntity.ok(categoriaService.getAllProductosFromCategoria(idCategoria));
    }
}
