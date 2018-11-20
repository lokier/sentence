# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.7.20)
# Database: jfinal_club
# Generation Time: 2018-09-01 09:11:17 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table account
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account`;

CREATE TABLE `account` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickName` varchar(50) NOT NULL,
  `userName` varchar(150) NOT NULL,
  `password` varchar(150) NOT NULL,
  `salt` varchar(150) NOT NULL,
  `status` int(11) NOT NULL,
  `createAt` datetime NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `avatar` varchar(100) NOT NULL,
  `likeCount` int(11) NOT NULL DEFAULT '0' COMMENT '被赞次数',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;

INSERT INTO `account` (`id`, `nickName`, `userName`, `password`, `salt`, `status`, `createAt`, `ip`, `avatar`, `likeCount`)
VALUES
	(1,'JFinalClub','test@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',1,'2018-04-18 09:00:19','175.12.244.105','0/1.jpg',999),
	(2,'伽利略','test1@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',1,'2018-04-19 10:19:11','175.12.244.105','0/1.jpg',0),
	(3,'牛顿','test2@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',1,'2018-04-20 10:05:08','175.12.244.105','0/1.jpg',0),
	(4,'爱因斯坦','test3@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',1,'2018-04-21 12:20:15','175.12.244.105','0/1.jpg',0),
	(5,'银河系','test4@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',-1,'2018-04-22 09:20:18','175.12.244.105','0/1.jpg',0),
	(6,'极速开发','test5@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',1,'2018-04-23 10:20:35','175.12.244.105','0/1.jpg',0),
	(7,'enjoy','test6@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',1,'2018-04-23 11:20:31','175.12.244.105','0/1.jpg',0),
	(8,'jfinal','test7@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',1,'2018-04-24 12:20:53','175.12.244.105','0/1.jpg',0),
	(9,'俱乐部第一美女','test8@test.com','a1f0917284a75c2c45dfeefd9040ce01144407c1a33d1bc3c45153ceb9d12d72','zmxyyZJkE-N6JjRhujp6U8l4Yu7vuQDZ',1,'2018-04-25 09:58:19','175.12.244.105','0/2.jpg',0);

/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table account_role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `account_role`;

