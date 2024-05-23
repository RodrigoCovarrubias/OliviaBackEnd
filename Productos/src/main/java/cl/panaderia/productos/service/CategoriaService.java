package cl.panaderia.productos.service;

import cl.panaderia.productos.dominio.Categoria;
import cl.panaderia.productos.dominio.Producto;
import cl.panaderia.productos.dto.ServiceDao;
import cl.panaderia.productos.rest.CrearCategoriaRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    @Autowired
    private ServiceDao categoriaDto;

    public boolean createCategoria(CrearCategoriaRequest categoriaRequest) {
        return categoriaDto.createCategoria(categoriaRequest.nombre());
    }

    public List<Categoria> getAllCategoria() {
        return categoriaDto.getAllCategoria();
    }

    public List<Producto> getAllProductosFromCategoria(Integer idCategoria) {
        return categoriaDto.getAllProductosFromCategoria(idCategoria);
    }


}
