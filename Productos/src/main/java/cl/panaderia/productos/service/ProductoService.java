package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.ProductFilter;
import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.dto.ServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ServiceDto productoDto;

    public boolean insertProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getIdCategoria() == 0 || producto.getDescripcion() == null || producto.getPrecio() == 0 || producto.getImagenUrl() == null|| producto.getStock() < 0) {
            return false;
        }
        producto.setSku(generateSKU(producto));
        return productoDto.insertProducto(producto);
    }

    public List<Producto> getAllProductos() {
        return productoDto.getAllProductos();
    }

    public Producto getProductoByName(String nombre) {
        return productoDto.getProductoByName(nombre);
    }

    public List<Producto> getAllProductosPriceGreaterThan(Integer precio) {
        return productoDto.getAllProductsWithGreaterPrice(precio);
    }

    public List<Producto> getAllProductosWithLessPrice(Integer precio) {
        return productoDto.getAllProductsWithLessPrice(precio);
    }

    public List<Producto> getAllProductosWithPriceBetween(Integer min, Integer max) {
        return productoDto.getAllProductsWithPriceBetween(min, max);
    }


    public boolean deleteProducto(Integer id) {
        return productoDto.deleteProducto(id);
    }

    public Producto getProductoById(Integer id) {
        return productoDto.getProductoById(id);
    }


    public Boolean updateProducto(Producto producto) {
        return productoDto.updateProducto(producto);
    }


    private String generateSKU(Producto producto) {
        return producto.getNombre().substring(0, 3) + Instant.now().toEpochMilli();
    }

    public Integer getValueById(Integer id) {
        return productoDto.getValueById(id);
    }

    public boolean validateStock(Integer id, Integer quantity) {
        return productoDto.validateStock(id, quantity);

    }

    public void updateStock(Integer id, Integer quantity) {
            productoDto.updateStock(id, quantity);
    }

    public List<Producto> getProductoByFilter(ProductFilter filter) {
        List<Producto> productos = productoDto.getAllProductos();
        return productos.stream()
                .filter(producto -> {
                    boolean matches = true;

                    if (filter.getNombre() != null) {
                        matches = matches && producto.getNombre().toLowerCase().contains(filter.getNombre().toLowerCase());
                    }
                    if (filter.getIdCategoria() != null) {
                        matches = matches && producto.getIdCategoria().equals(filter.getIdCategoria());
                    }
                    if (filter.getPrecio() != null) {
                        matches = matches && producto.getPrecio().equals(filter.getPrecio());
                    }
                    if (filter.getMinPrecio() != null) {
                        matches = matches && producto.getPrecio() >= filter.getMinPrecio();
                    }
                    if (filter.getMaxPrecio() != null) {
                        matches = matches && producto.getPrecio() <= filter.getMaxPrecio();
                    }

                    return matches;
                })
                .toList();
    }
}
