-- =============================================
-- 补充修复：为角色2（普通角色）添加教师模块依赖的系统菜单权限
-- 如果已存在则忽略（INSERT IGNORE）
-- =============================================

-- 系统管理相关菜单（确保教师模块页面依赖的系统接口有权限）
-- 103=部门管理(system:dept:list)，教师个人信息和引进计划页面需要调用部门列表
INSERT IGNORE INTO sys_role_menu VALUES ('2', '103');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '1016');  -- 部门查询

-- 确保教师管理父菜单(2000)及其所有子菜单和按钮权限都在角色2中
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2000');  -- 教师管理（父目录）
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2001');  -- 个人信息管理
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2002');  -- 个人信息
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2003');  -- 薪资管理
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2004');  -- 薪资报表
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2005');  -- 人才引进
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2006');  -- 教师培训
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2007');  -- 教师考核

-- 人才引进子菜单
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2015');  -- 引进计划
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2016');  -- 应聘管理
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2017');  -- 面试管理

-- 教师培训子菜单
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2018');  -- 培训计划
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2019');  -- 报名管理
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2020');  -- 结业管理
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2021');  -- 学时统计

-- 教师考核子菜单
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2022');  -- 考核方案
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2023');  -- 考核填报
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2024');  -- 审核管理
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2025');  -- 结果统计

-- 个人信息管理按钮权限
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2100');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2101');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2102');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2103');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2104');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2105');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2106');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2107');
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2108');

-- 人才引进按钮权限
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2200');  -- 计划查询
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2201');  -- 计划新增
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2202');  -- 计划修改
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2203');  -- 计划删除
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2204');  -- 应聘查询
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2205');  -- 应聘新增
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2206');  -- 应聘修改
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2207');  -- 应聘删除
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2208');  -- 应聘审批
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2209');  -- 面试查询
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2210');  -- 面试新增
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2211');  -- 面试修改
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2212');  -- 面试删除

-- 教师培训按钮权限
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2220');  -- 培训查询
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2221');  -- 培训新增
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2222');  -- 培训修改
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2223');  -- 培训删除
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2224');  -- 报名查询
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2225');  -- 报名审批
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2226');  -- 结业查询
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2227');  -- 结业新增
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2228');  -- 结业修改

-- 教师考核按钮权限
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2230');  -- 考核查询
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2231');  -- 考核新增
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2232');  -- 考核修改
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2233');  -- 考核删除
INSERT IGNORE INTO sys_role_menu VALUES ('2', '2234');  -- 考核审核
