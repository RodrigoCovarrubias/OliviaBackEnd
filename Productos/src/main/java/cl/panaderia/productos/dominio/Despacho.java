package cl.panaderia.productos.dominio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Despacho {

    private Integer idTipoDespacho; // 1: Despacho a domicilio, 2: Retiro en tienda
    private String correo;
    private String direccion;
    private String comuna;
    private String ciudad;
    private String nombre;
    private Integer numero;

}
