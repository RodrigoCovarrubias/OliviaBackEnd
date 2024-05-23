package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.TransBank;
import cl.transbank.webpay.Webpay;
import org.springframework.beans.factory.annotation.Autowired;

public class VentaService {

    @Autowired
    private TransBank transBank;

}