CREATE TABLE `account_role` (
  `accountId` int(11) NOT NULL,
  `roleId` int(11) NOT NULL,
  PRIMARY KEY (`accountId`,`roleId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `account_role` WRITE;
/*!40000 ALTER TABLE `account_role` DISABLE KEYS */;

INSERT INTO `account_role` (`accountId`, `roleId`)
VALUES
	(1,1),
	(4,4),
	(6,3),
	(7,2),
	(8,1),
	(9,4),
	(9,5),
	(9,6),
	(9,7);

/*!40000 ALTER TABLE `account_role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table auth_code
# ------------------------------------------------------------

DROP TABLE IF EXISTS `auth_code`;

CREATE TABLE `auth_code` (
  `id` varchar(33) NOT NULL,
  `accountId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  `type` int(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table document
# ------------------------------------------------------------

DROP TABLE IF EXISTS `document`;

CREATE TABLE `document` (
  `mainMenu` int(11) NOT NULL COMMENT '主菜单',
  `subMenu` int(11) NOT NULL COMMENT '子菜单',
  `title` varchar(300) NOT NULL,
  `content` text NOT NULL,
  `updateAt` datetime NOT NULL,
  `createAt` datetime NOT NULL,
  `publish` tinyint(2) NOT NULL DEFAULT '0',
  PRIMARY KEY (`mainMenu`,`subMenu`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `document` WRITE;
/*!40000 ALTER TABLE `document` DISABLE KEYS */;

INSERT INTO `document` (`mainMenu`, `subMenu`, `title`, `content`, `updateAt`, `createAt`, `publish`)
VALUES
	(1,0,'前言','<p>1：版本说明</p><p>2：术语约定</p><p>3：最佳实践<br/></p><p>4：</p>','2016-09-27 16:08:48','2016-09-25 16:40:09',1),
	(1,1,'概要','<h2>1、文档说明</h2><p>&nbsp; &nbsp; 社区文档频道将提供全面、权威、最新的开发文档，文档内容将随着项目发展、用户反饭不断进化、更新，建议以在线的形式查阅，保障始终获取到的信息是最新最权威的<br/></p><h2>2、版权声明</h2><p>&nbsp; &nbsp; JFinal 文档频道版权归 JFinal 社区所有，未经许可不得转载</p><h2>3、扫码入社</h2><p>&nbsp; &nbsp; 扫描下方二维码关注 JFinal 官方公众号，获取社区最新动态<br/></p><p style=\"text-align: center;\"><br/><img src=\"/assets/img/jfinal_weixin_service_qr_258.png\"/></p>','2016-09-28 21:20:57','2016-09-25 16:41:27',1),
	(1,2,'术语约定','<p>&nbsp; &nbsp; 文档内容将会不可避免的反复用到相同的名称、代码等，为了使文档内容简短、精要，所以在此对一些常用术语进行约定，这些约定无需记忆，了解即可</p><h2>1：AppConfig</h2><p>&nbsp; &nbsp;约定 AppConfig 为项目中的继承自 JFinalConfig 的类文件</p><h2>2：configXxx系列</h2><p>&nbsp; &nbsp;约定configXxx系列方法为 AppConfig 之中的方法，共有如下六个方 法：configConstant(...)、configRoute(...)、configEngine(...)、configHandler(...)、configInterceptor(...)、configPlugin(...)</p><p><br/></p>','2016-09-28 21:01:13','2016-09-25 17:56:11',1),
	(2,0,'最佳实践','<p>1：</p>','2016-09-27 16:26:49','2016-09-27 16:23:05',1),
	(2,1,'概要','<h2>1 概要</h2><p>&nbsp; &nbsp; 本章将介绍 JFinal 开发的最佳实践，合理的分层与组织结构是对复杂性最有效的管理方法，遵循最佳实践，不仅可以进一步提升开发效率，而且在项目演化的生命周期具有更好的可扩展性和可维护性，极大降低成本</p><h2>2 项目分层</h2><p>&nbsp; &nbsp; JFinal 最佳实践需划分 MVCS 四层，其中 MVC 是大家所熟知的Model、View、Controller， S是指Service业务层，Service 层是 JFinal 项目的核心，是重中之重</p><h2>3 模块划分</h2><p>&nbsp; &nbsp; 采用分而治之的策略进行模块划分，将复杂问题逐步转化为便于解决的简单问题</p><p>&nbsp; &nbsp; 模块划分采用分类的方式，对不同类别的概念进行识别分类，按类别划分模块。大的概念划分为小概念的组合，进而大类别划分为小类别的组合</p><p>&nbsp; &nbsp; 在项目初期认知还比较模糊的时候可以暂时依据 tableName 来划分。例如有三张表：project、share、feedback，则创建与这三张表同名的顶层 package，下图是 jfinal 社区的顶层 package 截图</p>','2016-09-29 11:41:58','2016-09-27 16:23:53',1),
	(2,2,'Model层','<h2>1 集中管理</h2><p>&nbsp; &nbsp; 有生命力的系统会不断进化与生长，对并发的要求会越来越高，当优化 sql 与引入 cache 这两种方式无法满足需要后，就进入了集群 + 分布式进化阶段</p><p>&nbsp; &nbsp; 分布式需要将大系统拆分成多个小型系统，而 Model 集中管理有利于这种拆分。将所有 Model 放在 common.model 这个包下非常容易抽取成一个maven 模块共享给分布式系统的其它模块</p><h2>2 使用Generator</h2><p>&nbsp; &nbsp;使用 Generator 生成的 model 符合 java bean 规范，并立即拥有 getter、setter 方法，有利于大量依靠 java bean 规范而工作的第三方工具，例如 fastjson、jackson。<br/></p><p>&nbsp; &nbsp;此外，生成的 model 拥有静态语言的好处，无需记忆字段名称，便于重构</p><h2>3 Model代码</h2><p>&nbsp; &nbsp; Model 中不要放业务逻辑，仅仅放一些纯粹 model 自己内部状态有关的通用方法，model 是要被其它业务模块共用的，不要放与具体某个业务有前端的代码，同样这也有利于未来的分布式</p><p>&nbsp; &nbsp;例如有一张 account 表，其中有一个 int status 字段，以下是代码示例：</p><pre class=\"brush:java;toolbar:false\">public&nbsp;class&nbsp;Account&nbsp;extends&nbsp;BaseAccount&lt;Account&gt;&nbsp;{\n&nbsp;&nbsp;\n&nbsp;&nbsp;public&nbsp;static&nbsp;final&nbsp;int&nbsp;STATUS_LOCK_ID&nbsp;=&nbsp;-1;&nbsp;&nbsp;//&nbsp;锁定账号\n&nbsp;&nbsp;public&nbsp;static&nbsp;final&nbsp;int&nbsp;STATUS_REG&nbsp;=&nbsp;0;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//&nbsp;注册、未激活\n&nbsp;&nbsp;public&nbsp;static&nbsp;final&nbsp;int&nbsp;STATUS_OK&nbsp;=&nbsp;1;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;//&nbsp;正常、已激活\n\n&nbsp;&nbsp;public&nbsp;boolean&nbsp;isStatusOk()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;getStatus()&nbsp;==&nbsp;STATUS_OK;\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;public&nbsp;boolean&nbsp;isStatusReg()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;getStatus()&nbsp;==&nbsp;STATUS_REG;\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;public&nbsp;boolean&nbsp;isStatusLockId()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;return&nbsp;getStatus()&nbsp;==&nbsp;STATUS_LOCK_ID;\n&nbsp;&nbsp;}\n｝</pre><h2>4：避免Model中创建dao</h2><p>&nbsp; &nbsp; JFinal 以往提供 demo 的 Model 中做了一个不好的示范，在其中创建了 public static final Xxx dao 对象，原本是为了在查询时可以少创建一次对象，但发现有很多用户使用该 dao 对象进行了查询以外的操作，例如 save()、update()、set() 等操作</p><p>&nbsp; &nbsp; 由于 static Xxx dao 对象是全局共享的，所以会有线程安全问题，为了彻底杜绝新手的误用，jfinal 最佳实践需要将 dao 对象从 Model 中删除，转而在 Service 中创建，例如：</p><pre class=\"brush:java;toolbar:false\">public&nbsp;class&nbsp;AccountService&nbsp;{\n\n&nbsp;&nbsp;private&nbsp;Account&nbsp;dao&nbsp;=&nbsp;new&nbsp;Account();\n&nbsp;&nbsp;\n&nbsp;&nbsp;public&nbsp;Ret&nbsp;login(String&nbsp;userName,&nbsp;String&nbsp;password)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;String&nbsp;sql=&nbsp;&quot;select&nbsp;*&nbsp;from&nbsp;account&nbsp;where&nbsp;userName=?&nbsp;limit&nbsp;1&quot;;\n&nbsp;&nbsp;&nbsp;&nbsp;Account&nbsp;account&nbsp;=&nbsp;dao.findFirst(sql,&nbsp;userName);\n&nbsp;&nbsp;&nbsp;&nbsp;//&nbsp;...&nbsp;其它代码\n&nbsp;&nbsp;}\n}</pre><p>&nbsp; &nbsp;如上代码所示， dao 对象从 Account 中转移到了 AccountService 中，并声明为 private 避免外界对该对象的使用。同时也避免了线程安全问题。<br/></p>','2016-09-29 23:37:09','2016-09-27 16:25:40',1),
	(2,3,'Service层','<h2>1 用到sql的代码</h2><p>&nbsp; &nbsp;有些开发者习惯于 sql 随手就来，不管是在哪里只要有需要就直接 sql 操作数据库。这种习惯表面上会带来开发效率与便利性，但事实并非如此<br/></p><p>&nbsp; &nbsp;&nbsp;</p><h2>2 与业务有关的代码</h2><p><br/></p><h2>2 优先考虑业务层</h2><p>&nbsp; &nbsp;当要写代码时，优先考虑将代码写在 Service 层中，例如有个 RegValidator 用于注册时对表单提交的数据进行校验，其中需要判断一项 nickName 是否已被注删，在 RegValidator 中有如下代码：</p><pre class=\"brush:java;toolbar:false\">public&nbsp;class&nbsp;RegValidator&nbsp;extends&nbsp;Validator&nbsp;{\n&nbsp;&nbsp;public&nbsp;void&nbsp;validate(Controller&nbsp;c)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;String&nbsp;sql&nbsp;=&nbsp;&quot;select&nbsp;id&nbsp;from&nbsp;account&nbsp;where&nbsp;nickName=?&nbsp;limit&nbsp;1&quot;;\n&nbsp;&nbsp;&nbsp;&nbsp;Account&nbsp;account&nbsp;=&nbsp;new&nbsp;Account().findFirst(sql,&nbsp;c.getPara(&quot;nickName&quot;));\n&nbsp;&nbsp;&nbsp;&nbsp;if&nbsp;(account&nbsp;!=&nbsp;null)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;addError(&quot;msg&quot;,&nbsp;&quot;nickName&nbsp;已被注册&quot;);\n&nbsp;&nbsp;&nbsp;&nbsp;}\n&nbsp;&nbsp;&nbsp;&nbsp;//&nbsp;...&nbsp;其它代码\n&nbsp;&nbsp;}\n}</pre><p>&nbsp; &nbsp; 最佳实践是 nickName 是否被注册的代码写在 AccountService 层中，有利于代码重用，有助于未来分布式演化，有利于在业务层添加缓存等机制，将代码挪至业务层后 RegValidator 的样子：</p><pre class=\"brush:java;toolbar:false\">public&nbsp;class&nbsp;RegValidator&nbsp;extends&nbsp;Validator&nbsp;{\n&nbsp;&nbsp;public&nbsp;void&nbsp;validate(Controller&nbsp;c)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;if&nbsp;(AccountService.me.isNickNameRegisted(c.getPara(&quot;nickName&quot;)))&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;addError(&quot;msg&quot;,&nbsp;&quot;nickName&nbsp;已被注册&quot;):\n&nbsp;&nbsp;&nbsp;&nbsp;}\n&nbsp;&nbsp;}\n}</pre><p><br/></p>','2016-09-30 00:00:51','2016-09-27 16:26:01',1),
	(2,4,'Controller层','<p>待添加</p>','2016-09-28 21:22:14','2016-09-28 21:22:14',1),
	(2,5,'View层','<p>待添加</p>','2016-09-28 21:22:55','2016-09-28 21:22:37',1),
	(2,6,'其它','<p>待添加</p>','2016-09-28 21:23:33','2016-09-28 21:23:33',1),
	(3,0,'JFinal架构','<p>待添加</p>','2016-09-27 16:09:27','2016-09-26 21:53:36',1),
	(3,1,'顶层架构','<p>待添加</p>','2016-09-27 16:10:09','2016-09-26 21:14:44',1);

/*!40000 ALTER TABLE `document` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table download
# ------------------------------------------------------------

DROP TABLE IF EXISTS `download`;

CREATE TABLE `download` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `fileName` varchar(280) NOT NULL,
  `descr` varchar(280) NOT NULL COMMENT '描述',
  `fileType` varchar(20) NOT NULL COMMENT '文件类型',
  `size` varchar(20) NOT NULL,
  `createDate` date NOT NULL,
  `path` varchar(280) NOT NULL,
  `downloadCount` int(11) NOT NULL,
  `isShow` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `download` WRITE;
/*!40000 ALTER TABLE `download` DISABLE KEYS */;

INSERT INTO `download` (`id`, `fileName`, `descr`, `fileType`, `size`, `createDate`, `path`, `downloadCount`, `isShow`)
VALUES
	(42,'jfinal-3.4-manual.pdf','JFinal 手册','PDF','1.39 MB','2016-01-19','/download/3.4/',140252,1),
	(43,'jfinal-3.4-all.zip','JFinal 3.4 all','ZIP','20.26 MB','2016-01-19','/download/3.4/',127351,1),
	(44,'jfinal-3.4_demo.zip','JFinal demo','ZIP','5.91 MB','2016-01-19','/download/3.4/',123110,1),
	(45,'GeneratorDemo.java','Generator demo','Java','2 KB','2016-01-19','/download/3.4/',110699,1),
	(46,'jfinal-weixin-1.7-bin-with-src.jar','JFinal weixin 1.7','JAR','258 KB','2016-01-12','/download/3.4/',11633,0),
	(47,'jfinal-weixin-1.8-bin-with-src.jar','JFinal Weixin 1.8','JAR','279 KB','2016-07-11','/download/3.4/',13503,1),
	(48,'jfinal-weixin-1.8-lib.zip','JFinal Weixin 1.8 lib','ZIP','4.31 MB','2016-07-11','/download/3.4/',11312,1),
	(49,'jfinal-3.4-changelog.txt','JFinal changelog','TXT','6 KB','2016-01-19','/download/3.4/',15590,1);

/*!40000 ALTER TABLE `download` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table download_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `download_log`;

CREATE TABLE `download_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  `fileName` varchar(200) NOT NULL,
  `downloadDate` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table favorite
# ------------------------------------------------------------

DROP TABLE IF EXISTS `favorite`;

CREATE TABLE `favorite` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `refType` int(11) NOT NULL COMMENT '收藏类型：1为项目，2为分享，3为反馈',
  `refId` int(11) NOT NULL COMMENT '被收藏的资源 id',
  `createAt` datetime NOT NULL COMMENT '收藏时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table feedback
# ------------------------------------------------------------

DROP TABLE IF EXISTS `feedback`;

CREATE TABLE `feedback` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `projectId` int(11) NOT NULL,
  `title` varchar(150) NOT NULL,
  `content` text NOT NULL,
  `createAt` datetime NOT NULL,
  `clickCount` int(11) NOT NULL DEFAULT '0',
  `report` int(11) NOT NULL DEFAULT '0',
  `likeCount` int(11) NOT NULL DEFAULT '0',
  `favoriteCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `feedback` WRITE;
/*!40000 ALTER TABLE `feedback` DISABLE KEYS */;

INSERT INTO `feedback` (`id`, `accountId`, `projectId`, `title`, `content`, `createAt`, `clickCount`, `report`, `likeCount`, `favoriteCount`)
VALUES
	(4,1,1,'反馈测试贴','<p>提问要描述清楚，反馈提升价值</p>','2018-04-27 16:05:45',0,0,0,0);

/*!40000 ALTER TABLE `feedback` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table feedback_like
# ------------------------------------------------------------

DROP TABLE IF EXISTS `feedback_like`;

CREATE TABLE `feedback_like` (
  `accountId` int(11) NOT NULL,
  `refId` int(11) NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`accountId`,`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table feedback_page_view
# ------------------------------------------------------------

DROP TABLE IF EXISTS `feedback_page_view`;

CREATE TABLE `feedback_page_view` (
  `feedbackId` varchar(25) NOT NULL,
  `visitDate` date NOT NULL,
  `visitCount` int(20) NOT NULL,
  PRIMARY KEY (`feedbackId`,`visitDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `feedback_page_view` WRITE;
/*!40000 ALTER TABLE `feedback_page_view` DISABLE KEYS */;

INSERT INTO `feedback_page_view` (`feedbackId`, `visitDate`, `visitCount`)
VALUES
	('4','2018-04-28',1),
	('4','2018-06-15',1),
	('4','2018-06-22',1);

/*!40000 ALTER TABLE `feedback_page_view` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table feedback_reply
# ------------------------------------------------------------

DROP TABLE IF EXISTS `feedback_reply`;

CREATE TABLE `feedback_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `feedbackId` int(11) NOT NULL,
  `accountId` int(11) NOT NULL,
  `content` text NOT NULL,
  `createAt` datetime NOT NULL,
  `report` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `feedback_reply` WRITE;
/*!40000 ALTER TABLE `feedback_reply` DISABLE KEYS */;

INSERT INTO `feedback_reply` (`id`, `feedbackId`, `accountId`, `content`, `createAt`, `report`)
VALUES
	(1,4,1,'1111','2018-06-22 11:35:25',0),
	(3,4,1,'3333','2018-06-22 11:35:28',0),
	(5,4,1,'55555','2018-06-22 11:35:31',0);

/*!40000 ALTER TABLE `feedback_reply` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table friend
# ------------------------------------------------------------

DROP TABLE IF EXISTS `friend`;

CREATE TABLE `friend` (
  `accountId` int(11) NOT NULL,
  `friendId` int(11) NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`accountId`,`friendId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table like_message_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `like_message_log`;

CREATE TABLE `like_message_log` (
  `accountId` int(11) NOT NULL,
  `refType` int(11) NOT NULL,
  `refId` int(11) NOT NULL,
  `createAt` datetime NOT NULL COMMENT 'creatAt用于未来清除该表中时间比较久远的记录',
  PRIMARY KEY (`accountId`,`refType`,`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='用于保存点赞的记录，用于记录点赞后发布过系统消息，保障只发一次';



# Dump of table login_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `login_log`;

CREATE TABLE `login_log` (
  `accountId` int(11) NOT NULL,
  `loginAt` datetime NOT NULL,
  `ip` varchar(100) DEFAULT NULL,
  KEY `accountId_index` (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `login_log` WRITE;
/*!40000 ALTER TABLE `login_log` DISABLE KEYS */;

INSERT INTO `login_log` (`accountId`, `loginAt`, `ip`)
VALUES
	(1,'2018-09-01 17:10:08','0:0:0:0:0:0:0:1');

/*!40000 ALTER TABLE `login_log` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table message
# ------------------------------------------------------------

DROP TABLE IF EXISTS `message`;

CREATE TABLE `message` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user` int(11) NOT NULL COMMENT '消息的主人',
  `friend` int(11) NOT NULL COMMENT '对方的ID',
  `sender` int(11) NOT NULL COMMENT '发送者',
  `receiver` int(11) NOT NULL COMMENT '接收者',
  `type` tinyint(2) NOT NULL COMMENT '0：普通消息，1：系统消息',
  `content` text NOT NULL,
  `createAt` datetime NOT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table news_feed
# ------------------------------------------------------------

DROP TABLE IF EXISTS `news_feed`;

CREATE TABLE `news_feed` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL COMMENT '动态创建者',
  `refType` tinyint(2) NOT NULL COMMENT '动态引用类型',
  `refId` int(11) NOT NULL DEFAULT '0' COMMENT '动态引用所关联的 id',
  `refParentType` tinyint(2) NOT NULL DEFAULT '0' COMMENT 'reply所属的贴子类型, 与type 字段填的值一样',
  `refParentId` int(11) NOT NULL DEFAULT '0',
  `createAt` datetime NOT NULL COMMENT '动态创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `news_feed` WRITE;
/*!40000 ALTER TABLE `news_feed` DISABLE KEYS */;

INSERT INTO `news_feed` (`id`, `accountId`, `refType`, `refId`, `refParentType`, `refParentId`, `createAt`)
VALUES
	(28,1,5,4,0,0,'2018-04-27 16:05:45'),
	(30,1,1,5,0,0,'2018-04-27 23:22:18'),
	(31,1,1,6,0,0,'2018-04-27 23:25:12'),
	(32,1,1,7,0,0,'2018-04-27 23:26:50'),
	(33,1,3,13,0,0,'2018-04-27 23:38:57'),
	(34,1,1,8,0,0,'2018-04-27 23:40:45'),
	(35,9,4,1,3,13,'2018-06-14 16:55:47'),
	(36,1,4,2,3,14,'2018-06-16 00:01:15'),
	(42,1,6,1,5,4,'2018-06-22 11:35:25'),
	(44,1,6,3,5,4,'2018-06-22 11:35:28'),
	(46,1,6,5,5,4,'2018-06-22 11:35:31');

/*!40000 ALTER TABLE `news_feed` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `permission`;

CREATE TABLE `permission` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actionKey` varchar(512) NOT NULL DEFAULT '',
  `controller` varchar(512) NOT NULL DEFAULT '',
  `remark` varchar(1024) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `permission` WRITE;
/*!40000 ALTER TABLE `permission` DISABLE KEYS */;

INSERT INTO `permission` (`id`, `actionKey`, `controller`, `remark`)
VALUES
	(1,'/admin','com.jfinal.club._admin.index.IndexAdminController','后台管理首页'),
	(2,'/admin/account','com.jfinal.club._admin.account.AccountAdminController','账户管理首页'),
	(3,'/admin/account/addRole','com.jfinal.club._admin.account.AccountAdminController','添加角色'),
	(4,'/admin/account/assignRoles','com.jfinal.club._admin.account.AccountAdminController','分配角色'),
	(5,'/admin/account/deleteRole','com.jfinal.club._admin.account.AccountAdminController','删除角色'),
	(6,'/admin/account/edit','com.jfinal.club._admin.account.AccountAdminController','修改账户'),
	(7,'/admin/account/lock','com.jfinal.club._admin.account.AccountAdminController','锁定账户'),
	(8,'/admin/account/unlock','com.jfinal.club._admin.account.AccountAdminController','解锁账户'),
	(9,'/admin/account/update','com.jfinal.club._admin.account.AccountAdminController','更新账户'),
	(10,'/admin/feedback','com.jfinal.club._admin.feedback.FeedbackAdminController','反馈管理首页'),
	(11,'/admin/feedback/delete','com.jfinal.club._admin.feedback.FeedbackAdminController','删除反馈'),
	(12,'/admin/feedback/deleteReply','com.jfinal.club._admin.feedback.FeedbackAdminController','删除反馈回复'),
	(13,'/admin/feedback/edit','com.jfinal.club._admin.feedback.FeedbackAdminController','修改反馈'),
	(14,'/admin/feedback/lock','com.jfinal.club._admin.feedback.FeedbackAdminController','锁定反馈'),
	(15,'/admin/feedback/getReplyList','com.jfinal.club._admin.feedback.FeedbackAdminController','获取反馈回复列表'),
	(16,'/admin/feedback/unlock','com.jfinal.club._admin.feedback.FeedbackAdminController','解锁反馈'),
	(17,'/admin/feedback/update','com.jfinal.club._admin.feedback.FeedbackAdminController','更新反馈'),
	(18,'/admin/permission','com.jfinal.club._admin.permission.PermissionAdminController','权限管理首页'),
	(19,'/admin/permission/delete','com.jfinal.club._admin.permission.PermissionAdminController','删除权限'),
	(20,'/admin/permission/edit','com.jfinal.club._admin.permission.PermissionAdminController','修改权限'),
	(21,'/admin/permission/sync','com.jfinal.club._admin.permission.PermissionAdminController','同步权限'),
	(22,'/admin/permission/update','com.jfinal.club._admin.permission.PermissionAdminController','更新权限'),
	(23,'/admin/project','com.jfinal.club._admin.project.ProjectAdminController','项目管理首页'),
	(24,'/admin/project/delete','com.jfinal.club._admin.project.ProjectAdminController','删除项目'),
	(25,'/admin/project/edit','com.jfinal.club._admin.project.ProjectAdminController','修改项目'),
	(26,'/admin/project/lock','com.jfinal.club._admin.project.ProjectAdminController','锁定项目'),
	(27,'/admin/project/unlock','com.jfinal.club._admin.project.ProjectAdminController','解锁项目'),
	(28,'/admin/project/update','com.jfinal.club._admin.project.ProjectAdminController','更新项目'),
	(29,'/admin/role','com.jfinal.club._admin.role.RoleAdminController','角色管理首页'),
	(30,'/admin/role/add','com.jfinal.club._admin.role.RoleAdminController','创建角色'),
	(31,'/admin/role/addPermission','com.jfinal.club._admin.role.RoleAdminController','添加权限'),
	(32,'/admin/role/assignPermissions','com.jfinal.club._admin.role.RoleAdminController','分配权限'),
	(33,'/admin/role/delete','com.jfinal.club._admin.role.RoleAdminController','删除角色'),
	(34,'/admin/role/deletePermission','com.jfinal.club._admin.role.RoleAdminController','删除权限'),
	(35,'/admin/role/edit','com.jfinal.club._admin.role.RoleAdminController','修改角色'),
	(36,'/admin/role/save','com.jfinal.club._admin.role.RoleAdminController','创建角色提交'),
	(37,'/admin/role/update','com.jfinal.club._admin.role.RoleAdminController','修改角色提交'),
	(38,'/admin/share','com.jfinal.club._admin.share.ShareAdminController','分享管理首页'),
	(39,'/admin/share/delete','com.jfinal.club._admin.share.ShareAdminController','删除分享'),
	(40,'/admin/share/deleteReply','com.jfinal.club._admin.share.ShareAdminController','删除分享回复'),
	(41,'/admin/share/edit','com.jfinal.club._admin.share.ShareAdminController','修改分享'),
	(42,'/admin/share/lock','com.jfinal.club._admin.share.ShareAdminController','锁定分享'),
	(43,'/admin/share/getReplyList','com.jfinal.club._admin.share.ShareAdminController','获取分享回复列表'),
	(44,'/admin/share/unlock','com.jfinal.club._admin.share.ShareAdminController','解锁分享'),
	(45,'/admin/share/update','com.jfinal.club._admin.share.ShareAdminController','更新分享'),
	(46,'/admin/project/add','com.jfinal.club._admin.project.ProjectAdminController','创建项目'),
	(47,'/admin/project/save','com.jfinal.club._admin.project.ProjectAdminController','创建项目提交'),
	(48,'/admin/share/add','com.jfinal.club._admin.share.ShareAdminController','创建分享'),
	(49,'/admin/share/save','com.jfinal.club._admin.share.ShareAdminController','创建分享提交'),
	(50,'/admin/feedback/add','com.jfinal.club._admin.feedback.FeedbackAdminController','创建反馈'),
	(51,'/admin/feedback/save','com.jfinal.club._admin.feedback.FeedbackAdminController','创建反馈提交'),
	(52,'/admin/feedback/getReply','com.jfinal.club._admin.feedback.FeedbackAdminController','获取反馈回复'),
	(53,'/admin/share/getReply','com.jfinal.club._admin.share.ShareAdminController','获取分享回复'),
	(54,'/admin/account/avatar','com.jfinal.club._admin.account.AccountAdminController','更换头像'),
	(55,'/admin/account/saveAvatar','com.jfinal.club._admin.account.AccountAdminController','保存头像'),
	(56,'/admin/account/uploadAvatar','com.jfinal.club._admin.account.AccountAdminController','上传头像'),
	(57,'/admin/account/showAdminList','com.jfinal.club._admin.account.AccountAdminController','显示后台账户'),
	(58,'/admin/doc','com.jfinal.club._admin.document.DocumentAdminController','文档管理首页'),
	(59,'/admin/doc/add','com.jfinal.club._admin.document.DocumentAdminController','创建文档'),
	(60,'/admin/doc/delete','com.jfinal.club._admin.document.DocumentAdminController','删除文档'),
	(61,'/admin/doc/edit','com.jfinal.club._admin.document.DocumentAdminController','修改文档'),
	(62,'/admin/doc/publish','com.jfinal.club._admin.document.DocumentAdminController','发布文档'),
	(63,'/admin/doc/save','com.jfinal.club._admin.document.DocumentAdminController','创建文档提交'),
	(64,'/admin/doc/unpublish','com.jfinal.club._admin.document.DocumentAdminController','取消发布文档'),
	(65,'/admin/doc/update','com.jfinal.club._admin.document.DocumentAdminController','修改文档提交');

/*!40000 ALTER TABLE `permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table project
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project`;

CREATE TABLE `project` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `title` varchar(150) NOT NULL,
  `content` text NOT NULL,
  `createAt` datetime NOT NULL,
  `clickCount` int(11) NOT NULL DEFAULT '0',
  `report` int(11) NOT NULL DEFAULT '0',
  `likeCount` int(11) NOT NULL DEFAULT '0',
  `favoriteCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `project` WRITE;
/*!40000 ALTER TABLE `project` DISABLE KEYS */;

INSERT INTO `project` (`id`, `accountId`, `name`, `title`, `content`, `createAt`, `clickCount`, `report`, `likeCount`, `favoriteCount`)
VALUES
	(1,1,'JFinal','jfinal 极速开发框架','<p>JFinal 是基于 Java 语言的极速 WEB + ORM 框架，其核心设计目标是开发迅速、代码量少、学习简单、功能强大、轻量级、易扩展、Restful。在拥有Java语言所有优势的同时再拥有ruby、python、php等动态语言的开发效率！为您节约更多时间，去陪恋人、家人和朋友 :)</p><h2>JFinal有如下主要特点：</h2><ul class=\" list-paddingleft-2\"><li><p>MVC架构，设计精巧，使用简单</p></li><li><p>遵循COC原则，零配置，无xml</p></li><li><p>独创Db + Record模式，灵活便利</p></li><li><p>ActiveRecord支持，使数据库开发极致快速</p></li><li><p>自动加载修改后的java文件，开发过程中无需重启web server</p></li><li><p>AOP支持，拦截器配置灵活，功能强大</p></li><li><p>Plugin体系结构，扩展性强</p></li><li><p>多视图支持，支持FreeMarker、JSP、Velocity</p></li><li><p>强大的Validator后端校验功能</p></li><li><p>功能齐全，拥有struts2的绝大部分功能</p></li><li><p>体积小仅632K，且无第三方依赖</p></li></ul><h2>以下是JFinal实现Blog管理的示例：</h2><h3>1：控制器(支持FreeMarker、JSP、Velocity、JSON等以及自定义视图渲染)</h3><pre>@Before(BlogInterceptor.class)\npublic&nbsp;class&nbsp;BlogController&nbsp;extends&nbsp;Controller&nbsp;{\n&nbsp;&nbsp;public&nbsp;void&nbsp;index()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;setAttr(\"blogList\",&nbsp;Blog.dao.find(\"select&nbsp;*&nbsp;from&nbsp;blog\"));\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;public&nbsp;void&nbsp;add()&nbsp;{\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;@Before(BlogValidator.class)\n&nbsp;&nbsp;public&nbsp;void&nbsp;save()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;getModel(Blog.class).save();\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;public&nbsp;void&nbsp;edit()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;setAttr(\"blog\",&nbsp;Blog.dao.findById(getParaToInt()));\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;@Before(BlogValidator.class)\n&nbsp;&nbsp;public&nbsp;void&nbsp;update()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;getModel(Blog.class).update();\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;public&nbsp;void&nbsp;delete()&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;Blog.dao.deleteById(getParaToInt());\n&nbsp;&nbsp;}\n}</pre><h3>2：Model(无xml、无annotation、无attribute、无getter、无setter)</h3><pre>public&nbsp;class&nbsp;Blog&nbsp;extends&nbsp;Model&lt;Blog&gt;&nbsp;{\n}</pre><h3>3：Validator(API引导式校验，比xml校验方便N倍，有代码检查不易出错)</h3><pre>public&nbsp;class&nbsp;BlogValidator&nbsp;extends&nbsp;Validator&nbsp;{\n&nbsp;&nbsp;protected&nbsp;void&nbsp;validate(Controller&nbsp;controller)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;validateRequiredString(\"blog.title\",&nbsp;\"titleMsg\",&nbsp;\"请输入Blog标题!\");\n&nbsp;&nbsp;&nbsp;&nbsp;validateRequiredString(\"blog.content\",&nbsp;\"contentMsg\",&nbsp;\"请输入Blog内容!\");\n&nbsp;&nbsp;}\n\n&nbsp;&nbsp;protected&nbsp;void&nbsp;handleError(Controller&nbsp;controller)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;controller.keepModel(Blog.class);\n&nbsp;&nbsp;}\n}</pre><h3>4：拦截器(在此demo中仅为示例，本demo不需要此拦截器)</h3><pre>public&nbsp;class&nbsp;BlogInterceptor&nbsp;implements&nbsp;Interceptor&nbsp;{\n&nbsp;&nbsp;public&nbsp;void&nbsp;intercept(Invocation&nbsp;inv)&nbsp;{\n&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(\"Before&nbsp;invoking&nbsp;\"&nbsp;+&nbsp;inv.getActionKey());\n&nbsp;&nbsp;&nbsp;&nbsp;inv.invoke();\n&nbsp;&nbsp;&nbsp;&nbsp;System.out.println(\"After&nbsp;invoking&nbsp;\"&nbsp;+&nbsp;inv.getActionKey());\n&nbsp;&nbsp;}\n}</pre><h3>5：最新文档与最新下载</h3><ul class=\" list-paddingleft-2\"><li><p><a href=\"/doc\" target=\"_self\" title=\"JFinal 3.4 手册\">JFinal 3.4 手册(在线版)</a></p></li><li><p><a href=\"/download?file=jfinal-3.4-all.zip\" target=\"_blank\" title=\"JFinal 3.4 all\">JFinal 3.4 all</a></p></li><li><p><a href=\"/download?file=jfinal-3.4_demo_for_maven.zip\" target=\"_blank\" title=\"JFinal 3.4 demo for maven\">JFinal 3.4 demo for maven</a></p></li><li><p><a href=\"/download?file=jfinal-3.4_demo.zip\" target=\"_blank\" title=\"JFinal 3.4 demo\">JFinal 3.4 demo</a></p></li></ul>','2016-06-06 06:06:06',0,0,131,66),
	(2,1,'JFinal Weixin','jfinal weixin 极速开发 SDK','<p>JFinal Weixin 是基于 JFinal 的微信公众号极速开发 SDK，只需浏览 Demo 代码即可进行极速开发，自 JFinal Weixin 1.2 版本开始已添加对多公众号支持。</p><h2>1、WeixinConfig配置</h2><p>详情请见：JFinal weixin中的WeixinConfig配置</p><h2>2、WeixinMsgController</h2><p>WeixinMsgController 通过继承自 MsgController 便拥有了接收消息和发送消息的便利方法</p><h2>3、WeixinApiController</h2><p>通过调用 MenuApi、UserApi 等 Api 的相关方法即可获取封装成 ApiResult 对象的结果，使用 render 系列方法即可快捷输出结果</p><h2>4、非Maven用户得到所有依赖 jar 包两种方法</h2><p>将项目导入eclipse jee中，使用 export 功能导出 war包，其中的 WEB-INF/lib 下面会自动生成 jar 包 让使用 maven 的朋友使用 mvn package 打出 war包，其中的 WEB-INF/lib 下面会自动生成 jar 包 以上两种方法注意要先将pom.xml中的导出类型设置为 war，添加 war 内容进去即可 依赖jackson或fastjson</p><h2>5、jar包依赖详细说明</h2><p>详见请见：JFinal weixin 1.9 Jar依赖</p><h2>6、WIKI持续更新中</h2><p>WIKI：<a href=\"http://git.oschina.net/jfinal/jfinal-weixin/wikis/home\" target=\"_blank\">http://git.oschina.net/jfinal/jfinal-weixin/wikis/home</a> 欢迎更多同学来帮助完善！</p><h3>5：最新下载</h3><p><a href=\"/download?file=jfinal-weixin-1.9-bin-with-src.jar\" target=\"_blank\">JFinal weixin 1.9</a></p>','2016-06-06 06:07:00',0,0,0,0),
	(3,1,'憨憨电影网','憨憨电影—基于jfinal搭建的电影网站','<p>&nbsp; &nbsp; 本人利用闲余时间，通过jfinal搭建的一个电影网站,里面收录的都是高质量的电影。\n\n &nbsp; &nbsp;</p><p>&nbsp; &nbsp; 项目的主要架构是 nginx + tomcat + jetty + redis + mysql 。其中，nginx作为静态文件服务和负载均衡；tomcat 和 jetty 作为应用服务器，处理请求；redis取代session；mysql数据库大家都懂。另外，SSL 证书使用的是开源的Let\'s Encrypt。</p><p>&nbsp; &nbsp; 欢迎大家访问，网址是：<a href=\"https://www.hanhanfilm.com\" target=\"_blank\">https://www.hanhanfilm.com</a></p>','2018-04-27 23:19:37',0,0,0,0),
	(5,1,'EOVA','jfinal 快速开发平台','<p>&nbsp; &nbsp; 首创JFinal快速开发平台，降低70%开发成本。快速搞定各类管理系统，赶紧用EOVA给自己加薪吧!&nbsp;</p><p>&nbsp; &nbsp; Eova能快速实现啥效果呐?\n\n\n\n定时任务有木有?\n\n\n\n什么?你要做复杂的自定义报表?\n\n\n\nWord报表呢?&nbsp;</p><p>&nbsp; &nbsp; 你认识的控件都有!\n\n\n\n做网站后台管理，就用EOVA快速开发\n\n其它疑问请上社区提问！<a href=\"http://www.eova.cn\n\n\n来自广大用户的呼声\">http://www.eova.cn\n\n\n来自广大用户的呼声</a></p><p>&nbsp; &nbsp; 楼上这些曾经使用Eova的同学2017年都成了CTO,迎娶了白富美,走向了人生巅峰!\n\n你还在犹豫什么?\n\n赶紧抄起鼠标&nbsp;<a href=\"https://gitee.com/eova/eova\" target=\"_blank\">https://gitee.com/eova/eova</a> Star吧!</p>','2018-04-27 23:22:18',0,0,0,0),
	(6,1,'jfinal-mail-plugin','jfinal 邮件发送插件','<p>&nbsp; &nbsp; 简介</p><p>&nbsp; &nbsp; &nbsp;jfinal-mail-plugin是jfinal的一个邮件发送插件，支持发送普通邮件、与附件邮件，邮件内容支持通过模板生成，同时还支持多个邮件发送源，她继承了Jfinal核心目标“开发迅速，代码量少，学习简单。。。”，只需简单的2行代码即可实现邮件发送！为您节约更多时间，去陪恋人、家人和朋友 :) ，核心代码通过spring-context-support包的邮件模块移植，JavaMailSender对象如何发送邮件可直接参照Spring的邮件发送文档。</p><p>项目地址：<a href=\"http://git.oschina.net/xiyoufang/jfinal-mail-plugin\n\">http://git.oschina.net/xiyoufang/jfinal-mail-plugin&nbsp;</a></p><p>官方网站：<a href=\"http://www.fsdev.cn/\n\n\">http://www.fsdev.cn/&nbsp;</a></p><p><a href=\"http://www.fsdev.cn/\n\n\"></a><span style=\"font-family: 微软雅黑; font-size: 18px;\">动态SQL插件</span>：<a href=\"http://git.oschina.net/xiyoufang/jfinal-xsql-plugin\" target=\"_blank\">http://git.oschina.net/xiyoufang/jfinal-xsql-plugin </a></p><p>&nbsp;<span style=\"font-family: 微软雅黑; font-size: 18px;\">PDF生成插件：<a href=\"http://git.oschina.net/xiyoufang/jfinal-pdf-plugin\" target=\"_blank\"> http://git.oschina.net/xiyoufang/jfinal-pdf-plugin </a></span></p><p>&nbsp;</p>','2018-04-27 23:25:12',0,0,0,0),
	(7,1,'jfinal3.0从入门到入魔','jfinal3.0从入门到入魔系列','<p>&nbsp; &nbsp; 那个什么，宣布一个事情，jfinal3.0视频教程发布了，来VIP群一起和我修仙，一起上天飞吧！！捷足先登，然后封神！</p>','2018-04-27 23:26:50',0,0,0,0),
	(8,1,'testhaha','测试锁定','<p>测试锁定功能，<span style=\"font-family: 微软雅黑; font-size: 18px;\">测试锁定功能modibybb</span></p>','2018-04-27 23:40:45',0,3,0,0),
	(9,1,'新项目a','新标题bb','<p>新内容哈哈哈哈<br></p>','2018-06-09 21:48:34',0,0,0,0);

/*!40000 ALTER TABLE `project` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table project_like
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project_like`;

CREATE TABLE `project_like` (
  `accountId` int(11) NOT NULL,
  `refId` int(11) NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`accountId`,`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table project_page_view
# ------------------------------------------------------------

DROP TABLE IF EXISTS `project_page_view`;

CREATE TABLE `project_page_view` (
  `projectId` varchar(25) NOT NULL,
  `visitDate` date NOT NULL,
  `visitCount` int(20) NOT NULL,
  PRIMARY KEY (`projectId`,`visitDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `project_page_view` WRITE;
/*!40000 ALTER TABLE `project_page_view` DISABLE KEYS */;

INSERT INTO `project_page_view` (`projectId`, `visitDate`, `visitCount`)
VALUES
	('1','2018-04-28',1),
	('2','2018-04-28',2),
	('7','2018-06-22',2),
	('8','2018-04-28',1),
	('8','2018-05-19',1),
	('9','2018-06-09',1),
	('9','2018-06-14',1);

/*!40000 ALTER TABLE `project_page_view` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table refer_me
# ------------------------------------------------------------

DROP TABLE IF EXISTS `refer_me`;

CREATE TABLE `refer_me` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `referAccountId` int(11) NOT NULL COMMENT '接收者账号id',
  `newsFeedId` int(11) NOT NULL COMMENT 'newsFeedId',
  `type` tinyint(2) NOT NULL COMMENT '@我、评论我等等的refer类型',
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `refer_me` WRITE;
/*!40000 ALTER TABLE `refer_me` DISABLE KEYS */;

INSERT INTO `refer_me` (`id`, `referAccountId`, `newsFeedId`, `type`, `createAt`)
VALUES
	(1,1,35,2,'2018-06-14 16:55:47');

/*!40000 ALTER TABLE `refer_me` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table remind
# ------------------------------------------------------------

DROP TABLE IF EXISTS `remind`;

CREATE TABLE `remind` (
  `accountId` int(11) NOT NULL COMMENT '用户账号id，必须手动指定，不自增',
  `referMe` int(11) NOT NULL DEFAULT '0' COMMENT '提到我的消息条数',
  `message` int(11) NOT NULL DEFAULT '0' COMMENT '私信条数',
  `fans` int(11) NOT NULL DEFAULT '0' COMMENT '粉丝增加个数',
  PRIMARY KEY (`accountId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `remind` WRITE;
/*!40000 ALTER TABLE `remind` DISABLE KEYS */;

INSERT INTO `remind` (`accountId`, `referMe`, `message`, `fans`)
VALUES
	(1,0,0,0);

/*!40000 ALTER TABLE `remind` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role`;

CREATE TABLE `role` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) NOT NULL DEFAULT '',
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `role` WRITE;
/*!40000 ALTER TABLE `role` DISABLE KEYS */;

INSERT INTO `role` (`id`, `name`, `createAt`)
VALUES
	(1,'超级管理员','2018-03-19 09:58:19'),
	(2,'CEO','2018-04-27 22:37:18'),
	(3,'CTO','2018-04-27 22:37:25'),
	(4,'项目经理','2018-04-27 22:37:44'),
	(5,'小编','2018-04-27 22:37:59'),
	(6,'new','2018-05-19 00:16:36'),
	(7,'test','2018-05-23 21:32:07'),
	(8,'新加个角色','2018-06-12 17:55:09');

/*!40000 ALTER TABLE `role` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table role_permission
# ------------------------------------------------------------

DROP TABLE IF EXISTS `role_permission`;

CREATE TABLE `role_permission` (
  `roleId` int(11) NOT NULL,
  `permissionId` int(11) NOT NULL,
  PRIMARY KEY (`roleId`,`permissionId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

LOCK TABLES `role_permission` WRITE;
/*!40000 ALTER TABLE `role_permission` DISABLE KEYS */;

INSERT INTO `role_permission` (`roleId`, `permissionId`)
VALUES
	(2,1),
	(2,2),
	(2,3),
	(2,4),
	(2,5),
	(2,6),
	(2,7),
	(2,8),
	(2,9),
	(2,10),
	(2,11),
	(2,12),
	(2,13),
	(2,14),
	(2,15),
	(2,16),
	(2,17),
	(2,18),
	(2,19),
	(2,20),
	(2,21),
	(2,22),
	(2,23),
	(2,24),
	(2,25),
	(2,26),
	(2,27),
	(2,28),
	(2,29),
	(2,30),
	(2,31),
	(2,32),
	(2,33),
	(2,34),
	(2,35),
	(2,36),
	(2,37),
	(2,38),
	(2,39),
	(2,40),
	(2,41),
	(2,42),
	(2,43),
	(2,44),
	(2,45),
	(2,46),
	(2,47),
	(2,48),
	(2,49),
	(2,50),
	(2,51),
	(2,52),
	(2,53),
	(2,54),
	(2,55),
	(2,56),
	(3,1),
	(3,2),
	(3,3),
	(3,4),
	(3,5),
	(3,6),
	(3,7),
	(3,8),
	(3,9),
	(3,10),
	(3,18),
	(3,19),
	(3,20),
	(3,21),
	(3,22),
	(3,23),
	(3,29),
	(3,30),
	(3,31),
	(3,32),
	(3,33),
	(3,34),
	(3,35),
	(3,36),
	(3,37),
	(3,38),
	(3,54),
	(3,55),
	(3,56),
	(4,1),
	(4,2),
	(4,10),
	(4,23),
	(4,24),
	(4,25),
	(4,26),
	(4,27),
	(4,28),
	(4,38),
	(4,46),
	(4,47),
	(5,1),
	(5,2),
	(5,10),
	(5,11),
	(5,12),
	(5,13),
	(5,14),
	(5,15),
	(5,16),
	(5,17),
	(5,23),
	(5,38),
	(5,39),
	(5,40),
	(5,41),
	(5,42),
	(5,43),
	(5,44),
	(5,45),
	(5,48),
	(5,49),
	(5,50),
	(5,51),
	(8,12);

/*!40000 ALTER TABLE `role_permission` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table sensitive_words
# ------------------------------------------------------------

DROP TABLE IF EXISTS `sensitive_words`;

CREATE TABLE `sensitive_words` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `word` varchar(32) NOT NULL DEFAULT '',
  `status` tinyint(4) NOT NULL DEFAULT '1',
  `word_pinyin` varchar(64) NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `sensitive_words` WRITE;
/*!40000 ALTER TABLE `sensitive_words` DISABLE KEYS */;

INSERT INTO `sensitive_words` (`id`, `word`, `status`, `word_pinyin`)
VALUES
	(1,'发票',1,'fapiao');

/*!40000 ALTER TABLE `sensitive_words` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table session
# ------------------------------------------------------------

DROP TABLE IF EXISTS `session`;

CREATE TABLE `session` (
  `id` varchar(33) NOT NULL,
  `accountId` int(11) NOT NULL,
  `expireAt` bigint(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `session` WRITE;
/*!40000 ALTER TABLE `session` DISABLE KEYS */;

INSERT INTO `session` (`id`, `accountId`, `expireAt`)
VALUES
	('185e2d0fe4b24c18a3e9a9195014299a',1,1624459338971),
	('5b8e2a2109a9449a8fe660a9ab1b69aa',1,1621269626427),
	('5fe04df586aa498b8305e42252b68f54',1,1619454238258),
	('6d6bb97c9bc64a409ee56cdd8be6404e',1,1623645425909),
	('9ecce60a582a422d8aa214308d962fcb',1,1630401008373);

/*!40000 ALTER TABLE `session` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table share
# ------------------------------------------------------------

DROP TABLE IF EXISTS `share`;

CREATE TABLE `share` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `accountId` int(11) NOT NULL,
  `projectId` int(11) NOT NULL,
  `title` varchar(150) NOT NULL,
  `content` text NOT NULL,
  `createAt` datetime NOT NULL,
  `clickCount` int(11) NOT NULL DEFAULT '0',
  `report` int(11) NOT NULL DEFAULT '0',
  `likeCount` int(11) NOT NULL DEFAULT '0',
  `favoriteCount` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `share` WRITE;
/*!40000 ALTER TABLE `share` DISABLE KEYS */;

INSERT INTO `share` (`id`, `accountId`, `projectId`, `title`, `content`, `createAt`, `clickCount`, `report`, `likeCount`, `favoriteCount`)
VALUES
	(1,1,1,'玩转 JFinal 新社区的正确姿势','<p>JFinal 极速开发新社区于2016年6月6号6点6分6秒正式上线了，社区将提供高品质、专业化的极速开发项目、以及项目的分享与反馈。新社区主要分为项目、分享、反馈三大模块，其用途分别为：</p><h2>1、项目<br></h2><p>发布、收集与 JFinal 极速开发有关的项目，供开发者参考、学习、使用</p><h2>2、分享<br></h2><p>针对于项目，分享有关该项目的一切有价值的知识、代码等等资源，提升开发效率</p><h2>3、反馈<br></h2><p>针对于项目，向作者反馈在使用过程中碰到的问题或者提出改进建议，用户与作者共同打造高水平项目<br></p><h2><span style=\"color: rgb(255, 0, 0);\">用户注意事项：</span></h2><ul class=\" list-paddingleft-2\" style=\"list-style-type: disc;\"><li><p>注册以后换上个人头像有利于社区氛围与文化建设</p></li><li><p>为了保障社区内容的专注与高品质，请支持只发表技术相关内容<br></p></li><li><p><span style=\"font-family: 微软雅黑; font-size: 18px;\">为提升价值、节省开发者时间，</span>低质量、非技术性内容会酌情进行清理，请见谅</p></li></ul><p>&nbsp; &nbsp; JFinal 极度关注为开发者节省时间、提升效率、带来价值，从而会坚持内容的高品质，走少而精的道路，泛娱乐化的与技术无关的内容只会无情地浪费广大开发者有限的生命，请大家支持 JFinal 极速开发社区的价值观！！！</p>','2066-06-06 06:06:06',0,0,16,0),
	(2,1,1,'JFinal 新社区 share分享栏目','<h2>乐于分享、传递价值</h2>\n<p>&nbsp; &nbsp; JFinal 新社区分享栏目，用于开发者针对于本站某个项目分享出自己所拥有的有价值的资源，例如实战中具体的代码，项目使用心德、技巧等一切可以为大家节省时间、提升效率的资源。<br></p>','2066-06-06 06:06:03',0,0,8,3),
	(3,1,2,'JFinal Weixin 1.8 发布，微信极速 SDK','<p>&nbsp; &nbsp; 离上一次 JFinal weixin 1.7 发布，已经过去了 6 个月。在过去的半年时间里 JFinal Weixin 紧随微信公众平台的演化，不断增加了新的 API，同时也在不断完善原有 API，力求打造一个完备的微信公众平台 SDK，让开发更快速、更开心！</p><p>&nbsp;&nbsp; &nbsp;JFinal Weixin 1.8 共有 27 项新增与改进，新增功能主要有：微信红包接口、微信支付对账单接口、消息转发到指定客服、微信连WIFI联网后下发消息事件、卡券相关事件消息、用户Tag接口、个性化菜单接口等等。1.8 版对原有代码也进行了打磨，例如去除 freemarker 了依赖，截止到今天，此版本是目前市面上 Java 版微信SDK中jar包依赖最少的一个。</p><p>&nbsp; &nbsp; 最后感谢所有对 JFinal Weixin 有贡献的开发者们：@Dreamlu @Javen205 @亻紫菜彡 @osc余书慧 @12叔 @Jimmy哥 @author @Lucare，正是你们无私的奉献让这个世界越来越美好！</p><p><br></p><p>Jar 包下载：<a href=\"http://www.jfinal.com/download?file=jfinal-weixin-1.8-bin-with-src.jar\" target=\"_blank\">http://www.jfinal.com/download?file=jfinal-weixin-1.8-bin-with-src.jar</a></p><p>非 maven 用户获取依赖的 jar包：<a href=\"http://www.jfinal.com/download?file=jfinal-weixin-1.8-lib.zip\" target=\"_blank\">http://www.jfinal.com/download?file=jfinal-weixin-1.8-lib.zip</a></p><p><span style=\"font-family: 微软雅黑; font-size: 18px;\">详细开发文档地址：<a href=\"http://git.oschina.net/jfinal/jfinal-weixin/wikis/home\" target=\"_blank\">http://git.oschina.net/jfinal/jfinal-weixin/wikis/home</a></span></p><p><br></p><p>JFinal Weixin 1.8 Change log&nbsp;</p><p>1：去掉freemarker依赖，感谢@亻紫菜彡的意见&nbsp;</p><p>2：添加个性化菜单接口&nbsp;</p><p>3：添加微信支付对账单接口&nbsp;</p><p>4：添加没有找到对应的消息和事件消息的自定义处理&nbsp;</p><p>5：添加微信连WIFI联网后下发消息事件&nbsp;</p><p>6：fixed客服接口，删除客服帐号&nbsp;</p><p>7：添加获取自动回复规则&nbsp;</p><p>8：更新ReturnCode&nbsp;</p><p>9：新增将消息转发到指定客服&nbsp;</p><p>10：更改pom.xml，打jar包时排除demo目录&nbsp;</p><p>11：添加\"获取在线客服接待信息\"&nbsp;</p><p>12：新增发送图文消息（点击跳转到图文消息页面）&nbsp;</p><p>13：添加微信红包接口，感谢@osc余书慧童鞋的贡献&nbsp;</p><p>14：Bug searchByDevice感谢@12叔&nbsp;</p><p>15：ApiConfig实现序列化，方便缓存感谢@Jimmy哥&nbsp;</p><p>16：企业付款demoWeixinTransfersController感谢@author osc就看看&nbsp;</p><p>17：新增微信支付PC-模式一、模式二demo&nbsp;</p><p>18：添加对okhttp3的支持，修复okhttp2中download误用成httpsClient&nbsp;</p><p>19：添加对直接请求msg接口的异常提示&nbsp;</p><p>20：添加IOutils.toString的字符集参数&nbsp;</p><p>21：修改成maven目录结构&nbsp;</p><p>22：添加卡券相关事件消息&nbsp;</p><p>23：优化xml解析&nbsp;</p><p>24：TemplateData,JsonKit JSON序列化错误&nbsp;</p><p>25：添加用户tag接口&nbsp;</p><p>26：修复AccessToken超时并发问题，感谢@Lucare&nbsp;</p><p>27：添加java doc，详见：<a href=\"http://www.dreamlu.net/jfinal-weixin/apidocs/\">http://www.dreamlu.net/jfinal-weixin/apidocs/</a></p><p><br></p>','2016-07-11 11:44:30',0,0,0,0),
	(4,1,1,'springboot使用enjoy支持热加载','<p>&nbsp; &nbsp; 声明：本文主要介绍在用springboot集成enjoy模板引擎后，修改java代码或者前台代码时项目热加载重启之后出现异常(com.jfinal.template.TemplateException: object is not an instance of declaring class)的解决方案以及原因。</p><p>&nbsp; &nbsp; 简单介绍spring-boot-devtools：</p><p>&nbsp; &nbsp; 当我们用springboot构建项目的时候，如果需要其支持热加载（即修改代码保存后项目自动重新启动，节省开发时间）则需要引入spring-boot-devtools依赖，maven坐标如下：</p>','2018-02-04 22:26:04',0,0,0,0),
	(5,1,1,'JFinal使用技巧-Enjoy导出XLS','<p>&nbsp; &nbsp; 看见群里在说Enjoy除了能渲染页面还能干什么用？</p><p>&nbsp; &nbsp; &nbsp;看见“@毛斯特洛夫斯基”说：\n\n“\n你用的到模版的时候，就能体会到enjoy的优点\n用不到的时候，不要手里有锤子就看谁都像是钉子\n”\n\n笑坏我了哈哈~ 嗯，我来分享一个小工具吧， 我自己这边一直在用的偏方。。。\n上 石马 吧~</p>','2018-02-04 22:26:43',0,0,0,0),
	(13,1,9,'广告贴将被锁定a','<p>这是广告贴，演示锁定功能，广告贴锁定以后，可以通过分析广告内容的模式，开发出拦截器阻止广告贴发布bbb</p>','2018-04-27 23:38:57',0,3,0,0),
	(14,1,2,'一个分享','<p>创建一个分享创建一个分享创建一个分享创建一个分享创建一个分享yyy</p>','2018-06-12 12:15:10',0,0,0,0);

/*!40000 ALTER TABLE `share` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table share_like
# ------------------------------------------------------------

DROP TABLE IF EXISTS `share_like`;

CREATE TABLE `share_like` (
  `accountId` int(11) NOT NULL,
  `refId` int(11) NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`accountId`,`refId`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table share_page_view
# ------------------------------------------------------------

DROP TABLE IF EXISTS `share_page_view`;

CREATE TABLE `share_page_view` (
  `shareId` varchar(25) NOT NULL,
  `visitDate` date NOT NULL,
  `visitCount` int(20) NOT NULL,
  PRIMARY KEY (`shareId`,`visitDate`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `share_page_view` WRITE;
/*!40000 ALTER TABLE `share_page_view` DISABLE KEYS */;

INSERT INTO `share_page_view` (`shareId`, `visitDate`, `visitCount`)
VALUES
	('1','2018-04-28',1),
	('1','2018-06-14',1),
	('13','2018-05-01',1),
	('13','2018-06-12',1),
	('13','2018-06-14',1),
	('14','2018-06-20',1),
	('14','2018-06-22',1),
	('5','2018-04-28',1);

/*!40000 ALTER TABLE `share_page_view` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table share_reply
# ------------------------------------------------------------

DROP TABLE IF EXISTS `share_reply`;

CREATE TABLE `share_reply` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `shareId` int(11) NOT NULL,
  `accountId` int(11) NOT NULL,
  `content` text NOT NULL,
  `createAt` datetime NOT NULL,
  `report` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `share_reply` WRITE;
/*!40000 ALTER TABLE `share_reply` DISABLE KEYS */;

INSERT INTO `share_reply` (`id`, `shareId`, `accountId`, `content`, `createAt`, `report`)
VALUES
	(1,13,9,'回复在此','2018-06-14 16:55:47',0),
	(2,14,1,'这里多来点回复','2018-06-16 00:01:15',0);

/*!40000 ALTER TABLE `share_reply` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table task_list
# ------------------------------------------------------------

DROP TABLE IF EXISTS `task_list`;

CREATE TABLE `task_list` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type` int(11) NOT NULL,
  `refId` int(11) NOT NULL,
  `status` tinyint(2) NOT NULL DEFAULT '0' COMMENT '0初始，1成功，2失败',
  `msg` varchar(1000) DEFAULT '' COMMENT '用substring保证长度不超出范围',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table task_run_log
# ------------------------------------------------------------

DROP TABLE IF EXISTS `task_run_log`;

CREATE TABLE `task_run_log` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `taskName` varchar(50) NOT NULL,
  `createAt` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



# Dump of table upload_counter
# ------------------------------------------------------------

DROP TABLE IF EXISTS `upload_counter`;

CREATE TABLE `upload_counter` (
  `uploadType` varchar(50) NOT NULL,
  `counter` int(11) NOT NULL,
  `descr` varchar(50) NOT NULL,
  PRIMARY KEY (`uploadType`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

LOCK TABLES `upload_counter` WRITE;
/*!40000 ALTER TABLE `upload_counter` DISABLE KEYS */;

INSERT INTO `upload_counter` (`uploadType`, `counter`, `descr`)
VALUES
	('club',0,'记录club模块上传图片的总数量，用于生成相对路径'),
	('document',0,'记录document模块上传图片的总数量，用于生成相对路径'),
	('feedback',313,'记录feedback模块上传图片的总数量，用于生成相对路径'),
	('project',72,'记录project模块上传图片的总数量，用于生成相对路径'),
	('share',197,'记录share模块上传图片的总数量，用于生成相对路径');

/*!40000 ALTER TABLE `upload_counter` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
