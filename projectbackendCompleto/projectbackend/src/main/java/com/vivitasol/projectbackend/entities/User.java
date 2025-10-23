package com.vivitasol.projectbackend.entities; // ¡ESTE ES TU PAQUETE REAL!

import java.time.LocalDateTime;
import jakarta.persistence.*; // Usa jakarta para Spring Boot moderno
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.core.GrantedAuthority; // Necesario para Spring Security (Autenticación)
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
public class User implements UserDetails { // Implementamos UserDetails para autenticación

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String nombre;

    @Email(message = "El email debe ser válido")
    @Column(unique = true, nullable = false, length = 255)
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    @Column(nullable = false, length = 255)
    private String password;

    // Rol: cliente, vendedor, super-admin
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role rol; 

    // Estado: activo, inactivo
    @Column(nullable = false)
    private Boolean estado = true; // Por defecto activo

    @Column(nullable = false)
    private LocalDateTime fechaCreacion; // Fecha de creación (sistema)

    // Enumeración para el campo 'rol'
    public enum Role {
        CLIENTE,
        VENDEDOR,
        SUPER_ADMIN
    }
    
    // Generación de la fecha antes de guardar
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
    }
    
    // --- MÉTODOS DE USERDETAILS (REQUERIDOS PARA LOGIN) ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Retorna el rol como una autoridad (e.g., ROLE_SUPER_ADMIN)
        return List.of(new SimpleGrantedAuthority("ROLE_" + rol.name()));
    }

    @Override
    public String getUsername() {
        return email; // Usamos el email como nombre de usuario para el login
    }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() {
        return estado; // La cuenta está habilitada si el estado es 'true'
    }

    // --- Getters y Setters --- (DEBES GENERARLOS EN TU IDE o añadirlos aquí)
    
    // ... (El código de Getters y Setters debe ir aquí)

    @Override
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRol() {
        return rol;
    }

    public void setRol(Role rol) {
        this.rol = rol;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }
}