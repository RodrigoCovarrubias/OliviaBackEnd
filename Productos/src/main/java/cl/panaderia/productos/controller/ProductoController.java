package cl.panaderia.productos.controller;

import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    @PostMapping("/crearProducto")
    public ResponseEntity<Boolean> insertProducto(@RequestBody Producto producto) {

        return ResponseEntity.ok(productoService.insertProducto(producto));
    }

    @GetMapping("/getAllProductos")
    public ResponseEntity<List<Producto>> getProductos() {
        return ResponseEntity.ok(productoService.getAllProductos());
    }

    @GetMapping("/getProductoByName")
    public ResponseEntity<Producto> getProductoByName(@RequestParam String nombre) {
        return ResponseEntity.ok(productoService.getProductoByName(nombre));
    }

    @GetMapping("/getAllProductosPriceGreaterThan")
    public ResponseEntity<List<Producto>> getAllProductosPriceGreaterThan(@RequestParam Integer precio) {
        return ResponseEntity.ok(productoService.getAllProductosPriceGreaterThan(precio));
    }

    @GetMapping("/getAllProductosWithLessPrice")
    public ResponseEntity<List<Producto>> getAllProductosWithLessPrice(@RequestParam Integer precio) {
        return ResponseEntity.ok(productoService.getAllProductosWithLessPrice(precio));
    }

    @GetMapping("/getAllProductosWithPriceBetween")
    public ResponseEntity<List<Producto>> getAllProductosWithPriceBetween(@RequestParam Integer min, @RequestParam Integer max) {
        return ResponseEntity.ok(productoService.getAllProductosWithPriceBetween(min, max));
    }

    @DeleteMapping("/deleteProducto")
    public ResponseEntity<Boolean> deleteProducto(@RequestParam Integer id) {
        return ResponseEntity.ok(productoService.deleteProducto(id));
    }

    @GetMapping("/getProductoById")
    public ResponseEntity<Producto> getProductoById(@RequestParam Integer id) {
        return ResponseEntity.ok(productoService.getProductoById(id));
    }

    @PutMapping("/updateProducto")
    public ResponseEntity<Boolean> updateProducto(@RequestBody Producto producto) {
        return ResponseEntity.ok(productoService.updateProducto(producto));
    }


}
