# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.01 (MySQL 5.7.18)
# Database: zantech
# Generation Time: 2017-08-04 12:16:05 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# Dump of table Permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Permission`;

CREATE TABLE `Permission` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `roleId` BIGINT(20) NOT NULL,
  `routeId` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `kRole` (`roleId`),
  KEY `kRoute` (`routeId`),
  CONSTRAINT `fkPermissionRole` FOREIGN KEY (`roleId`) REFERENCES `Role` (`id`),
  CONSTRAINT `fkPermissionRoute` FOREIGN KEY (`routeId`) REFERENCES `Route` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

LOCK TABLES `Permission` WRITE;
/*!40000 ALTER TABLE `Permission` DISABLE KEYS */;

INSERT INTO `Permission` (`id`, `roleId`, `routeId`)
VALUES
	(1,2,1),
	(2,2,2),
	(3,2,3),
	(4,2,4);

/*!40000 ALTER TABLE `Permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Role`;

CREATE TABLE `Role` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

LOCK TABLES `Role` WRITE;
/*!40000 ALTER TABLE `Role` DISABLE KEYS */;

INSERT INTO `Role` (`id`, `name`, `description`)
VALUES
	(1,'Administrator','Partner API access. Can query accounts.'),
	(2,'SuperUser','Super user with all access');

/*!40000 ALTER TABLE `Role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table Route
# ------------------------------------------------------------

DROP TABLE IF EXISTS `Route`;

CREATE TABLE `Route` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(20) NOT NULL,
  `method` VARCHAR(6) NOT NULL,
  `description` VARCHAR(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

LOCK TABLES `Route` WRITE;
/*!40000 ALTER TABLE `Route` DISABLE KEYS */;

INSERT INTO `Route` (`id`, `name`, `method`, `description`)
VALUES
	(1,'Users','GET','Query Users'),
	(2,'Users/*','GET','Query Users by ID'),
	(3,'Users/*','PUT','Update User'),
	(4,'Users','POST','Create new User');

/*!40000 ALTER TABLE `Route` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table User
# ------------------------------------------------------------

DROP TABLE IF EXISTS `User`;

CREATE TABLE `User` (
  `id` BIGINT(20) NOT NULL AUTO_INCREMENT,
  `userName` VARCHAR(20) NOT NULL,
  `password` VARCHAR(400) NOT NULL,
  `status` VARCHAR(10) NOT NULL,
  `roleId` BIGINT(20) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `kUserName` (`userName`),
  CONSTRAINT `fkUserRole` FOREIGN KEY (`roleId`) REFERENCES `Role` (`id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

LOCK TABLES `User` WRITE;
/*!40000 ALTER TABLE `User` DISABLE KEYS */;

INSERT INTO `User` (`id`, `userName`, `password`, `status`, `roleId`)
VALUES
	(1,'zandrewitte','$argon2i$v=19$m=65536,t=2,p=1$Ln6X2PoDBPVrA+0qeAdKFQ$9uAxw1SPsfbjSSdj2WwYjmZHwZKjJ/YJP5dQfXe8+yI','Active',2);

/*!40000 ALTER TABLE `User` ENABLE KEYS */;
UNLOCK TABLES;

-- --------------------------------------------------------

--
-- Table structure for table `drum_info`
--
DROP TABLE IF EXISTS `DrumInfo`;

