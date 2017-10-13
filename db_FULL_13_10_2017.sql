/*
SQLyog Ultimate v11.3 (64 bit)
MySQL - 5.6.16 : Database - test
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
/*Table structure for table `company` */

DROP TABLE IF EXISTS `company`;

CREATE TABLE `company` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `complete_code` varchar(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_company_parent_id` (`parent_id`),
  CONSTRAINT `fk_company_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `company` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

/*Data for the table `company` */

insert  into `company`(`id`,`code`,`complete_code`,`name`,`description`,`state`,`parent_id`) values (1,'vnpt','vnpt','vnpt','vnpt',NULL,NULL),(2,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(3,'ssdc','vnpt.tech.ssdc','ssdc','vnpt tech ssdc',NULL,2),(4,'nms','vnpt.tech.ssdc.nms','nms','vnpt tech ssdc nms',NULL,3),(5,'plat','vnpt.tech.ssdc.plat','platform','vnpt tech ssdc plat',NULL,3),(6,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(7,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(8,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(9,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(10,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(11,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(12,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(13,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(14,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(15,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(16,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(17,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(18,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(19,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1),(20,'tech','vnpt.tech','vnpt tech','vnpt tech',NULL,1);

/*Table structure for table `databasechangelog` */

DROP TABLE IF EXISTS `databasechangelog`;

CREATE TABLE `databasechangelog` (
  `ID` varchar(255) NOT NULL,
  `AUTHOR` varchar(255) NOT NULL,
  `FILENAME` varchar(255) NOT NULL,
  `DATEEXECUTED` datetime NOT NULL,
  `ORDEREXECUTED` int(11) NOT NULL,
  `EXECTYPE` varchar(10) NOT NULL,
  `MD5SUM` varchar(35) DEFAULT NULL,
  `DESCRIPTION` varchar(255) DEFAULT NULL,
  `COMMENTS` varchar(255) DEFAULT NULL,
  `TAG` varchar(255) DEFAULT NULL,
  `LIQUIBASE` varchar(20) DEFAULT NULL,
  `CONTEXTS` varchar(255) DEFAULT NULL,
  `LABELS` varchar(255) DEFAULT NULL,
  `DEPLOYMENT_ID` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `databasechangelog` */

insert  into `databasechangelog`(`ID`,`AUTHOR`,`FILENAME`,`DATEEXECUTED`,`ORDEREXECUTED`,`EXECTYPE`,`MD5SUM`,`DESCRIPTION`,`COMMENTS`,`TAG`,`LIQUIBASE`,`CONTEXTS`,`LABELS`,`DEPLOYMENT_ID`) values ('00000000000001','jhipster','config/liquibase/changelog/00000000000000_initial_schema.xml','2017-10-06 12:41:26',1,'EXECUTED','7:06d507a322ecd082311ebbd6456dcacd','createTable tableName=jhi_user; createIndex indexName=idx_user_login, tableName=jhi_user; createIndex indexName=idx_user_email, tableName=jhi_user; createTable tableName=jhi_authority; createTable tableName=jhi_user_authority; addPrimaryKey tableN...','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053735-1','jhipster','config/liquibase/changelog/20171006053735_added_entity_Company.xml','2017-10-06 12:41:26',2,'EXECUTED','7:3fd7ac5c27f0aeb4d7482cf56b565d10','createTable tableName=company','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053736-1','jhipster','config/liquibase/changelog/20171006053736_added_entity_GroupEngineer.xml','2017-10-06 12:41:26',3,'EXECUTED','7:17d085cecf033457758487a165132796','createTable tableName=group_engineer','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053737-1','jhipster','config/liquibase/changelog/20171006053737_added_entity_ItemType.xml','2017-10-06 12:41:27',4,'EXECUTED','7:9c4b05346553f1347301b9d82b34c675','createTable tableName=item_type','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053738-1','jhipster','config/liquibase/changelog/20171006053738_added_entity_MechanicType.xml','2017-10-06 12:41:27',5,'EXECUTED','7:38e3b75972b8a4dd038a59488aa779d8','createTable tableName=mechanic_type','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053739-1','jhipster','config/liquibase/changelog/20171006053739_added_entity_Mechanic.xml','2017-10-06 12:41:27',6,'EXECUTED','7:ce07e2d5854187a9e892067e73aca857','createTable tableName=mechanic','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053740-1','jhipster','config/liquibase/changelog/20171006053740_added_entity_Material.xml','2017-10-06 12:41:27',7,'EXECUTED','7:6ea6c4736d1c0da7e8abdf80dd06c6bc','createTable tableName=material','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053741-1','jhipster','config/liquibase/changelog/20171006053741_added_entity_WorkType.xml','2017-10-06 12:41:27',8,'EXECUTED','7:b550cfbbba2ecb9230ad8400c2e49d81','createTable tableName=work_type','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053742-1','jhipster','config/liquibase/changelog/20171006053742_added_entity_WorkOrder.xml','2017-10-06 12:41:27',9,'EXECUTED','7:e02351deca9b6ab49dffa232e2f08995','createTable tableName=work_order','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053743-1','jhipster','config/liquibase/changelog/20171006053743_added_entity_StockItem.xml','2017-10-06 12:41:27',10,'EXECUTED','7:4ececb608887b4224a34ca14589d6aa7','createTable tableName=stock_item','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053744-1','jhipster','config/liquibase/changelog/20171006053744_added_entity_ManHours.xml','2017-10-06 12:41:27',11,'EXECUTED','7:32999e1c14f24b6088d03904dd2ce702','createTable tableName=man_hours','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053735-2','jhipster','config/liquibase/changelog/20171006053735_added_entity_constraints_Company.xml','2017-10-06 12:41:27',12,'EXECUTED','7:520c34b4bbfc74fcb230784dbe8e8809','addForeignKeyConstraint baseTableName=company, constraintName=fk_company_parent_id, referencedTableName=company','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053736-2','jhipster','config/liquibase/changelog/20171006053736_added_entity_constraints_GroupEngineer.xml','2017-10-06 12:41:27',13,'EXECUTED','7:3aa0ebcfb091d6c1a562032b37af783e','addForeignKeyConstraint baseTableName=group_engineer, constraintName=fk_group_engineer_parent_id, referencedTableName=group_engineer','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053737-2','jhipster','config/liquibase/changelog/20171006053737_added_entity_constraints_ItemType.xml','2017-10-06 12:41:27',14,'EXECUTED','7:ac0dc1d79faa3d3828e2635b61889f47','addForeignKeyConstraint baseTableName=item_type, constraintName=fk_item_type_parent_id, referencedTableName=item_type','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053739-2','jhipster','config/liquibase/changelog/20171006053739_added_entity_constraints_Mechanic.xml','2017-10-06 12:41:28',15,'EXECUTED','7:179211d301cc3c3c3983efc5a51f00a4','addForeignKeyConstraint baseTableName=mechanic, constraintName=fk_mechanic_item_type_id, referencedTableName=item_type; addForeignKeyConstraint baseTableName=mechanic, constraintName=fk_mechanic_mechanic_type_id, referencedTableName=mechanic_type;...','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053740-2','jhipster','config/liquibase/changelog/20171006053740_added_entity_constraints_Material.xml','2017-10-06 12:41:28',16,'EXECUTED','7:902a991c096fc911a958391f4dc61b64','addForeignKeyConstraint baseTableName=material, constraintName=fk_material_item_type_id, referencedTableName=item_type','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053742-2','jhipster','config/liquibase/changelog/20171006053742_added_entity_constraints_WorkOrder.xml','2017-10-06 12:41:28',17,'EXECUTED','7:2a9d671c222b8ab2099659b82d896dbf','addForeignKeyConstraint baseTableName=work_order, constraintName=fk_work_order_work_type_id, referencedTableName=work_type; addForeignKeyConstraint baseTableName=work_order, constraintName=fk_work_order_group_engineer_id, referencedTableName=group...','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053743-2','jhipster','config/liquibase/changelog/20171006053743_added_entity_constraints_StockItem.xml','2017-10-06 12:41:28',18,'EXECUTED','7:744560994d60a0918bdf99be348f2cad','addForeignKeyConstraint baseTableName=stock_item, constraintName=fk_stock_item_work_order_id, referencedTableName=work_order','',NULL,'3.5.3',NULL,NULL,'7268485833'),('20171006053744-2','jhipster','config/liquibase/changelog/20171006053744_added_entity_constraints_ManHours.xml','2017-10-06 12:41:28',19,'EXECUTED','7:7e01b3f3a7dfda75c0684082be46f296','addForeignKeyConstraint baseTableName=man_hours, constraintName=fk_man_hours_work_order_id, referencedTableName=work_order; addForeignKeyConstraint baseTableName=man_hours, constraintName=fk_man_hours_group_engineer_id, referencedTableName=group_e...','',NULL,'3.5.3',NULL,NULL,'7268485833');

/*Table structure for table `databasechangeloglock` */

DROP TABLE IF EXISTS `databasechangeloglock`;

CREATE TABLE `databasechangeloglock` (
  `ID` int(11) NOT NULL,
  `LOCKED` bit(1) NOT NULL,
  `LOCKGRANTED` datetime DEFAULT NULL,
  `LOCKEDBY` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `databasechangeloglock` */

insert  into `databasechangeloglock`(`ID`,`LOCKED`,`LOCKGRANTED`,`LOCKEDBY`) values (1,'','2017-10-10 14:11:23','thuyetlv (192.168.147.1)');

/*Table structure for table `group_engineer` */

DROP TABLE IF EXISTS `group_engineer`;

CREATE TABLE `group_engineer` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `complete_code` varchar(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `jhi_cost` float NOT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_group_engineer_parent_id` (`parent_id`),
  CONSTRAINT `fk_group_engineer_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `group_engineer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `group_engineer` */

insert  into `group_engineer`(`id`,`code`,`complete_code`,`name`,`description`,`jhi_cost`,`parent_id`) values (1,'en','en','eng',NULL,5,NULL),(2,'1','en.1','eng 1',NULL,7,1),(3,'2','en.2','eng 2',NULL,8,1);

/*Table structure for table `item_type` */

DROP TABLE IF EXISTS `item_type`;

CREATE TABLE `item_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `complete_code` varchar(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `jhi_specification` varchar(255) DEFAULT NULL,
  `parent_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_item_type_parent_id` (`parent_id`),
  CONSTRAINT `fk_item_type_parent_id` FOREIGN KEY (`parent_id`) REFERENCES `item_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `item_type` */

insert  into `item_type`(`id`,`code`,`complete_code`,`name`,`jhi_specification`,`parent_id`) values (1,'aa','aa','aa Name','{\"spe01\":\"a\",\"spe02\":\"b\",\"spe03\":\"c\",\"spe04\":\"d\"}',NULL);

/*Table structure for table `jhi_authority` */

DROP TABLE IF EXISTS `jhi_authority`;

CREATE TABLE `jhi_authority` (
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `jhi_authority` */

insert  into `jhi_authority`(`name`) values ('ROLE_ADMIN'),('ROLE_USER');

/*Table structure for table `jhi_persistent_audit_event` */

DROP TABLE IF EXISTS `jhi_persistent_audit_event`;

CREATE TABLE `jhi_persistent_audit_event` (
  `event_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `principal` varchar(50) NOT NULL,
  `event_date` timestamp NULL DEFAULT NULL,
  `event_type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`),
  KEY `idx_persistent_audit_event` (`principal`,`event_date`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

/*Data for the table `jhi_persistent_audit_event` */

insert  into `jhi_persistent_audit_event`(`event_id`,`principal`,`event_date`,`event_type`) values (1,'admin','2017-10-06 12:42:27','AUTHENTICATION_SUCCESS'),(2,'admin','2017-10-06 12:42:27','AUTHENTICATION_SUCCESS'),(3,'admin','2017-10-06 14:19:33','AUTHENTICATION_SUCCESS'),(4,'admin','2017-10-09 15:56:16','AUTHENTICATION_SUCCESS'),(5,'admin','2017-10-09 15:57:33','AUTHENTICATION_SUCCESS'),(6,'admin','2017-10-09 15:58:29','AUTHENTICATION_SUCCESS'),(7,'admin','2017-10-09 16:00:59','AUTHENTICATION_SUCCESS'),(8,'admin','2017-10-09 17:05:58','AUTHENTICATION_SUCCESS'),(9,'admin','2017-10-12 00:04:23','AUTHENTICATION_SUCCESS'),(10,'admin','2017-10-12 16:15:37','AUTHENTICATION_SUCCESS');

/*Table structure for table `jhi_persistent_audit_evt_data` */

DROP TABLE IF EXISTS `jhi_persistent_audit_evt_data`;

CREATE TABLE `jhi_persistent_audit_evt_data` (
  `event_id` bigint(20) NOT NULL,
  `name` varchar(150) NOT NULL,
  `value` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`event_id`,`name`),
  KEY `idx_persistent_audit_evt_data` (`event_id`),
  CONSTRAINT `fk_evt_pers_audit_evt_data` FOREIGN KEY (`event_id`) REFERENCES `jhi_persistent_audit_event` (`event_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `jhi_persistent_audit_evt_data` */

/*Table structure for table `jhi_user` */

DROP TABLE IF EXISTS `jhi_user`;

CREATE TABLE `jhi_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(50) NOT NULL,
  `password_hash` varchar(60) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `image_url` varchar(256) DEFAULT NULL,
  `activated` bit(1) NOT NULL,
  `lang_key` varchar(5) DEFAULT NULL,
  `activation_key` varchar(20) DEFAULT NULL,
  `reset_key` varchar(20) DEFAULT NULL,
  `created_by` varchar(50) NOT NULL,
  `created_date` timestamp NOT NULL,
  `reset_date` timestamp NULL DEFAULT NULL,
  `last_modified_by` varchar(50) DEFAULT NULL,
  `last_modified_date` timestamp NULL DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  `group_engineer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`),
  UNIQUE KEY `idx_user_login` (`login`),
  UNIQUE KEY `email` (`email`),
  UNIQUE KEY `idx_user_email` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `jhi_user` */

insert  into `jhi_user`(`id`,`login`,`password_hash`,`first_name`,`last_name`,`email`,`image_url`,`activated`,`lang_key`,`activation_key`,`reset_key`,`created_by`,`created_date`,`reset_date`,`last_modified_by`,`last_modified_date`,`company_id`,`group_engineer_id`) values (1,'system','$2a$10$mE.qmcV0mFU5NcKh73TZx.z4ueI/.bDWbj0T1BYyqP481kGGarKLG','System','System','system@localhost','','','vi',NULL,NULL,'system','2017-10-06 12:41:26',NULL,'system',NULL,NULL,NULL),(2,'anonymoususer','$2a$10$j8S5d7Sr7.8VTOYNviDPOeWX8KcYILUVJBsYV83Y5NtECayypx9lO','Anonymous','User','anonymous@localhost','','','vi',NULL,NULL,'system','2017-10-06 12:41:26',NULL,'system',NULL,NULL,NULL),(3,'admin','$2a$10$gSAhZrxMllrbgj/kkK9UceBPpChGWJA7SYIb1Mqo.n5aNLq1/oRrC','Administrator','Administrator','admin@localhost','','','vi',NULL,NULL,'system','2017-10-06 12:41:26',NULL,'system',NULL,2,NULL),(4,'user','$2a$10$VEjxo0jq2YG9Rbk2HmX9S.k1uZBGYUHdUcid3g/vfiEl7lwWgOH/K','User','User','user@localhost','','','vi',NULL,NULL,'system','2017-10-06 12:41:26',NULL,'system',NULL,NULL,NULL),(5,'thuyetlv','$2a$10$wkeFMph5FlvuTvWr5xyXd.LF3hEFcx0d/tSyl005GRxWFtZPss7ka','thuyet','le','lethuyet.10.11.1990@gmail.com',NULL,'','vi',NULL,'85119224876599055755','admin','2017-10-10 16:07:51','2017-10-10 16:07:51','admin','2017-10-10 16:07:51',NULL,NULL);

/*Table structure for table `jhi_user_authority` */

DROP TABLE IF EXISTS `jhi_user_authority`;

CREATE TABLE `jhi_user_authority` (
  `user_id` bigint(20) NOT NULL,
  `authority_name` varchar(50) NOT NULL,
  PRIMARY KEY (`user_id`,`authority_name`),
  KEY `fk_authority_name` (`authority_name`),
  CONSTRAINT `fk_authority_name` FOREIGN KEY (`authority_name`) REFERENCES `jhi_authority` (`name`),
  CONSTRAINT `fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `jhi_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `jhi_user_authority` */

insert  into `jhi_user_authority`(`user_id`,`authority_name`) values (1,'ROLE_ADMIN'),(3,'ROLE_ADMIN'),(5,'ROLE_ADMIN'),(1,'ROLE_USER'),(3,'ROLE_USER'),(4,'ROLE_USER');

/*Table structure for table `man_hours` */

DROP TABLE IF EXISTS `man_hours`;

CREATE TABLE `man_hours` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `mh` float NOT NULL,
  `work_order_id` bigint(20) DEFAULT NULL,
  `group_engineer_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_man_hours_work_order_id` (`work_order_id`),
  KEY `fk_man_hours_group_engineer_id` (`group_engineer_id`),
  CONSTRAINT `fk_man_hours_group_engineer_id` FOREIGN KEY (`group_engineer_id`) REFERENCES `group_engineer` (`id`),
  CONSTRAINT `fk_man_hours_work_order_id` FOREIGN KEY (`work_order_id`) REFERENCES `work_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `man_hours` */

/*Table structure for table `material` */

DROP TABLE IF EXISTS `material`;

CREATE TABLE `material` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `complete_code` varchar(20) NOT NULL,
  `name` varchar(128) CHARACTER SET utf8 COLLATE utf8_unicode_ci NOT NULL,
  `description` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `jhi_cost` float NOT NULL,
  `unit` varchar(255) NOT NULL,
  `quantity` int(11) NOT NULL,
  `location` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `jhi_specification` varchar(255) CHARACTER SET utf8 COLLATE utf8_unicode_ci DEFAULT NULL,
  `item_type_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_material_item_type_id` (`item_type_id`),
  CONSTRAINT `fk_material_item_type_id` FOREIGN KEY (`item_type_id`) REFERENCES `item_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `material` */

insert  into `material`(`id`,`code`,`complete_code`,`name`,`description`,`jhi_cost`,`unit`,`quantity`,`location`,`img_url`,`img_path`,`jhi_specification`,`item_type_id`) values (1,'13','AA.13','1','dưes',23,'23',23,NULL,'/upload-dir/material/thu-nghiem-boi-thuyet-le-van.jpg',NULL,'{\"spe01\":\"a1\",\"spe02\":\"b2\",\"spe03\":\"c3\",\"spe04\":\"d4\"}',1),(2,'01','AA.01','Demo','VNPT',6,'20',4,'Tang 4','/upload-dir/material/13511961-1220503341295994-2660312853285834304-n.jpg',NULL,'{\"spe01\":\"2\",\"spe02\":\"1\",\"spe03\":\"3\",\"spe04\":\"\",\"spe05\":\"\",\"spe06\":\"\",\"spe07\":\"\",\"spe08\":\"\",\"spe09\":\"\",\"spe10\":\"\"}',1);

/*Table structure for table `mechanic` */

DROP TABLE IF EXISTS `mechanic`;

CREATE TABLE `mechanic` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `complete_code` varchar(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `since` date NOT NULL,
  `jhi_specification` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `img_url` varchar(255) DEFAULT NULL,
  `img_path` varchar(255) DEFAULT NULL,
  `item_type_id` bigint(20) DEFAULT NULL,
  `mechanic_type_id` bigint(20) DEFAULT NULL,
  `company_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_mechanic_item_type_id` (`item_type_id`),
  KEY `fk_mechanic_mechanic_type_id` (`mechanic_type_id`),
  KEY `fk_mechanic_company_id` (`company_id`),
  CONSTRAINT `fk_mechanic_company_id` FOREIGN KEY (`company_id`) REFERENCES `company` (`id`),
  CONSTRAINT `fk_mechanic_item_type_id` FOREIGN KEY (`item_type_id`) REFERENCES `item_type` (`id`),
  CONSTRAINT `fk_mechanic_mechanic_type_id` FOREIGN KEY (`mechanic_type_id`) REFERENCES `mechanic_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `mechanic` */

insert  into `mechanic`(`id`,`code`,`complete_code`,`name`,`description`,`note`,`since`,`jhi_specification`,`location`,`img_url`,`img_path`,`item_type_id`,`mechanic_type_id`,`company_id`) values (1,'AA','AA','AAA',NULL,NULL,'2017-10-01',NULL,NULL,NULL,NULL,1,1,2);

/*Table structure for table `mechanic_type` */

DROP TABLE IF EXISTS `mechanic_type`;

CREATE TABLE `mechanic_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `jhi_specification` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `mechanic_type` */

insert  into `mechanic_type`(`id`,`code`,`name`,`description`,`note`,`jhi_specification`) values (1,'MT','MT 1',NULL,NULL,NULL);

/*Table structure for table `stock_item` */

DROP TABLE IF EXISTS `stock_item`;

CREATE TABLE `stock_item` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `quantity` int(11) NOT NULL,
  `work_order_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_stock_item_work_order_id` (`work_order_id`),
  CONSTRAINT `fk_stock_item_work_order_id` FOREIGN KEY (`work_order_id`) REFERENCES `work_order` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `stock_item` */

/*Table structure for table `work_order` */

DROP TABLE IF EXISTS `work_order`;

CREATE TABLE `work_order` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `start_time` date NOT NULL,
  `end_time` date NOT NULL,
  `i_interval` int(11) DEFAULT NULL,
  `is_repeat` int(11) NOT NULL,
  `task` varchar(255) DEFAULT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `note` varchar(255) DEFAULT NULL,
  `mh_total` float DEFAULT NULL,
  `mh_total_cost` float DEFAULT NULL,
  `stock_total_cost` float DEFAULT NULL,
  `last_update` date DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `work_type_id` bigint(20) DEFAULT NULL,
  `group_engineer_id` bigint(20) DEFAULT NULL,
  `mechanic_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_work_order_work_type_id` (`work_type_id`),
  KEY `fk_work_order_group_engineer_id` (`group_engineer_id`),
  KEY `fk_work_order_mechanic_id` (`mechanic_id`),
  CONSTRAINT `fk_work_order_group_engineer_id` FOREIGN KEY (`group_engineer_id`) REFERENCES `group_engineer` (`id`),
  CONSTRAINT `fk_work_order_mechanic_id` FOREIGN KEY (`mechanic_id`) REFERENCES `mechanic` (`id`),
  CONSTRAINT `fk_work_order_work_type_id` FOREIGN KEY (`work_type_id`) REFERENCES `work_type` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;

/*Data for the table `work_order` */

insert  into `work_order`(`id`,`code`,`name`,`start_time`,`end_time`,`i_interval`,`is_repeat`,`task`,`reason`,`note`,`mh_total`,`mh_total_cost`,`stock_total_cost`,`last_update`,`status`,`work_type_id`,`group_engineer_id`,`mechanic_id`) values (1,'code1','name1','2017-10-01','2017-10-12',1,1,'task 1','resason','note',10,12,14,'2017-10-12',NULL,1,1,1),(2,'code2','name2','2017-10-01','2017-10-12',1,1,'task 1','resason','note',10,12,14,'2017-10-12',NULL,1,1,1),(3,'code3','name3','2017-10-01','2017-10-12',1,1,'task 1','resason','note',10,12,14,'2017-10-12',NULL,1,1,1);

/*Table structure for table `work_type` */

DROP TABLE IF EXISTS `work_type`;

CREATE TABLE `work_type` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `name` varchar(128) NOT NULL,
  `i_interval` int(11) DEFAULT NULL,
  `is_repeat` int(11) NOT NULL,
  `task` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `work_type` */

insert  into `work_type`(`id`,`code`,`name`,`i_interval`,`is_repeat`,`task`) values (1,'AA','AA',5,1,'task1');

/* Function  structure for function  `GetCompanyTree` */

/*!50003 DROP FUNCTION IF EXISTS `GetCompanyTree` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `GetCompanyTree`(GivenID INT,LevelLimit INT) RETURNS varchar(1024) CHARSET latin1
    DETERMINISTIC
BEGIN
    DECLARE rv,q,queue,queue_children,front_rc,level_part VARCHAR(1024);
    DECLARE queue_length,front_id,front_ht,pos,curr_level INT;
    SET rv = '';
    SET queue = CONCAT(GivenID,':0');
    SET queue_length = 1;
    WHILE queue_length > 0 DO
        SET front_id = FORMAT(queue,0);
        IF queue_length = 1 THEN
            SET front_rc = queue;
            SET queue = '';
            SET pos = LOCATE(':',front_rc);
        ELSE
            SET pos = LOCATE(',',queue);
            SET front_rc = LEFT(queue,pos - 1);
            SET q = SUBSTR(queue,pos + 1);
            SET queue = q;
            SET pos = LOCATE(':',front_rc);
        END IF;
        SET front_id = LEFT(front_rc,pos - 1);
        SET front_ht = SUBSTR(front_rc,pos + 1);
        SET queue_length = queue_length - 1;
        SELECT IFNULL(qc,'') INTO queue_children
        FROM (SELECT GROUP_CONCAT(CONCAT(id,':',front_ht+1)) qc
        FROM company WHERE parent_id = front_id) A;
        IF LENGTH(queue_children) = 0 THEN
            IF LENGTH(queue) = 0 THEN
                SET queue_length = 0;
            END IF;
        ELSE
            IF LENGTH(rv) = 0 THEN
                IF front_ht < LevelLimit THEN
                    SET rv = queue_children;
                END IF;
            ELSE
                IF front_ht < LevelLimit THEN
                    SET rv = CONCAT(rv,',',queue_children);
                END IF;
            END IF;
            IF LENGTH(queue) = 0 THEN
                SET queue = queue_children;
            ELSE
                SET queue = CONCAT(queue,',',queue_children);
            END IF;
            SET queue_length = LENGTH(queue) - LENGTH(REPLACE(queue,',','')) + 1;
        END IF;
    END WHILE;
    #
    # Strip away level parts of the output
    #
    IF LENGTH(rv) > 0 THEN
        SET curr_level = 1;
        WHILE curr_level <= LevelLimit DO
            SET level_part = CONCAT(':',curr_level);
            SET rv = REPLACE(rv,level_part,'');
            SET curr_level = curr_level + 1;
        END WHILE;
    END IF;
    RETURN rv;
END */$$
DELIMITER ;

/* Function  structure for function  `GetEngineerTree` */

/*!50003 DROP FUNCTION IF EXISTS `GetEngineerTree` */;
DELIMITER $$

/*!50003 CREATE DEFINER=`root`@`localhost` FUNCTION `GetEngineerTree`(GivenID INT,LevelLimit INT) RETURNS varchar(1024) CHARSET latin1
    DETERMINISTIC
BEGIN
    DECLARE rv,q,queue,queue_children,front_rc,level_part VARCHAR(1024);
    DECLARE queue_length,front_id,front_ht,pos,curr_level INT;
    SET rv = '';
    SET queue = CONCAT(GivenID,':0');
    SET queue_length = 1;
    WHILE queue_length > 0 DO
        SET front_id = FORMAT(queue,0);
        IF queue_length = 1 THEN
            SET front_rc = queue;
            SET queue = '';
            SET pos = LOCATE(':',front_rc);
        ELSE
            SET pos = LOCATE(',',queue);
            SET front_rc = LEFT(queue,pos - 1);
            SET q = SUBSTR(queue,pos + 1);
            SET queue = q;
            SET pos = LOCATE(':',front_rc);
        END IF;
        SET front_id = LEFT(front_rc,pos - 1);
        SET front_ht = SUBSTR(front_rc,pos + 1);
        SET queue_length = queue_length - 1;
        SELECT IFNULL(qc,'') INTO queue_children
        FROM (SELECT GROUP_CONCAT(CONCAT(id,':',front_ht+1)) qc
        FROM `group_engineer` WHERE parent_id = front_id) A;
        IF LENGTH(queue_children) = 0 THEN
            IF LENGTH(queue) = 0 THEN
                SET queue_length = 0;
            END IF;
        ELSE
            IF LENGTH(rv) = 0 THEN
                IF front_ht < LevelLimit THEN
                    SET rv = queue_children;
                END IF;
            ELSE
                IF front_ht < LevelLimit THEN
                    SET rv = CONCAT(rv,',',queue_children);
                END IF;
            END IF;
            IF LENGTH(queue) = 0 THEN
                SET queue = queue_children;
            ELSE
                SET queue = CONCAT(queue,',',queue_children);
            END IF;
            SET queue_length = LENGTH(queue) - LENGTH(REPLACE(queue,',','')) + 1;
        END IF;
    END WHILE;
    #
    # Strip away level parts of the output
    #
    IF LENGTH(rv) > 0 THEN
        SET curr_level = 1;
        WHILE curr_level <= LevelLimit DO
            SET level_part = CONCAT(':',curr_level);
            SET rv = REPLACE(rv,level_part,'');
            SET curr_level = curr_level + 1;
        END WHILE;
    END IF;
    RETURN rv;
END */$$
DELIMITER ;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
