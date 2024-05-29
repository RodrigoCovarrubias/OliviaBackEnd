package cl.panaderia.productos.controller;


import cl.panaderia.productos.dominio.NewsLetterRequest;
import cl.panaderia.productos.service.NewsLetterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("newsletter")
public class NewsLetterController {

    @Autowired
    private NewsLetterService newsLetterService;

    @PostMapping("suscribe")
    public ResponseEntity<Boolean> suscribe(@RequestBody NewsLetterRequest newsLetter) {
        return ResponseEntity.ok(newsLetterService.suscribe(newsLetter));
    }

    @GetMapping("getSubscribers")
    public ResponseEntity<List<NewsLetterRequest>> getAllSuscribers() {
        return ResponseEntity.ok(newsLetterService.getAllSuscribers());
    }

}
