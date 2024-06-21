package cl.panaderia.productos.controller;

import cl.panaderia.productos.dominio.ProductoVentaRequest;
import cl.panaderia.productos.dominio.VentaRequest;
import cl.panaderia.productos.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("venta")
public class VentaController {

    @Autowired
    private VentaService ventaService;

    @PostMapping("pay")
    public Map<String, Object> pay(@RequestBody VentaRequest productos) {

        try {
            return ventaService.createTransaction(productos);

        } catch (IOException e) {
            throw new RuntimeException("Error creating transaction: " + e.getMessage());
        }

    }

    @PostMapping("get/confirmarVenta")
    public RedirectView confirmarVenta(@RequestBody Map<String, Object> req) throws IOException {
        Object token = req.get("token_ws");
        boolean isClientAnnulment = false;

        if (req.get("TBK_TOKEN") != null) {
            token = req.get("TBK_TOKEN");
            isClientAnnulment = true;
        }

        String urlToRedirect = ventaService.confirmTransaction(token.toString(), isClientAnnulment);
        RedirectView rw = new RedirectView();
        rw.setUrl(urlToRedirect);
        return rw;
    }

    @GetMapping("get/confirmarVenta")
    public RedirectView getConfirmarVenta(@RequestParam Map<String, Object> req) throws IOException {
        Object token = req.get("token_ws");
        boolean isClientAnnulment = false;

        if (req.get("TBK_TOKEN") != null) {
            token = req.get("TBK_TOKEN");
            isClientAnnulment = true;
        }

        String urlToRedirect = ventaService.confirmTransaction(token.toString(), isClientAnnulment);
        RedirectView rw = new RedirectView();
        rw.setUrl(urlToRedirect);
        return rw;
    }

}
