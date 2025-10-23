package com.vivitasol.projectbackend.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
// ... otras importaciones
import jakarta.validation.constraints.NotNull;
// 🟢 Añade las anotaciones de Lombok aquí:
import lombok.Getter; 
import lombok.Setter; 
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "products")
@Getter // 🟢 Genera automáticamente todos los métodos get...()
@Setter // 🟢 Genera automáticamente todos los métodos set...()
@NoArgsConstructor // 🟢 Constructor sin argumentos (necesario para JPA)
@AllArgsConstructor // 🟢 Constructor con todos los argumentos
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @NotBlank(message = "La descripción es obligatoria")
    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.01", message = "El precio debe ser positivo")
    private BigDecimal precio;

    @Min(value = 0, message = "El stock no puede ser negativo")
    private Integer stock; // Control de stock [cite: 404]

    @NotBlank(message = "La URL de la imagen es obligatoria")
    private String imagenUrl; // Imagen (URL) [cite: 401]

    // Relación con Categoría (asumimos que la categoría es una entidad separada)
    // Para simplificar, aquí usaremos solo el nombre de la categoría o un ID simple.
    // Si tienes una entidad Category, usa @ManyToOne.
    @NotBlank(message = "La categoría es obligatoria")
    private String categoria; // Categoría [cite: 401]

    @Column(nullable = false)
    private Boolean estado = true; // Estado (activo, inactivo) [cite: 401]

    @Column(nullable = false)
    private LocalDateTime fechaCreacion; // Fecha de creación (sistema) [cite: 402]

    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }

    // --- Getters y Setters --- 
    // Debes generarlos en tu IDE (Source Action > Generate Getters and Setters)
}