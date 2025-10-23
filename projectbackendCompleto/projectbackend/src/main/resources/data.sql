-- -----------------------------------------------------------------------------
-- SCRIPT SQL DE POBLAMIENTO INICIAL (SEED DATA)
-- -----------------------------------------------------------------------------

-- #################################
-- 1. USUARIOS
-- #################################

-- Administrador (admin@tienda.cl)

-- #################################
-- 2. PRODUCTOS Y CATEGORÍAS
-- (Mantengo el resto del código que enviaste, el cual está correcto)
-- #################################

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Smartphone X1', 'Teléfono de última generación.', 599900.00, 10, 'url/img/phone.jpg', 'Tecnología', TRUE, NOW());

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Laptop Pro', 'Portátil ultradelgado.', 899000.00, 3, 'url/img/laptop.jpg', 'Tecnología', TRUE, NOW()); -- Stock Crítico (3)

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Reloj Inteligente', 'Con GPS y monitor cardíaco.', 120000.00, 1, 'url/img/smartwatch.jpg', 'Tecnología', TRUE, NOW()); -- Stock Crítico (1)

-- 2. Hogar
INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Aspiradora Inteligente', 'Limpieza automática con IA.', 150000.00, 8, 'url/img/aspiradora.jpg', 'Hogar', TRUE, NOW());

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Mesa de Centro Nórdica', 'Madera de pino.', 120000.00, 7, 'url/img/mesa.jpg', 'Hogar', TRUE, NOW());

-- 3. Deportes
INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Bicicleta Montaña', 'Marco de aluminio, 21 velocidades.', 320000.00, 5, 'url/img/bici.jpg', 'Deportes', TRUE, NOW());

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Mancuernas Ajustables', 'Hasta 20kg por mancuerna.', 95000.00, 3, 'url/img/mancuernas.jpg', 'Deportes', TRUE, NOW()); -- Stock Crítico (3)

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Esterilla Yoga PRO', 'Antideslizante y ecológica.', 15000.00, 1, 'url/img/mat.jpg', 'Deportes', TRUE, NOW()); -- Stock Crítico (1)

-- 4. Libros
INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Cien Años de Soledad', 'Edición de bolsillo.', 12000.00, 15, 'url/img/cien.jpg', 'Libros', TRUE, NOW());

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Guía de Spring Boot', 'Para desarrolladores FullStack.', 25000.00, 4, 'url/img/spring.jpg', 'Libros', TRUE, NOW()); -- Stock Crítico (4)

-- 5. Juguetes
INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Set de Bloques Armables', '1000 piezas, compatible con otras marcas.', 45000.00, 12, 'url/img/bloques.jpg', 'Juguetes', TRUE, NOW());

-- Productos Adicionales (Total: 15)
INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Muñeco Articulado Deluxe', 'Edición limitada.', 55000.00, 2, 'url/img/muneco.jpg', 'Juguetes', TRUE, NOW()); -- Stock Crítico (2)

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Ficción Científica: El Origen', 'Tapa dura.', 18000.00, 11, 'url/img/origen.jpg', 'Libros', TRUE, NOW());

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Puzzle 1000 Piezas', 'Paisaje de montaña.', 10000.00, 6, 'url/img/puzzle.jpg', 'Juguetes', TRUE, NOW());

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Auriculares BT Pro', 'Para deporte y viajes.', 90000.00, 9, 'url/img/auris2.jpg', 'Tecnología', TRUE, NOW());

INSERT INTO products (nombre, descripcion, precio, stock, imagen_url, categoria, estado, fecha_creacion)
VALUES ('Difusor de Aromas', 'Con luz LED y temporizador.', 20000.00, 1, 'url/img/difusor.jpg', 'Hogar', TRUE, NOW()); -- Stock Crítico (1)