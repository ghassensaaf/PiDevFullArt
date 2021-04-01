-- phpMyAdmin SQL Dump
-- version 4.9.2
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Apr 01, 2021 at 09:45 PM
-- Server version: 8.0.18
-- PHP Version: 7.3.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fullart`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(128) NOT NULL,
  `pwd` varchar(128) NOT NULL,
  `nom` varchar(128) NOT NULL,
  `prenom` varchar(128) NOT NULL,
  `mail` varchar(128) NOT NULL,
  `tel` int(11) NOT NULL,
  `image` varchar(128) NOT NULL,
  `adresse` varchar(128) NOT NULL,
  PRIMARY KEY (`id_admin`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `tel` (`tel`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`id_admin`, `login`, `pwd`, `nom`, `prenom`, `mail`, `tel`, `image`, `adresse`) VALUES
(1, 'salma', 'f6852b2a3ac0cd7e69c801f69eddb57a', '', '', '', 0, '', ''),
(2, 'ahmed', '9193ce3b31332b03f7d8af056c692b84', 'ahmed', 'ahmed', 'ahme@aha.com', 22343234, 'AAAA', 'ssssoukra');

-- --------------------------------------------------------

--
-- Table structure for table `annonce`
--

DROP TABLE IF EXISTS `annonce`;
CREATE TABLE IF NOT EXISTS `annonce` (
  `id_annonce` int(11) NOT NULL AUTO_INCREMENT,
  `id_client` int(11) NOT NULL,
  `titre` varchar(128) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `prix_min` int(11) NOT NULL,
  `prix_max` int(11) NOT NULL,
  `date` date NOT NULL,
  `adresse` varchar(128) NOT NULL,
  `etat` tinyint(1) NOT NULL,
  `date_annonce` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nb_candidature` int(11) NOT NULL DEFAULT '0',
  `id_type_eve` int(11) NOT NULL,
  PRIMARY KEY (`id_annonce`),
  KEY `id_client` (`id_client`),
  KEY `id_type_eve` (`id_type_eve`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `annonce`
--

INSERT INTO `annonce` (`id_annonce`, `id_client`, `titre`, `description`, `prix_min`, `prix_max`, `date`, `adresse`, `etat`, `date_annonce`, `nb_candidature`, `id_type_eve`) VALUES
(2, 1, 'aa', 'EE', 22, 22, '2021-03-05', 'EE', 1, '2021-03-02 12:08:22', 6, 3),
(5, 2, 'barcha', 'azjeoaze', 1331, 313333, '2021-03-12', 'caoz', 0, '2021-03-10 12:30:11', 6, 3),
(10, 12, 'cherche pianniste', 'test', 50, 500, '2021-04-11', 'hammamet', 1, '2021-04-01 20:54:46', 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `artiste`
--

DROP TABLE IF EXISTS `artiste`;
CREATE TABLE IF NOT EXISTS `artiste` (
  `id_artiste` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(128) NOT NULL,
  `nom` varchar(128) NOT NULL,
  `prenom` varchar(128) NOT NULL,
  `adresse` varchar(128) NOT NULL,
  `mail` varchar(56) NOT NULL,
  `tel` int(11) NOT NULL,
  `pwd` varchar(128) NOT NULL,
  `description` varchar(1024) NOT NULL,
  `photo` varchar(128) NOT NULL,
  `couverture` varchar(128) NOT NULL,
  `etat` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_artiste`),
  UNIQUE KEY `login` (`login`,`mail`,`tel`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `artiste`
--

INSERT INTO `artiste` (`id_artiste`, `login`, `nom`, `prenom`, `adresse`, `mail`, `tel`, `pwd`, `description`, `photo`, `couverture`, `etat`) VALUES
(2, 'aaze', 'azeaze', 'azera', 'azer', 'azeraz', 1234567, '661b749e026d5f2bc5030f15785da401', 'azer', 'aar', 'area', 1),
(3, 'artiste', '', '0000', '', '', 0, '76a9e4fa77d4da21ba43a6499ff2c1d7', '', '', '', 1),
(4, 'ahmed', 'ahmed', 'ahmed', '', '', 0, '9193ce3b31332b03f7d8af056c692b84', '', '', '', 1);

-- --------------------------------------------------------

--
-- Table structure for table `avis`
--

DROP TABLE IF EXISTS `avis`;
CREATE TABLE IF NOT EXISTS `avis` (
  `id_avis` int(11) NOT NULL AUTO_INCREMENT,
  `id_artiste` int(11) NOT NULL,
  `note` int(11) NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  PRIMARY KEY (`id_avis`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `avis`
--

INSERT INTO `avis` (`id_avis`, `id_artiste`, `note`, `contenu`) VALUES
(18, 2, 4, 'trés bien '),
(20, 2, 5, 'j\'adore');

-- --------------------------------------------------------

--
-- Table structure for table `candidature`
--

DROP TABLE IF EXISTS `candidature`;
CREATE TABLE IF NOT EXISTS `candidature` (
  `id_candidature` int(11) NOT NULL AUTO_INCREMENT,
  `id_annonce` int(11) NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  `prix` int(11) NOT NULL,
  `etat` tinyint(1) NOT NULL,
  `id_artiste` int(11) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_candidature`),
  KEY `id_annonce` (`id_annonce`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `candidature`
--

INSERT INTO `candidature` (`id_candidature`, `id_annonce`, `contenu`, `prix`, `etat`, `id_artiste`, `date`) VALUES
(1, 2, 'aze', 22, 2, 4, '2021-03-12 12:50:10'),
(2, 5, 'yyeye', 12, 1, 4, '2021-03-12 13:54:53'),
(3, 2, 'jjjj', 455, 0, 4, '2021-04-01 19:27:18'),
(4, 10, 'test', 125, 0, 4, '2021-04-01 21:03:30'),
(5, 10, 'testtest', 150, 0, 4, '2021-04-01 21:13:05');

-- --------------------------------------------------------

--
-- Table structure for table `categorie`
--

DROP TABLE IF EXISTS `categorie`;
CREATE TABLE IF NOT EXISTS `categorie` (
  `id_cat` int(11) NOT NULL AUTO_INCREMENT,
  `id_type` int(11) NOT NULL,
  `id_groupe` int(11) NOT NULL,
  `id_artiste` int(11) NOT NULL,
  PRIMARY KEY (`id_cat`),
  KEY `id_type` (`id_type`),
  KEY `id_groupe` (`id_groupe`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `categorie`
--

INSERT INTO `categorie` (`id_cat`, `id_type`, `id_groupe`, `id_artiste`) VALUES
(1, 1, 2, 2);

-- --------------------------------------------------------

--
-- Table structure for table `categorie_annonce`
--

DROP TABLE IF EXISTS `categorie_annonce`;
CREATE TABLE IF NOT EXISTS `categorie_annonce` (
  `id_cat` int(11) NOT NULL AUTO_INCREMENT,
  `id_annonce` int(11) NOT NULL,
  `id_groupe` int(11) NOT NULL,
  `id_type` int(11) NOT NULL,
  PRIMARY KEY (`id_cat`),
  KEY `id_annonce` (`id_annonce`),
  KEY `id_groupe` (`id_groupe`),
  KEY `id_type` (`id_type`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
CREATE TABLE IF NOT EXISTS `client` (
  `id_client` int(11) NOT NULL AUTO_INCREMENT,
  `login` varchar(128) NOT NULL,
  `pwd` varchar(128) NOT NULL,
  `nom` varchar(128) NOT NULL,
  `prenom` varchar(128) NOT NULL,
  `mail` varchar(128) NOT NULL,
  `tel` int(11) NOT NULL,
  `photo` varchar(128) NOT NULL,
  `adresse` varchar(128) NOT NULL,
  `etat` int(11) DEFAULT '0',
  PRIMARY KEY (`id_client`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `mail` (`mail`),
  UNIQUE KEY `tel` (`tel`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id_client`, `login`, `pwd`, `nom`, `prenom`, `mail`, `tel`, `photo`, `adresse`, `etat`) VALUES
(1, 'salma', 'f6852b2a3ac0cd7e69c801f69eddb57a', 'salma', 'mouelhi', 'salma.mouelhi@esprit.tn', 24353635, 'YEYEYE', 'EYUEUEU', 1),
(2, 'ghassen', 'e10adc3949ba59abbe56e057f20f883e', '', '', 'saafghassen@gmail.com', 0, '', '', 0),
(5, 'test', '098f6bcd4621d373cade4e832627b4f6', 'test', 'test', 'test@test.test', 123, 'test', 'test', 0),
(6, 'beb', '5be372d4352c9807436913fcd665e652', 'beb', 'beb', 'beb@beb.beb', 1234, 'beb', 'beb', 0),
(8, 'ddd', 'b71e0711933bc4ce5b465586fbf06bf6', 'aa', 'dd', 'aaa', 12344444, 'C:\\Users\\salma\\Pictures\\58692012_2204919522921295_8632941568413663232_n.png', 'ddd', 0),
(9, 'emna', 'c545cf99924296439aa3b2db74b6f273', 'emna', 'emna', 'emna.emna@gmail.com', 29907814, 'em.png', 'soukra', 0),
(11, 'jjjjj', '3abf00fa61bfae2fff9133375e142416', 'test', 'test', 'tes@ss.ss', 2222, 'C:\\Users\\salma\\Pictures\\132666753_1711813872325541_321072634388844294_n.jpg', 'hh', 0),
(12, 'ahmedlmfo', 'e10adc3949ba59abbe56e057f20f883e', 'ghassen', 'saaf', 'ghassen.saaf@esprit.tn', 55238213, '', 'rue nessrine', 0);

-- --------------------------------------------------------

--
-- Table structure for table `commentaire`
--

DROP TABLE IF EXISTS `commentaire`;
CREATE TABLE IF NOT EXISTS `commentaire` (
  `id_commentaire` int(11) NOT NULL AUTO_INCREMENT,
  `id_pub` int(11) NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  `nb_like` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_commentaire`),
  KEY `id_pub` (`id_pub`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `commentaire`
--

INSERT INTO `commentaire` (`id_commentaire`, `id_pub`, `contenu`, `nb_like`) VALUES
(21, 1, 'hfgh', 9),
(27, 2, 'aaa', 3),
(28, 1, 'j\'ai pas arrivé à contacter l\'artiste ', 7),
(30, 1, 'aaaa', 5),
(31, 3, 'rgbf', 0),
(32, 1, 'zaaaa', 2),
(34, 2, 'j\'ai pas arrivé à contacter l\'artiste\n', 11),
(35, 2, 'ssss', 5),
(36, 2, 'hfgh', 8);

-- --------------------------------------------------------

--
-- Table structure for table `concert`
--

DROP TABLE IF EXISTS `concert`;
CREATE TABLE IF NOT EXISTS `concert` (
  `id_concert` int(11) NOT NULL AUTO_INCREMENT,
  `id_artiste` int(11) NOT NULL,
  `lieu` varchar(128) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id_concert`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `concert`
--

INSERT INTO `concert` (`id_concert`, `id_artiste`, `lieu`, `date`) VALUES
(1, 4, 'hammamet', '2021-04-13');

-- --------------------------------------------------------

--
-- Table structure for table `forgot`
--

DROP TABLE IF EXISTS `forgot`;
CREATE TABLE IF NOT EXISTS `forgot` (
  `client_mail` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `secret_code` int(11) NOT NULL,
  PRIMARY KEY (`client_mail`,`secret_code`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `forgot`
--

INSERT INTO `forgot` (`client_mail`, `secret_code`) VALUES
('salma.mouelhi@esprit.tn', 927927);

-- --------------------------------------------------------

--
-- Table structure for table `jaime`
--

DROP TABLE IF EXISTS `jaime`;
CREATE TABLE IF NOT EXISTS `jaime` (
  `id_like` int(11) NOT NULL AUTO_INCREMENT,
  `id_artiste` int(11) DEFAULT NULL,
  `nom` varchar(256) NOT NULL,
  `id_client` int(11) DEFAULT NULL,
  `id_pub` int(11) DEFAULT NULL,
  `id_commentaire` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_like`),
  KEY `id_artiste` (`id_artiste`),
  KEY `id_client` (`id_client`),
  KEY `id_pub` (`id_pub`),
  KEY `id_commentaire` (`id_commentaire`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `jaime`
--

INSERT INTO `jaime` (`id_like`, `id_artiste`, `nom`, `id_client`, `id_pub`, `id_commentaire`) VALUES
(42, NULL, 'test', 1, 1, NULL),
(43, 2, 'salma mouelhi', NULL, 1, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
CREATE TABLE IF NOT EXISTS `message` (
  `id_message` int(11) NOT NULL AUTO_INCREMENT,
  `contenu` varchar(1024) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id_client_dest` int(11) DEFAULT NULL,
  `id_client_exp` int(11) DEFAULT NULL,
  `id_artiste_dest` int(11) DEFAULT NULL,
  `id_artiste_exp` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_message`),
  KEY `id_client_dest` (`id_client_dest`),
  KEY `id_client_exp` (`id_client_exp`),
  KEY `id_artiste_dest` (`id_artiste_dest`),
  KEY `id_artiste_exp` (`id_artiste_exp`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `message`
--

INSERT INTO `message` (`id_message`, `contenu`, `date`, `id_client_dest`, `id_client_exp`, `id_artiste_dest`, `id_artiste_exp`) VALUES
(1, 'salma', '2021-03-05 10:07:42', NULL, 1, 2, NULL),
(2, 'ahmed', '2021-03-05 10:36:59', NULL, 1, 2, NULL),
(4, 'aaa', '2021-03-05 10:39:05', NULL, 1, 2, NULL),
(5, 'oooooooo', '2021-03-05 10:39:31', NULL, 1, 3, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id_notif` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Table structure for table `promo`
--

DROP TABLE IF EXISTS `promo`;
CREATE TABLE IF NOT EXISTS `promo` (
  `idpromo` int(11) NOT NULL AUTO_INCREMENT,
  `id_service` int(11) NOT NULL,
  `id_artiste` int(11) NOT NULL,
  `dated` date NOT NULL,
  `datef` date NOT NULL,
  `vpromo` int(11) NOT NULL,
  PRIMARY KEY (`idpromo`),
  KEY `id_service` (`id_service`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=MyISAM AUTO_INCREMENT=55 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `promo`
--

INSERT INTO `promo` (`idpromo`, `id_service`, `id_artiste`, `dated`, `datef`, `vpromo`) VALUES
(39, 15, 2, '2021-04-08', '2021-04-22', 12),
(40, 17, 2, '2021-04-16', '2021-04-30', 12),
(41, 15, 2, '2021-04-08', '2021-04-15', 23),
(42, 17, 12, '2021-04-06', '2021-04-09', 12),
(43, 18, 2, '2021-04-14', '2021-04-16', 3),
(44, 18, 2, '2021-04-14', '2021-04-16', 7),
(45, 13, 2, '2021-04-15', '2021-04-23', 12),
(53, 15, 2, '2021-04-01', '2021-04-03', 23),
(47, 17, 12, '2021-04-08', '2021-04-29', 2),
(48, 15, 2, '2021-04-15', '2021-04-15', 2),
(49, 15, 2, '2021-04-15', '2021-04-24', 9),
(50, 19, 12, '2021-03-30', '2021-04-16', 2),
(51, 15, 2, '2021-04-07', '2021-04-15', 12),
(52, 18, 2, '2021-04-13', '2021-04-15', 20),
(54, 12, 12, '2021-04-01', '2021-04-03', 20);

-- --------------------------------------------------------

--
-- Table structure for table `publication`
--

DROP TABLE IF EXISTS `publication`;
CREATE TABLE IF NOT EXISTS `publication` (
  `id_pub` int(11) NOT NULL AUTO_INCREMENT,
  `id_artiste` int(11) NOT NULL,
  `id_type` int(11) NOT NULL,
  `titre` varchar(128) NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  `date_pub` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `nb_like` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id_pub`),
  KEY `id_artiste` (`id_artiste`),
  KEY `id_type` (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `publication`
--

INSERT INTO `publication` (`id_pub`, `id_artiste`, `id_type`, `titre`, `contenu`, `date_pub`, `nb_like`) VALUES
(1, 2, 1, 'azer', 'azerfazerazez', '2021-03-19 19:49:56', 49),
(2, 2, 3, 'zzz', 'aaaaaa', '2021-03-03 18:13:13', 34),
(3, 4, 2, 'salma', 'salma salma', '2021-03-05 10:06:43', 45),
(4, 2, 1, 'majouni', 'maajouniiiiii', '2021-03-11 16:36:03', 47),
(5, 4, 3, 'anniversaire', 'en janvier un superbe anniversaire ', '2021-03-29 18:45:23', 10);

-- --------------------------------------------------------

--
-- Table structure for table `reclamation`
--

DROP TABLE IF EXISTS `reclamation`;
CREATE TABLE IF NOT EXISTS `reclamation` (
  `id_reclamation` int(11) NOT NULL AUTO_INCREMENT,
  `titre` varchar(128) NOT NULL,
  `contenu` varchar(1024) NOT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `etat` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT 'Non traiter ',
  `id_artiste` int(11) DEFAULT NULL,
  `id_client` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_reclamation`),
  KEY `id_artiste` (`id_artiste`),
  KEY `id_client` (`id_client`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `reclamation`
--

INSERT INTO `reclamation` (`id_reclamation`, `titre`, `contenu`, `date`, `etat`, `id_artiste`, `id_client`) VALUES
(2, 'sss', 'ssssssssss', '2021-03-02 20:28:27', 'd\'accord', 2, 1),
(7, 'aaa', 'aaaa', '2021-03-05 10:40:42', '0', NULL, 1),
(10, 'test', 'gazeho', '2021-03-12 12:49:54', '0', 4, NULL),
(11, 'aaa', 'aaa', '2021-03-23 12:24:59', 'ok ', NULL, 1),
(12, 'ssss', 'dddddd', '2021-03-23 14:34:34', '0', NULL, 1),
(13, 'majouna', 'majouna', '2021-03-23 16:11:34', '0', NULL, 1),
(14, 'hhhh', 'jszujzj', '2021-03-25 15:34:33', 'Non traiter ', 2, 1),
(15, 'salma', '', '2021-03-29 17:13:22', '0', NULL, 1),
(16, 'salma', '', '2021-03-29 17:22:29', '0', NULL, 1),
(17, '', 'aaaaa', '2021-03-29 17:22:42', '0', NULL, 1),
(18, 'salma', 'salma', '2021-03-29 17:23:31', '0', NULL, 1),
(19, 'salma', 'salma', '2021-03-29 17:28:04', '0', NULL, 1),
(20, 'ahmed', 'ahmed', '2021-03-29 17:28:19', '0', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
CREATE TABLE IF NOT EXISTS `service` (
  `id_service` int(11) NOT NULL AUTO_INCREMENT,
  `id_artiste` int(11) NOT NULL,
  `nom_service` varchar(128) NOT NULL,
  `prix_service` int(11) NOT NULL,
  `detail` varchar(1024) NOT NULL,
  PRIMARY KEY (`id_service`),
  KEY `id_artiste` (`id_artiste`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `service`
--

INSERT INTO `service` (`id_service`, `id_artiste`, `nom_service`, `prix_service`, `detail`) VALUES
(1, 4, 'test', 33, 'hhfi'),
(2, 4, 'ggggg', 555, 'hhfi'),
(3, 4, 'testeaze', 555, 'azfefazef');

-- --------------------------------------------------------

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
CREATE TABLE IF NOT EXISTS `ticket` (
  `id_ticket` varchar(128) NOT NULL,
  `id_client` int(11) NOT NULL,
  `id_concert` int(11) NOT NULL,
  PRIMARY KEY (`id_ticket`),
  KEY `client_id12` (`id_client`),
  KEY `concer_id2` (`id_concert`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `ticket`
--

INSERT INTO `ticket` (`id_ticket`, `id_client`, `id_concert`) VALUES
('188097235', 1, 1),
('427931506', 1, 1),
('772204407', 1, 1),
('976456175', 1, 1);

-- --------------------------------------------------------

--
-- Table structure for table `type_evenement`
--

DROP TABLE IF EXISTS `type_evenement`;
CREATE TABLE IF NOT EXISTS `type_evenement` (
  `id_type_eve` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(128) NOT NULL,
  PRIMARY KEY (`id_type_eve`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `type_evenement`
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
-- Table structure for table `type_groupe`
--

DROP TABLE IF EXISTS `type_groupe`;
CREATE TABLE IF NOT EXISTS `type_groupe` (
  `id_groupe` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(128) NOT NULL,
  PRIMARY KEY (`id_groupe`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `type_groupe`
--

INSERT INTO `type_groupe` (`id_groupe`, `nom`) VALUES
(1, 'solo'),
(2, 'band'),
(3, 'dj');

-- --------------------------------------------------------

--
-- Table structure for table `type_musique`
--

DROP TABLE IF EXISTS `type_musique`;
CREATE TABLE IF NOT EXISTS `type_musique` (
  `id_type` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(128) NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `type_musique`
--

INSERT INTO `type_musique` (`id_type`, `nom`) VALUES
(1, 'rock');

-- --------------------------------------------------------

--
-- Table structure for table `type_pub`
--

DROP TABLE IF EXISTS `type_pub`;
CREATE TABLE IF NOT EXISTS `type_pub` (
  `id_type` int(11) NOT NULL AUTO_INCREMENT,
  `nom` varchar(128) NOT NULL,
  PRIMARY KEY (`id_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `type_pub`
--

INSERT INTO `type_pub` (`id_type`, `nom`) VALUES
(1, 'lien'),
(2, 'video'),
(3, 'image');

--
-- Constraints for dumped tables
--

--
-- Constraints for table `annonce`
--
ALTER TABLE `annonce`
  ADD CONSTRAINT `id_client3` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_type_eve` FOREIGN KEY (`id_type_eve`) REFERENCES `type_evenement` (`id_type_eve`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `avis`
--
ALTER TABLE `avis`
  ADD CONSTRAINT `id_artiste3` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `candidature`
--
ALTER TABLE `candidature`
  ADD CONSTRAINT `id_annonce1` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_artiste7` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `categorie`
--
ALTER TABLE `categorie`
  ADD CONSTRAINT `id_artiste5` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_groupe` FOREIGN KEY (`id_groupe`) REFERENCES `type_groupe` (`id_groupe`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_type1` FOREIGN KEY (`id_type`) REFERENCES `type_musique` (`id_type`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `categorie_annonce`
--
ALTER TABLE `categorie_annonce`
  ADD CONSTRAINT `id_annonce` FOREIGN KEY (`id_annonce`) REFERENCES `annonce` (`id_annonce`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_groupe1` FOREIGN KEY (`id_groupe`) REFERENCES `type_groupe` (`id_groupe`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_type3` FOREIGN KEY (`id_type`) REFERENCES `type_musique` (`id_type`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `commentaire`
--
ALTER TABLE `commentaire`
  ADD CONSTRAINT `id_pub` FOREIGN KEY (`id_pub`) REFERENCES `publication` (`id_pub`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `concert`
--
ALTER TABLE `concert`
  ADD CONSTRAINT `id_artiste2` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `jaime`
--
ALTER TABLE `jaime`
  ADD CONSTRAINT `id_artiste4` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_client` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_commentaire` FOREIGN KEY (`id_commentaire`) REFERENCES `commentaire` (`id_commentaire`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_pub2` FOREIGN KEY (`id_pub`) REFERENCES `publication` (`id_pub`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `publication`
--
ALTER TABLE `publication`
  ADD CONSTRAINT `id_artiste1` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_type` FOREIGN KEY (`id_type`) REFERENCES `type_pub` (`id_type`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `reclamation`
--
ALTER TABLE `reclamation`
  ADD CONSTRAINT `id_artiste6` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `id_client2` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `service`
--
ALTER TABLE `service`
  ADD CONSTRAINT `id_artiste` FOREIGN KEY (`id_artiste`) REFERENCES `artiste` (`id_artiste`) ON DELETE RESTRICT ON UPDATE RESTRICT;

--
-- Constraints for table `ticket`
--
ALTER TABLE `ticket`
  ADD CONSTRAINT `client_id12` FOREIGN KEY (`id_client`) REFERENCES `client` (`id_client`) ON DELETE RESTRICT ON UPDATE RESTRICT,
  ADD CONSTRAINT `concer_id2` FOREIGN KEY (`id_concert`) REFERENCES `concert` (`id_concert`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
