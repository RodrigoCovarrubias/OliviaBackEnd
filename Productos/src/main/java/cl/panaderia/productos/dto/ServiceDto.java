package cl.panaderia.productos.dto;

import cl.panaderia.productos.dominio.*;
import cl.panaderia.productos.util.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
public class ServiceDto {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public boolean insertProducto(Producto producto) {
        return jdbcTemplate.update(Query.INSERT, ps -> {
            ps.setString(1, producto.getNombre());
            ps.setLong(2, producto.getIdCategoria());
            ps.setString(3, producto.getDescripcion());
            ps.setInt(4, producto.getPrecio());
            ps.setString(5, producto.getImagenUrl());
            ps.setInt(6, producto.getStock());
            ps.setString(7, producto.getSku());
            Array ingredientesArray = ps.getConnection().createArrayOf("VARCHAR", producto.getIngredientes());
            ps.setArray(8, ingredientesArray);
        }) == 1;
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
                producto.setSku(rs.getString("sku"));
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
                producto.setSku(rs.getString("sku"));
                String ingredientes = rs.getString("ingredientes");
                if (ingredientes != null) producto.setIngredientes(ingredientes.replaceAll("[{}]","").split(","));
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
                producto.setSku(rs.getString("sku"));
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
                producto.setSku(rs.getString("sku"));
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
                producto.setSku(rs.getString("sku"));
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
                producto.setSku(rs.getString("sku"));
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
                producto.setSku(rs.getString("sku"));
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

    public boolean suscribe(NewsLetterRequest newsLetter) {
        return jdbcTemplate.update(Query.INSERT_NEWSLETTER, newsLetter.getNombre(), newsLetter.getCorreo()) == 1;
    }

    public List<RolesResponse> getRoles() {
        return jdbcTemplate.query(Query.GET_ROLES, rs ->{
            List<RolesResponse> roles = new ArrayList<>();
            while(rs.next()) {
                roles.add(new RolesResponse(rs.getInt("id"), rs.getString("nombre")));
            }
            return roles;
        });
    }

    public boolean updateProductoStock(int id, int stock) {
        return jdbcTemplate.update(Query.UPDATE_STOCK, stock, id) == 1;
    }

