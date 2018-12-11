CREATE TABLE `juzi` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
   `publish` tinyint(2) NOT NULL DEFAULT '0' COMMENT '发布状态',
  `content` varchar(1024) NOT NULL COMMENT '内容',
   `length` smallint NOT NULL COMMENT '内容长度',
   `author` varchar(32) NOT NULL COMMENT '作者',
   `from` varchar(64) NOT NULL COMMENT '来自',
   `category` varchar(32) NOT NULL COMMENT '分类',
   `remark` varchar(500) NOT NULL COMMENT '点评',
   `tags` varchar(120) NOT NULL COMMENT '鉴赏标签',
   `applyDesc` varchar(120) NOT NULL COMMENT '使用描述',
  `updateAt` datetime NOT NULL COMMENT '更新时间',
  `createAt` datetime NOT NULL COMMENT '创建时间',
   `accountAt` int(11) NOT NULL COMMENT '谁发布的',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
