package com.vivitasol.projectbackend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner; 
import org.springframework.context.annotation.Bean; 
import org.springframework.security.crypto.password.PasswordEncoder;

import com.vivitasol.projectbackend.entities.Product;
import com.vivitasol.projectbackend.entities.User;
import com.vivitasol.projectbackend.repositories.ProductRepository;
import com.vivitasol.projectbackend.repositories.UserRepository; 
import com.vivitasol.projectbackend.entities.User.Role; 
import java.time.LocalDateTime;

@SpringBootApplication
public class ProjectbackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProjectbackendApplication.class, args);
    }
    
    // 🟢 BEAN FINAL: Crea el usuario Admin y Vendedor, hasheando la clave.
    @Bean
    public CommandLineRunner inicializarUsuarios(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            // 1. Verifica si la tabla de usuarios está vacía
            if (userRepository.count() == 0) {
                
                // --- 1. USUARIO ADMINISTRADOR ---
                User admin = new User();
                admin.setNombre("Super Administrador");
                admin.setEmail("admin@tienda.cl");
                admin.setPassword(passwordEncoder.encode("admin123")); // ⬅️ HASH CORRECTO
                admin.setRol(Role.SUPER_ADMIN);
                admin.setEstado(true);
                admin.setFechaCreacion(LocalDateTime.now());
                userRepository.save(admin);
                System.out.println("✅ USUARIO ADMIN CREADO (Clave: admin123)");
                
                // --- 2. USUARIO VENDEDOR ---
                User vendedor = new User();
                vendedor.setNombre("Vendedor");
                vendedor.setEmail("vendedor@tienda.cl");
                vendedor.setPassword(passwordEncoder.encode("admin123")); 
                vendedor.setRol(Role.VENDEDOR);
                vendedor.setEstado(true);
                vendedor.setFechaCreacion(LocalDateTime.now());
                
                userRepository.save(vendedor);
                System.out.println("✅ USUARIO VENDEDOR CREADO (Clave: admin123)");
            }
        };
    }
	// projectbackendCompleto/projectbackend/src/main/java/.../ProjectbackendApplication.java