    public int insertVenta(Integer idBoleta) {
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(Query.INSERT_VENTA, new String[] {"id"});
            ps.setInt(1, 1); // medio de pago seteado en duro ya que solo considera pagos por webpay
            ps.setInt(2, idBoleta);
            ps.setDate(3, new java.sql.Date(System.currentTimeMillis()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().intValue();
    }

    public boolean updateProducto(Producto producto) {
        StringBuilder query = new StringBuilder(Query.UPDATE_PRODUCTO);
        List<Object> params = new ArrayList<>();
        if (producto.getNombre() != null) {
            query.append("nombre = ?, ");
            params.add(producto.getNombre());
        }
        if (producto.getIdCategoria() != null) {
            query.append("id_categoria = ?, ");
            params.add(producto.getIdCategoria());
        }
        if (producto.getDescripcion() != null) {
            query.append("descripcion = ?, ");
            params.add(producto.getDescripcion());
        }
        if (producto.getPrecio() != null) {
            query.append("precio = ?, ");
            params.add(producto.getPrecio());
        }
        if (producto.getImagenUrl() != null) {
            query.append("imagen_url = ?, ");
            params.add(producto.getImagenUrl());
        }
        if (producto.getStock() != null) {
            query.append("stock = ?, ");
            params.add(producto.getStock());
        }
        if (!params.isEmpty()) {
            query.delete(query.length() - 2, query.length());
        }
        query.append(" WHERE id = ?");
        params.add(producto.getId());

        return jdbcTemplate.update(query.toString(), params.toArray()) == 1;

    }

    public List<NewsLetterRequest> getAllSuscribers() {
        return jdbcTemplate.query(Query.GET_NEWSLETTER_SUBS, rs ->{
            List<NewsLetterRequest> newsLetters = new ArrayList<>();
            while(rs.next()) {
                NewsLetterRequest newsLetter = new NewsLetterRequest();
                newsLetter.setNombre(rs.getString("nombre"));
                newsLetter.setCorreo(rs.getString("correo"));
                newsLetters.add(newsLetter);
            }
            return newsLetters;
        });
    }

    public Integer getValueById(Integer id) {
        return jdbcTemplate.queryForObject(Query.GET_PRODUCTO_VALUE, Integer.class, id);
    }

    public boolean validateStock(Integer id, Integer quantity) {
        return Boolean.TRUE.equals(jdbcTemplate.query(Query.GET_PRODUCTO_STOCK, ps -> ps.setInt(1, id), rs -> {
                    if (rs.next()) {
                        return rs.getInt("stock") >= quantity;
                    }
                    return false;
                }
        ));
    }

    public void updateStock (Integer id, Integer quantity) {
        jdbcTemplate.update(Query.UPDATESTOCK, quantity, id);
    }

    public void batcgInsertVentaProducto(Integer idVenta, List<ProductoVentaRequest> productos) {
        jdbcTemplate.batchUpdate(Query.INSERTPRODUCTBYVENTA, productos, productos.size(), (ps, producto) -> {
            ps.setInt(1, idVenta);
            ps.setInt(2, producto.getId());
            ps.setInt(3, producto.getQuantity());
        });
    }

    public void insertDespacho(Integer idVenta, Despacho despacho) {
        jdbcTemplate.update(Query.INSERT_DESPACHO,
                idVenta, despacho.getIdTipoDespacho(),despacho.getCorreo(),despacho.getDireccion(), despacho.getComuna(), despacho.getCiudad(), despacho.getNombre(), despacho.getNumero());
    }

    public Despacho getDespachoById(Integer id) {
        return jdbcTemplate.query(Query.GET_DESPACHO, ps -> ps.setInt(1, id), rs -> {
            if (rs.next()) {
                return new Despacho(
                        rs.getInt("id_tipo_despacho"),
                        rs.getString("correo"),
                        rs.getString("direccion"),
                        rs.getString("comuna"),
                        rs.getString("ciudad"),
                        rs.getString("nombre"),
                        rs.getInt("numero")

                );
            } return new Despacho();
        });
    }
    public List<Sell> getSell(Integer id) {

        return jdbcTemplate.query(Query.GET_SELL,ps-> ps.setInt(1, id), rs -> {
            List<Sell> sells = new ArrayList<>();
            while (rs.next()) {
                Sell venta = new Sell();
                venta.setId(rs.getLong("id_venta"));
                venta.setIdProducto(rs.getInt("id_producto"));
                venta.setId(rs.getLong("id"));
                venta.setCantidad(rs.getInt("cantidad"));
                sells.add(venta);
            }
            return sells;
        });
    }

    public Integer getSellType(Integer id) {
        return jdbcTemplate.query(Query.GET_SELL_TYPE, ps ->
                ps.setInt(1, id),
                rs -> {
            if (rs.next()) {
                return rs.getInt("id_tipo_despacho");
            } else {
                return null;
            }
        });
    }

    public User getLogin(String name, String password) {
        return jdbcTemplate.query(Query.VALIDATE_LOGIN, ps -> {
            ps.setString(1, password);
            ps.setString(2, name);
        }, rs -> {
            if (rs.next()) {
                return new User(rs.getString("nombre")
                        ,rs.getInt("id_rol"));
            } else {
                return null;
            }
        });
    }

    public List<Integer> getAllSells() {
        return jdbcTemplate.query(Query.GET_ALL_SELLS, rs ->{
            List<Integer> sells = new ArrayList<>();
            while (rs.next()) {
                sells.add(rs.getInt("id"));
           }
            return sells;
        });
    }

    public StatusDetail getSellStatus(Integer id) {
        return jdbcTemplate.query(Query.GET_SELL_STATUS, ps -> ps.setInt(1, id), rs -> {
            if (rs.next()) {
                Date date = new Date(rs.getTimestamp("fecha").getTime());
                return new StatusDetail(
                        rs.getString("nombre"),
                        date);
            }
            return null;
        });
    }

    public Boolean saveSellStatus(Integer id, Integer status) {
        return jdbcTemplate.update(Query.INSERT_SELL_STATUS,ps -> {
            ps.setInt(1, id);
            ps.setInt(2, status);
        }) ==1;
    }

    public List<Status> getStatus() {
        return jdbcTemplate.queryForList(Query.GET_ALL_STATUS,Status.class);
    }
}