CREATE TABLE `DrumInfo` (
  `id` INT(11) NOT NULL AUTO_INCREMENT,
  `number` VARCHAR(255) NOT NULL,
  `abb` VARCHAR(255) NOT NULL,
  `impulse` VARCHAR(255) NOT NULL,
  `cableType` VARCHAR(255) NOT NULL,
  `length` INT(11) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=INNODB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `drum_info`
--

INSERT INTO `DrumInfo` (`id`, `number`, `abb`, `impulse`, `cableType`, `length`) VALUES
(1, 'H 38006', 'ABB 00113', 'IMP / 2 / 0001', 'UVG 2 ACM (Blue Stripe)', 1000),
(2, 'H 39019', 'ABB 00104', 'IMP / 2 / 0002', 'UVG 2 ACM (Blue Stripe)', 1000),
(3, 'H 37997', 'ABB 00111', 'IMP / 2 / 0003', 'UVG 2 ACM (Blue Stripe)', 1000),
(4, 'H 41658', 'ABB 00018', 'IMP / 4 / 0001', 'UVG 4 ACM (Blue Stripe)', 2000),
(5, 'H 41622', 'ABB 00019', 'IMP / 4 / 0002', 'UVG 4 ACM (Blue Stripe)', 2000),
(6, 'H 5223', 'ABB 00030', 'IMP / 4 / 0003', 'UVG 4 ACM (Blue Stripe)', 1000),
(7, 'H 38449', 'ABB 00031', 'IMP / 4 / 0004', 'UVG 4 ACM (Blue Stripe)', 1000),
(8, 'H 38497', 'ABB 00032', 'IMP / 4 / 0005', 'UVG 4 ACM (Blue Stripe)', 1000),
(9, 'H 38483', 'ABB 00033', 'IMP / 4 / 0006', 'UVG 4 ACM (Blue Stripe)', 1000),
(10, 'H 38460', 'ABB 00034', 'IMP / 4 / 0007', 'UVG 4 ACM (Blue Stripe)', 1000),
(11, 'H 41571', 'ABB 00062', 'IMP / 4 / 0008', 'UVG 4 ACM (Blue Stripe)', 2000),
(12, 'H 40931', 'ABB 00063', 'IMP / 4 / 0009', 'UVG 4 ACM (Blue Stripe)', 2000),
(13, 'H 41599', 'ABB 00064', 'IMP / 4 / 0010', 'UVG 4 ACM (Blue Stripe)', 2000),
(14, 'H 41612', 'ABB 00105', 'IMP / 4 / 0011', 'UVG 4 ACM (Blue Stripe)', 2000),
(15, 'H 41626', 'ABB 00106', 'IMP / 4 / 0012', 'UVG 4 ACM (Blue Stripe)', 2000),
(16, 'H 36976', 'ABB 00109', 'IMP / 4 / 0014', 'UVG 4 ACM (Blue Stripe)', 1000),
(17, 'H 44713', 'ABB 00110', 'IMP / 4 / 0015', 'UVG 4 ACM (Blue Stripe)', 1000),
(18, 'H 38320', 'ABB 00112', 'IMP / 4 / 0016', 'UVG 4 ACM (Blue Stripe)', 1000),
(19, 'H 47852', 'ABB 0001', 'IMP / 8 / 0001', 'UVG 8 ACM (Blue Stripe)', 2004),
(25, 'H 47863', 'ABB 0002', 'IMP / 8 / 0002', 'UVG 8 ACM (Blue Stripe)', 2000),
(26, 'H 45272', 'ABB 0003', 'IMP / 8 / 0003', 'UVG 8 ACM (Blue Stripe)', 2005),
(27, 'H 46317', 'ABB 0004', 'IMP / 8 / 0004', 'UVG 8 ACM (Blue Stripe)', 2002),
(28, 'H 44466', 'ABB 0005', 'IMP / 8 / 0005', 'UVG 8 ACM (Blue Stripe)', 2002),
(29, 'H 46306', 'ABB 0006', 'IMP / 8 / 0006', 'UVG 8 ACM (Blue Stripe)', 2000),
(30, 'H 46318', 'ABB 0007', 'IMP / 8 / 0007', 'UVG 8 ACM (Blue Stripe)', 2004),
(31, 'H 46315', 'ABB 0008', 'IMP / 8 / 0008', 'UVG 8 ACM (Blue Stripe)', 2010),
(32, 'H 46291', 'ABB 0009', 'IMP / 8 / 0009', 'UVG 8 ACM (Blue Stripe)', 2001),
(33, 'H 46323', 'ABB 0010', 'IMP / 8 / 0010', 'UVG 8 ACM (Blue Stripe)', 2010),
(34, 'H 45265', 'ABB 0011', 'IMP / 8 / 0011', 'UVG 8 ACM (Blue Stripe)', 2001),
(35, 'H 44500', 'ABB 00025', 'IMP / 8 / 0012', 'UVG 8 ACM (Blue Stripe)', 2004),
(36, 'H 44477', 'ABB 00026', 'IMP / 8 / 0013', 'UVG 8 ACM (Blue Stripe)', 2001),
(37, 'H 44480', 'ABB 00027', 'IMP / 8 / 0014', 'UVG 8 ACM (Blue Stripe)', 2000),
(38, 'H 47874', 'ABB 00035', 'IMP / 8 / 0015', 'UVG 8 ACM (Blue Stripe)', 2000),
(39, 'H 46328', 'ABB 00036', 'IMP / 8 / 0016', 'UVG 8 ACM (Blue Stripe)', 2000),
(40, 'H 47858', 'ABB 00037', 'IMP / 8 / 0017', 'UVG 8 ACM (Blue Stripe)', 2000),
(41, 'H 46134', 'ABB 00038', 'IMP / 8 / 0018', 'UVG 8 ACM (Blue Stripe)', 2000),
(42, 'H 47866', 'ABB 00039', 'IMP / 8 / 0019', 'UVG 8 ACM (Blue Stripe)', 2000),
(43, 'H 47865', 'ABB 00040', 'IMP / 8 / 0020', 'UVG 8 ACM (Blue Stripe)', 2000),
(44, 'H 47854', 'ABB 00041', 'IMP / 8 / 0021', 'UVG 8 ACM (Blue Stripe)', 2001),
(45, 'H 47861', 'ABB 00042', 'IMP / 8 / 0022', 'UVG 8 ACM (Blue Stripe)', 2000),
(46, 'H 46102', 'ABB 00043', 'IMP / 8 / 0023', 'UVG 8 ACM (Blue Stripe)', 2000),
(47, 'H 47859', 'ABB 00044', 'IMP / 8 / 0024', 'UVG 8 ACM (Blue Stripe)', 2000),
(48, 'H 47862', 'ABB 00045', 'IMP / 8 / 0025', 'UVG 8 ACM (Blue Stripe)', 2000),
(49, 'H 47870', 'ABB 00046', 'IMP / 8 / 0026', 'UVG 8 ACM (Blue Stripe)', 2001),
(50, 'H 47855', 'ABB 00051', 'IMP / 8 / 0027', 'UVG 8 ACM (Blue Stripe)', 2000),
(51, 'H 47873', 'ABB 00052', 'IMP / 8 / 0028', 'UVG 8 ACM (Blue Stripe)', 2003),
(52, 'H 44928', 'ABB 00020', 'IMP / 16 / 0001', 'UVG 16 ACM ( Blue Stripe )', 1000),
(53, 'H 39062', 'ABB 00021', 'IMP / 16 / 0002', 'UVG 16 ACM ( Blue Stripe )', 1000),
(54, 'H 39121', 'ABB 00022', 'IMP / 16 / 0003', 'UVG 16 ACM ( Blue Stripe )', 1000),
(55, 'H 42981', 'ABB 00023', 'IMP / 16 / 0004', 'UVG 16 ACM ( Blue Stripe )', 1000),
(56, 'H 46056', 'ABB 00047', 'IMP / 16 / 0005', 'UVG 16 ACM ( Blue Stripe )', 2000),
(57, 'H 46478', 'ABB 00048', 'IMP / 16 / 0006', 'UVG 16 ACM ( Blue Stripe )', 2000),
(58, 'H 41465', 'ABB 00049', 'IMP / 16 / 0007', 'UVG 16 ACM ( Blue Stripe )', 2000),
(59, 'H 41451', 'ABB 00050', 'IMP / 16 / 0008', 'UVG 16 ACM ( Blue Stripe )', 2000),
(60, 'H 46267', 'ABB 00053', 'IMP / 16 / 0009', 'UVG 16 ACM ( Blue Stripe )', 2000),
(61, 'H 41448', 'ABB 00054', 'IMP / 16 / 0010', 'UVG 16 ACM ( Blue Stripe )', 2000),
(62, 'H 46052', 'ABB 00055', 'IMP / 16 / 0011', 'UVG 16 ACM ( Blue Stripe )', 2000),
(63, 'H 46116', 'ABB 00056', 'IMP / 16 / 0012', 'UVG 16 ACM ( Blue Stripe )', 2000),
(64, 'H 41450', 'ABB 00058', 'IMP / 16 / 0013', 'UVG 16 ACM ( Blue Stripe )', 2000),
(65, 'H 41480', 'ABB 00059', 'IMP / 16 / 0014', 'UVG 16 ACM ( Blue Stripe )', 2000),
(67, 'H 41915', 'ABB 00060', 'IMP / 16 / 0015', 'UVG 16 ACM ( Blue Stripe )', 2000),
(68, 'H 46285', 'ABB 00061', 'IMP / 16 / 0016', 'UVG 16 ACM ( Blue Stripe )', 2000),
(69, 'H 44939', 'ABB 00065', 'IMP / 16 / 0017', 'UVG 16 ACM ( Blue Stripe )', 1000),
(70, 'H 42738', 'ABB 00066', 'IMP / 16 / 0018', 'UVG 16 ACM ( Blue Stripe )', 1000),
(71, 'H 44936', 'ABB 00068', 'IMP / 16 / 0020', 'UVG 16 ACM ( Blue Stripe )', 1000),
(72, 'H 39066', 'ABB 00069', 'IMP / 16 / 0021', 'UVG 16 ACM ( Blue Stripe )', 1000),
(73, 'H 44951', 'ABB 00070', 'IMP / 16 / 0022', 'UVG 16 ACM ( Blue Stripe )', 1000),
(74, 'H 44933', 'ABB 00071', 'IMP / 16 / 0023', 'UVG 16 ACM ( Blue Stripe )', 1000),
(75, 'H 44940', 'ABB 00072', 'IMP / 16 / 0024', 'UVG 16 ACM ( Blue Stripe )', 1000),
(76, 'H 44947', 'ABB 00073', 'IMP / 16 / 0025', 'UVG 16 ACM ( Blue Stripe )', 1000),
(78, 'H 44950', 'ABB 00074', 'IMP / 16 / 0026', 'UVG 16 ACM ( Blue Stripe )', 1000),
(79, 'H 44946', 'ABB 00079', 'IMP / 16 / 0027', 'UVG 16 ACM ( Blue Stripe )', 1000),
(80, 'H 39072', 'ABB 00080', 'IMP / 16 / 0028', 'UVG 16 ACM ( Blue Stripe )', 1000),
(81, 'H 44941', 'ABB 00081', 'IMP / 16 / 0029', 'UVG 16 ACM ( Blue Stripe )', 1000),
(82, 'H 40231', 'ABB 00082', 'IMP / 16 / 0030', 'UVG 16 ACM ( Blue Stripe )', 1000),
(83, 'H 43024', 'ABB 00083', 'IMP / 16 / 0031', 'UVG 16 ACM ( Blue Stripe )', 1000),
(84, 'H 41223', 'ABB 00024', 'IMP / 20 / 0001', 'UVG 20 ACM ( Blue Stripe )', 2000),
(85, 'H 46119', 'ABB 00028', 'IMP / 20 / 0002', 'UVG 20 ACM ( Blue Stripe )', 2000),
(86, 'H 46089', 'ABB 00029', 'IMP / 32 / 0001', 'UVG 32 ACM ( Blue Stripe )', 2000),
(87, 'H 41919', 'ABB 00087', 'IMP / 40 / 0003', 'UVG 40 ACM ( Blue Stripe )', 2000),
(88, 'H 41917', 'ABB 00088', 'IMP / 40 / 0004', 'UVG 40 ACM ( Blue Stripe )', 2000),
(89, 'H 27187', 'ABB 00076', 'IMP / 4 / 1001', 'UVH 4 ACM ( Blue Stripe )', 1000),
(90, 'H 27176', 'ABB 00077', 'IMP / 4 / 1002', 'UVH 4 ACM ( Blue Stripe )', 1000),
(91, 'H 44432', 'ABB 00084', 'IMP / 8 / 2001', 'UVG 8 CCM (Blue Stripe)', 2000),
(92, 'H 44386', 'ABB 00078', 'IMP / 8 / 2002', 'UVG 8 CCM (Blue Stripe)', 2001),
(93, 'H 41459', 'ABB 00057', 'IMP / 8 / 1001', 'UVH 8 ACM ( Blue Stripe )', 2000),
(94, 'H 33274', 'ABB 00075', 'IMP / 8 / 1002', 'UVH 8 ACM ( Blue Stripe )', 1000),
(95, 'H 39145', 'ABB 00066', 'IMP / 16 / 1001', 'UVG 16 CCM ( Blue Stripe )', 993);


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
