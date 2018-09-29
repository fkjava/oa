-- MySQL dump 10.13  Distrib 5.7.23, for Linux (x86_64)
--
-- Host: localhost    Database: oa
-- ------------------------------------------------------
-- Server version	5.7.23-0ubuntu0.18.04.1

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
-- Table structure for table `hrm_department`
--

DROP TABLE IF EXISTS `hrm_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hrm_department` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `number` double DEFAULT NULL,
  `owner_id` varchar(36) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKev06mn2gkio4mrynmy0gbbk7w` (`owner_id`),
  KEY `FKdirw51girxqa9kmn5nmslmm5c` (`parent_id`),
  CONSTRAINT `FKdirw51girxqa9kmn5nmslmm5c` FOREIGN KEY (`parent_id`) REFERENCES `hrm_department` (`id`),
  CONSTRAINT `FKev06mn2gkio4mrynmy0gbbk7w` FOREIGN KEY (`owner_id`) REFERENCES `id_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hrm_department`
--

LOCK TABLES `hrm_department` WRITE;
/*!40000 ALTER TABLE `hrm_department` DISABLE KEYS */;
INSERT INTO `hrm_department` VALUES ('1953ed3b-4d06-4873-96b6-56ddd02401b0','测试部',55000,NULL,NULL),('27a728fe-f8b7-4bc2-be02-f7beb442980c','市场部',57500,NULL,NULL),('4242e3d8-6925-4d28-8be4-e9d3bae0e069','外交部',0,'fa29d696-cc93-4c12-9646-a93f23eb8798','fe98d4a6-6f15-4862-8f72-1911cc920367'),('59e29242-72f2-4dfb-9deb-c0bca1976680','法务部',20000,NULL,'fe98d4a6-6f15-4862-8f72-1911cc920367'),('6f691412-71b2-4155-884a-7ffbbdaf69d4','运维部',5000,NULL,'fe98d4a6-6f15-4862-8f72-1911cc920367'),('a840a687-bb16-401c-97b9-9043ad77e310','电商部',90000,NULL,NULL),('c2cdc845-458d-4f56-a2a4-58aff777433b','董事会',10000,NULL,'fe98d4a6-6f15-4862-8f72-1911cc920367'),('c37cee82-45c7-492f-9612-a76424590708','研发部',60000,NULL,NULL),('fe98d4a6-6f15-4862-8f72-1911cc920367','花果山',45000,'17ba8030-21f0-473b-bf6f-5e2437ddcf5f',NULL);
/*!40000 ALTER TABLE `hrm_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hrm_employtt`
--

DROP TABLE IF EXISTS `hrm_employtt`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hrm_employtt` (
  `id` varchar(36) NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `department_id` varchar(36) DEFAULT NULL,
  `position_id` varchar(36) DEFAULT NULL,
  `user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FKb9f1joipy4n1j4p6qwdwclmdk` (`department_id`),
  KEY `FKb9ld52ha0ollidfk16ajnyqaq` (`position_id`),
  KEY `FKgwtakkfyn7rcqpllmcwdoejd0` (`user_id`),
  CONSTRAINT `FKb9f1joipy4n1j4p6qwdwclmdk` FOREIGN KEY (`department_id`) REFERENCES `hrm_department` (`id`),
  CONSTRAINT `FKb9ld52ha0ollidfk16ajnyqaq` FOREIGN KEY (`position_id`) REFERENCES `hrm_position` (`id`),
  CONSTRAINT `FKgwtakkfyn7rcqpllmcwdoejd0` FOREIGN KEY (`user_id`) REFERENCES `id_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hrm_employtt`
--

LOCK TABLES `hrm_employtt` WRITE;
/*!40000 ALTER TABLE `hrm_employtt` DISABLE KEYS */;
INSERT INTO `hrm_employtt` VALUES ('6a1a286b-8f5f-443a-933a-88487d23c7df','车陂','13924120301','c2cdc845-458d-4f56-a2a4-58aff777433b','06c3346d-1db9-459c-83fd-02120fca3313','06f575ca-60c4-4584-a523-d8dd461595de');
/*!40000 ALTER TABLE `hrm_employtt` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hrm_position`
--

DROP TABLE IF EXISTS `hrm_position`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `hrm_position` (
  `id` varchar(36) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hrm_position`
--

LOCK TABLES `hrm_position` WRITE;
/*!40000 ALTER TABLE `hrm_position` DISABLE KEYS */;
INSERT INTO `hrm_position` VALUES ('06c3346d-1db9-459c-83fd-02120fca3313','技术1岗'),('720cc0a9-e63b-442d-b92c-fd104be0ee12','T2');
/*!40000 ALTER TABLE `hrm_position` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `id_role`
--

DROP TABLE IF EXISTS `id_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `id_role` (
  `id` varchar(36) NOT NULL,
  `fixed` bit(1) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `role_key` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_9skut3cftra9j5dl6358dlc6a` (`role_key`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_role`
--

LOCK TABLES `id_role` WRITE;
/*!40000 ALTER TABLE `id_role` DISABLE KEYS */;
INSERT INTO `id_role` VALUES ('14a8bf59-4305-4847-8d7c-f513e3c8a58f',_binary '\0','项目经理','PM'),('524f3058-68cd-4b77-97ac-d7abe7b7ebf9',_binary '\0','技术经理','TM'),('54ab5374-aee0-43a3-96e9-4be076701410',_binary '\0','网络管理员','NET_MANAGER'),('5bf2fc86-43da-419f-8614-f240470716e5',_binary '\0','系统管理员','SYS'),('80e7af06-9c55-4054-bdba-c40fe86c81b1',_binary '','普通用户','USER');
/*!40000 ALTER TABLE `id_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `id_user`
--

DROP TABLE IF EXISTS `id_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `id_user` (
  `id` varchar(36) NOT NULL,
  `birthday` date DEFAULT NULL,
  `login_name` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_nxxv0v5ks1ndp8w5uxod2d13` (`login_name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_user`
--

LOCK TABLES `id_user` WRITE;
/*!40000 ALTER TABLE `id_user` DISABLE KEYS */;
INSERT INTO `id_user` VALUES ('031cef83-c58c-44cd-a522-8625cb045f35','1970-01-01',NULL,'貂蝉','asdfasdf','SEPARATION'),('06f575ca-60c4-4584-a523-d8dd461595de','1970-01-01','qin','秦始皇','$2a$10$RRHj.cUU8/Kdt.AvTerlaet9esRtTor13gkAqGhyXYVXG.rpmXXlS','NORMAL'),('17ba8030-21f0-473b-bf6f-5e2437ddcf5f','1970-01-01','dasheng','齐天大圣','$2a$10$8wyuAOmRGyGVNks9wHjfsuyP4usoCv1Z0SMHECCCfAxu8cqN4HlUW','NORMAL'),('2cedce0f-a85a-40f4-bae8-26c45a7a9ddf','1970-01-01','guanyu','关羽','$2a$10$8wyuAOmRGyGVNks9wHjfsuyP4usoCv1Z0SMHECCCfAxu8cqN4HlUW','NORMAL'),('3560fc21-8b5f-463a-b961-366cd42828b7','2018-12-07','new_user','新增','12341234','NORMAL'),('3e10126c-f631-4dcc-9734-bc658053d9b2','1970-01-01','zsf','张三丰','asdfasdf',NULL),('5e232d3c-8af7-469b-800d-75d47efebc93','1970-01-01','xiexun','谢逊','asdfasdf',NULL),('77c9502c-9584-4276-96f6-8bb645e635fe','1970-01-01',NULL,'阿斗','asdfasdf','SEPARATION'),('7a3f572c-b881-4e2f-8c3a-56c54929950b','1970-01-01','yuhuang','玉皇','asdfasdf',NULL),('90145238-1dbf-4499-a3ad-f68c531b8f79','1970-01-01','huangyong','黄勇','asdfasdf',NULL),('b4adf5b2-c331-4015-99a9-6dd08d386146','1986-01-16','lwq2','罗文强1','asdfasdf',NULL),('bc682d38-b540-4df4-ae66-cd73cc342adc','1970-01-01',NULL,'朱良付','asdfasdf','SEPARATION'),('bc80c62b-4487-4695-a650-4de4b798c008','1970-01-01','ligang','李刚','asdfasdf',NULL),('c81e2b9d-09a3-4729-b6ac-d718c17ccd58','2012-12-01','lwq','罗文强','asdfasdf',NULL),('d23cda97-5a06-43d7-b70b-91d633f4726c','1970-01-01',NULL,'妲己','asdfasdf','SEPARATION'),('e2b68fd3-9de4-4876-ba21-dadc929dac2d','1970-01-01',NULL,'赵云','asdfasdf','SEPARATION'),('e83ba3c3-057f-443d-bfe3-852c280fffe0','1970-01-01','zwj','张无忌','asdfasdf',NULL),('f48e1d15-4c94-4617-8c5a-a846bcc1bbb3','1985-01-01','test','测试','',NULL),('fa29d696-cc93-4c12-9646-a93f23eb8798','1970-01-01','lixunhuan','李寻欢','$2a$10$IuLsqiCSKzYu4HhzD/t4nuj1hzj.GUb0mSjUh5DB8deFJ.IWSj.Pa','NORMAL');
/*!40000 ALTER TABLE `id_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `id_user_roles`
--

DROP TABLE IF EXISTS `id_user_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `id_user_roles` (
  `user_id` varchar(36) NOT NULL,
  `roles_id` varchar(36) NOT NULL,
  KEY `FK4rc2pfugsad4xf44s2y954m4f` (`roles_id`),
  KEY `FKh9mgpnsps7f37caf5konmr07g` (`user_id`),
  CONSTRAINT `FK4rc2pfugsad4xf44s2y954m4f` FOREIGN KEY (`roles_id`) REFERENCES `id_role` (`id`),
  CONSTRAINT `FKh9mgpnsps7f37caf5konmr07g` FOREIGN KEY (`user_id`) REFERENCES `id_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `id_user_roles`
--

LOCK TABLES `id_user_roles` WRITE;
/*!40000 ALTER TABLE `id_user_roles` DISABLE KEYS */;
INSERT INTO `id_user_roles` VALUES ('c81e2b9d-09a3-4729-b6ac-d718c17ccd58','54ab5374-aee0-43a3-96e9-4be076701410'),('c81e2b9d-09a3-4729-b6ac-d718c17ccd58','524f3058-68cd-4b77-97ac-d7abe7b7ebf9'),('b4adf5b2-c331-4015-99a9-6dd08d386146','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('b4adf5b2-c331-4015-99a9-6dd08d386146','524f3058-68cd-4b77-97ac-d7abe7b7ebf9'),('b4adf5b2-c331-4015-99a9-6dd08d386146','54ab5374-aee0-43a3-96e9-4be076701410'),('bc80c62b-4487-4695-a650-4de4b798c008','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('bc80c62b-4487-4695-a650-4de4b798c008','54ab5374-aee0-43a3-96e9-4be076701410'),('90145238-1dbf-4499-a3ad-f68c531b8f79','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('90145238-1dbf-4499-a3ad-f68c531b8f79','524f3058-68cd-4b77-97ac-d7abe7b7ebf9'),('90145238-1dbf-4499-a3ad-f68c531b8f79','54ab5374-aee0-43a3-96e9-4be076701410'),('3e10126c-f631-4dcc-9734-bc658053d9b2','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('3e10126c-f631-4dcc-9734-bc658053d9b2','54ab5374-aee0-43a3-96e9-4be076701410'),('e83ba3c3-057f-443d-bfe3-852c280fffe0','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('e83ba3c3-057f-443d-bfe3-852c280fffe0','14a8bf59-4305-4847-8d7c-f513e3c8a58f'),('e83ba3c3-057f-443d-bfe3-852c280fffe0','54ab5374-aee0-43a3-96e9-4be076701410'),('5e232d3c-8af7-469b-800d-75d47efebc93','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('5e232d3c-8af7-469b-800d-75d47efebc93','54ab5374-aee0-43a3-96e9-4be076701410'),('7a3f572c-b881-4e2f-8c3a-56c54929950b','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('7a3f572c-b881-4e2f-8c3a-56c54929950b','54ab5374-aee0-43a3-96e9-4be076701410'),('f48e1d15-4c94-4617-8c5a-a846bcc1bbb3','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('f48e1d15-4c94-4617-8c5a-a846bcc1bbb3','524f3058-68cd-4b77-97ac-d7abe7b7ebf9'),('f48e1d15-4c94-4617-8c5a-a846bcc1bbb3','54ab5374-aee0-43a3-96e9-4be076701410'),('3560fc21-8b5f-463a-b961-366cd42828b7','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('3560fc21-8b5f-463a-b961-366cd42828b7','14a8bf59-4305-4847-8d7c-f513e3c8a58f'),('3560fc21-8b5f-463a-b961-366cd42828b7','54ab5374-aee0-43a3-96e9-4be076701410'),('2cedce0f-a85a-40f4-bae8-26c45a7a9ddf','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('2cedce0f-a85a-40f4-bae8-26c45a7a9ddf','524f3058-68cd-4b77-97ac-d7abe7b7ebf9'),('17ba8030-21f0-473b-bf6f-5e2437ddcf5f','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('17ba8030-21f0-473b-bf6f-5e2437ddcf5f','54ab5374-aee0-43a3-96e9-4be076701410'),('fa29d696-cc93-4c12-9646-a93f23eb8798','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('fa29d696-cc93-4c12-9646-a93f23eb8798','5bf2fc86-43da-419f-8614-f240470716e5'),('2cedce0f-a85a-40f4-bae8-26c45a7a9ddf','5bf2fc86-43da-419f-8614-f240470716e5'),('06f575ca-60c4-4584-a523-d8dd461595de','80e7af06-9c55-4054-bdba-c40fe86c81b1');
/*!40000 ALTER TABLE `id_user_roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note`
--

DROP TABLE IF EXISTS `note`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note` (
  `id` varchar(36) NOT NULL,
  `content` longtext,
  `publish_time` datetime(6) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `write_time` datetime(6) DEFAULT NULL,
  `type_id` varchar(36) DEFAULT NULL,
  `write_user_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK9527dlwrq1xicouxhg2s2sfv1` (`type_id`),
  KEY `FKf9rshpdyy0rsciwbnx4742ox9` (`write_user_id`),
  CONSTRAINT `FK9527dlwrq1xicouxhg2s2sfv1` FOREIGN KEY (`type_id`) REFERENCES `note_type` (`id`),
  CONSTRAINT `FKf9rshpdyy0rsciwbnx4742ox9` FOREIGN KEY (`write_user_id`) REFERENCES `id_user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note`
--

LOCK TABLES `note` WRITE;
/*!40000 ALTER TABLE `note` DISABLE KEYS */;
INSERT INTO `note` VALUES ('0827e260-229e-42da-875b-ed1abe7fe866','<p>由于XXX原因，撤回原有【今天晚上一起看月亮】公告，原文：</p><s><p>都说十五的月亮十六圆，今天十六，正好看月亮。</p></s>','2018-09-25 09:35:35.038000','PUBLISHED','撤回【今天晚上一起看月亮】','2018-09-25 09:35:35.038000','9f817bed-4cbd-4633-a577-033378354176',NULL),('09c87902-d4cc-47f4-98f8-ad28df382821','<p><img src=\"/storage/file/567cf53a-b2fc-4d2a-bad0-e2953d4919fd\" style=\"max-width:100%;\"><br></p>','2018-09-25 09:14:59.540000','PUBLISHED','测试图片上传','2018-09-22 17:18:46.722000','76ab2edb-c34e-49e6-8607-a05bda7c4a97','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('0f922e15-7f8b-45c6-a60f-227e0b0b1600','<pre><code>   &lt;div id=\"div1\"&gt;\r\n        &lt;p&gt;欢迎使用 &lt;b&gt;wangEditor&lt;/b&gt; 富文本编辑器&lt;/p&gt;\r\n    &lt;/div&gt;\r\n    &lt;textarea id=\"text1\" style=\"width:100%; height:200px;\"&gt;&lt;/textarea&gt;\r\n\r\n    &lt;script src=\"https://cdn.bootcss.com/jquery/3.2.1/jquery.min.js\"&gt;&lt;/script&gt;\r\n    &lt;script type=\"text/javascript\" src=\"../wangEditor.js\"&gt;&lt;/script&gt;\r\n    &lt;script type=\"text/javascript\"&gt;\r\n        var E = window.wangEditor\r\n        var editor = new E(\'#div1\')\r\n        var $text1 = $(\'#text1\')\r\n        editor.customConfig.onchange = function (html) {\r\n            // 监控变化，同步更新到 textarea\r\n            $text1.val(html)\r\n        }\r\n        editor.create()\r\n        // 初始化 textarea 的值\r\n        $text1.val(editor.txt.html())\r\n    &lt;/script&gt;</code></pre>',NULL,'DRAFT','为何之前已经处理的违章，现在又变成未处理了？','2018-09-22 15:18:27.424000','76ab2edb-c34e-49e6-8607-a05bda7c4a97','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('122018bc-a1f2-4743-ab37-72e715177267','','2018-09-25 10:10:10.685000','REVOKED','11111','2018-09-25 10:10:06.431000','f543dd2d-1fc3-4f96-b121-87ad9271d3d6','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('148ef265-2be2-4c89-ada3-983c7bf9879d','<p>dd</p>','2018-09-25 10:04:25.839000','REVOKED','为何之前已经处理的违章，现在又变成未处理了？','2018-09-25 10:04:13.213000','f543dd2d-1fc3-4f96-b121-87ad9271d3d6','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('28cc749a-0d85-4796-b67d-09464e0acb42','<p>由于2222222222原因，撤回原有【11111】公告，原文：</p><s></s>','2018-09-25 10:10:21.175000','PUBLISHED','撤回【11111】','2018-09-25 10:10:21.175000','9f817bed-4cbd-4633-a577-033378354176',NULL),('3cfe150b-c5ff-49ff-a789-9532d023921e','<p>发布以后，我们就有钱了！</p>',NULL,'DRAFT','最近要发布一个非常版本','2018-09-22 16:04:03.911000','76ab2edb-c34e-49e6-8607-a05bda7c4a97','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('6408ba6a-c39e-40a4-8452-85c2cca80ff4',NULL,'2018-09-25 09:43:40.869000','PUBLISHED','为何之前已经处理的违章，现在又变成未处理了？','2018-09-22 15:12:16.086000','76ab2edb-c34e-49e6-8607-a05bda7c4a97','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('69979116-c323-44ba-ac5f-1fa6c01143b6','<p>网易科技讯 9月25日消息，据BBC报道，中国雄心勃勃、希望快速扩张的北斗卫星导航系统已经能够为世界许多地方（不仅仅是亚洲）提供服务，但它真的能与美国已经成熟的全球定位系统(GPS)匹敌吗？<br></p><p><img src=\"/storage/file/09325f91-ba05-448c-914a-ad10910ab85e\" style=\"max-width:100%;\"><br></p><p>图：随着2018年十多颗卫星发射升空，北斗卫星导航系统的覆盖范围正在迅速扩大<br></p><p><br></p><p>达林太（Dalintai）是中国北方的一名牧民，他以前每天骑摩托车跑好几公里路为牲畜送水。现在，他要做的就是发送一条短信来操作自动供水系统。达林太说：“我可以通过这个系统随时随地为牛羊送水。”这条短信是通过中国正在扩大的北斗卫星导航系统发送的，它已经被用于运输、农业甚至精确导弹。</p><p>最初，北斗卫星导航系统是为军方设计的，目的是减少对美国GPS系统的依赖。随着覆盖范围的扩大，北斗系统逐渐成为巨大的商业机会。上个月，北京市政府要求3.35万辆出租车(约占出租车总数的一半)安装北斗系统，同时还设定了一个目标，即到2020年所有新车都将由北斗系统引导。</p><p>华为、小米和一加等国内手机品牌现在都与北斗系统兼容，不过苹果在9月12日的发布会上表示，其新款iPhone系列不支持北斗系统。中国越来越热衷于向世界其他地区推广自己技术实力。北斗卫星导航系统的首席设计师杨长峰直言不讳地表达了中国吸引更多海外客户的雄心。他去年曾表示：“中国的北斗系统也是世界的北斗，全球卫星导航市场肯定也是北斗追寻的市场。”</p><p><b>太空丝绸之路</b></p><p>北斗卫星导航系统以北斗七星(西方大熊星座)命名，已经有20多年开发历史，但直到2000年、2012年才分别在中国和亚太地区投入使用。到2020年完成时，它将拥有35颗卫星，以提供全球覆盖。仅今年一年，中国就发射了10多颗北斗卫星，上周又发射了两颗。此外，中国官方媒体报道称，更多卫星将以“前所未有的密集度发射”。</p><p><img src=\"/storage/file/4904ce48-3784-4095-8cc4-e9a5fd5cfa2b\" style=\"max-width:100%;\"><br></p><p>图：北斗卫星导航系统扩张的时间线，中国计划到2020年在轨道上拥有35颗北斗卫星<br></p><p><br></p><p>到2018年底，北斗卫星导航系统将覆盖“一带一路”沿线国家。“一带一路”是中国主导的大规模基础设施和贸易项目，而北斗系统则被称为“太空丝绸之路”。北斗卫星导航系统已经覆盖了包括巴基斯坦、老挝以及印度尼西亚在内的30个国家。英国皇家联合军种防务与安全研究所的亚历山德拉·斯蒂金斯（Alexandra Stickings）表示:“这期间肯定有涉及扩大影响力的因素，但更多则是关于经济安全的。”</p><p>斯蒂金斯表示，能够与全球定位系统(GPS)匹敌的全球导航系统，是中国成为太空领域全球领导者雄心的一个重要组成部分。他说：“拥有自己系统的主要优势在于可保证访问的安全性，从某种意义上说，中国不需要再依赖其他国家提供类似服务。美国可以禁止用户访问某些特定地区，特别是在冲突时期。”</p>','2018-09-25 14:51:40.610000','PUBLISHED','中国北斗导航系统走向全球 它真能匹敌GPS吗','2018-09-25 14:51:28.405000','76ab2edb-c34e-49e6-8607-a05bda7c4a97','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('7556e56f-3632-4672-b498-0397701ab619','<p>都说十五的月亮十六圆，今天十六，正好看月亮。</p>','2018-09-25 09:16:40.711000','REVOKED','今天晚上一起看月亮','2018-09-25 09:16:26.950000','f543dd2d-1fc3-4f96-b121-87ad9271d3d6','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('80938a08-9a99-44c5-b6a6-8df37095292a','<p>由于不想混了原因，撤回原有【撤回对话框测试】公告，原文：</p><s><p>测试撤回</p></s>','2018-09-25 09:53:36.842000','PUBLISHED','撤回【撤回对话框测试】','2018-09-25 09:53:36.842000','9f817bed-4cbd-4633-a577-033378354176',NULL),('9ac217c5-8844-45a9-b05b-1dafa0d63e7c','<p>由于3333333333原因，撤回原有【333333】公告，原文：</p><s></s>','2018-09-25 10:15:27.667000','PUBLISHED','撤回【333333】','2018-09-25 10:15:27.667000','9f817bed-4cbd-4633-a577-033378354176','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('abc258a9-d48e-4b36-8f13-c65d80f617bc','<p>由于原因，撤回原有【为何之前已经处理的违章】公告，原文：</p><s></s>','2018-09-25 10:06:32.810000','PUBLISHED','撤回【为何之前已经处理的违章】','2018-09-25 10:06:32.810000','9f817bed-4cbd-4633-a577-033378354176',NULL),('b6cf55ee-b362-40a7-99f4-058f1e2b466f','','2018-09-25 10:06:27.950000','REVOKED','为何之前已经处理的违章','2018-09-25 10:06:19.945000','f543dd2d-1fc3-4f96-b121-87ad9271d3d6','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('beb292a3-ebae-4c40-9d86-c0963d2ad098','<p>测试撤回</p>','2018-09-25 09:44:11.394000','REVOKED','撤回对话框测试','2018-09-25 09:44:06.442000','f543dd2d-1fc3-4f96-b121-87ad9271d3d6','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('cc083390-0b62-4d11-824b-d376ac18f9ca','<p>由于222222原因，撤回原有【222222222】公告，原文：</p><s><p>222222222</p></s>','2018-09-25 10:12:47.882000','PUBLISHED','撤回【222222222】','2018-09-25 10:12:47.882000','9f817bed-4cbd-4633-a577-033378354176',NULL),('cca385c4-f1d6-47de-bc2a-14a8762e43df','<p>222222222</p>','2018-09-25 10:12:41.002000','REVOKED','222222222','2018-09-25 10:12:36.653000','f543dd2d-1fc3-4f96-b121-87ad9271d3d6','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('cd1dd760-558a-4568-8e44-b80d52b6e0a7','','2018-09-25 10:15:20.329000','REVOKED','333333','2018-09-25 10:15:16.454000','f543dd2d-1fc3-4f96-b121-87ad9271d3d6','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('dc21bcfb-9126-46d2-9217-e1c265b81506','<p>由于ddddddddddd原因，撤回原有【为何之前已经处理的违章，现在又变成未处理了？】公告，原文：</p><s><p>dd</p></s>','2018-09-25 10:04:32.694000','PUBLISHED','撤回【为何之前已经处理的违章，现在又变成未处理了？】','2018-09-25 10:04:32.694000','9f817bed-4cbd-4633-a577-033378354176',NULL);
/*!40000 ALTER TABLE `note` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note_read`
--

DROP TABLE IF EXISTS `note_read`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note_read` (
  `id` varchar(36) NOT NULL,
  `read_time` datetime(6) DEFAULT NULL,
  `note_id` varchar(36) DEFAULT NULL,
  `reader_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK86q94ei12qutjxqqix48kfxbk` (`note_id`),
  KEY `FK4dp5mt04wxl052m1kmdm9jp6e` (`reader_id`),
  CONSTRAINT `FK4dp5mt04wxl052m1kmdm9jp6e` FOREIGN KEY (`reader_id`) REFERENCES `id_user` (`id`),
  CONSTRAINT `FK86q94ei12qutjxqqix48kfxbk` FOREIGN KEY (`note_id`) REFERENCES `note` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note_read`
--

LOCK TABLES `note_read` WRITE;
/*!40000 ALTER TABLE `note_read` DISABLE KEYS */;
INSERT INTO `note_read` VALUES ('742ee69a-e687-485c-843f-977d3ff3a003','2018-09-25 15:07:51.663000','69979116-c323-44ba-ac5f-1fa6c01143b6','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf'),('771d499e-6315-4c55-9edd-231437583f3b','2018-09-25 15:11:14.670000','69979116-c323-44ba-ac5f-1fa6c01143b6','17ba8030-21f0-473b-bf6f-5e2437ddcf5f'),('xx','2012-12-01 00:00:00.000000','0827e260-229e-42da-875b-ed1abe7fe866','2cedce0f-a85a-40f4-bae8-26c45a7a9ddf');
/*!40000 ALTER TABLE `note_read` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `note_type`
--

DROP TABLE IF EXISTS `note_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `note_type` (
  `id` varchar(36) NOT NULL,
  `deletable` bit(1) DEFAULT NULL,
  `modifiable` bit(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `revocable` bit(1) DEFAULT NULL,
  `number` double NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `note_type`
--

LOCK TABLES `note_type` WRITE;
/*!40000 ALTER TABLE `note_type` DISABLE KEYS */;
INSERT INTO `note_type` VALUES ('76ab2edb-c34e-49e6-8607-a05bda7c4a97',_binary '',_binary '','公司新闻',_binary '\0',0),('9f817bed-4cbd-4633-a577-033378354176',_binary '\0',_binary '\0','撤回公告',_binary '\0',9999999),('f0e89f3b-6a35-45ca-baee-72a6b54d33be',_binary '',_binary '','招聘公告',_binary '\0',0),('f543dd2d-1fc3-4f96-b121-87ad9271d3d6',_binary '\0',_binary '\0','活动通知',_binary '',0);
/*!40000 ALTER TABLE `note_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `storage_file_info`
--

DROP TABLE IF EXISTS `storage_file_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `storage_file_info` (
  `id` varchar(255) NOT NULL,
  `content_length` bigint(20) DEFAULT NULL,
  `content_type` varchar(255) DEFAULT NULL,
  `file_path` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `upload_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `storage_file_info`
--

LOCK TABLES `storage_file_info` WRITE;
/*!40000 ALTER TABLE `storage_file_info` DISABLE KEYS */;
INSERT INTO `storage_file_info` VALUES ('09325f91-ba05-448c-914a-ad10910ab85e',18668,'image/jpeg','8101446c-affc-46ae-9f84-4667f6db421d','北斗.jpg','2018-09-25 14:50:29.719000'),('0e5b314c-80fb-49f9-a4d1-a034885f6989',32694,'image/png','c1816c1a-e14f-42b2-9054-6a19d15f9c4f','c242be5b8e62e06ae7670416b756a3ae.png','2018-09-25 16:36:44.723000'),('42f48a55-c504-4a0d-b395-04a89b0c4315',56889,'image/png','42079ffa-de1f-4889-bc71-642198f6caa2','2018-09-21 01-00-46 的屏幕截图.png','2018-09-22 17:13:54.785000'),('4904ce48-3784-4095-8cc4-e9a5fd5cfa2b',80622,'image/png','87244fe5-95b1-4082-8a06-8eed79c8c0f9','gps.png','2018-09-25 14:51:00.767000'),('567cf53a-b2fc-4d2a-bad0-e2953d4919fd',56889,'image/png','688b538b-98c1-4f78-a057-b9e9d82af927','2018-09-21 01-00-46 的屏幕截图.png','2018-09-22 17:17:37.298000'),('5b2afb83-cb39-4d5d-ba09-074cc0f336e0',932733,'image/png','6e41014d-3a37-4860-b6a6-dcf0f77f2bce','疯狂Java学习路线图.png','2018-09-22 17:14:11.093000'),('84d4b0f6-6be3-4539-b06c-6f3cc60654b2',56889,'image/png','6fa74d2e-f580-4de8-9e69-c0cdc2bcd921','2018-09-21 01-00-46 的屏幕截图.png','2018-09-22 16:52:25.158000'),('d176cd5d-1d91-4956-b2c6-1cf6fa1607e9',56889,'image/png','dcc16a02-cb27-4cce-845a-b136ac391df5','2018-09-21 01-00-46 的屏幕截图.png','2018-09-22 17:12:20.136000');
/*!40000 ALTER TABLE `storage_file_info` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu` (
  `id` varchar(36) NOT NULL,
  `method` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL,
  `parent_id` varchar(36) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK2jrf4gb0gjqi8882gxytpxnhe` (`parent_id`),
  CONSTRAINT `FK2jrf4gb0gjqi8882gxytpxnhe` FOREIGN KEY (`parent_id`) REFERENCES `sys_menu` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES ('09ad07cf-dde9-4d42-b9b7-6cb7e5775c63','GET','阅读公告','/note/read','609ad524-284d-4759-a383-a3b268dcde26'),('1a2ffc33-69f8-4b3c-874b-8aa0a5812959','GET','人事管理','/hrm',NULL),('1b74ae2a-9e54-41bc-8473-6599dbb47829','GET','系统管理','/system',NULL),('255efb69-956c-4dac-87b5-deaa31cf6e45','GET','公告维护','/note/maintain','609ad524-284d-4759-a383-a3b268dcde26'),('28e79db8-34f4-4135-b690-a5ae90c260d2','GET','公告类型管理','/note/type','609ad524-284d-4759-a383-a3b268dcde26'),('2d063992-7c8d-4333-952a-5b166d593335','GET','部门','/hrm/department','1a2ffc33-69f8-4b3c-874b-8aa0a5812959'),('373d6205-ae3a-491a-a29b-f248a42c439d','GET','菜单配置','/system/menu','1b74ae2a-9e54-41bc-8473-6599dbb47829'),('3c8b2911-8a47-4e82-a588-f5bad8907af5','GET','技术管理','/tm',NULL),('609ad524-284d-4759-a383-a3b268dcde26','GET','公告管理','/note',NULL),('65c20f10-4f09-4dd2-9321-2ba8b08ae548','POST','保存','/identity/role','703b0e7e-682c-413a-b161-ff3dcf2993bf'),('703b0e7e-682c-413a-b161-ff3dcf2993bf','GET','角色管理','/identity/role','1b74ae2a-9e54-41bc-8473-6599dbb47829'),('76fd64b3-e831-4d0c-92b0-dbf077d0d8f3','POST','保存','/storage/file','83fac622-06d2-4f84-ae01-94e165137ef0'),('8111974b-a2a2-41e2-afc4-b80b1dca8c73','GET','岗位','/hrm/position','1a2ffc33-69f8-4b3c-874b-8aa0a5812959'),('83fac622-06d2-4f84-ae01-94e165137ef0','GET','文件存储','/storage/file','e1414d02-7371-42bb-8a55-d608bbd7a15b'),('b0371c17-a8a4-4b0a-af6f-9c7773e79e1b','GET','新技术研讨','/tm/nt','3c8b2911-8a47-4e82-a588-f5bad8907af5'),('b21dc468-f711-4de2-8343-cf9a9c6a64f0','GET','员工','/hrm/employee','1a2ffc33-69f8-4b3c-874b-8aa0a5812959'),('ccda8ed5-fae0-4ecd-a94e-8b6167184bd3','GET','查看研讨计划','/tm/nt/**','b0371c17-a8a4-4b0a-af6f-9c7773e79e1b'),('ce6206e4-7f9b-4c0e-9aa6-bdcd06c1e9a5','GET','用户管理','/identity/user','1b74ae2a-9e54-41bc-8473-6599dbb47829'),('e1414d02-7371-42bb-8a55-d608bbd7a15b','GET','通用服务','/',NULL),('f53380e4-82a8-400b-9b09-137c6b6a3ef6','GET','撤回','/note/maintain/revoke','255efb69-956c-4dac-87b5-deaa31cf6e45');
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu_roles`
--

DROP TABLE IF EXISTS `sys_menu_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `sys_menu_roles` (
  `menu_id` varchar(36) NOT NULL,
  `role_id` varchar(36) NOT NULL,
  KEY `FKe19xptro1aluolimm64593a7v` (`role_id`),
  KEY `FK9hsn6sstemr6nikky88rtbu9g` (`menu_id`),
  CONSTRAINT `FK9hsn6sstemr6nikky88rtbu9g` FOREIGN KEY (`menu_id`) REFERENCES `sys_menu` (`id`),
  CONSTRAINT `FKe19xptro1aluolimm64593a7v` FOREIGN KEY (`role_id`) REFERENCES `id_role` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu_roles`
--

LOCK TABLES `sys_menu_roles` WRITE;
/*!40000 ALTER TABLE `sys_menu_roles` DISABLE KEYS */;
INSERT INTO `sys_menu_roles` VALUES ('65c20f10-4f09-4dd2-9321-2ba8b08ae548','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('65c20f10-4f09-4dd2-9321-2ba8b08ae548','524f3058-68cd-4b77-97ac-d7abe7b7ebf9'),('b0371c17-a8a4-4b0a-af6f-9c7773e79e1b','524f3058-68cd-4b77-97ac-d7abe7b7ebf9'),('ccda8ed5-fae0-4ecd-a94e-8b6167184bd3','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('3c8b2911-8a47-4e82-a588-f5bad8907af5','54ab5374-aee0-43a3-96e9-4be076701410'),('373d6205-ae3a-491a-a29b-f248a42c439d','5bf2fc86-43da-419f-8614-f240470716e5'),('ce6206e4-7f9b-4c0e-9aa6-bdcd06c1e9a5','5bf2fc86-43da-419f-8614-f240470716e5'),('703b0e7e-682c-413a-b161-ff3dcf2993bf','14a8bf59-4305-4847-8d7c-f513e3c8a58f'),('703b0e7e-682c-413a-b161-ff3dcf2993bf','5bf2fc86-43da-419f-8614-f240470716e5'),('609ad524-284d-4759-a383-a3b268dcde26','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('09ad07cf-dde9-4d42-b9b7-6cb7e5775c63','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('255efb69-956c-4dac-87b5-deaa31cf6e45','5bf2fc86-43da-419f-8614-f240470716e5'),('28e79db8-34f4-4135-b690-a5ae90c260d2','5bf2fc86-43da-419f-8614-f240470716e5'),('83fac622-06d2-4f84-ae01-94e165137ef0','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('76fd64b3-e831-4d0c-92b0-dbf077d0d8f3','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('f53380e4-82a8-400b-9b09-137c6b6a3ef6','80e7af06-9c55-4054-bdba-c40fe86c81b1'),('1a2ffc33-69f8-4b3c-874b-8aa0a5812959','5bf2fc86-43da-419f-8614-f240470716e5'),('2d063992-7c8d-4333-952a-5b166d593335','5bf2fc86-43da-419f-8614-f240470716e5'),('b21dc468-f711-4de2-8343-cf9a9c6a64f0','5bf2fc86-43da-419f-8614-f240470716e5'),('8111974b-a2a2-41e2-afc4-b80b1dca8c73','5bf2fc86-43da-419f-8614-f240470716e5');
/*!40000 ALTER TABLE `sys_menu_roles` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-09-28 17:04:53
