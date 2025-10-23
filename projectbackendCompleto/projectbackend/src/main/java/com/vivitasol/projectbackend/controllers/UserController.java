package com.vivitasol.projectbackend.controllers; // Paquete ajustado

import com.vivitasol.projectbackend.entities.User;
import com.vivitasol.projectbackend.services.UserService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    // CRUD: Crear Usuario
    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        // Validación: Asegurar que la contraseña no sea nula o vacía antes de guardar
        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    // CRUD: Leer todos los usuarios
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return new ResponseEntity<>(userService.findAll(), HttpStatus.OK);
    }

    // CRUD: Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @Valid @RequestBody User user) {
        User updatedUser = userService.updateUser(id, user);
        if (updatedUser != null) {
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // CRUD: Eliminar/Inhabilitar (Soft Delete)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deactivateUser(@PathVariable Long id) {
        if (userService.deactivateUser(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 OK
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}