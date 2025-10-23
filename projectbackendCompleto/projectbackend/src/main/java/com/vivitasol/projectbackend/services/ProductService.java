package com.vivitasol.projectbackend.services; // Paquete ajustado

import com.vivitasol.projectbackend.entities.Product;
import com.vivitasol.projectbackend.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // CRUD: Crear/Guardar Producto
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // CRUD: Leer todos los productos
    public List<Product> findAll() {
        return productRepository.findAll();
    }
    
    // Alerta de Stock: Encontrar productos con stock crítico (Rúbrica) [cite: 444]
    public List<Product> findCriticalStock(Integer limit) {
        return productRepository.findByStockLessThanAndEstadoTrue(limit);
    }

    // CRUD: Actualizar Producto
    public Product updateProduct(Long id, Product updatedProduct) {
        return productRepository.findById(id).map(product -> {
            product.setNombre(updatedProduct.getNombre());
            product.setDescripcion(updatedProduct.getDescripcion());
            product.setPrecio(updatedProduct.getPrecio());
            product.setStock(updatedProduct.getStock());
            product.setCategoria(updatedProduct.getCategoria());
            product.setImagenUrl(updatedProduct.getImagenUrl());
            product.setEstado(updatedProduct.getEstado());
            return productRepository.save(product);
        }).orElse(null);
    }

    // CRUD: Eliminar/Inhabilitar (Soft Delete) [cite: 401]
    public boolean deactivateProduct(Long id) {
        return productRepository.findById(id).map(product -> {
            product.setEstado(false); 
            productRepository.save(product);
            return true;
        }).orElse(false);
    }
}