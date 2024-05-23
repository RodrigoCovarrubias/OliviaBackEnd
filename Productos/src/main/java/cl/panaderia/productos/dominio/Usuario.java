package cl.panaderia.productos.dominio;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Usuario {

    public Integer id;
    public String nombre;
    public String apaterno;
    public String amaterno;
    public String correo;
    public Integer idRol;
    public String contrasena;

}
