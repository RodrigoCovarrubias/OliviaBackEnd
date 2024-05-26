package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.dto.ServiceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    @Autowired
    private ServiceDto productoDto;

    public boolean insertProducto(Producto producto) {
        if (producto.getNombre() == null || producto.getIdCategoria() == 0 || producto.getDescripcion() == null || producto.getPrecio() == 0 || producto.getImagenUrl() == null|| producto.getStock() == 0) {
            return false;
        }
        return productoDto.insertProducto(producto.getNombre(), producto.getIdCategoria(), producto.getDescripcion(), producto.getPrecio(), producto.getImagenUrl(), producto.getStock());
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

    public Boolean updateProductoStock(Integer idVenta, List<Producto> productos) {

       return null;
    }

    public Boolean updateProducto(Producto producto) {
        return productoDto.updateProducto(producto);
    }


}
