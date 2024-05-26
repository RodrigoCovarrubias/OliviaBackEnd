package cl.panaderia.productos.util;

public class Query {

    //Querys para tabla usuarios
    public static final String SELECT_ALL_USERS = "SELECT * FROM usuario u ";
    public static final String SELECT_USER_BY_ID = "SELECT * FROM usuario u "+
            "WHERE u.id = ?";
    public static final String UPDATE_USUARIO = "UPDATE usuario SET ";
    public static final String GET_ROLES = "SELECT * FROM rol";

    public static final String INSERT_NEWSLETTER = "INSERT INTO newsletter (nombre, correo) VALUES (?, ?)";
    public static final String DELETE_USUARIO = "DELETE FROM usuario WHERE id = ?";
    public static final String INSERT_USER = "INSERT INTO usuario (nombre, apaterno, amaterno, correo, contrasena,id_rol) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String SELECT_ALL = "SELECT * FROM productos";
    public static final String INSERT = "INSERT INTO producto (nombre, id_categoria, descripcion, precio, imagen_url, stock) VALUES (?, ?, ?, ?, ?, ?)";
    public static final String INSERT_CATEGORIA = "INSERT INTO categoria (nombre) VALUES (?)";
    public static final String SELECT_ALL_CATEGORIA = "SELECT * FROM categoria";
    public static final String GET_PRODUCTO_FROM_CATEGORY = "SELECT * FROM producto WHERE id_categoria = ?";
    public static final String GET_ALL_PRODUCTOS = "SELECT * FROM producto";
    public static final String GET_PRODUCTO_BY_NAME = "SELECT * FROM producto WHERE nombre = ?";
    public static final String GET_ALL_PRODUCT_WITH_GREATER_PRICE = "SELECT * FROM producto WHERE precio > ?";
    public static final String GET_ALL_PRODUCT_WITH_LESS_PRICE = "SELECT * FROM producto WHERE precio < ?";
    public static final String GET_ALL_PRODUCT_WITH_PRICE_BETWEEN = "SELECT * FROM producto WHERE precio BETWEEN ? AND ?";
    public static final String DELETE_PRODUCTO = "DELETE FROM producto WHERE id = ?";
    public static final String GET_PRODUCTO_BY_ID = "SELECT * FROM producto WHERE id = ?";
    public static final String UPDATE_STOCK = "UPDATE producto SET stock = ? WHERE id = ?";
    public static final String INSERT_VENTA = "INSERT INTO public.venta (id_medio_pago, id_boleta, fecha) VALUES( ?, ?, ?)";
    public static final String UPDATE_PRODUCTO = "UPDATE producto SET ";
}
