package com.vivitasol.projectbackend.services;

import com.vivitasol.projectbackend.entities.User;
import com.vivitasol.projectbackend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository; // Simula la capa de datos

    @Mock
    private PasswordEncoder passwordEncoder; // Simula la encriptación

    @InjectMocks
    private UserService userService; // Inyecta los mocks en el servicio que queremos probar

    // Configuración antes de cada prueba
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // Prueba 1: Verifica la Creación con Encriptación (Requisito de Rúbrica)
    @Test
    void testCreateUser_ShouldEncryptPasswordAndSave() {
        // 1. Configuración (Arrange)
        User user = new User();
        user.setEmail("test@test.com");
        user.setPassword("rawPassword");
        user.setRol(User.Role.CLIENTE);

        // 2. Mocking (Simulación)
        // Cuando se llame a passwordEncoder.encode(), retorna un hash simulado
        when(passwordEncoder.encode("rawPassword")).thenReturn("hashedPassword");
        // Cuando se llame a userRepository.save(), retorna el usuario guardado
        when(userRepository.save(any(User.class))).thenReturn(user);

        // 3. Ejecución (Act)
        User createdUser = userService.createUser(user);

        // 4. Verificación (Assert)
        // Asegura que la contraseña está encriptada
        assertEquals("hashedPassword", createdUser.getPassword()); 
        // Verifica que el método save fue llamado una vez
        verify(userRepository, times(1)).save(user); 
    }

    // Prueba 2: Inhabilitación de Usuario (Requisito CRUD/Estado)
    @Test
    void testDeactivateUser_ShouldSetEstadoToFalse() {
        // 1. Configuración
        User user = new User();
        user.setId(1L);
        user.setEstado(true); // Está activo inicialmente

        // 2. Mocking
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(any(User.class))).thenReturn(user);

        // 3. Ejecución
        boolean result = userService.deactivateUser(1L);

        // 4. Verificación
        assertTrue(result); // Debe retornar true
        assertFalse(user.getEstado()); // El estado debe ser falso (inactivo)
        verify(userRepository, times(1)).save(user); 
    }
    
    // Prueba 3: Encontrar usuario por ID (Requisito de Lógica de Lectura)
    @Test
    void testFindById_UserExists() {
        // 1. Configuración
        User user = new User();
        user.setId(2L);

        // 2. Mocking
        when(userRepository.findById(2L)).thenReturn(Optional.of(user));

        // 3. Ejecución
        Optional<User> foundUser = userService.findById(2L);

        // 4. Verificación
        assertTrue(foundUser.isPresent()); 
        assertEquals(2L, foundUser.get().getId());
    }
}