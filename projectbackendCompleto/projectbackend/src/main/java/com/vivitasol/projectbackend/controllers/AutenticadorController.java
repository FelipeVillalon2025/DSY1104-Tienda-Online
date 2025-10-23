package com.vivitasol.projectbackend.controllers; // Ajusta tu paquete

import com.vivitasol.projectbackend.entities.User;
import com.vivitasol.projectbackend.entities.User.Role;
import com.vivitasol.projectbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AutenticadorController { // Nombre de la clase en español

    @Autowired
    private AuthenticationManager authenticationManager; 
    
    @Autowired
    private UserService servicioUsuario; // Nombre de variable en español

    // 🟢 DTO CORREGIDO: Usamos 'password' para consistencia con Spring Security
    public static class CredencialesLogin {
        public String email;
        public String password; // ⬅️ CAMBIADO DE 'clave' A 'password'
    }

    // Endpoint de Login (Requisito de la rúbrica)
    @PostMapping("/login")
    public ResponseEntity<?> autenticarUsuario(@RequestBody CredencialesLogin credenciales) {
        try {
            // 1. Autenticar usando el email y el password
            Authentication authentication = authenticationManager.authenticate(
                // 🟢 CORRECCIÓN: Ahora usa credenciales.password
                new UsernamePasswordAuthenticationToken(credenciales.email, credenciales.password) 
            );

            // 2. Establecer el contexto de seguridad
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // 3. Obtener los detalles del usuario autenticado
            UserDetails detallesUsuario = (UserDetails) authentication.getPrincipal();
            User usuario = servicioUsuario.findByEmail(detallesUsuario.getUsername());

            // 4. Validación de Rol de Administrador (Requisito de la rúbrica)
            if (usuario == null || usuario.getRol() != Role.SUPER_ADMIN || !usuario.getEstado()) {
                 // Si no es admin o está inactivo
                 return new ResponseEntity<>("Acceso denegado. Se requiere rol de administrador.", HttpStatus.UNAUTHORIZED);
            }
            
            // 5. Retornar la información del usuario (sin JWT)
            return new ResponseEntity<>(usuario, HttpStatus.OK);

        } catch (Exception e) {
            // Manejo de credenciales inválidas
            return new ResponseEntity<>("Credenciales inválidas. Por favor, verifica tu email y contraseña.", HttpStatus.UNAUTHORIZED);
        }
    }
}