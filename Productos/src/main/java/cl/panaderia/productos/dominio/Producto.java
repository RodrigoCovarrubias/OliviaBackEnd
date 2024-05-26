package cl.panaderia.productos.dominio;

import lombok.Data;

@Data
public class Producto {

    private int id;
    private String nombre;
    private Integer idCategoria;
    private String descripcion;
    private Integer precio;
    private Integer stock;
    private String imagenUrl;
}
