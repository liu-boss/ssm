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

 Date: 10/09/2020 17:48:20
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
INSERT INTO `t_dept` VALUES (4, 2, 'UI设计', 0, '2020-08-31 10:37:34', '2020-09-10 17:46:47', '2222');
INSERT INTO `t_dept` VALUES (5, 2, '运维部门', 0, '2020-08-31 10:37:57', '2020-08-31 10:43:44', '');
INSERT INTO `t_dept` VALUES (6, 3, '后端开发', 0, '2020-08-31 10:40:10', '2020-08-31 10:40:10', '');
INSERT INTO `t_dept` VALUES (7, 3, '前端开发', 0, '2020-08-31 10:40:20', '2020-08-31 10:40:20', '');
INSERT INTO `t_dept` VALUES (8, 0, '销售部', 0, '2020-08-31 10:40:30', '2020-08-31 10:40:30', '');
INSERT INTO `t_dept` VALUES (9, 8, '销售大厅', 0, '2020-08-31 10:40:43', '2020-08-31 10:40:43', '');
INSERT INTO `t_dept` VALUES (10, 8, '销售调研', 0, '2020-08-31 10:40:56', '2020-08-31 10:40:56', '');

-- ----------------------------
-- Table structure for t_login_log
-- ----------------------------
DROP TABLE IF EXISTS `t_login_log`;
CREATE TABLE `t_login_log`  (
  `id` bigint(11) NOT NULL AUTO_INCREMENT COMMENT 'id',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户名',
  `login_time` datetime(0) NOT NULL COMMENT '登录时间',
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '登录地点',
  `ip` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT 'IP地址',
  `os` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作系统',
  `browser` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '浏览器',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_login_log_login_time`(`login_time`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 82 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '登录日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_login_log
-- ----------------------------
INSERT INTO `t_login_log` VALUES (72, 'zhangyukang', '2020-09-10 16:54:42', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (62, 'zhangyukang', '2020-09-10 16:09:14', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (61, 'zhangyukang', '2020-09-10 16:07:51', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (57, 'zhangyukang', '2020-09-10 13:51:35', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (60, 'zhangyukang', '2020-09-10 14:01:07', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (47, 'zhangyukang', '2020-09-10 13:02:20', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (48, 'zhangyukang', '2020-09-10 13:03:56', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (59, 'test', '2020-09-10 14:00:55', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (50, 'zhangyukang', '2020-09-10 13:10:34', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (58, 'zhangyukang', '2020-09-10 13:58:27', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (52, 'zhangyukang', '2020-09-10 13:16:40', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (53, 'zhangyukang', '2020-09-10 13:22:08', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (54, 'zhangyukang', '2020-09-10 13:26:40', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (55, 'zhangyukang', '2020-09-10 13:27:48', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (56, 'zhangyukang', '2020-09-10 13:30:42', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (71, 'zhangyukang', '2020-09-10 16:48:26', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (70, 'zhangyukang', '2020-09-10 16:46:56', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (63, 'zhangyukang', '2020-09-10 16:22:08', NULL, NULL, 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (64, 'zhangyukang', '2020-09-10 16:25:55', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (65, 'zhangyukang', '2020-09-10 16:31:28', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (66, 'zhangyukang', '2020-09-10 16:33:10', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (67, 'zhangyukang', '2020-09-10 16:39:06', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (68, 'zhangyukang', '2020-09-10 16:40:37', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (69, 'zhangyukang', '2020-09-10 16:44:40', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (80, 'zhangyukang', '2020-09-10 17:42:09', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (77, 'zhangyukang', '2020-09-10 17:36:45', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (78, 'zhangyukang', '2020-09-10 17:39:07', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (79, 'zhangyukang', '2020-09-10 17:40:33', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (76, 'zhangyukang', '2020-09-10 17:35:10', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (75, 'zhangyukang', '2020-09-10 17:27:52', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (74, 'zhangyukang', '2020-09-10 17:14:07', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (73, 'zhangyukang', '2020-09-10 17:13:34', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');
INSERT INTO `t_login_log` VALUES (81, 'zhangyukang', '2020-09-10 17:46:28', '内网IP|0|0|内网IP|内网IP', '127.0.0.1', 'Windows 10', 'Chrome 85');

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
) ENGINE = InnoDB AUTO_INCREMENT = 30 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '菜单表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_menu
-- ----------------------------
INSERT INTO `t_menu` VALUES (1, 0, '系统管理', '', '', 'icon-standard-application-view-tile', '0', 1, '2020-08-31 10:09:35', '2020-09-06 13:56:14');
INSERT INTO `t_menu` VALUES (2, 1, '用户管理', '/system/userPage.do', 'system:user:view', 'icon-standard-application-link', '0', 1, '2020-08-31 10:11:08', '2020-09-06 13:05:38');
INSERT INTO `t_menu` VALUES (3, 1, '角色管理', '/system/rolePage.do', 'system:role:view', 'icon-standard-application-osx-terminal', '0', 2, '2020-08-31 10:13:00', '2020-09-08 20:52:26');
INSERT INTO `t_menu` VALUES (4, 1, '部门管理', '/system/deptPage.do', 'system:dept:view', 'icon-standard-application-osx', '0', 3, '2020-08-31 10:15:38', '2020-09-08 20:52:40');
INSERT INTO `t_menu` VALUES (5, 1, '菜单管理', '/system/menuPage.do', 'system:menu:view', 'icon-standard-shield-add', '0', 4, '2020-08-31 10:16:26', '2020-09-08 20:52:50');
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
INSERT INTO `t_menu` VALUES (21, 25, '在线用户', '/monitor/onlinePage.do', 'monitor:online:view', 'icon-standard-application-key', '0', 1, '2020-08-31 20:33:03', '2020-09-08 20:53:01');
INSERT INTO `t_menu` VALUES (22, 21, '踢出用户', NULL, 'system:online:forceLogout', NULL, '1', 0, '2020-08-31 20:33:38', '2020-09-08 20:48:15');
INSERT INTO `t_menu` VALUES (23, 25, 'Druid监控', '/monitor/druidPage.do', 'monitor:druid:view', 'icon-standard-key', '0', 3, '2020-09-06 12:11:15', '2020-09-08 20:33:26');
INSERT INTO `t_menu` VALUES (24, 25, '登入记录', '/monitor/loginLogPage.do', 'monitor:loginLog:view', 'icon-standard-lock-edit', '0', 2, '2020-09-08 20:23:19', '2020-09-08 20:56:18');
INSERT INTO `t_menu` VALUES (25, 0, '监控管理', '', '', 'icon-standard-calculator', '0', 2, '2020-09-08 20:31:56', '2020-09-08 20:51:54');
INSERT INTO `t_menu` VALUES (26, 24, '删除操作', NULL, 'monitor:loginLog:delete', NULL, '1', 0, '2020-09-08 20:44:48', '2020-09-08 20:44:48');
INSERT INTO `t_menu` VALUES (27, 25, '操作日志', '/monitor/operateLogPage.do', 'monitor:operateLog:view', 'icon-standard-calendar-view-day', '0', 0, '2020-09-10 16:23:57', '2020-09-10 16:23:57');
INSERT INTO `t_menu` VALUES (28, 27, '删除操作日志', NULL, 'monitor:operateLog:delete', NULL, '1', 0, '2020-09-10 16:49:21', '2020-09-10 16:49:21');
INSERT INTO `t_menu` VALUES (29, 2, '重置用户密码', NULL, 'system:user:resetPassword', NULL, '1', 0, '2020-09-10 16:51:17', '2020-09-10 16:51:17');

-- ----------------------------
-- Table structure for t_operate
-- ----------------------------
DROP TABLE IF EXISTS `t_operate`;
CREATE TABLE `t_operate`  (
  `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '日志ID',
  `username` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作用户',
  `operation` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作内容',
  `time` decimal(11, 0) NULL DEFAULT NULL COMMENT '耗时',
  `controller` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL,
  `method` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '操作方法',
  `return_value` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '返回值',
  `params` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '方法参数',
  `ip` varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作者IP',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `location` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL COMMENT '操作地点',
  `type` int(255) NULL DEFAULT NULL COMMENT '1: 成功,2: 异常',
  `error_msg` text CHARACTER SET utf8 COLLATE utf8_general_ci NULL COMMENT '错误信息',
  PRIMARY KEY (`id`) USING BTREE,
  INDEX `t_log_create_time`(`create_time`) USING BTREE
) ENGINE = MyISAM AUTO_INCREMENT = 73 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '操作日志表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_operate
-- ----------------------------
INSERT INTO `t_operate` VALUES (72, 'zhangyukang', '[系统模块]=>更新部门', 88, 'DeptController', 'update ()', '{\"code\":0,\"msg\":null,\"data\":null}', '[{\"id\":4,\"parentId\":2,\"deptName\":\"UI设计\",\"orderNum\":0,\"remark\":\"2222\"}]', '127.0.0.1', '2020-09-10 17:46:47', '内网IP|0|0|内网IP|内网IP', 1, NULL);
INSERT INTO `t_operate` VALUES (70, 'zhangyukang', '[系统模块]=>密码重置', 25, 'UserController', 'resetPassword ()', '{\"code\":0,\"msg\":null,\"data\":null}', '[4]', '127.0.0.1', '2020-09-10 17:44:12', '内网IP|0|0|内网IP|内网IP', 1, NULL);
INSERT INTO `t_operate` VALUES (71, 'zhangyukang', '[系统模块]=>编辑部门', 3, 'DeptController', 'get ()', '{\"code\":0,\"msg\":null,\"data\":{\"id\":4,\"parentId\":2,\"deptName\":\"UI设计\",\"orderNum\":0,\"createTime\":1598841454000,\"modifyTime\":1598841808000,\"remark\":\"\"}}', '[4]', '127.0.0.1', '2020-09-10 17:46:44', '内网IP|0|0|内网IP|内网IP', 1, NULL);
INSERT INTO `t_operate` VALUES (68, 'zhangyukang', '[系统模块]=>新增用户', 24, 'UserController', 'add ()', 'null', '[{\"username\":\"test\",\"password\":\"1234\",\"email\":\"test@qq.com\",\"mobile\":\"15041414141\",\"sex\":\"1\",\"status\":\"1\",\"description\":\"ewrwer\",\"roleIdList\":[2],\"deptId\":5}]', '127.0.0.1', '2020-09-10 17:03:05', '内网IP|0|0|内网IP|内网IP', 2, '用户名已被占用');
INSERT INTO `t_operate` VALUES (69, 'zhangyukang', '[系统模块]=>新增用户', 6, 'UserController', 'add ()', 'null', '[{\"username\":\"zhangyuakng\",\"password\":\"1234\",\"email\":\"test@qq.com\",\"mobile\":\"15041414141\",\"sex\":\"1\",\"status\":\"1\",\"description\":\"ewrwer\",\"roleIdList\":[2],\"deptId\":5}]', '127.0.0.1', '2020-09-10 17:03:12', '内网IP|0|0|内网IP|内网IP', 2, '邮箱已被占用');

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
INSERT INTO `t_role` VALUES (1, '超级管理员', '系统的最高权限', '2020-08-31 10:10:02', '2020-09-10 16:51:25');
INSERT INTO `t_role` VALUES (2, '网站管理员', '管理文章', '2020-08-31 10:55:02', '2020-09-10 16:39:28');

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
INSERT INTO `t_role_menu` VALUES (2, 18);
INSERT INTO `t_role_menu` VALUES (2, 19);
INSERT INTO `t_role_menu` VALUES (2, 20);
INSERT INTO `t_role_menu` VALUES (2, 21);
INSERT INTO `t_role_menu` VALUES (2, 25);
INSERT INTO `t_role_menu` VALUES (1, 1);
INSERT INTO `t_role_menu` VALUES (1, 2);
INSERT INTO `t_role_menu` VALUES (1, 9);
INSERT INTO `t_role_menu` VALUES (1, 10);
INSERT INTO `t_role_menu` VALUES (1, 11);
INSERT INTO `t_role_menu` VALUES (1, 29);
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
INSERT INTO `t_role_menu` VALUES (1, 25);
INSERT INTO `t_role_menu` VALUES (1, 27);
INSERT INTO `t_role_menu` VALUES (1, 28);
INSERT INTO `t_role_menu` VALUES (1, 21);
INSERT INTO `t_role_menu` VALUES (1, 22);
INSERT INTO `t_role_menu` VALUES (1, 24);
INSERT INTO `t_role_menu` VALUES (1, 26);
INSERT INTO `t_role_menu` VALUES (1, 23);
INSERT INTO `t_role_menu` VALUES (1, 18);
INSERT INTO `t_role_menu` VALUES (1, 19);
INSERT INTO `t_role_menu` VALUES (1, 20);

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
) ENGINE = InnoDB AUTO_INCREMENT = 5 CHARACTER SET = utf8 COLLATE = utf8_general_ci COMMENT = '用户表' ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of t_user
-- ----------------------------
INSERT INTO `t_user` VALUES (1, 'zhangyukang', '4decb28fdcc4f5ea2f847337f1826a2b', 'bbfc0d4c899dea9965a9002f36841708', '3053161401@qq.com', '15079437282', '1', '2020-08-31 09:46:47', '2020-08-31 10:44:00', '2020-09-10 17:46:28', '1', NULL, 'test', 6, NULL);
INSERT INTO `t_user` VALUES (2, 'test', '28dcfab8a4b4bc1ed303ea1ba2c7f695', 'c02eafe490675ce8cc157d45933de2e6', 'test@qq.com', '13426527485', '1', '2020-08-31 10:54:27', '2020-09-10 16:54:48', '2020-09-10 14:00:55', '1', NULL, 'test', 6, 1);
INSERT INTO `t_user` VALUES (3, 'test2', '83839e2890f7219b55446b003ef93ee9', '8961fd3737f659fea8f96ba51858c5cd', 'test2@qq.com', '15241415254', '1', '2020-09-08 09:00:26', '2020-09-10 16:39:19', NULL, '0', NULL, '1111', 4, 1);
INSERT INTO `t_user` VALUES (4, 'test3', 'a8890f548479de9b23ca2f69d021020a', 'f81969fdf0909b84939d54dd219bfd38', 'test3@qq.com', '13525254541', '1', '2020-09-10 13:59:42', '2020-09-10 17:44:12', NULL, '0', NULL, '11', 5, 1);

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
INSERT INTO `t_user_role` VALUES (4, 2);
INSERT INTO `t_user_role` VALUES (3, 2);

SET FOREIGN_KEY_CHECKS = 1;
