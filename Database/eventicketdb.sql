CREATE DATABASE  IF NOT EXISTS `eventicketdb` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `eventicketdb`;
-- MySQL dump 10.13  Distrib 8.0.22, for Win64 (x86_64)
--
-- Host: localhost    Database: eventicketdb
-- ------------------------------------------------------
-- Server version	8.0.22

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
-- Table structure for table `event`
--

DROP TABLE IF EXISTS `event`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `event` (
  `name` varchar(50) DEFAULT NULL,
  `details` varchar(200) DEFAULT NULL,
  `numberOfTickets` int DEFAULT NULL,
  `id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `event`
--

LOCK TABLES `event` WRITE;
/*!40000 ALTER TABLE `event` DISABLE KEYS */;
INSERT INTO `event` VALUES ('Untold','Concert live, judetul Cluj',15998,'1'),('Electric Castle','Concert in aer liber, Banffy Castle, Cluj, Romania',1890,'2'),('Neversea','Beach concert, Constanta, Romania',1600,'3'),('Summer Well','Concert, Bucharest',2500,'4'),('Ceva nou','concert in aer liber',10,'5');
/*!40000 ALTER TABLE `event` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderc`
--

DROP TABLE IF EXISTS `orderc`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderc` (
  `id` varchar(50) NOT NULL,
  `userId` varchar(45) DEFAULT NULL,
  `orderItemId` varchar(45) DEFAULT NULL,
  `total` double DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderc`
--

LOCK TABLES `orderc` WRITE;
/*!40000 ALTER TABLE `orderc` DISABLE KEYS */;
INSERT INTO `orderc` VALUES ('9800af33-4134-4ee7-9393-f6aaff007d9b','f0206f14-dfbc-4aaf-b41e-59cf34ddc924',NULL,80000),('c7a6b162-bf04-4eea-815d-042b1ed289fe','aa124cc0-c56d-470a-872e-798de37f3938',NULL,2000);
/*!40000 ALTER TABLE `orderc` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orderitem`
--

DROP TABLE IF EXISTS `orderitem`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orderitem` (
  `id` varchar(45) NOT NULL,
  `idOrder` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `idt` varchar(45) DEFAULT NULL,
  `userId` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orderitem`
--

LOCK TABLES `orderitem` WRITE;
/*!40000 ALTER TABLE `orderitem` DISABLE KEYS */;
INSERT INTO `orderitem` VALUES ('0f9986db-b4f9-41fe-94b0-3c5275e121a8','9800af33-4134-4ee7-9393-f6aaff007d9b',80000,'0544c92c-22f9-4f00-b82f-7645f63e1857','f0206f14-dfbc-4aaf-b41e-59cf34ddc924'),('aab3dfcb-52ba-4a91-bbe1-d019ea229920','c7a6b162-bf04-4eea-815d-042b1ed289fe',2000,'a8ff1eb2-d48e-4309-932d-a6b7fc002cc4','aa124cc0-c56d-470a-872e-798de37f3938');
/*!40000 ALTER TABLE `orderitem` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` varchar(50) DEFAULT NULL,
  `typet` varchar(45) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `idt` varchar(45) NOT NULL,
  PRIMARY KEY (`idt`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES ('2','VIP',8000,1290,'0544c92c-22f9-4f00-b82f-7645f63e1857'),('2','REG',80000,100,'0eff2212-88c9-4a09-be4f-18511226985f'),('2','NORMAL',5000,500,'1343142d-3066-4726-a210-b193c6a85d5e'),('4','VIP',5000,700,'196b3f14-498f-4f81-84c9-afc9285f2ab6'),('4','PUR',3000,900,'77b0e4b0-282d-454d-8b43-7775037ded7f'),('3','VIP',6000,600,'98fc4aac-4ec9-4dad-9f64-0d349dce31dd'),('1','ADULT',5000,7000,'a4702284-6418-482a-9a10-f5168ec2ed58'),('1','VIP',1000,1998,'a8ff1eb2-d48e-4309-932d-a6b7fc002cc4'),('5','OLD',500,100,'bd463f46-3d06-418f-93db-8a35784bb0dc'),('5','vip',60,10,'d7d2d257-8fb2-4685-99ba-40c723d4a04c'),('3','NORMAL',3000,1000,'dd372a43-3e07-4ee3-973d-070efe0cffa6'),('4','REGULAR',7000,900,'eee9e345-e0bf-4ed0-ba87-6c193b433207'),('1','CHILD',300,7000,'f10095af-aad4-4c02-922f-e2beb79d4c6f'),('5','CHIL',300,234,'f5773d12-7272-4d2e-a6a2-b529c25a18a2');
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` varchar(50) NOT NULL,
  `username` varchar(45) DEFAULT NULL,
  `firstName` varchar(45) DEFAULT NULL,
  `lastName` varchar(45) DEFAULT NULL,
  `phoneNumber` varchar(45) DEFAULT NULL,
  `password` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `address` varchar(90) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES ('f0206f14-dfbc-4aaf-b41e-59cf34ddc924','diana','Diana','Urcan','0865879898','aaaaaa','urcan.diana10@yahoo.com','Romania, Cluj');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usersession`
--

DROP TABLE IF EXISTS `usersession`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usersession` (
  `id` varchar(50) NOT NULL,
  `active` tinyint DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usersession`
--

LOCK TABLES `usersession` WRITE;
/*!40000 ALTER TABLE `usersession` DISABLE KEYS */;
INSERT INTO `usersession` VALUES ('diana',0),('dianaurcan',0);
/*!40000 ALTER TABLE `usersession` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-06 13:29:51
