package com.vivitasol.projectbackend.services;

import com.vivitasol.projectbackend.entities.User;
import com.vivitasol.projectbackend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder; 

    // --- NUEVO MÉTODO AÑADIDO ---
    // Requerido por el AutenticadorController para obtener el objeto User completo
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    // ----------------------------

    // Crear/Guardar Usuario (Encriptación de Contraseña)
    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    // Leer todos los usuarios
    public List<User> findAll() {
        return userRepository.findAll();
    }

    // Leer usuario por ID
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }
    
    // Actualizar usuario
    public User updateUser(Long id, User updatedUser) {
        return userRepository.findById(id).map(user -> {
            user.setNombre(updatedUser.getNombre());
            user.setEmail(updatedUser.getEmail());
            user.setRol(updatedUser.getRol());
            user.setEstado(updatedUser.getEstado());
            return userRepository.save(user);
        }).orElse(null);
    }

    // Eliminar/Inhabilitar (Soft Delete)
    public boolean deactivateUser(Long id) {
        return userRepository.findById(id).map(user -> {
            user.setEstado(false); // Cambiar estado a inactivo
            userRepository.save(user);
            return true;
        }).orElse(false);
    }
}