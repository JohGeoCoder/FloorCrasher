CREATE DATABASE  IF NOT EXISTS `floorcrasher` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `floorcrasher`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: floorcrasher
-- ------------------------------------------------------
-- Server version	5.6.21-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `convention`
--

DROP TABLE IF EXISTS `convention`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `convention` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `startDate` date NOT NULL,
  `endDate` date NOT NULL,
  `location` varchar(45) NOT NULL,
  `categories` varchar(256) DEFAULT NULL,
  `adultOnly` bit(1) NOT NULL DEFAULT b'0',
  `active` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `convention`
--

LOCK TABLES `convention` WRITE;
/*!40000 ALTER TABLE `convention` DISABLE KEYS */;
/*!40000 ALTER TABLE `convention` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role`
--

DROP TABLE IF EXISTS `role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `role` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role`
--

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;
INSERT INTO `role` VALUES (2,'JoeRecla','ROLE_USER'),(3,'JohnGeorge','ROLE_USER');
/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student`
--

DROP TABLE IF EXISTS `student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `dateOfBirth` datetime NOT NULL,
  `emailAddress` varchar(255) NOT NULL,
  `firstName` varchar(255) NOT NULL,
  `lastName` varchar(255) NOT NULL,
  `password` varchar(8) NOT NULL,
  `userName` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student`
--

LOCK TABLES `student` WRITE;
/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` VALUES (43,'1991-04-07 00:00:00','asdf@gmail.com','john','george','password','raarara'),(44,'1986-04-07 00:00:00','abc123@gmail.com','john','george','joejoe','joecracko'),(45,'2002-04-07 00:00:00','qwerty@gmail.com','john','george','glelin','glelin'),(46,'2014-10-15 00:00:00','sadfalsdjl@gmail.com','joe','joe','joejoe','joejoejoe'),(47,'2014-10-11 00:00:00','redred@gmail.com','red','red','redred','redredred'),(48,'2014-10-31 00:00:00','hihi@gmail.com','hi','hi','hihi','hihihi'),(49,'2014-12-18 00:00:00','joejoe@gmail.com','joe','joe','joejoe','asldkfjasldk'),(50,'2014-12-18 00:00:00','','','joe','joejoe','123445'),(51,'2014-12-18 00:00:00','','','joe','','');
/*!40000 ALTER TABLE `student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `email` varchar(50) NOT NULL,
  `zipcode` varchar(5) DEFAULT NULL,
  `gender` varchar(10) NOT NULL,
  `birthdate` date NOT NULL,
  `passhash` varchar(2054) NOT NULL,
  `enabled` tinyint(4) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=100038 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (100036,'JoeRecla','JoeJoe@gmail.com','12345','MALE','2015-01-22','1000:f535b8f6ef68d5460c39d9834a06cd0392f81e9677f7ffc3dd6f973cacb1b226e31340e9cb4697e7ecacac40ba2027d71bab148dd35f66f8aa24de05feaeda4fc1c190cdb141b5056387ae4f5c8d895e1fe83f602994a5850087037493d196e3d4b50b4e2dc8d6a75c8b43c32550ba2bd0c19b5660bbb83f419213bce0c6ba90901a01f2c5b79c8253813d835c855f68f81e5ed39b26c577086181e475a898ac222a7a21c697c4128604f7ec9671a641a376865e8e10f0d608cc72278b60485da04d37416db2bf3dcfc0069881f30c75074f55f32e0bda8b6a075d8993344e35459e8968f607d448fbe7fbcbf2ce53198380522e75c7c4bb9ed9a8857ea551bd36c4093b97c5368631c6ebc7a55f64f0599a99096624c4e6bd26153004beabef009cd8d987c0fd1ec9f80d39e409735549ca11aaa18654c2e1ad1b6b018f3cf546b85c33378ade153c97cd8ad0b4a3c16a49636a90ac0825ebb294825b14e18936697865cc745a0741450f42dc8f25fd979ecb142ee718422d8858fbdec631395f976b5e57f074848afff9b27769f7d7a6ba010fff3d582b13a3607668d0302cfd357ac6b6a52c210046972998500a15ece00a02be101e9113d9c41182a198cb8ff094966fd5bbe4000ec5eb27f94282fc46c637bd579de8c3c9b3b645fba6338553b6d1457abe09039dc1fb98d5b86a1c61a14d69b030111d9fd9244e6316d2:718862fa083f12bb7b8910cd9f38036c5accbb7ce0ed4283b2eb6b6f6dafbc146f56b56937c02741645bdfa16898474f7698efdf5d811ad1661cd3b3ec592f9ba52defb7208f12fe52c6ad8dab410d83a54eb556eb73b5ec242ec303505b4626975abd8270b609c11ab819c2134c35cddb51b0bcc9febef31ca6f1918eb1260aae09c01eb3552f448cc8ce970f4de399178c21d85e31c2f318e024c9cf571adec3c9a4ac9f36affdb643adc8dc8f54407e8184d92ad45ec652a224e58bb3c31686726d27cca88c215b9fa0ed37e8cf70eda11b8abd9eb2d16a626bc6dfc7851ebbda775720fa9d94500f902174920d7fc59fe162b1b49c06b2e58136d7bce3692948809eb68d2f155c4f4ac3d51546d6bac8bc3a6711a60a377d16ef6c5ebfa58c36986c38585e2423dae1a3f0370de3012e730d5eb17b6615f02f08cc3b10936d4d47133c4d93ae91822edf7768a4077a08f9ff4807f172748b6e934406bb9562c594d3a2addcabcec53bb94a719abab6c99000c961407d35dd74bf9fd092909b0881a2177578c0f649831c612f0131004a4da41bffdd30d134e49e7cf024abcf3ec1e883c025c3d6d675d01e00f104501c2730d99adde6e47433e14626a8d301858c1f871f68dc4f0f2f65a87f3bfc97b43c1257a79697dbefc8dcb0c08a9fcd61f029fec9a45a5b5e7dbcb8df599a63162b0ebda019887cb190df1cb0abdb',1),(100037,'JohnGeorge','JohnGeorge@gmail.com','12345','MALE','2015-01-22','1000:0a0ad9b63dec36a481bee4ef3c339bcb63f84be478ebef73240fac8fb59e5ee9e3da6ee2d6bc532b1a10ca197d0a2fbef5e04a23b10428944fa6663db2e683956268bde4f4d5481d2efe4be600c15979d627bd55b3b4bff4133243b6474f322b8e2e5d2a18d0e7d8b4f99b58a9cceffd55be3855ac6b2f1fd66b1caf63c01ca4b086476fff5705a289bbd867e6ebb4d8e8419d81e253233ae7c519ec283efc91f611b6151f4aaf6569fbf5726f49b37a4420ae492801b9793e92fd5da3c73d3a16409b755804aa9d3d17dcb01dd8a50453659948bebae06333c8a0377ee4835d33937c5e877a4b192a827ab911fd665a7cf30c08852fb89410f63828f25dec0ec6682f579c7add70b9fb93db47b4e7e7339d2012e0dbbecd8168341a4020e45d2ceb13d6132df8456ac60e227c8a3ec9a51e085fe7f21e7a97f69de9c2fefc08004795ecd9c9204a06dcbb08a70f2488677abcf2662d3e777673e3c56d546ea42d2ca29243aea3123fd963e8186f70014d1185ea5fef75ed9b1489670af524e58c18a3cc374bbf0d7ac27088059965f29defc4fbb0377d2d1a3a5630054d75716e4dbea3c71e0bec29efbaed22678fb230f9313502d38f31f5f3b114d5033d10aba22705f35dc3bb79eaff4aa5510e2b5f34a098530ab6bd679bec0413592dda1b1ae113e3e77363255672694dd6b5b98ffb72ac3b784b6153436496e4397a0e:7a75ee6afc96e4b4505121418b0aa52c4f02233ef3624db781017be7f27be85a6893e8c39f421dcae9cb37cd0887d2022e626e644607e2b8a2253ef27321e8917e95facf0de7118d75fb08e2b733047e11ba94784bd375b6480d46c50708c5153bb2a234028530c012d8bfc32f8d15aa95f162ace9b33119d59a044a27141bf60e5dafa5790dc77b266c3f2e06c2de9ffbcb6a60d5385d6061f719811964662b26f8afad9763d873bc798e54faba44c659eeabf17de0afb630e1dc3d16fd037c63b3b20c7a3d9be58caf0914a5163cd8625d77e08bcdf4dcf99768b5950bdba79c0d4dda5ac15fd0cb07e6f5732b9adf9c3c499d5fc448f4d8d91fc750ad5c4bfd3064446372a6c5ae65b784aae5b88d9b86f388a8d460557f68e220c9cff7f04b83382ccc72f13f78ec83180ccdaa0f0c1e4ec9814321787fa0e32cf837bbc3f0822b18ecd98917245bc2e65063b83200725b06b6b98c9041ad1d302ecd5b3ca5d10004efb4f43a3e8a70edf3cff692c5e50085960ad703323bd80b1847c5f5bb1bb2e9db8e312301b65cf23b1dd40661c0e6b5fa7ffd5258ec482ba56f7566727b3204688d83bd555c759d7a177b3164e707a20e5fc6d9786bfffb28996b1d1172af2dcb5ae404dbfdd289749df7aa204b9f71144da882fa71f6d35cc011829fc4f0d0999b1f3d3a17bffb58c91c3feb95f4c728a35f566c993202ae6abee3',1);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-02-13 13:10:46
