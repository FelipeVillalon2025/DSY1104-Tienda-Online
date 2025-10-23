package com.vivitasol.projectbackend.repositories; // Paquete ajustado

import com.vivitasol.projectbackend.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    // Para la búsqueda/filtrado: Búsqueda por nombre (o parte del nombre)
    List<Product> findByNombreContainingIgnoreCase(String nombre); 

    // Para la búsqueda/filtrado: Búsqueda por categoría
    List<Product> findByCategoriaIgnoreCase(String categoria);
    
    // Para la alerta de stock: Encontrar productos con stock bajo (ej. < 5)
    List<Product> findByStockLessThanAndEstadoTrue(Integer stock);
}