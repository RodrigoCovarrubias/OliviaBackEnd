package cl.panaderia.productos.controller;

import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.dominio.VentaRequest;
import cl.panaderia.productos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("pay")
    public Map<String, Object> pay(@RequestBody List<VentaRequest> productos) {

        try {
            return ventaService.createTransaction(productos);

        } catch (IOException e) {
            throw new RuntimeException("Error creating transaction: " + e.getMessage());
        }

    }

    @PostMapping("confirmarVenta")
    public ResponseEntity<Boolean> confirmarVenta(@RequestBody List<Producto> productos) {
        return ResponseEntity.ok(ventaService.confirmarVenta(productos));
    }


}
