-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: student_management
-- ------------------------------------------------------
-- Server version	5.7.44-log

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
-- Table structure for table `kehoachcosinhvien`
--

DROP TABLE IF EXISTS `kehoachcosinhvien`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `kehoachcosinhvien` (
  `maKeHoach` int(11) NOT NULL,
  `hocKyDuKien` int(11) DEFAULT NULL,
  `namHocDuKien` varchar(20) COLLATE utf8_unicode_ci DEFAULT NULL,
  `trangThai` int(11) DEFAULT NULL,
  `maHocPhan` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  `maSinhVien` varchar(255) COLLATE utf8_unicode_ci NOT NULL,
  PRIMARY KEY (`maHocPhan`,`maKeHoach`,`maSinhVien`),
  KEY `FKt1sae10ye7v4ca6hphllhbj0w` (`maSinhVien`),
  CONSTRAINT `FK1ybk48txp8v2cncbhh2mlc2tp` FOREIGN KEY (`maHocPhan`) REFERENCES `hocphan` (`maHocPhan`),
  CONSTRAINT `FKt1sae10ye7v4ca6hphllhbj0w` FOREIGN KEY (`maSinhVien`) REFERENCES `sinhvien` (`maSinhVien`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-06-19  3:48:51
