/*
SQLyog Community Edition- MySQL GUI v8.03 
MySQL - 5.6.12-log : Database - 22_counterfeit
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;

CREATE DATABASE /*!32312 IF NOT EXISTS*/`22_counterfeit` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `22_counterfeit`;

/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `ins_id` int(11) DEFAULT NULL,
  `ins_name` varchar(20) DEFAULT NULL,
  `ins_license` varchar(20) DEFAULT NULL,
  `ins_establishedyr` year(4) DEFAULT NULL,
  `ins_place` varchar(20) DEFAULT NULL,
  `ins_post` varchar(20) DEFAULT NULL,
  `ins_pin` varchar(20) DEFAULT NULL,
  `ins_district` varchar(20) DEFAULT NULL,
  `ins_ph` varchar(20) DEFAULT NULL,
  `ins_email` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `company` */

insert  into `company`(`ins_id`,`ins_name`,`ins_license`,`ins_establishedyr`,`ins_place`,`ins_post`,`ins_pin`,`ins_district`,`ins_ph`,`ins_email`) values (3,'JAM','Kannur',2022,'iuuoioi','Perimbadari','678954','kannur','9876543210','fathima@gmail.com'),(5,'milma','12333',0000,'wwww','wwww','678904','Alappuzha','9876543678','mo@gmail.com');

/*Table structure for table `complaint` */

DROP TABLE IF EXISTS `complaint`;

CREATE TABLE `complaint` (
  `complaint_id` int(11) NOT NULL AUTO_INCREMENT,
  `complaint` varchar(100) DEFAULT NULL,
  `complaint_date` varchar(100) DEFAULT NULL,
  `reply` varchar(100) DEFAULT NULL,
  `reply_date` varchar(100) DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`complaint_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `complaint` */

insert  into `complaint`(`complaint_id`,`complaint`,`complaint_date`,`reply`,`reply_date`,`user_id`) values (5,'hiii','2022-11-30','hi','2022-11-30',4);