// 🟢 BEAN FINAL: Inserta productos solo si la tabla está vacía
@Bean
public CommandLineRunner inicializarProductos(ProductRepository productRepository) {
	return args -> {
		// Verifica si la tabla de productos está vacía
		if (productRepository.count() == 0) {
			
			// --- INSERCIÓN DE PRODUCTOS (Asegurar los 15 productos y el stock crítico) ---
			
			// 1. Tecnología 
			Product p1 = new Product();
			p1.setNombre("Smartphone X1"); p1.setDescripcion("Teléfono de última generación.");
			p1.setPrecio(new java.math.BigDecimal("599900.00")); p1.setStock(10);
			p1.setImagenUrl("url/img/phone.jpg"); p1.setCategoria("Tecnología"); p1.setEstado(true);
			productRepository.save(p1);

			// 2. Laptop Pro (Stock Crítico)
			Product p2 = new Product();
			p2.setNombre("Laptop Pro"); p2.setDescripcion("Portátil ultradelgado.");
			p2.setPrecio(new java.math.BigDecimal("899000.00")); p2.setStock(3);
			p2.setImagenUrl("url/img/laptop.jpg"); p2.setCategoria("Tecnología"); p2.setEstado(true);
			productRepository.save(p2);
			
			// 3. Reloj Inteligente (Stock Crítico)
			Product p3 = new Product();
			p3.setNombre("Reloj Inteligente"); p3.setDescripcion("Con GPS y monitor cardíaco.");
			p3.setPrecio(new java.math.BigDecimal("120000.00")); p3.setStock(1);
			p3.setImagenUrl("url/img/smartwatch.jpg"); p3.setCategoria("Tecnología"); p3.setEstado(true);
			productRepository.save(p3);

			// 4. Aspiradora Inteligente
			Product p4 = new Product();
			p4.setNombre("Aspiradora Inteligente"); p4.setDescripcion("Limpieza automática con IA.");
			p4.setPrecio(new java.math.BigDecimal("150000.00")); p4.setStock(8);
			p4.setImagenUrl("url/img/aspiradora.jpg"); p4.setCategoria("Hogar"); p4.setEstado(true);
			productRepository.save(p4);

			// 5. Mesa de Centro Nórdica
			Product p5 = new Product();
			p5.setNombre("Mesa de Centro Nórdica"); p5.setDescripcion("Madera de pino.");
			p5.setPrecio(new java.math.BigDecimal("120000.00")); p5.setStock(7);
			p5.setImagenUrl("url/img/mesa.jpg"); p5.setCategoria("Hogar"); p5.setEstado(true);
			productRepository.save(p5);
			
			// 6. Bicicleta Montaña
			Product p6 = new Product();
			p6.setNombre("Bicicleta Montaña"); p6.setDescripcion("Marco de aluminio, 21 velocidades.");
			p6.setPrecio(new java.math.BigDecimal("320000.00")); p6.setStock(5);
			p6.setImagenUrl("url/img/bici.jpg"); p6.setCategoria("Deportes"); p6.setEstado(true);
			productRepository.save(p6);
			
			// 7. Mancuernas Ajustables (Stock Crítico)
			Product p7 = new Product();
			p7.setNombre("Mancuernas Ajustables"); p7.setDescripcion("Hasta 20kg por mancuerna.");
			p7.setPrecio(new java.math.BigDecimal("95000.00")); p7.setStock(3);
			p7.setImagenUrl("url/img/mancuernas.jpg"); p7.setCategoria("Deportes"); p7.setEstado(true);
			productRepository.save(p7);
			
			// 8. Esterilla Yoga PRO (Stock Crítico)
			Product p8 = new Product();
			p8.setNombre("Esterilla Yoga PRO"); p8.setDescripcion("Antideslizante y ecológica.");
			p8.setPrecio(new java.math.BigDecimal("15000.00")); p8.setStock(1);
			p8.setImagenUrl("url/img/mat.jpg"); p8.setCategoria("Deportes"); p8.setEstado(true);
			productRepository.save(p8);
			
			// 9. Cien Años de Soledad
			Product p9 = new Product();
			p9.setNombre("Cien Años de Soledad"); p9.setDescripcion("Edición de bolsillo.");
			p9.setPrecio(new java.math.BigDecimal("12000.00")); p9.setStock(15);
			p9.setImagenUrl("url/img/cien.jpg"); p9.setCategoria("Libros"); p9.setEstado(true);
			productRepository.save(p9);

			// 10. Guía de Spring Boot (Stock Crítico)
			Product p10 = new Product();
			p10.setNombre("Guía de Spring Boot"); p10.setDescripcion("Para desarrolladores FullStack.");
			p10.setPrecio(new java.math.BigDecimal("25000.00")); p10.setStock(4);
			p10.setImagenUrl("url/img/spring.jpg"); p10.setCategoria("Libros"); p10.setEstado(true);
			productRepository.save(p10);
			
			// 11. Set de Bloques Armables
			Product p11 = new Product();
			p11.setNombre("Set de Bloques Armables"); p11.setDescripcion("1000 piezas, compatible con otras marcas.");
			p11.setPrecio(new java.math.BigDecimal("45000.00")); p11.setStock(12);
			p11.setImagenUrl("url/img/bloques.jpg"); p11.setCategoria("Juguetes");
			p11.setEstado(true);
			productRepository.save(p11);

			// 12. Muñeco Articulado Deluxe (Stock Crítico)
			Product p12 = new Product();
			p12.setNombre("Muñeco Articulado Deluxe"); p12.setDescripcion("Edición limitada.");
			p12.setPrecio(new java.math.BigDecimal("55000.00")); p12.setStock(2);
			p12.setImagenUrl("url/img/muneco.jpg"); p12.setCategoria("Juguetes");
			p12.setEstado(true);
			productRepository.save(p12);

			// 13. Ficción Científica: El Origen
			Product p13 = new Product();
			p13.setNombre("Ficción Científica: El Origen"); p13.setDescripcion("Tapa dura.");
			p13.setPrecio(new java.math.BigDecimal("18000.00")); p13.setStock(11);
			p13.setImagenUrl("url/img/origen.jpg"); p13.setCategoria("Libros");
			p13.setEstado(true);
			productRepository.save(p13);
			
			// 14. Puzzle 1000 Piezas
			Product p14 = new Product();
			p14.setNombre("Puzzle 1000 Piezas"); p14.setDescripcion("Paisaje de montaña.");
			p14.setPrecio(new java.math.BigDecimal("10000.00")); p14.setStock(6);
			p14.setImagenUrl("url/img/puzzle.jpg"); p14.setCategoria("Juguetes");
			p14.setEstado(true);
			productRepository.save(p14);

			// 15. Auriculares BT Pro
			Product p15 = new Product();
			p15.setNombre("Auriculares BT Pro"); p15.setDescripcion("Para deporte y viajes.");
			p15.setPrecio(new java.math.BigDecimal("90000.00")); p15.setStock(9);
			p15.setImagenUrl("url/img/auris2.jpg"); p15.setCategoria("Tecnología");
			p15.setEstado(true);
			productRepository.save(p15);
			
			// 16. Difusor de Aromas (Stock Crítico)
			Product p16 = new Product();
			p16.setNombre("Difusor de Aromas"); p16.setDescripcion("Con luz LED y temporizador.");
			p16.setPrecio(new java.math.BigDecimal("20000.00")); p16.setStock(1);
			p16.setImagenUrl("url/img/difusor.jpg"); p16.setCategoria("Hogar");
			p16.setEstado(true);
			productRepository.save(p16);
			
			System.out.println("✅ 16 PRODUCTOS INSERTADOS AUTOMÁTICAMENTE");
		}
	};
}

}