-- =============================================
-- 教师端操作系统 - 数据库脚本
-- 新增教师角色、教师端菜单、测试账号
-- =============================================

-- ----------------------------
-- 1、tea_teacher 表新增 user_id 字段（兼容MySQL 5.7+/8.0）
-- ----------------------------
SET @col_exists = (SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS
  WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = 'tea_teacher' AND COLUMN_NAME = 'user_id');
SET @sql = IF(@col_exists = 0,
  'ALTER TABLE tea_teacher ADD COLUMN user_id BIGINT DEFAULT NULL COMMENT ''关联系统用户ID''',
  'SELECT 1');
PREPARE stmt FROM @sql;
EXECUTE stmt;
DEALLOCATE PREPARE stmt;

-- ----------------------------
-- 2、新增"教师"角色（role_id=3）
-- ----------------------------
DELETE FROM sys_role_menu WHERE role_id = 3;
DELETE FROM sys_role WHERE role_id = 3;
INSERT INTO sys_role VALUES(3, '教师', 'teacher', 3, 5, 1, 1, '0', '0', 'admin', sysdate(), '', NULL, '教师角色-仅本人数据');

-- ----------------------------
-- 3、新增教师端菜单（menu_id 从 3000 起）
-- ----------------------------
-- 清理已有教师端菜单
DELETE FROM sys_menu WHERE menu_id >= 3000 AND menu_id < 3100;

-- 一级目录：教师端
INSERT INTO sys_menu VALUES(3000, '教师端', 0, 5, 'portal', NULL, '', '', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', sysdate(), '', NULL, '教师端操作目录');

-- 二级菜单
INSERT INTO sys_menu VALUES(3001, '个人中心', 3000, 1, 'info',       'portal/info/index',       '', '', 1, 0, 'C', '0', '0', 'portal:info:query',  'user',     'admin', sysdate(), '', NULL, '教师个人中心');
INSERT INTO sys_menu VALUES(3002, '薪资查询', 3000, 2, 'salary',     'portal/salary/index',     '', '', 1, 0, 'C', '0', '0', 'portal:salary:query', 'money',   'admin', sysdate(), '', NULL, '教师薪资查询');
INSERT INTO sys_menu VALUES(3003, '培训报名', 3000, 3, 'training',   'portal/training/index',   '', '', 1, 0, 'C', '0', '0', 'portal:training:query', 'education', 'admin', sysdate(), '', NULL, '教师培训报名');
INSERT INTO sys_menu VALUES(3004, '学时统计', 3000, 4, 'hours',      'portal/training/hours',   '', '', 1, 0, 'C', '0', '0', 'portal:training:query', 'log',     'admin', sysdate(), '', NULL, '教师学时统计');
INSERT INTO sys_menu VALUES(3005, '考核填报', 3000, 5, 'assessment', 'portal/assessment/index', '', '', 1, 0, 'C', '0', '0', 'portal:assess:query', 'edit',    'admin', sysdate(), '', NULL, '教师考核填报');
INSERT INTO sys_menu VALUES(3006, '考核结果', 3000, 6, 'result',     'portal/assessment/result', '', '', 1, 0, 'C', '0', '0', 'portal:assess:query', 'chart',  'admin', sysdate(), '', NULL, '教师考核结果');

-- 按钮权限 - 个人中心
INSERT INTO sys_menu VALUES(3010, '个人信息查询', 3001, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'portal:info:query',  '#', 'admin', sysdate(), '', NULL, '');
INSERT INTO sys_menu VALUES(3011, '个人信息修改', 3001, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'portal:info:edit',   '#', 'admin', sysdate(), '', NULL, '');

-- 按钮权限 - 薪资查询
INSERT INTO sys_menu VALUES(3020, '薪资查询', 3002, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'portal:salary:query', '#', 'admin', sysdate(), '', NULL, '');

-- 按钮权限 - 培训报名
INSERT INTO sys_menu VALUES(3030, '培训查询', 3003, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'portal:training:query',  '#', 'admin', sysdate(), '', NULL, '');
INSERT INTO sys_menu VALUES(3031, '培训报名', 3003, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'portal:training:enroll', '#', 'admin', sysdate(), '', NULL, '');

-- 按钮权限 - 考核填报
INSERT INTO sys_menu VALUES(3050, '考核查询', 3005, 1, '', '', '', '', 1, 0, 'F', '0', '0', 'portal:assess:query',  '#', 'admin', sysdate(), '', NULL, '');
INSERT INTO sys_menu VALUES(3051, '考核填报', 3005, 2, '', '', '', '', 1, 0, 'F', '0', '0', 'portal:assess:edit',   '#', 'admin', sysdate(), '', NULL, '');
INSERT INTO sys_menu VALUES(3052, '考核提交', 3005, 3, '', '', '', '', 1, 0, 'F', '0', '0', 'portal:assess:submit', '#', 'admin', sysdate(), '', NULL, '');

-- ----------------------------
-- 4、为教师角色绑定菜单权限
-- ----------------------------
INSERT INTO sys_role_menu VALUES(3, 3000);
INSERT INTO sys_role_menu VALUES(3, 3001);
INSERT INTO sys_role_menu VALUES(3, 3002);
INSERT INTO sys_role_menu VALUES(3, 3003);
INSERT INTO sys_role_menu VALUES(3, 3004);
INSERT INTO sys_role_menu VALUES(3, 3005);
INSERT INTO sys_role_menu VALUES(3, 3006);
INSERT INTO sys_role_menu VALUES(3, 3010);
INSERT INTO sys_role_menu VALUES(3, 3011);
INSERT INTO sys_role_menu VALUES(3, 3020);
INSERT INTO sys_role_menu VALUES(3, 3030);
INSERT INTO sys_role_menu VALUES(3, 3031);
INSERT INTO sys_role_menu VALUES(3, 3050);
INSERT INTO sys_role_menu VALUES(3, 3051);
INSERT INTO sys_role_menu VALUES(3, 3052);

-- ----------------------------
-- 5、创建测试教师账号
-- user_id=100, user_name=teacher1, 密码=admin123
-- ----------------------------
DELETE FROM sys_user_role WHERE user_id = 100;
DELETE FROM sys_user WHERE user_id = 100;
INSERT INTO sys_user VALUES(100, 103, 'teacher1', '张老师', '00', 'teacher1@example.com', '13800001111', '0', '', '$2a$10$7JB720yubVSZvUI0rEqK/.VqGOZTH.ulu33dHOiBE8ByOhJIrdAu2', '0', '0', '127.0.0.1', sysdate(), sysdate(), 'admin', sysdate(), '', NULL, '测试教师账号');
INSERT INTO sys_user_role VALUES(100, 3);

-- ----------------------------
-- 6、将 tea_teacher 表中第一条教师记录关联到测试教师账号
-- 注意：请先确认 teacher_id 的实际值，手动调整
-- ----------------------------
UPDATE tea_teacher SET user_id = 100 WHERE teacher_id = 1;
