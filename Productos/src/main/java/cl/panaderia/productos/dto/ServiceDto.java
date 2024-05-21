package cl.panaderia.productos.dto;

import cl.panaderia.productos.dominio.Categoria;
import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ServiceDto {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean insertProducto(String nombre, int idCategoria, String descripcion, Integer precio) {
        return jdbcTemplate.update(Query.INSERT, nombre, idCategoria, descripcion, precio) == 1;
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
                producto.setImgUrl(rs.getString("img_url"));
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
                producto.setImgUrl(rs.getString("img_url"));
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
                producto.setImgUrl(rs.getString("img_url"));
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
                producto.setImgUrl(rs.getString("img_url"));
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
                producto.setImgUrl(rs.getString("img_url"));
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
                producto.setImgUrl(rs.getString("img_url"));
                productos.add(producto);
            }
            return productos;
        }, min, max);
    }

    public boolean deleteProducto(int id) {
        return jdbcTemplate.update(Query.DELETE_PRODUCTO, id) == 1;
    }

}
