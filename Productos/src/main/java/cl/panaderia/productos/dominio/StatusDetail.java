package cl.panaderia.productos.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StatusDetail {
    private String nombre;
    private Date fecha;

}

