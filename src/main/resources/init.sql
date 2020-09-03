/*
 Navicat Premium Data Transfer

 Source Server         : localhost_3307
 Source Server Type    : MySQL
 Source Server Version : 50728
 Source Host           : localhost:3307
 Source Schema         : ssm_shiro

 Target Server Type    : MySQL
 Target Server Version : 50728
 File Encoding         : 65001

 Date: 03/09/2020 16:14:34
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for t_dept
-- ----------------------------
DROP TABLE IF EXISTS `t_dept`;
CREATE TABLE `t_dept`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `parent_id` bigint(20) NOT NULL COMMENT '上级部门ID',
  `dept_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '部门名称',
  `order_num` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '备注信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_dept_parent_id`(`parent_id`) USING BTREE,
  INDEX `t_dept_dept_name`(`dept_name`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 11 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '部门表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_dept
-- ----------------------------
INSERT INTO `t_dept` VALUES (1, 0, '研发部', 0, '2020-08-31 10:36:44', '2020-08-31 10:36:44', '');
INSERT INTO `t_dept` VALUES (2, 1, '产品部门', 0, '2020-08-31 10:37:07', '2020-08-31 10:37:07', '');
INSERT INTO `t_dept` VALUES (3, 1, '开发部门', 0, '2020-08-31 10:37:24', '2020-08-31 10:37:24', '');
INSERT INTO `t_dept` VALUES (4, 2, 'UI设计', 0, '2020-08-31 10:37:34', '2020-08-31 10:43:28', '');
INSERT INTO `t_dept` VALUES (5, 2, '运维部门', 0, '2020-08-31 10:37:57', '2020-08-31 10:43:44', '');
INSERT INTO `t_dept` VALUES (6, 3, '后端开发', 0, '2020-08-31 10:40:10', '2020-08-31 10:40:10', '');
INSERT INTO `t_dept` VALUES (7, 3, '前端开发', 0, '2020-08-31 10:40:20', '2020-08-31 10:40:20', '');
INSERT INTO `t_dept` VALUES (8, 0, '销售部', 0, '2020-08-31 10:40:30', '2020-08-31 10:40:30', '');
INSERT INTO `t_dept` VALUES (9, 8, '销售大厅', 0, '2020-08-31 10:40:43', '2020-08-31 10:40:43', '');
INSERT INTO `t_dept` VALUES (10, 8, '销售调研', 0, '2020-08-31 10:40:56', '2020-08-31 10:40:56', '');

-- ----------------------------
-- Table structure for t_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_menu`;
CREATE TABLE `t_menu`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '菜单/按钮ID',
  `parent_id` bigint(20) NOT NULL COMMENT '上级菜单ID',
  `menu_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '菜单/按钮名称',
  `url` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '菜单URL',
  `perms` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '权限标识',
  `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '图标',
  `type` char(2) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '类型 0菜单 1按钮',
  `order_num` bigint(20) NOT NULL COMMENT '排序',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_menu_parent_id`(`parent_id`) USING BTREE,
  INDEX `t_menu_menu_id`(`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 23 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, 0, '系统管理', '', '', 'icon-add', '0', 1, '2020-08-31 10:09:35', '2020-08-31 10:45:01');
INSERT INTO `t_menu` VALUES (2, 1, '用户管理', '/system/userPage.do', 'system:user:view', 'icon-add', '0', 1, '2020-08-31 10:11:08', '2020-08-31 10:11:12');
INSERT INTO `t_menu` VALUES (3, 1, '角色管理', '/system/rolePage.do', 'system:role:view', 'icon-edit', '0', 1, '2020-08-31 10:13:00', '2020-08-31 10:25:05');
INSERT INTO `t_menu` VALUES (4, 1, '部门管理', '/system/deptPage.do', 'system:dept:view', 'icon-edit', '0', 1, '2020-08-31 10:15:38', '2020-08-31 10:15:42');
INSERT INTO `t_menu` VALUES (5, 1, '菜单管理', '/system/menuPage.do', 'system:menu:view', 'icon-edit', '0', 1, '2020-08-31 10:16:26', '2020-08-31 10:16:27');
INSERT INTO `t_menu` VALUES (6, 5, '菜单添加', NULL, 'system:menu:add', NULL, '1', 1, '2020-08-31 10:18:28', '2020-08-31 10:18:31');
INSERT INTO `t_menu` VALUES (7, 5, '菜单修改', NULL, 'system:menu:update', NULL, '1', 1, '2020-08-31 10:19:10', '2020-08-31 10:19:14');
INSERT INTO `t_menu` VALUES (8, 5, '菜单删除', NULL, 'system:menu:delete', NULL, '1', 0, '2020-08-31 10:20:14', '2020-09-03 16:05:29');
INSERT INTO `t_menu` VALUES (9, 2, '用户添加', NULL, 'system:user:add', NULL, '1', 0, '2020-08-31 10:20:57', '2020-08-31 10:20:57');
INSERT INTO `t_menu` VALUES (10, 2, '用户删除', NULL, 'system:user:delete', NULL, '1', 0, '2020-08-31 10:21:16', '2020-08-31 10:21:16');
INSERT INTO `t_menu` VALUES (11, 2, '用户修改', NULL, 'system:user:update', NULL, '1', 0, '2020-08-31 10:21:45', '2020-08-31 10:21:45');
INSERT INTO `t_menu` VALUES (12, 3, '角色添加', NULL, 'system:role:add', NULL, '1', 0, '2020-08-31 10:22:24', '2020-08-31 10:22:24');
INSERT INTO `t_menu` VALUES (13, 3, '角色修改', NULL, 'system:role:update', NULL, '1', 0, '2020-08-31 10:22:44', '2020-08-31 10:22:44');
INSERT INTO `t_menu` VALUES (14, 3, '角色删除', NULL, 'system:role:delete', NULL, '1', 0, '2020-08-31 10:23:09', '2020-08-31 10:23:09');
INSERT INTO `t_menu` VALUES (15, 4, '部门添加', NULL, 'system:dept:add', NULL, '1', 0, '2020-08-31 10:23:36', '2020-08-31 10:23:36');
INSERT INTO `t_menu` VALUES (16, 4, '部门修改', NULL, 'system:dept:update', NULL, '1', 0, '2020-08-31 10:24:04', '2020-08-31 10:24:04');
INSERT INTO `t_menu` VALUES (17, 4, '部门删除', NULL, 'system:dept:delete', NULL, '1', 0, '2020-08-31 10:24:34', '2020-08-31 10:24:34');
INSERT INTO `t_menu` VALUES (21, 1, '在线用户', '/system/onlinePage.do', 'system:online:view', 'icon-edit', '0', 0, '2020-08-31 20:33:03', '2020-08-31 20:36:22');
INSERT INTO `t_menu` VALUES (22, 21, '踢出用户', NULL, 'sytem:online:forgout', NULL, '1', 0, '2020-08-31 20:33:38', '2020-08-31 20:33:38');

-- ----------------------------
-- Table structure for t_role
-- ----------------------------
DROP TABLE IF EXISTS `t_role`;
CREATE TABLE `t_role`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色名称',
  `remark` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '角色描述',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role
-- ----------------------------
INSERT INTO `t_role` VALUES (1, '超级管理员', '系统的最高权限', '2020-08-31 10:10:02', '2020-09-03 15:31:23');
INSERT INTO `t_role` VALUES (2, '网站管理员', '管理文章', '2020-08-31 10:55:02', '2020-09-01 17:12:54');

-- ----------------------------
-- Table structure for t_role_menu
-- ----------------------------
DROP TABLE IF EXISTS `t_role_menu`;
CREATE TABLE `t_role_menu`  (
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  `menu_id` bigint(20) NOT NULL COMMENT '菜单/按钮ID',
  INDEX `t_role_menu_menu_id`(`menu_id`) USING BTREE,
  INDEX `t_role_menu_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '角色菜单关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_role_menu
-- ----------------------------
INSERT INTO `t_role_menu` VALUES (2, 1);
INSERT INTO `t_role_menu` VALUES (2, 21);
INSERT INTO `t_role_menu` VALUES (2, 18);
INSERT INTO `t_role_menu` VALUES (2, 19);
INSERT INTO `t_role_menu` VALUES (2, 20);
INSERT INTO `t_role_menu` VALUES (1, 1);
INSERT INTO `t_role_menu` VALUES (1, 21);
INSERT INTO `t_role_menu` VALUES (1, 22);
INSERT INTO `t_role_menu` VALUES (1, 2);
INSERT INTO `t_role_menu` VALUES (1, 9);
INSERT INTO `t_role_menu` VALUES (1, 10);
INSERT INTO `t_role_menu` VALUES (1, 11);
INSERT INTO `t_role_menu` VALUES (1, 3);
INSERT INTO `t_role_menu` VALUES (1, 12);
INSERT INTO `t_role_menu` VALUES (1, 13);
INSERT INTO `t_role_menu` VALUES (1, 14);
INSERT INTO `t_role_menu` VALUES (1, 4);
INSERT INTO `t_role_menu` VALUES (1, 15);
INSERT INTO `t_role_menu` VALUES (1, 16);
INSERT INTO `t_role_menu` VALUES (1, 17);
INSERT INTO `t_role_menu` VALUES (1, 5);
INSERT INTO `t_role_menu` VALUES (1, 8);
INSERT INTO `t_role_menu` VALUES (1, 6);
INSERT INTO `t_role_menu` VALUES (1, 7);
INSERT INTO `t_role_menu` VALUES (1, 18);
INSERT INTO `t_role_menu` VALUES (1, 20);
INSERT INTO `t_role_menu` VALUES (1, 19);

-- ----------------------------
-- Table structure for t_user
-- ----------------------------
DROP TABLE IF EXISTS `t_user`;
CREATE TABLE `t_user`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `password` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码',
  `salt` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '盐',
  `email` varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '邮箱',
  `mobile` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '联系电话',
  `status` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态 0锁定 1有效',
  `create_time` datetime(0) NOT NULL COMMENT '创建时间',
  `modify_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `last_login_time` datetime(0) NULL DEFAULT NULL COMMENT '最近访问时间',
  `sex` char(1) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '性别 0男 1女 2保密',
  `avatar` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '头像',
  `description` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '描述',
  `dept_id` bigint(20) NULL DEFAULT NULL COMMENT '部门ID',
  `type` int(255) NULL DEFAULT NULL COMMENT '用户类型 0 超级管理 1 普通用户',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_user_username`(`username`) USING BTREE,
  INDEX `t_user_mobile`(`mobile`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'zhangyukang', '4decb28fdcc4f5ea2f847337f1826a2b', 'bbfc0d4c899dea9965a9002f36841708', '3053161401@qq.com', '15079437282', '1', '2020-08-31 09:46:47', '2020-08-31 10:44:00', '2020-09-03 16:13:28', '1', NULL, 'test', 6, NULL);
INSERT INTO `t_user` VALUES (2, 'test', 'cfb0d432214ed19e6eee417e405d8354', 'd8c057270180c5dbd8a2f62f4bdeec84', 'test@qq.com', '13426527485', '1', '2020-08-31 10:54:27', '2020-08-31 10:55:16', '2020-09-01 18:19:57', '0', NULL, 'test', 6, 1);

-- ----------------------------
-- Table structure for t_user_role
-- ----------------------------
DROP TABLE IF EXISTS `t_user_role`;
CREATE TABLE `t_user_role`  (
  `user_id` bigint(20) NOT NULL COMMENT '用户ID',
  `role_id` bigint(20) NOT NULL COMMENT '角色ID',
  INDEX `t_user_role_user_id`(`user_id`) USING BTREE,
  INDEX `t_user_role_role_id`(`role_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户角色关联表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user_role
-- ----------------------------
INSERT INTO `t_user_role` VALUES (1, 1);
INSERT INTO `t_user_role` VALUES (2, 2);

SET FOREIGN_KEY_CHECKS = 1;
