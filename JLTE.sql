-- --------------------------------------------------------
-- Host:                         localhost
-- Versión del servidor:         5.7.24 - MySQL Community Server (GPL)
-- SO del servidor:              Win64
-- HeidiSQL Versión:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

-- Volcando estructura para tabla javasoporte.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `documento` varchar(20) NOT NULL,
  `nombresApellidos` varchar(100) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `cel` varchar(20) DEFAULT NULL,
  `estado` enum('Activo','Inactivo','Eliminado') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.clientes: ~2 rows (aproximadamente)


-- Volcando estructura para tabla javasoporte.marca
CREATE TABLE IF NOT EXISTS `marca` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marca` varchar(50) NOT NULL,
  `estado` enum('Activo','Inactivo','Eliminado') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `marca` (`marca`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.marca: ~2 rows (aproximadamente)


-- Volcando estructura para tabla javasoporte.modelo
CREATE TABLE IF NOT EXISTS `modelo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marca_id` int(11) NOT NULL,
  `modelo` varchar(50) NOT NULL,
  `estado` enum('Activo','Inactivo','Eliminado') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `modelo` (`modelo`),
  KEY `fk_marca_modelo` (`marca_id`),
  CONSTRAINT `fk_marca_modelo` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.modelo: ~6 rows (aproximadamente)


-- Volcando estructura para tabla javasoporte.rol
CREATE TABLE IF NOT EXISTS `rol` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `nombre` (`nombre`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.rol: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `rol` DISABLE KEYS */;
INSERT INTO `rol` (`id`, `nombre`) VALUES
	(1, 'ADMINISTRADOR'),
	(4, 'CLIENTE'),
	(2, 'GERENTE'),
	(3, 'TECNICO');
/*!40000 ALTER TABLE `rol` ENABLE KEYS */;

-- Volcando estructura para tabla javasoporte.serie
CREATE TABLE IF NOT EXISTS `serie` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `modelo_id` int(11) NOT NULL,
  `serie` varchar(50) NOT NULL,
  `estado` enum('Activo','Inactivo','Eliminado') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `serie` (`serie`),
  KEY `FK_modelo_serie` (`modelo_id`),
  CONSTRAINT `FK_modelo_serie` FOREIGN KEY (`modelo_id`) REFERENCES `modelo` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.serie: ~2 rows (aproximadamente)


-- Volcando estructura para tabla javasoporte.servicio
CREATE TABLE IF NOT EXISTS `servicio` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `servicio` varchar(250) NOT NULL,
  `precio` decimal(11,2) NOT NULL DEFAULT '0.00',
  `stock` int(11) NOT NULL DEFAULT '0',
  `type` enum('Servicio','Repuesto') NOT NULL DEFAULT 'Servicio',
  `estado` enum('Activo','Inactivo','Eliminado') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.servicio: ~2 rows (aproximadamente)


-- Volcando estructura para tabla javasoporte.usuario
CREATE TABLE IF NOT EXISTS `usuario` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `rol_id` int(11) NOT NULL,
  `nombres` varchar(50) NOT NULL,
  `apellidos` varchar(50) NOT NULL,
  `email` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `estado` enum('Activo','Inactivo','Eliminado') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_rol` (`rol_id`),
  CONSTRAINT `fk_rol` FOREIGN KEY (`rol_id`) REFERENCES `rol` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.usuario: ~5 rows (aproximadamente)
-- Volcando estructura para tabla javasoporte.mantenimiento
CREATE TABLE IF NOT EXISTS `mantenimiento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `cliente_id` int(11) NOT NULL,
  `usuario_id` int(11) NOT NULL,
  `equipo` enum('Laptop','Pc','Otros') NOT NULL,
  `marca_id` int(11) NOT NULL,
  `modelo_id` int(11) NOT NULL,
  `serie_id` int(11) NOT NULL,
  `diagnostico` enum('Si','No') NOT NULL,
  `falla` text,
  `solucion` text,
  `descuento` decimal(11,2) unsigned NOT NULL DEFAULT '0.00',
  `total` decimal(11,2) unsigned NOT NULL,
  `fecha` datetime DEFAULT CURRENT_TIMESTAMP,
  `f_entrega` datetime DEFAULT NULL,
  `estado` enum('Taller','Almacen','Entregado','Cancelar') NOT NULL DEFAULT 'Taller',
  PRIMARY KEY (`id`),
  KEY `FK_cliente` (`cliente_id`),
  KEY `FK_usuario` (`usuario_id`),
  KEY `FK_modelo` (`modelo_id`),
  KEY `FK_marca` (`marca_id`),
  KEY `FK_serie` (`serie_id`),
  CONSTRAINT `FK_cliente` FOREIGN KEY (`cliente_id`) REFERENCES `clientes` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_marca` FOREIGN KEY (`marca_id`) REFERENCES `marca` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_modelo` FOREIGN KEY (`modelo_id`) REFERENCES `modelo` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_serie` FOREIGN KEY (`serie_id`) REFERENCES `serie` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_usuario` FOREIGN KEY (`usuario_id`) REFERENCES `usuario` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.mantenimiento: ~10 rows (aproximadamente)
/*!40000 ALTER TABLE `mantenimiento` DISABLE KEYS */;
/*!40000 ALTER TABLE `mantenimiento` ENABLE KEYS */;
-- Volcando estructura para tabla javasoporte.dmantenimiento
CREATE TABLE IF NOT EXISTS `dmantenimiento` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `mante_id` int(11) NOT NULL,
  `servicio_id` int(11) NOT NULL,
  `precio` decimal(11,2) unsigned NOT NULL,
  `cantidad` int(11) NOT NULL,
  `total` decimal(11,2) unsigned NOT NULL,
  `estado` enum('Activo','Inactivo') NOT NULL DEFAULT 'Activo',
  PRIMARY KEY (`id`),
  KEY `FK_manteminiento` (`mante_id`),
  KEY `FK_servicio` (`servicio_id`),
  CONSTRAINT `FK_manteminiento` FOREIGN KEY (`mante_id`) REFERENCES `mantenimiento` (`id`) ON UPDATE CASCADE,
  CONSTRAINT `FK_servicio` FOREIGN KEY (`servicio_id`) REFERENCES `servicio` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4;

-- Volcando datos para la tabla javasoporte.dmantenimiento: ~4 rows (aproximadamente)
/*!40000 ALTER TABLE `dmantenimiento` DISABLE KEYS */;

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
