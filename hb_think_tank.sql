/*
Navicat MySQL Data Transfer

Source Server         : localhost_3306
Source Server Version : 50549
Source Host           : localhost:3306
Source Database       : hb_think_tank

Target Server Type    : MYSQL
Target Server Version : 50549
File Encoding         : 65001

Date: 2017-11-04 10:30:16
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for article
-- ----------------------------
DROP TABLE IF EXISTS `article`;
CREATE TABLE `article` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键',
  `title` varchar(50) DEFAULT NULL COMMENT '标题',
  `author` varchar(50) DEFAULT NULL COMMENT '作者',
  `createdDate` datetime DEFAULT NULL COMMENT '创作时间',
  `content` varchar(255) DEFAULT NULL COMMENT '内容',
  `imgUrl` varchar(255) DEFAULT NULL COMMENT '图片地址',
  `imgState` varchar(50) DEFAULT NULL COMMENT '图片说明',
  `textTypeId` int(11) DEFAULT NULL COMMENT '文本类型',
  `organizationId` int(11) DEFAULT NULL COMMENT '机构Id',
  `addTime` datetime DEFAULT NULL COMMENT '添加时间',
  PRIMARY KEY (`id`),
  KEY `textTypeId` (`textTypeId`) USING BTREE,
  KEY `organizationId` (`organizationId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8 COMMENT='智库文章表';

-- ----------------------------
-- Table structure for article_sort
-- ----------------------------
DROP TABLE IF EXISTS `article_sort`;
CREATE TABLE `article_sort` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `name` varchar(20) DEFAULT NULL COMMENT '类型名称',
  `parentId` int(11) DEFAULT NULL COMMENT '父Id',
  PRIMARY KEY (`id`),
  KEY `parentId` (`parentId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for organization
-- ----------------------------
DROP TABLE IF EXISTS `organization`;
CREATE TABLE `organization` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `name` varchar(50) DEFAULT NULL COMMENT '机构名',
  `intro` varchar(255) DEFAULT NULL COMMENT '单位简介',
  `lead` varchar(50) DEFAULT NULL COMMENT '单位领导',
  `expert` varchar(50) DEFAULT NULL COMMENT '单位专家',
  `institutionSetting` varchar(255) DEFAULT NULL COMMENT '机构设置',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8 COMMENT='机构表';

-- ----------------------------
-- Table structure for sys_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_menu`;
CREATE TABLE `sys_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `name` varchar(255) DEFAULT NULL COMMENT '菜单名',
  `code` varchar(255) DEFAULT NULL COMMENT '菜单编码',
  `menuId` int(11) DEFAULT NULL COMMENT '父级菜单Id',
  PRIMARY KEY (`id`),
  KEY `menuId` (`menuId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_role`;
CREATE TABLE `sys_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `name` varchar(255) DEFAULT NULL COMMENT '角色名',
  `note` varchar(255) DEFAULT NULL COMMENT '角色备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `sys_role_menu`;
CREATE TABLE `sys_role_menu` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `roleId` int(11) DEFAULT NULL COMMENT '角色Id',
  `menuId` int(11) DEFAULT NULL COMMENT '菜单Id',
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`) USING BTREE,
  KEY `menuId` (`menuId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user
-- ----------------------------
DROP TABLE IF EXISTS `sys_user`;
CREATE TABLE `sys_user` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `username` varchar(64) DEFAULT NULL COMMENT '登陆名',
  `password` varchar(255) DEFAULT NULL COMMENT '登陆密码',
  `roleId` int(11) DEFAULT NULL COMMENT '角色id',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  KEY `roleId` (`roleId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for sys_user_role
-- ----------------------------
DROP TABLE IF EXISTS `sys_user_role`;
CREATE TABLE `sys_user_role` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `userId` int(11) DEFAULT NULL COMMENT '用户Id',
  `roleId` int(11) DEFAULT NULL COMMENT '角色Id',
  PRIMARY KEY (`id`),
  KEY `userId` (`userId`) USING BTREE,
  KEY `roleId` (`roleId`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for video_news
-- ----------------------------
DROP TABLE IF EXISTS `video_news`;
CREATE TABLE `video_news` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `title` varchar(50) DEFAULT NULL COMMENT '视频标题',
  `content` varchar(255) DEFAULT NULL COMMENT '详情内容',
  `videoUrl` varchar(255) DEFAULT NULL COMMENT '视频url',
  `createTime` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for zk_expert
-- ----------------------------
DROP TABLE IF EXISTS `zk_expert`;
CREATE TABLE `zk_expert` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '主键自增',
  `name` varchar(50) DEFAULT NULL COMMENT '专家姓名',
  `position` varchar(50) DEFAULT NULL COMMENT '专家职位',
  `organizationId` int(11) DEFAULT NULL COMMENT '所属机构',
  `resume` varchar(255) DEFAULT NULL COMMENT '个人简历',
  `research` varchar(255) DEFAULT NULL COMMENT '研究领域',
  `educational` varchar(255) DEFAULT NULL COMMENT '教育背景',
  `report` varchar(255) DEFAULT NULL COMMENT '内部报告',
  `academicPaper` varchar(255) DEFAULT NULL COMMENT '学术论文',
  `composition` varchar(255) DEFAULT NULL COMMENT '学术著作',
  `researchProject` varchar(255) DEFAULT NULL COMMENT '研究项目',
  `partTimeJob` varchar(255) DEFAULT NULL COMMENT '学术兼职',
  `socialPrestige` varchar(255) DEFAULT NULL COMMENT '社会荣誉',
  `researchResult` varchar(255) DEFAULT NULL COMMENT '研究成果',
  `contactWay` varchar(50) DEFAULT NULL COMMENT '联系方式',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
