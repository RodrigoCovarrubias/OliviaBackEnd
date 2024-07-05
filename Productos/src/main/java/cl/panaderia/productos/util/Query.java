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
    public static final String INSERT = "INSERT INTO producto (nombre, id_categoria, descripcion, precio, imagen_url, stock, sku, ingredientes) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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
    public static final String GET_NEWSLETTER_SUBS = "SELECT * FROM newsletter";
    public static final String GET_PRODUCTO_VALUE = "SELECT precio FROM producto WHERE id = ?";
    public static final String GET_PRODUCTO_STOCK = "SELECT stock FROM producto WHERE id = ?";
    public static final String UPDATESTOCK = "UPDATE producto SET stock = stock - ? WHERE id = ?";
    public static final String INSERTPRODUCTBYVENTA = "INSERT INTO public.venta_producto (id_venta, id_producto,cantidad) VALUES(?, ?, ?)";
    public static final String INSERT_DESPACHO = "INSERT INTO public.despacho (id_venta,id_tipo_despacho, correo, direccion, comuna, ciudad, nombre, numero) VALUES(?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String GET_SELL = "SELECT id_venta, id_producto, id, cantidad FROM public.venta_producto WHERE id_venta = ?";
    public static final String GET_SELL_TYPE = "SELECT id_tipo_despacho from despacho WHERE id_venta = ?";
    public static final String VALIDATE_LOGIN = "SELECT id_rol FROM public.usuario where contrasena = ? and correo = ?;";
    public static final String GET_ALL_SELLS = "select id from venta v";
    public static final String GET_SELL_STATUS = "select e.nombre, ve.fecha from venta_estado ve inner join estado e on e.id = ve.id_estado where id_venta = ? order by fecha desc limit 1";
    public static final String INSERT_SELL_STATUS = "INSERT INTO public.venta_estado(id_venta, id_estado, fecha)VALUES(?, ?, NOW())";
    public static final String GET_ALL_STATUS = "select * from estado e ";
}
