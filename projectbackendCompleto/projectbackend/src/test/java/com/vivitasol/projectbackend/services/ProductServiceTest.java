package com.vivitasol.projectbackend.services; // Asegúrate de usar tu paquete correcto

import com.vivitasol.projectbackend.entities.Product;
import com.vivitasol.projectbackend.repositories.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository; // 1. Simula la capa de datos (la DB)

    @InjectMocks
    private ProductService productService; // 2. Inyecta los mocks en el servicio a probar

    // Se ejecuta antes de cada prueba para inicializar los mocks
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Prueba 1: Verifica la Creación del Producto (Requisito CRUD)
    @Test
    void testCreateProduct_ShouldReturnSavedProduct() {
        // Arrange (Configuración): Crea un producto de ejemplo
        Product product = new Product();
        product.setNombre("Teléfono Móvil");

        // Mocking (Simulación): Cuando se llame a productRepository.save(), retorna el mismo objeto
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act (Ejecución)
        Product createdProduct = productService.createProduct(product);

        // Assert (Verificación)
        assertNotNull(createdProduct);
        assertEquals("Teléfono Móvil", createdProduct.getNombre());
        // Verifica que el método save fue llamado exactamente una vez
        verify(productRepository, times(1)).save(product);
    }

    // Prueba 2: Inhabilitación de Producto (Soft Delete) (Requisito CRUD/Estado)
    @Test
    void testDeactivateProduct_ShouldSetEstadoToFalse() {
        // Arrange
        Product product = new Product();
        product.setId(1L);
        product.setEstado(true); // El producto está activo inicialmente

        // Mocking: findById retorna el producto, luego save lo guarda
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        boolean result = productService.deactivateProduct(1L);

        // Assert
        assertTrue(result); // La inhabilitación debe ser exitosa
        assertFalse(product.getEstado()); // El estado debe haber cambiado a inactivo (false)
        verify(productRepository, times(1)).save(product);
    }

    // Prueba 3: Encontrar Productos con Stock Crítico (Requisito de Rúbrica: Alerta de Stock)
    @Test
    void testFindCriticalStock_ShouldReturnProductsBelowLimit() {
        // Arrange
        Product p1 = new Product(); 
        p1.setStock(2); p1.setEstado(true);
        Product p2 = new Product(); 
        p2.setStock(4); p2.setEstado(true);
        
        List<Product> lowStockList = Arrays.asList(p1, p2);

        // Mocking: Simula el comportamiento del método de alerta del repositorio
        when(productRepository.findByStockLessThanAndEstadoTrue(5)).thenReturn(lowStockList);

        // Act
        List<Product> critical = productService.findCriticalStock(5);

        // Assert
        assertNotNull(critical);
        assertEquals(2, critical.size()); // Deben haber dos productos en la lista
        // Verifica que la función del repositorio fue llamada con el parámetro correcto
        verify(productRepository, times(1)).findByStockLessThanAndEstadoTrue(5);
    }
    
    // Prueba 4: Actualización de Producto (Verificación de Lógica)
    @Test
    void testUpdateProduct_ShouldUpdateDetailsExcludingId() {
        // Arrange
        Product existingProduct = new Product();
        existingProduct.setId(1L);
        existingProduct.setNombre("Antiguo Nombre");
        
        Product updatedDetails = new Product();
        updatedDetails.setNombre("Nuevo Nombre");
        updatedDetails.setEstado(false); // Cambia el estado

        // Mocking
        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(existingProduct);

        // Act
        Product result = productService.updateProduct(1L, updatedDetails);

        // Assert
        assertNotNull(result);
        assertEquals("Nuevo Nombre", result.getNombre()); // Verifica que el nombre fue actualizado
        assertFalse(result.getEstado()); // Verifica que el estado fue actualizado
        verify(productRepository, times(1)).save(existingProduct);
    }
}