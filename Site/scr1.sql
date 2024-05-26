DROP TABLE IF EXISTS `commande`;
CREATE TABLE IF NOT EXISTS `commande` (
  `id_commande` int NOT NULL,
  `adresse_depart` varchar(100) NOT NULL,
  `adresse_arrivee` varchar(100) NOT NULL,
  `temps_depart` datetime NOT NULL,
  `temps_arrivee` datetime NOT NULL,
  `distance` float NOT NULL,
  `prix` float NOT NULL,
  `id_client` int NOT NULL,
  `id_livreur` int NOT NULL,
  PRIMARY KEY (`id_commande`),
  KEY `foreign-key` (`id_client`),
  KEY `foreign-key2` (`id_livreur`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;