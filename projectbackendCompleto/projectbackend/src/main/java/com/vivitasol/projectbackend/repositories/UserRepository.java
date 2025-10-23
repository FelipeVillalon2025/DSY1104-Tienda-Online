package com.vivitasol.projectbackend.repositories;

import com.vivitasol.projectbackend.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

// Proporciona todos los métodos CRUD básicos de JPA
public interface UserRepository extends JpaRepository<User, Long> {
    // Necesario para la autenticación y validación de usuarios
    User findByEmail(String email);

    // Método para el control de inhabilitación (Soft Delete)
    Iterable<User> findByEstado(Boolean estado);
}