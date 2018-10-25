CREATE TABLE `establecimiento` (
  `id` bigint(20) AUTO_INCREMENT NOT NULL,
  `nombre` varchar(255) NOT NULL,
  `direccion` varchar(255) DEFAULT NULL,
  `longitud` double NOT NULL,
  `latitud` double NOT NULL,
  `hora_inicio` time NOT NULL DEFAULT '12:00:00',
  `hora_cierre` time NOT NULL DEFAULT '03:00:00',
  `lunes` bit(1) NOT NULL DEFAULT 1,
  `martes` bit(1) NOT NULL DEFAULT 1,
  `miercoles` bit(1) NOT NULL DEFAULT 1,
  `jueves` bit(1) NOT NULL DEFAULT 1,
  `viernes` bit(1) NOT NULL DEFAULT 1,
  `sabado` bit(1) NOT NULL DEFAULT 1,
  `domingo` bit(1) NOT NULL DEFAULT 1,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UKrk31lb3hy2f4g7x5jt5kchuny` (`nombre`,`longitud`,`latitud`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `genero_musical` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(255) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `establecimiento_generos` (
  `establecimiento_id` bigint(20) NOT NULL,
  `generos_id` bigint(20) NOT NULL,
  KEY `FKmb89y3uyn5ip5dp90lh1mravb` (`generos_id`),
  KEY `FKsm6lxtjeveijy4t7bmvmgc1ou` (`establecimiento_id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1

CREATE TABLE `hibernate_sequence` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1