-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem3
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `ocena`
--

DROP TABLE IF EXISTS `ocena`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ocena` (
  `IdOcena` int NOT NULL AUTO_INCREMENT,
  `Broj` varchar(45) NOT NULL,
  `Datum` date NOT NULL,
  `Vreme` varchar(45) NOT NULL,
  `IdKorisnik` int NOT NULL,
  `IdAS` int NOT NULL,
  PRIMARY KEY (`IdOcena`),
  KEY `IdAS_idx` (`IdAS`),
  KEY `IdKorisnik_idx` (`IdKorisnik`),
  CONSTRAINT `IdAS` FOREIGN KEY (`IdAS`) REFERENCES `podsistem2`.`audiosnimak` (`IdAS`) ON UPDATE CASCADE,
  CONSTRAINT `IdKorisnik` FOREIGN KEY (`IdKorisnik`) REFERENCES `podsistem1`.`korisnik` (`IdKorisnik`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ocena`
--

LOCK TABLES `ocena` WRITE;
/*!40000 ALTER TABLE `ocena` DISABLE KEYS */;
INSERT INTO `ocena` VALUES (1,'5','2025-04-12','09:12:34',1,1),(2,'4','2025-04-15','10:25:15',1,2),(3,'3','2025-04-18','11:30:45',2,3),(4,'5','2025-04-20','12:45:30',2,4),(5,'2','2025-04-22','13:10:10',3,5),(6,'4','2025-04-25','14:15:50',3,6),(7,'3','2025-04-28','15:20:05',4,7),(8,'5','2025-05-02','16:30:30',4,8),(9,'1','2025-05-05','17:45:15',5,9),(10,'2','2025-05-08','08:50:20',5,10),(11,'4','2025-05-10','09:10:05',6,11),(12,'5','2025-05-12','10:20:15',6,12),(13,'3','2025-05-15','11:25:30',7,13),(14,'4','2025-05-18','12:30:45',7,14),(15,'2','2025-05-20','13:35:50',8,15),(16,'5','2025-05-23','14:40:05',8,16),(17,'1','2025-05-25','15:45:10',9,17),(18,'3','2025-05-28','16:50:20',9,18),(19,'4','2025-06-01','17:55:25',10,19),(20,'5','2025-06-03','09:00:30',10,20),(21,'2','2025-06-06','10:05:35',11,21),(22,'3','2025-06-08','11:10:40',11,22),(23,'4','2025-06-11','12:15:45',12,23),(24,'5','2025-06-14','13:20:50',12,24),(25,'1','2025-06-16','14:25:55',13,25),(26,'2','2025-06-19','15:30:00',13,26),(27,'3','2025-06-22','16:35:05',14,27),(28,'4','2025-06-25','17:40:10',14,28),(29,'5','2025-06-28','09:45:15',1,29),(30,'2','2025-07-01','10:50:20',2,30),(31,'3','2025-07-04','11:55:25',3,31),(32,'4','2025-07-07','13:00:30',4,32),(33,'5','2025-07-10','14:05:35',5,33),(34,'1','2025-07-13','15:10:40',6,34),(35,'2','2025-07-16','16:15:45',7,35),(36,'3','2025-07-19','17:20:50',8,36),(37,'4','2025-07-22','09:25:55',9,37),(38,'5','2025-07-25','10:30:00',10,38),(39,'1','2025-08-01','11:35:05',11,39),(40,'2','2025-08-05','12:40:10',12,40);
/*!40000 ALTER TABLE `ocena` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `omiljenepesme`
--

DROP TABLE IF EXISTS `omiljenepesme`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `omiljenepesme` (
  `IdAudio` int NOT NULL,
  `IdKor` int NOT NULL,
  PRIMARY KEY (`IdAudio`,`IdKor`),
  KEY `IdKor_idx` (`IdKor`),
  CONSTRAINT `IdAudio` FOREIGN KEY (`IdAudio`) REFERENCES `podsistem2`.`audiosnimak` (`IdAS`) ON UPDATE CASCADE,
  CONSTRAINT `IdKor` FOREIGN KEY (`IdKor`) REFERENCES `podsistem1`.`korisnik` (`IdKorisnik`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `omiljenepesme`
--

LOCK TABLES `omiljenepesme` WRITE;
/*!40000 ALTER TABLE `omiljenepesme` DISABLE KEYS */;
INSERT INTO `omiljenepesme` VALUES (1,1),(2,1),(3,1),(35,1),(43,1),(4,2),(5,2),(6,2),(40,2),(44,2),(7,3),(8,3),(9,3),(45,3),(10,4),(11,4),(12,4),(46,4),(13,5),(14,5),(15,5),(47,5),(16,6),(17,6),(18,6),(48,6),(19,7),(20,7),(21,7),(49,7),(22,8),(23,8),(24,8),(50,8),(5,9),(25,9),(26,9),(27,9),(10,10),(28,10),(29,10),(30,10),(15,11),(31,11),(32,11),(33,11),(20,12),(34,12),(35,12),(36,12),(25,13),(37,13),(38,13),(39,13),(30,14),(40,14),(41,14),(42,14);
/*!40000 ALTER TABLE `omiljenepesme` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paket`
--

DROP TABLE IF EXISTS `paket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paket` (
  `IdPaket` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  `Cena` double NOT NULL,
  PRIMARY KEY (`IdPaket`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paket`
--

LOCK TABLES `paket` WRITE;
/*!40000 ALTER TABLE `paket` DISABLE KEYS */;
INSERT INTO `paket` VALUES (1,'Paket Start',499),(2,'Paket Basic',799),(3,'Paket Standard',1199),(4,'Paket Premium',1599),(5,'Paket Deluxe',1999),(6,'Paket Gold',2499),(7,'Paket Silver',1799),(8,'Paket Bronze',999),(9,'Paket Family',1499),(10,'Paket Student',699),(11,'Paket Weekend',399),(12,'Paket Monthly',299),(13,'Paket Platinum',2999),(14,'Paket VIP',4999),(15,'Paket Trial',0);
/*!40000 ALTER TABLE `paket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pretplata`
--

DROP TABLE IF EXISTS `pretplata`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pretplata` (
  `IdPretplata` int NOT NULL AUTO_INCREMENT,
  `IdKorisnik` int NOT NULL,
  `IdPaket` int NOT NULL,
  `DatumPoc` date NOT NULL,
  `VremePoc` varchar(45) NOT NULL,
  `Cena` double NOT NULL,
  PRIMARY KEY (`IdPretplata`),
  KEY `IdKor_idx` (`IdKorisnik`),
  KEY `IdPaket_idx` (`IdPaket`),
  CONSTRAINT `IdKorisnik_` FOREIGN KEY (`IdKorisnik`) REFERENCES `podsistem1`.`korisnik` (`IdKorisnik`) ON UPDATE CASCADE,
  CONSTRAINT `IdPaket` FOREIGN KEY (`IdPaket`) REFERENCES `paket` (`IdPaket`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pretplata`
--

LOCK TABLES `pretplata` WRITE;
/*!40000 ALTER TABLE `pretplata` DISABLE KEYS */;
INSERT INTO `pretplata` VALUES (1,1,1,'2025-01-05','09:12:34',499),(2,1,3,'2025-02-06','10:45:20',1199),(3,1,5,'2025-03-08','11:30:55',1999),(4,2,2,'2025-01-10','08:20:10',799),(5,2,6,'2025-02-11','09:50:05',2499),(6,3,3,'2024-12-15','07:15:22',1199),(7,3,7,'2025-01-17','12:30:40',1799),(8,3,9,'2025-02-20','14:05:15',1499),(9,3,12,'2025-03-23','16:22:33',299),(10,4,4,'2025-01-01','10:10:10',1599),(11,5,5,'2025-01-03','11:11:11',1999),(12,5,8,'2025-02-05','13:13:13',999),(13,6,6,'2024-12-20','09:09:09',2499),(14,6,10,'2025-01-22','14:14:14',699),(15,6,13,'2025-03-25','18:18:18',2999),(16,7,7,'2025-02-01','08:08:08',1799),(17,8,8,'2025-01-12','12:12:12',999),(18,8,11,'2025-03-15','15:15:15',399),(19,9,9,'2025-02-05','17:17:17',1499),(20,10,10,'2025-01-18','08:08:08',699),(21,10,14,'2025-02-20','12:12:12',4999),(22,10,1,'2025-03-23','16:16:16',499),(23,11,11,'2025-01-07','09:09:09',399),(24,11,2,'2025-02-10','10:10:10',799),(25,12,12,'2025-03-01','11:11:11',299),(26,13,13,'2025-01-25','08:08:08',2999),(27,13,3,'2025-03-28','09:09:09',1199),(28,14,14,'2024-12-01','07:07:07',4999),(29,14,4,'2025-01-05','08:08:08',1599),(30,14,6,'2025-02-10','09:09:09',2499),(31,14,8,'2025-03-15','10:10:10',999);
/*!40000 ALTER TABLE `pretplata` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `slusanje`
--

DROP TABLE IF EXISTS `slusanje`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `slusanje` (
  `IdSlu` int NOT NULL AUTO_INCREMENT,
  `IdKorisnika` int NOT NULL,
  `IdAudioS` int NOT NULL,
  `DatumOd` date NOT NULL,
  `VremeOd` varchar(45) NOT NULL,
  `SekundeOd` int NOT NULL,
  `SekundeUkupno` int NOT NULL,
  PRIMARY KEY (`IdSlu`),
  KEY `IdKorisnika_idx` (`IdKorisnika`),
  KEY `IdAudioS_idx` (`IdAudioS`),
  CONSTRAINT `IdAudioS` FOREIGN KEY (`IdAudioS`) REFERENCES `podsistem2`.`audiosnimak` (`IdAS`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `IdKorisnika` FOREIGN KEY (`IdKorisnika`) REFERENCES `podsistem1`.`korisnik` (`IdKorisnik`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `slusanje`
--

LOCK TABLES `slusanje` WRITE;
/*!40000 ALTER TABLE `slusanje` DISABLE KEYS */;
INSERT INTO `slusanje` VALUES (1,1,1,'2025-01-05','09:12:34',0,120),(2,1,5,'2025-01-20','14:25:50',10,180),(3,1,12,'2025-02-15','11:05:23',0,200),(4,2,2,'2025-01-08','08:45:10',0,150),(5,2,6,'2025-02-10','12:15:05',60,180),(6,3,3,'2025-01-12','13:30:45',0,240),(7,3,7,'2025-01-28','14:10:15',50,180),(8,3,8,'2025-02-14','15:20:30',0,210),(9,4,9,'2025-01-22','09:10:00',0,230),(10,5,10,'2025-01-03','10:25:15',20,180),(11,5,11,'2025-02-07','11:40:30',0,300),(12,6,12,'2025-01-18','08:00:45',10,250),(13,6,13,'2025-02-20','09:15:00',0,180),(14,6,14,'2025-03-03','10:30:15',0,200),(15,7,15,'2025-01-25','12:00:00',0,180),(16,8,16,'2025-02-01','13:10:30',30,220),(17,8,17,'2025-02-18','14:20:45',0,180),(18,9,18,'2025-01-30','15:30:10',0,200),(19,10,19,'2025-02-05','09:45:30',0,240),(20,10,20,'2025-03-10','10:50:45',50,180),(21,11,21,'2025-01-15','11:00:00',0,1200),(22,11,22,'2025-02-12','12:15:15',600,900),(23,12,23,'2025-01-28','13:30:30',0,1125),(24,12,24,'2025-03-05','14:45:45',100,1000),(25,13,25,'2025-01-10','08:10:00',0,2030),(26,13,26,'2025-02-18','09:20:15',30,1200),(27,14,27,'2025-01-22','10:30:30',0,1235),(28,14,28,'2025-02-28','11:40:45',50,1320),(29,14,29,'2025-03-04','12:50:00',0,1140),(30,14,30,'2025-03-15','14:00:15',20,1500);
/*!40000 ALTER TABLE `slusanje` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'podsistem3'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-29 18:31:20
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem1
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `korisnik`
--

DROP TABLE IF EXISTS `korisnik`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `korisnik` (
  `IdKorisnik` int NOT NULL AUTO_INCREMENT,
  `Ime` varchar(45) NOT NULL,
  `Email` varchar(45) NOT NULL,
  `Godiste` int NOT NULL,
  `Pol` char(1) NOT NULL,
  `IdMesto` int NOT NULL,
  PRIMARY KEY (`IdKorisnik`),
  KEY `IdMesto_idx` (`IdMesto`),
  CONSTRAINT `IdMesto` FOREIGN KEY (`IdMesto`) REFERENCES `mesto` (`IdMesto`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `korisnik`
--

LOCK TABLES `korisnik` WRITE;
/*!40000 ALTER TABLE `korisnik` DISABLE KEYS */;
INSERT INTO `korisnik` VALUES (1,'Marko','marko.petrovic@gmail.com',1998,'M',1),(2,'Ana','ana.savic99@yahoo.com',1999,'Z',2),(3,'Petar','petar1980@gmail.com',2000,'M',3),(4,'Jovana','jovana.ceganjac@gmail.com',2001,'Z',4),(5,'Nikola','nikola.k88@yahoo.com',1997,'M',5),(6,'Milica','mila1996@gmail.com',1996,'Z',6),(7,'Stefan','stefan_s123@gmail.com',1995,'M',7),(8,'Ivana','ivana_banana@yahoo.com',2002,'Z',8),(9,'Luka','luka.m89@gmail.com',1994,'M',9),(10,'Marija','marija_r92@yahoo.com',1993,'Z',10),(11,'Aleksandar','alek.s1982@gmail.com',1992,'M',11),(12,'Tamara','tamara_t01@gmail.com',1998,'Z',12),(13,'Vladimir','vlad_the_best@yahoo.com',1990,'M',13),(14,'Dragana','draga.99@gmail.com',1999,'Z',14);
/*!40000 ALTER TABLE `korisnik` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `mesto`
--

DROP TABLE IF EXISTS `mesto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `mesto` (
  `IdMesto` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`IdMesto`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `mesto`
--

LOCK TABLES `mesto` WRITE;
/*!40000 ALTER TABLE `mesto` DISABLE KEYS */;
INSERT INTO `mesto` VALUES (1,'Beograd'),(2,'Novi Sad'),(3,'Niš'),(4,'Kragujevac'),(5,'Subotica'),(6,'Zrenjanin'),(7,'Pančevo'),(8,'Čačak'),(9,'Kraljevo'),(10,'Sombor'),(11,'Leskovac'),(12,'Valjevo'),(13,'Vranje'),(14,'Šabac'),(15,'Požarevac'),(16,'Smederevo'),(17,'Užice'),(18,'Pirot'),(19,'Zaječar'),(20,'Kikinda'),(21,'Beograd'),(22,'Novi Sad'),(23,'Niš'),(24,'Kragujevac'),(25,'Subotica'),(26,'Zrenjanin'),(27,'Pančevo'),(28,'Čačak'),(29,'Kraljevo'),(30,'Sombor'),(31,'Leskovac'),(32,'Valjevo'),(33,'Vranje'),(34,'Šabac'),(35,'Požarevac'),(36,'Smederevo'),(37,'Užice'),(38,'Pirot'),(39,'Zaječar'),(40,'Kikinda');
/*!40000 ALTER TABLE `mesto` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'podsistem1'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-29 18:31:20
-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: podsistem2
-- ------------------------------------------------------
-- Server version	8.0.40

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `audiokategorija`
--

DROP TABLE IF EXISTS `audiokategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audiokategorija` (
  `IdAS` int NOT NULL,
  `IdKat` int NOT NULL,
  PRIMARY KEY (`IdAS`,`IdKat`),
  KEY `IdKat_idx` (`IdKat`),
  CONSTRAINT `IdAS` FOREIGN KEY (`IdAS`) REFERENCES `audiosnimak` (`IdAS`) ON UPDATE CASCADE,
  CONSTRAINT `IdKat` FOREIGN KEY (`IdKat`) REFERENCES `kategorija` (`IdKat`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audiokategorija`
--

LOCK TABLES `audiokategorija` WRITE;
/*!40000 ALTER TABLE `audiokategorija` DISABLE KEYS */;
INSERT INTO `audiokategorija` VALUES (11,2),(12,2),(13,2),(14,2),(15,2),(16,2),(17,2),(18,2),(19,2),(20,2),(21,3),(22,3),(23,3),(24,3),(1,5),(2,5),(3,5),(4,5),(5,5),(6,6),(7,6),(8,6),(9,6),(10,6),(25,12),(26,12),(27,12),(28,12),(29,12),(30,12),(41,13),(42,13),(45,13),(50,13),(43,18),(44,18),(48,18),(46,19),(47,19),(49,19),(31,20),(32,20),(33,20),(34,20),(35,20),(36,20),(37,20),(38,20),(39,20),(40,20);
/*!40000 ALTER TABLE `audiokategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `audiosnimak`
--

DROP TABLE IF EXISTS `audiosnimak`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `audiosnimak` (
  `IdAS` int NOT NULL AUTO_INCREMENT,
  `IdKorisnik` int NOT NULL,
  `Naziv` varchar(45) NOT NULL,
  `Trajanje` int NOT NULL,
  `Datum` date NOT NULL,
  `Vreme` varchar(45) NOT NULL,
  PRIMARY KEY (`IdAS`),
  KEY `IdKorisnik_idx` (`IdKorisnik`),
  CONSTRAINT `IdKorisnik` FOREIGN KEY (`IdKorisnik`) REFERENCES `podsistem1`.`korisnik` (`IdKorisnik`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `audiosnimak`
--

LOCK TABLES `audiosnimak` WRITE;
/*!40000 ALTER TABLE `audiosnimak` DISABLE KEYS */;
INSERT INTO `audiosnimak` VALUES (1,1,'Bijelo Dugme - Đurđevdan',320,'2025-01-10','14:30:00'),(2,2,'Azra - Balkan',255,'2025-01-12','09:00:00'),(3,3,'EKV - Par godina za nas',280,'2025-01-15','18:45:00'),(4,4,'Oliver Dragojević - Cesarica',255,'2025-01-18','20:00:00'),(5,5,'Parni Valjak - Sve još miriše na nju',305,'2025-01-20','21:15:00'),(6,6,'Dino Merlin - Kad si rekla da me voliš',280,'2025-01-22','10:10:00'),(7,7,'Zdravko Čolić - Ti si mi u krvi',305,'2025-01-25','19:40:00'),(8,8,'Toma Zdravković - Danka',215,'2025-01-26','11:20:00'),(9,9,'Ed Sheeran - Shape of You',233,'2025-01-27','23:55:00'),(10,10,'Željko Joksimović - Lane moje',185,'2025-01-29','08:25:00'),(11,11,'Podcast Tehnologija #1',2700,'2025-02-01','16:00:00'),(12,12,'Podcast Tehnologija #2',3000,'2025-02-02','13:15:00'),(13,13,'Podcast Istorija Balkana',3330,'2025-02-03','09:30:00'),(14,14,'Podcast Psihologija i život',2420,'2025-02-04','07:00:00'),(15,1,'Podcast Nauka za sve',2110,'2025-02-05','19:00:00'),(16,2,'Podcast Sport - fudbal',3600,'2025-02-06','19:15:00'),(17,3,'Podcast Sport - košarka',2880,'2025-02-07','06:45:00'),(18,4,'Podcast Ekonomija danas',3130,'2025-02-08','22:10:00'),(19,5,'Podcast Kultura i umetnost',2485,'2025-02-09','10:00:00'),(20,6,'Podcast Film i serije',2775,'2025-02-10','11:00:00'),(21,7,'Audiobook: Na Drini ćuprija - Poglavlje 1',1200,'2025-02-11','12:15:00'),(22,8,'Audiobook: Na Drini ćuprija - Poglavlje 2',1330,'2025-02-12','09:00:00'),(23,9,'Audiobook: Prokleta Avlija - Poglavlje 1',1125,'2025-02-13','20:45:00'),(24,10,'Audiobook: Prokleta Avlija - Poglavlje 2',1170,'2025-02-14','21:15:00'),(25,11,'Lekcija: Osnove SQL jezika',1800,'2025-02-15','21:45:00'),(26,12,'Lekcija: Newtonovi zakoni',1520,'2025-02-16','14:00:00'),(27,13,'Lekcija: Osnove programiranja',1935,'2025-02-17','16:30:00'),(28,14,'Lekcija: Uvod u ekonomiju',1720,'2025-02-18','10:15:00'),(29,1,'Lekcija: Baze podataka',2030,'2025-02-19','09:30:00'),(30,2,'Lekcija: Fizika - Elektromagnetizam',1755,'2025-02-20','11:00:00'),(31,3,'Zvuci prirode - Šuma',1800,'2025-02-21','08:00:00'),(32,4,'Zvuci prirode - Kiša',1540,'2025-02-22','21:15:00'),(33,5,'Zvuci prirode - More',1695,'2025-02-23','21:45:00'),(34,6,'Zvuci prirode - Planina',1650,'2025-02-24','20:00:00'),(35,7,'Zvuci prirode - Ptice',1080,'2025-02-25','06:30:00'),(36,8,'Zvuci prirode - Vetar',1335,'2025-02-26','15:45:00'),(37,9,'Zvuci prirode - Potok',980,'2025-02-27','12:10:00'),(38,10,'Zvuci prirode - Okean',1845,'2025-02-28','23:20:00'),(39,11,'Zvuci prirode - Kamin',2110,'2025-03-01','18:00:00'),(40,12,'Zvuci prirode - Noć u prirodi',2400,'2025-03-02','21:00:00'),(41,13,'Motivacioni govor - uspeh',720,'2025-03-03','09:00:00'),(42,14,'Predavanje o veštačkoj inteligenciji',3315,'2025-03-04','10:00:00'),(43,1,'Konferencija IT 2025 - Panel 1',3600,'2025-03-05','11:00:00'),(44,2,'Konferencija IT 2025 - Panel 2',2725,'2025-03-06','12:00:00'),(45,3,'Govorni zapis - sastanak firme',1205,'2025-03-07','13:00:00'),(46,4,'Javno predavanje - zdravlje',2140,'2025-03-08','14:00:00'),(47,5,'Govori - politička debata',3030,'2025-03-09','15:00:00'),(48,6,'Konferencija o obrazovanju',2530,'2025-03-10','16:00:00'),(49,7,'Predavanje o filozofiji',2295,'2025-03-11','17:00:00'),(50,8,'Motivacioni govor - istrajnost',950,'2025-03-12','18:00:00');
/*!40000 ALTER TABLE `audiosnimak` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `kategorija`
--

DROP TABLE IF EXISTS `kategorija`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kategorija` (
  `IdKat` int NOT NULL AUTO_INCREMENT,
  `Naziv` varchar(45) NOT NULL,
  PRIMARY KEY (`IdKat`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `kategorija`
--

LOCK TABLES `kategorija` WRITE;
/*!40000 ALTER TABLE `kategorija` DISABLE KEYS */;
INSERT INTO `kategorija` VALUES (1,'Intervju'),(2,'Podcast'),(3,'Audiobook'),(4,'Dokumentarac'),(5,'Muzika - Rock'),(6,'Muzika - Pop'),(7,'Muzika - Jazz'),(8,'Muzika - Klasika'),(9,'Muzika - Elektronska'),(10,'Muzika - Narodna'),(11,'Edukativni materijal'),(12,'Lekcija'),(13,'Govorni zapis'),(14,'Radio emisija'),(15,'Priča za decu'),(16,'Meditacija'),(17,'Audioblog'),(18,'Konferencija'),(19,'Govori i predavanja'),(20,'Zvuci prirode'),(21,'Intervju'),(22,'Podcast'),(23,'Audiobook'),(24,'Dokumentarac'),(25,'Muzika - Rock'),(26,'Muzika - Pop'),(27,'Muzika - Jazz'),(28,'Muzika - Klasika'),(29,'Muzika - Elektronska'),(30,'Muzika - Narodna'),(31,'Edukativni materijal'),(32,'Lekcija'),(33,'Govorni zapis'),(34,'Radio emisija'),(35,'Priča za decu'),(36,'Meditacija'),(37,'Audioblog'),(38,'Konferencija'),(39,'Govori i predavanja'),(40,'Zvuci prirode');
/*!40000 ALTER TABLE `kategorija` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'podsistem2'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-29 18:31:20
