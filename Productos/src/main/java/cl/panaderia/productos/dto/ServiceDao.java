package cl.panaderia.productos.dto;

import cl.panaderia.productos.dominio.Categoria;
import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.dominio.Usuario;
import cl.panaderia.productos.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Repository
public class ServiceDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean insertProducto(String nombre, int idCategoria, String descripcion, Integer precio, String imagenUrl ) {
        return jdbcTemplate.update(Query.INSERT, nombre, idCategoria, descripcion, precio, imagenUrl) == 1;
    }

    public boolean createCategoria(String nombre) {
        return jdbcTemplate.update(Query.INSERT_CATEGORIA, nombre) == 1;
    }

    public List<Categoria> getAllCategoria() {
        return jdbcTemplate.query(Query.SELECT_ALL_CATEGORIA, rs ->{
            List<Categoria> categorias = new ArrayList<>();
            while(rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setId(rs.getInt("id"));
                categoria.setNombre(rs.getString("nombre"));
                categorias.add(categoria);
            }
            return categorias;
        });
    }

    public List<Producto> getAllProductosFromCategoria(Integer idCategoria) {
        return jdbcTemplate.query(Query.GET_PRODUCTO_FROM_CATEGORY, rs ->{
            List<Producto> productos = new ArrayList<>();
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagenUrl(rs.getString("imagen_url"));
                productos.add(producto);
            }
            return productos;
        }, idCategoria);
    }

    public List<Producto> getAllProductos() {
        return jdbcTemplate.query(Query.GET_ALL_PRODUCTOS, rs ->{
            List<Producto> productos = new ArrayList<>();
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagenUrl(rs.getString("imagen_url"));
                productos.add(producto);
            }
            return productos;
        });
    }

    public Producto getProductoByName(String nombre) {
        try {
            return jdbcTemplate.queryForObject(Query.GET_PRODUCTO_BY_NAME, (rs, rowNum) -> {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagenUrl(rs.getString("imagen_url"));
                return producto;
            }, nombre);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Producto> getAllProductsWithGreaterPrice(Integer precio) {
        return jdbcTemplate.query(Query.GET_ALL_PRODUCT_WITH_GREATER_PRICE, rs ->{
            List<Producto> productos = new ArrayList<>();
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagenUrl(rs.getString("imagen_url"));
                productos.add(producto);
            }
            return productos;
        }, precio);
    }

    public List<Producto> getAllProductsWithLessPrice(Integer precio) {
        return jdbcTemplate.query(Query.GET_ALL_PRODUCT_WITH_LESS_PRICE, rs ->{
            List<Producto> productos = new ArrayList<>();
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagenUrl(rs.getString("imagen_url"));
                productos.add(producto);
            }
            return productos;
        }, precio);
    }

    public List<Producto> getAllProductsWithPriceBetween(Integer min, Integer max) {
        return jdbcTemplate.query(Query.GET_ALL_PRODUCT_WITH_PRICE_BETWEEN, rs ->{
            List<Producto> productos = new ArrayList<>();
            while(rs.next()) {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagenUrl(rs.getString("imagen_url"));
                productos.add(producto);
            }
            return productos;
        }, min, max);
    }

    public boolean deleteProducto(int id) {
        return jdbcTemplate.update(Query.DELETE_PRODUCTO, id) == 1;
    }

    public Producto getProductoById(int id) {
        try {
            return jdbcTemplate.queryForObject(Query.GET_PRODUCTO_BY_ID, (rs, rowNum) -> {
                Producto producto = new Producto();
                producto.setId(rs.getInt("id"));
                producto.setNombre(rs.getString("nombre"));
                producto.setIdCategoria(rs.getInt("id_categoria"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setPrecio(rs.getInt("precio"));
                producto.setStock(rs.getInt("stock"));
                producto.setImagenUrl(rs.getString("imagen_url"));
                return producto;
            }, id);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Usuario> getAllUsers() {
        return jdbcTemplate.query(Query.SELECT_ALL_USERS, rs ->{
            List<Usuario> usuarios = new ArrayList<>();
            while(rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getInt("id"));
                usuario.setNombre(rs.getString("nombre"));
                usuario.setApaterno(rs.getString("apaterno"));
                usuario.setAmaterno(rs.getString("amaterno"));
                usuario.setCorreo(rs.getString("correo"));
                usuario.setIdRol(rs.getInt("id_rol"));
                usuarios.add(usuario);
            }
            return usuarios;
        });
    }

    public Usuario getUser(Integer id) {
        return jdbcTemplate.query(Query.SELECT_USER_BY_ID,ps -> ps.setInt(1,id),
            rs  -> {
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombre(rs.getString("nombre"));
                    usuario.setApaterno(rs.getString("apaterno"));
                    usuario.setAmaterno(rs.getString("amaterno"));
                    usuario.setCorreo(rs.getString("correo"));
                    usuario.setIdRol(rs.getInt("id_rol"));
                    return usuario;}
                return null;
        });
    }

    public boolean createUser(Usuario usuario) {
        return jdbcTemplate.update(Query.INSERT_USER,
                usuario.getNombre(), usuario.getApaterno(), usuario.getAmaterno(), usuario.getCorreo(), usuario.getContrasena(), usuario.getIdRol()) == 1;
    }

    public boolean deleteUser(Integer id) {
        return jdbcTemplate.update(Query.DELETE_USUARIO, id) == 1;
    }

    public boolean updateUser(Integer id, Usuario usuario) {
        StringBuilder query = new StringBuilder(Query.UPDATE_USUARIO);
        List<Object> params = new ArrayList<>();

        if (usuario.getNombre() != null) {
            query.append("nombre = ?, ");
            params.add(usuario.getNombre());
        }
        if (usuario.getApaterno() != null) {
            query.append("apaterno = ?, ");
            params.add(usuario.getApaterno());
        }
        if (usuario.getAmaterno() != null) {
            query.append("amaterno = ?, ");
            params.add(usuario.getAmaterno());
        }
        if (usuario.getCorreo() != null) {
            query.append("correo = ?, ");
            params.add(usuario.getCorreo());
        }
        if (usuario.getContrasena() != null) {
            query.append("contrasena = ?, ");
            params.add(usuario.getContrasena());
        }
        if (usuario.getIdRol() != null) {
            query.append("id_rol = ?, ");
            params.add(usuario.getIdRol());
        }

        // Remove the last comma and space
        if (params.size() > 0) {
            query.delete(query.length() - 2, query.length());
        }

        query.append(" WHERE id = ?");
        params.add(id);

        return jdbcTemplate.update(query.toString(), params.toArray()) == 1;
    }
    }