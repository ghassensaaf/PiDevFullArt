-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1:3306
-- Généré le : lun. 01 mars 2021 à 12:03
-- Version du serveur :  8.0.21
-- Version de PHP : 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `fullart`
--

-- --------------------------------------------------------

--
-- Structure de la table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id_admin` int NOT NULL AUTO_INCREMENT,
  `login` varchar(128) NOT NULL,
  `pwd` varchar(128) NOT NULL,
  `nom` varchar(128) NOT NULL,
  `prenom` varchar(128) NOT NULL,
  `mail` varchar(128) NOT NULL,
  `tel` int NOT NULL,
  `image` varchar(128) NOT NULL,
  `adresse` varchar(128) NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `tel` (`tel`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `annonce`
--

DROP TABLE IF EXISTS `annonce`;
CREATE TABLE IF NOT EXISTS `annonce` (
  `id_annonce` int NOT NULL AUTO_INCREMENT,
  `id_client` int NOT NULL,
  `titre` varchar(128) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `prix min` int NOT NULL,
  `prix max` int NOT NULL,
  `date` date NOT NULL,
  `adresse` varchar(128) NOT NULL,
  `etat` tinyint(1) NOT NULL,
  `date_annonce` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nb_candidature` int NOT NULL DEFAULT '0',
  `id_type_eve` int NOT NULL,
  PRIMARY KEY (`id_annonce`),
  KEY `id_client` (`id_client`),
  KEY `id_type_eve` (`id_type_eve`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `artiste`
--

DROP TABLE IF EXISTS `artiste`;
CREATE TABLE IF NOT EXISTS `artiste` (
  `id_artiste` int NOT NULL AUTO_INCREMENT,
  `login` varchar(128) NOT NULL,
  `nom` varchar(128) NOT NULL,
  `prenom` varchar(128) NOT NULL,
  `adresse` varchar(128) NOT NULL,
  `mail` varchar(56) NOT NULL,
  `tel` int NOT NULL,
  `pwd` varchar(128) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `photo` varchar(128) NOT NULL,
  `couverture` varchar(128) NOT NULL,
  PRIMARY KEY (`id_artiste`),
  UNIQUE KEY `login` (`login`,`mail`,`tel`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `artiste`
--

INSERT INTO `artiste` (`id_artiste`, `login`, `nom`, `prenom`, `adresse`, `mail`, `tel`, `pwd`, `description`, `photo`, `couverture`) VALUES
(2, 'aaze', 'azeaze', 'azera', 'azer', 'azeraz', 1234567, 'azera', 'azer', 'aar', 'area');

-- --------------------------------------------------------

--
-- Structure de la table `avis`
--

DROP TABLE IF EXISTS `avis`;
CREATE TABLE IF NOT EXISTS `avis` (
  `id_avis` int NOT NULL AUTO_INCREMENT,
  `id_artiste` int NOT NULL,
  `note` int NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  PRIMARY KEY (`id_avis`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `candidature`
--

DROP TABLE IF EXISTS `candidature`;
CREATE TABLE IF NOT EXISTS `candidature` (
  `id_candidature` int NOT NULL AUTO_INCREMENT,
  `id_annonce` int NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  `prix` int NOT NULL,
  `etat` tinyint(1) NOT NULL,
  `id_artiste` int NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_candidature`),
  KEY `id_annonce` (`id_annonce`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie` (
  `id_cat` int NOT NULL AUTO_INCREMENT,
  `id_type` int NOT NULL,
  `id_groupe` int NOT NULL,
  `id_artiste` int NOT NULL,
  PRIMARY KEY (`id_cat`),
  KEY `id_type` (`id_type`),
  KEY `id_groupe` (`id_groupe`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `categorie`
--

INSERT INTO `categorie` (`id_cat`, `id_type`, `id_groupe`, `id_artiste`) VALUES
(1, 1, 2, 2);

-- --------------------------------------------------------

--
-- Structure de la table `categorie_annonce`
--

DROP TABLE IF EXISTS `categorie_annonce`;
CREATE TABLE IF NOT EXISTS `categorie_annonce` (
  `id_cat` int NOT NULL AUTO_INCREMENT,
  `id_annonce` int NOT NULL,
  `id_groupe` int NOT NULL,
  `id_type` int NOT NULL,
  PRIMARY KEY (`id_cat`),
  KEY `id_annonce` (`id_annonce`),
  KEY `id_groupe` (`id_groupe`),
  KEY `id_type` (`id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id_client` int NOT NULL AUTO_INCREMENT,
  `login` varchar(128) NOT NULL,
  `pwd` varchar(128) NOT NULL,
  `nom` varchar(128) NOT NULL,
  `prenom` varchar(128) NOT NULL,
  `mail` varchar(128) NOT NULL,
  `tel` int NOT NULL,
  `photo` varchar(128) NOT NULL,
  `adresse` varchar(128) NOT NULL,
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `tel` (`tel`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `id_commentaire` int NOT NULL AUTO_INCREMENT,
  `id_pub` int NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  `nb_like` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_commentaire`),
  KEY `id_pub` (`id_pub`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `concert`
--

DROP TABLE IF EXISTS `concert`;
CREATE TABLE IF NOT EXISTS `concert` (
  `id_concert` int NOT NULL AUTO_INCREMENT,
  `id_artiste` int NOT NULL,
  `lieu` varchar(128) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id_concert`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `jaime`
--

DROP TABLE IF EXISTS `jaime`;
CREATE TABLE IF NOT EXISTS `jaime` (
  `id_like` int NOT NULL AUTO_INCREMENT,
  `id_artiste` int DEFAULT NULL,
  `id_client` int DEFAULT NULL,
  `id_pub` int DEFAULT NULL,
  `id_commentaire` int DEFAULT NULL,
  PRIMARY KEY (`id_like`),
  KEY `id_artiste` (`id_artiste`),
  KEY `id_client` (`id_client`),
  KEY `id_pub` (`id_pub`),
  KEY `id_commentaire` (`id_commentaire`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `jaime`
--

INSERT INTO `jaime` (`id_like`, `id_artiste`, `id_client`, `id_pub`, `id_commentaire`) VALUES
(1, 2, NULL, 1, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id_message` int NOT NULL AUTO_INCREMENT,
  `contenu` varchar(1024) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_client_dest` int DEFAULT NULL,
  `id_client_exp` int DEFAULT NULL,
  `id_artiste_dest` int DEFAULT NULL,
  `id_artiste_exp` int DEFAULT NULL,
  PRIMARY KEY (`id_message`),
  KEY `id_client_dest` (`id_client_dest`),
  KEY `id_client_exp` (`id_client_exp`),
  KEY `id_artiste_dest` (`id_artiste_dest`),
  KEY `id_artiste_exp` (`id_artiste_exp`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `publication`
--

DROP TABLE IF EXISTS `publication`;
CREATE TABLE IF NOT EXISTS `publication` (
  `id_pub` int NOT NULL AUTO_INCREMENT,
  `id_artiste` int NOT NULL,
  `id_type` int NOT NULL,
  `titre` varchar(128) NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  `nb_like` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_pub`),
  KEY `id_artiste` (`id_artiste`),
  KEY `id_type` (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `publication`
--

INSERT INTO `publication` (`id_pub`, `id_artiste`, `id_type`, `titre`, `contenu`, `nb_like`) VALUES
(1, 2, 1, 'azer', 'azerfazerazez', 0);

-- --------------------------------------------------------

--
-- Structure de la table `reclamation`
--

DROP TABLE IF EXISTS `reclamation`;
CREATE TABLE IF NOT EXISTS `reclamation` (
  `id_reclamation` int NOT NULL AUTO_INCREMENT,
  `titre` varchar(128) NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `etat` tinyint(1) NOT NULL,
  `id_artiste` int DEFAULT NULL,
  `id_client` int DEFAULT NULL,
  PRIMARY KEY (`id_reclamation`),
  KEY `id_artiste` (`id_artiste`),
  KEY `id_client` (`id_client`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `service`
--

DROP TABLE IF EXISTS `service`;
CREATE TABLE IF NOT EXISTS `service` (
  `id_service` int NOT NULL AUTO_INCREMENT,
  `id_artiste` int NOT NULL,
  `nom_service` varchar(128) NOT NULL,
  `prix_service` float NOT NULL,
  `detail` varchar(1024) NOT NULL,
  PRIMARY KEY (`id_service`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Structure de la table `type_evenement`
--

DROP TABLE IF EXISTS `type_evenement`;
CREATE TABLE IF NOT EXISTS `type_evenement` (
  `id_type_eve` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(128) NOT NULL,
  PRIMARY KEY (`id_type_eve`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `type_evenement`
--

INSERT INTO `type_evenement` (`id_type_eve`, `nom`) VALUES
(1, 'Anniversaire'),
(2, 'Marriage'),
(3, 'Entreprise'),
(4, 'Centre commerciale'),
(5, 'Bar'),
(6, 'Restaurant'),
(7, 'Boite de nuit'),
(8, 'Hotel'),
(9, 'Soirée privée');

-- --------------------------------------------------------

--
-- Structure de la table `type_groupe`
--

DROP TABLE IF EXISTS `type_groupe`;
CREATE TABLE IF NOT EXISTS `type_groupe` (
  `id_groupe` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(128) NOT NULL,
  PRIMARY KEY (`id_groupe`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `type_groupe`
--

INSERT INTO `type_groupe` (`id_groupe`, `nom`) VALUES
(1, 'solo'),
(2, 'band'),
(3, 'dj');

-- --------------------------------------------------------

--
-- Structure de la table `type_musique`
--

DROP TABLE IF EXISTS `type_musique`;
CREATE TABLE IF NOT EXISTS `type_musique` (
  `id_type` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(128) NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `type_musique`
--

INSERT INTO `type_musique` (`id_type`, `nom`) VALUES
(1, 'rock');

-- --------------------------------------------------------

--
-- Structure de la table `type_pub`
--

DROP TABLE IF EXISTS `type_pub`;
CREATE TABLE IF NOT EXISTS `type_pub` (
  `id_type` int NOT NULL AUTO_INCREMENT,
  `nom` varchar(128) NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Déchargement des données de la table `type_pub`
--

INSERT INTO `type_pub` (`id_type`, `nom`) VALUES
(1, 'lien'),
(2, 'video'),
(3, 'image');

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `id_client3` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_type_eve` FOREIGN KEY (`id_type_eve`) REFERENCES `type_evenement` (`id_type_eve`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `id_artiste3` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `candidature`
--
ALTER TABLE `candidature`
  ADD CONSTRAINT `id_annonce1` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_artiste7` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `categorie`
--
ALTER TABLE `categorie`
  ADD CONSTRAINT `id_artiste5` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_groupe` FOREIGN KEY (`id_groupe`) REFERENCES `type_groupe` (`id_groupe`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_type1` FOREIGN KEY (`id_type`) REFERENCES `type_musique` (`id_type`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `categorie_annonce`
--
ALTER TABLE `categorie_annonce`
  ADD CONSTRAINT `id_annonce` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_groupe1` FOREIGN KEY (`id_groupe`) REFERENCES `type_groupe` (`id_groupe`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_type3` FOREIGN KEY (`id_type`) REFERENCES `type_musique` (`id_type`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `id_pub` FOREIGN KEY (`id_pub`) REFERENCES `publication` (`id_pub`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `concert`
--
ALTER TABLE `concert`
  ADD CONSTRAINT `id_artiste2` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `jaime`
--
ALTER TABLE `jaime`
  ADD CONSTRAINT `id_artiste4` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_commentaire` FOREIGN KEY (`id_commentaire`) REFERENCES `commentaire` (`id_commentaire`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_pub2` FOREIGN KEY (`id_pub`) REFERENCES `publication` (`id_pub`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `publication`
--
ALTER TABLE `publication`
  ADD CONSTRAINT `id_artiste1` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_type` FOREIGN KEY (`id_type`) REFERENCES `type_pub` (`id_type`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `id_artiste6` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_client2` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Contraintes pour la table `service`
--
ALTER TABLE `service`
  ADD CONSTRAINT `id_artiste` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
