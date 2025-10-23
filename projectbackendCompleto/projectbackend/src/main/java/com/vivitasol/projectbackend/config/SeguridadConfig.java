package com.vivitasol.projectbackend.config;

import com.vivitasol.projectbackend.entities.User; 
import com.vivitasol.projectbackend.services.UserService; 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

@Configuration
@EnableWebSecurity
public class SeguridadConfig { 

    // 1. Bean para el encriptador de contraseñas
    @Bean
    public PasswordEncoder codificadorContrasena() { 
        return new BCryptPasswordEncoder();
    }

    // 2. Servicio de Detalles de Usuario (Define cómo Spring encuentra al usuario)
    @Bean
    public UserDetailsService servicioDetallesUsuario(UserService servicioUsuario) { 
        return email -> {
            User usuario = servicioUsuario.findByEmail(email);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);
            }
            return usuario; // Retorna el objeto User, que actúa como UserDetails
        };
    }
    
    // 3. Proveedor de Autenticación (Define cómo se verifica el hash BCrypt)
    @Bean
    public DaoAuthenticationProvider proveedorAutenticacion(UserDetailsService servicioDetallesUsuario, PasswordEncoder codificadorContrasena) { 
        DaoAuthenticationProvider proveedor = new DaoAuthenticationProvider();
        proveedor.setUserDetailsService(servicioDetallesUsuario);
        proveedor.setPasswordEncoder(codificadorContrasena); 
        return proveedor;
    }

    // 4. Gestor de Autenticación (Requerido por el AutenticadorController)
    @Bean
    public AuthenticationManager gestorAutenticacion(AuthenticationConfiguration configuracion) throws Exception {
        return configuracion.getAuthenticationManager();
    }

    // 5. Configuración CORS (Usaremos este bean para la configuración automática de HttpSecurity)
    @Bean
    public CorsFilter filtroCors() { 
        UrlBasedCorsConfigurationSource fuente = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuracionCors = new CorsConfiguration();
        configuracionCors.setAllowCredentials(true);
        configuracionCors.addAllowedOrigin("http://localhost:5173"); 
        configuracionCors.addAllowedHeader("*");
        configuracionCors.addAllowedMethod("*");
        fuente.registerCorsConfiguration("/**", configuracionCors);
        return new CorsFilter(fuente);
    }

    // 6. Configuración de Reglas de Acceso (Permisos)
    @Bean
    public SecurityFilterChain cadenaFiltroSeguridad(HttpSecurity http, DaoAuthenticationProvider proveedorAutenticacion) throws Exception { 
        http
            // 🟢 CORRECCIÓN CLAVE 1: Configuración de CORS usando el método estándar de HttpSecurity
            .cors(cors -> cors.configurationSource(request -> {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(java.util.Collections.singletonList("http://localhost:5173"));
                config.setAllowedMethods(java.util.Collections.singletonList("*"));
                config.setAllowedHeaders(java.util.Collections.singletonList("*"));
                config.setAllowCredentials(true);
                return config;
            }))
            .csrf(AbstractHttpConfigurer::disable) 
            .sessionManagement(sesion -> sesion.sessionCreationPolicy(STATELESS))
            
            // 🟢 CORRECCIÓN CLAVE 2: Inyección del proveedor para la autenticación BCrypt
            .authenticationProvider(proveedorAutenticacion) 
            
            .authorizeHttpRequests(auth -> auth
                // 🟢 CORRECCIÓN: Permitir acceso a TODOS los endpoints de lectura del Dashboard
                .requestMatchers(
                    "/api/auth/login", 
                    "/api/productos", 
                    "/api/usuarios", 
                    "/api/productos/stock-critico"
                ).permitAll() 
                
                // Acceso restringido (solo CRUD: POST, PUT, DELETE)
                .requestMatchers("/api/productos/**").hasRole("SUPER_ADMIN")
                .requestMatchers("/api/usuarios/**").hasRole("SUPER_ADMIN")
                .anyRequest().authenticated()
            );

        return http.build();
    }
}