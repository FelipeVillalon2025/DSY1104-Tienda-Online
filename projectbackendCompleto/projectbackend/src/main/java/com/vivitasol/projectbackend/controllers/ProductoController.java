package com.vivitasol.projectbackend.controllers; // Ajusta tu paquete

import com.vivitasol.projectbackend.entities.Product; // Usamos Product, que ya definimos
import com.vivitasol.projectbackend.services.ProductService; // Usamos ProductService
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;

@RestController
@RequestMapping("/api/productos") // Endpoint en español
public class ProductoController {

    @Autowired
    private ProductService servicioProducto; // Nombre de variable en español

    // CRUD: Crear Producto
    @PostMapping
    public ResponseEntity<Product> crearProducto(@Valid @RequestBody Product producto) {
        Product productoCreado = servicioProducto.createProduct(producto);
        return new ResponseEntity<>(productoCreado, HttpStatus.CREATED);
    }

    // CRUD: Leer todos los productos
    @GetMapping
    public ResponseEntity<List<Product>> obtenerTodosProductos() {
        return new ResponseEntity<>(servicioProducto.findAll(), HttpStatus.OK);
    }
    
    // Alerta de Stock: Endpoint para dashboard administrativo (Requisito de la rúbrica)
    @GetMapping("/stock-critico") // Endpoint en español
    public ResponseEntity<List<Product>> obtenerStockCritico(@RequestParam(defaultValue = "5") int limite) {
        // Busca productos activos con stock menor al límite
        List<Product> stockCritico = servicioProducto.findCriticalStock(limite);
        return new ResponseEntity<>(stockCritico, HttpStatus.OK);
    }

    // CRUD: Actualizar Producto
    @PutMapping("/{id}")
    public ResponseEntity<Product> actualizarProducto(@PathVariable Long id, @Valid @RequestBody Product detallesProducto) {
        Product productoActualizado = servicioProducto.updateProduct(id, detallesProducto);
        if (productoActualizado != null) {
            return new ResponseEntity<>(productoActualizado, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // CRUD: Eliminar/Inhabilitar (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> inhabilitarProducto(@PathVariable Long id) { // Método en español
        if (servicioProducto.deactivateProduct(id)) { // Método del servicio en inglés, pero su uso es en español
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 OK
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}