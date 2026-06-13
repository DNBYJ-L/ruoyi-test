-- =============================================
-- 教师管理系统 - 数据库脚本
-- 模块1：教师个人信息管理
-- 模块3：薪资统计
-- =============================================

-- ----------------------------
-- 1、教师主表
-- ----------------------------
drop table if exists tea_teacher;
create table tea_teacher (
  teacher_id        bigint(20)      not null auto_increment    comment '教师ID',
  teacher_no        varchar(20)     not null                   comment '工号（T+年份+4位序号）',
  teacher_name      varchar(30)     not null                   comment '姓名',
  gender            char(1)         default '0'                comment '性别（0男 1女 2未知）',
  birth_date        date            default null               comment '出生日期',
  id_card           varchar(18)     default ''                 comment '身份证号',
  nation            varchar(20)     default ''                 comment '民族',
  political_status  varchar(20)     default ''                 comment '政治面貌',
  phone             varchar(11)     default ''                 comment '手机号',
  email             varchar(50)     default ''                 comment '邮箱',
  office_address    varchar(200)    default ''                 comment '办公地址',
  emergency_contact varchar(50)     default ''                 comment '紧急联系人',
  emergency_phone   varchar(11)     default ''                 comment '紧急联系人电话',
  avatar            varchar(100)    default ''                 comment '照片地址',
  dept_id           bigint(20)      default null               comment '所属院系ID',
  status            char(1)         default '0'                comment '状态（0在职 1离职 2退休）',
  del_flag          char(1)         default '0'                comment '删除标志（0代表存在 2代表删除）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (teacher_id),
  unique key uk_teacher_no (teacher_no)
) engine=innodb auto_increment=100 comment = '教师信息表';

-- ----------------------------
-- 2、学历学位表
-- ----------------------------
drop table if exists tea_education;
create table tea_education (
  education_id      bigint(20)      not null auto_increment    comment '学历ID',
  teacher_id        bigint(20)      not null                   comment '教师ID',
  education_level   varchar(20)     default ''                 comment '学历（本科/硕士/博士）',
  graduate_school   varchar(100)    default ''                 comment '毕业院校',
  major             varchar(100)    default ''                 comment '专业',
  degree_type       varchar(20)     default ''                 comment '学位类型（学士/硕士/博士）',
  obtain_date       date            default null               comment '获得时间',
  is_highest        char(1)         default '0'                comment '是否最高学历（0否 1是）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (education_id),
  key idx_teacher_id (teacher_id)
) engine=innodb auto_increment=100 comment = '教师学历学位表';

-- ----------------------------
-- 3、职称记录表
-- ----------------------------
drop table if exists tea_title_record;
create table tea_title_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  teacher_id        bigint(20)      not null                   comment '教师ID',
  title_name        varchar(50)     default ''                 comment '职称名称',
  title_series      varchar(50)     default ''                 comment '职称系列',
  obtain_date       date            default null               comment '取得时间',
  hire_position     varchar(50)     default ''                 comment '聘任岗位',
  is_current        char(1)         default '0'                comment '是否当前职称（0否 1是）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (record_id),
  key idx_teacher_id (teacher_id)
) engine=innodb auto_increment=100 comment = '教师职称记录表';

-- ----------------------------
-- 4、学科专业表
-- ----------------------------
drop table if exists tea_discipline;
create table tea_discipline (
  discipline_id     bigint(20)      not null auto_increment    comment '学科ID',
  teacher_id        bigint(20)      not null                   comment '教师ID',
  first_discipline  varchar(100)    default ''                 comment '一级学科',
  second_discipline varchar(100)    default ''                 comment '二级学科',
  research_direction varchar(200)   default ''                 comment '研究方向',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (discipline_id),
  key idx_teacher_id (teacher_id)
) engine=innodb auto_increment=100 comment = '教师学科专业表';

-- ----------------------------
-- 5、薪资配置表
-- ----------------------------
drop table if exists tea_salary_config;
create table tea_salary_config (
  config_id         bigint(20)      not null auto_increment    comment '配置ID',
  title_level       varchar(50)     not null                   comment '职称等级',
  post_salary       decimal(10,2)   default 0                  comment '岗位工资标准',
  scale_salary      decimal(10,2)   default 0                  comment '薪级工资标准',
  base_performance  decimal(10,2)   default 0                  comment '基础性绩效标准',
  talent_allowance  decimal(10,2)   default 0                  comment '人才津贴标准',
  housing_allowance decimal(10,2)   default 0                  comment '住房补贴标准',
  status            char(1)         default '0'                comment '状态（0正常 1停用）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (config_id)
) engine=innodb auto_increment=100 comment = '薪资配置表';

-- ----------------------------
-- 6、薪资发放记录表
-- ----------------------------
drop table if exists tea_salary_record;
create table tea_salary_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  teacher_id        bigint(20)      not null                   comment '教师ID',
  salary_year       int(4)          not null                   comment '年份',
  salary_month      int(2)          not null                   comment '月份',
  post_salary       decimal(10,2)   default 0                  comment '岗位工资',
  scale_salary      decimal(10,2)   default 0                  comment '薪级工资',
  base_performance  decimal(10,2)   default 0                  comment '基础性绩效',
  reward_performance decimal(10,2)  default 0                  comment '奖励性绩效',
  talent_allowance  decimal(10,2)   default 0                  comment '人才津贴',
  housing_allowance decimal(10,2)   default 0                  comment '住房补贴',
  other_allowance   decimal(10,2)   default 0                  comment '其他津贴',
  social_insurance  decimal(10,2)   default 0                  comment '代扣社保',
  housing_fund      decimal(10,2)   default 0                  comment '代扣公积金',
  income_tax        decimal(10,2)   default 0                  comment '代扣个税',
  actual_salary     decimal(10,2)   default 0                  comment '实发金额',
  gross_salary      decimal(10,2)   default 0                  comment '税前应发',
  issue_status      char(1)         default '0'                comment '发放状态（0未发放 1已发放）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (record_id),
  unique key uk_teacher_month (teacher_id, salary_year, salary_month),
  key idx_teacher_id (teacher_id)
) engine=innodb auto_increment=100 comment = '薪资发放记录表';

-- ----------------------------
-- 7、薪资调整记录表
-- ----------------------------
drop table if exists tea_salary_adjust;
create table tea_salary_adjust (
  adjust_id         bigint(20)      not null auto_increment    comment '调整ID',
  teacher_id        bigint(20)      not null                   comment '教师ID',
  adjust_reason     varchar(200)    default ''                 comment '调整原因',
  before_amount     decimal(10,2)   default 0                  comment '调整前金额',
  after_amount      decimal(10,2)   default 0                  comment '调整后金额',
  effective_month   varchar(7)      default ''                 comment '生效月份（YYYY-MM）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (adjust_id),
  key idx_teacher_id (teacher_id)
) engine=innodb auto_increment=100 comment = '薪资调整记录表';

-- =============================================
-- 菜单数据
-- =============================================
-- 一级菜单：教师管理
insert into sys_menu values('2000', '教师管理', '0', '5', 'teacher', null, '', '', 1, 0, 'M', '0', '0', '', 'peoples', 'admin', sysdate(), '', null, '教师管理目录');

-- 二级菜单
insert into sys_menu values('2001', '个人信息管理', '2000', '1', 'info',  'teacher/info/index',  '', '', 1, 0, 'C', '0', '0', 'teacher:info:list', 'user', 'admin', sysdate(), '', null, '教师个人信息管理菜单');
insert into sys_menu values('2002', '薪资统计',     '2000', '2', 'salary', 'teacher/salary/index', '', '', 1, 0, 'C', '0', '0', 'teacher:salary:list', 'money', 'admin', sysdate(), '', null, '薪资统计菜单');
insert into sys_menu values('2003', '薪资配置',     '2000', '3', 'salaryConfig', 'teacher/salary/config', '', '', 1, 1, 'C', '0', '0', 'teacher:salary:list', 'edit', 'admin', sysdate(), '', null, '薪资配置菜单');
insert into sys_menu values('2004', '薪资报表',     '2000', '4', 'salaryRecord', 'teacher/salary/record', '', '', 1, 1, 'C', '0', '0', 'teacher:salary:list', 'log', 'admin', sysdate(), '', null, '薪资报表菜单');

-- 个人信息管理按钮
insert into sys_menu values('2100', '教师查询', '2001', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:info:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2101', '教师新增', '2001', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:info:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2102', '教师修改', '2001', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:info:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2103', '教师删除', '2001', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:info:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2104', '教师导出', '2001', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:info:export', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2105', '教师导入', '2001', '6', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:info:import', '#', 'admin', sysdate(), '', null, '');

-- 薪资统计按钮
insert into sys_menu values('2110', '薪资查询', '2002', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:salary:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2111', '薪资新增', '2002', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:salary:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2112', '薪资修改', '2002', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:salary:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2113', '薪资导出', '2002', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:salary:export', '#', 'admin', sysdate(), '', null, '');

-- 给管理员角色赋予菜单权限
insert into sys_role_menu values ('2', '2000');
insert into sys_role_menu values ('2', '2001');
insert into sys_role_menu values ('2', '2002');
insert into sys_role_menu values ('2', '2003');
insert into sys_role_menu values ('2', '2004');
insert into sys_role_menu values ('2', '2100');
insert into sys_role_menu values ('2', '2101');
insert into sys_role_menu values ('2', '2102');
insert into sys_role_menu values ('2', '2103');
insert into sys_role_menu values ('2', '2104');
insert into sys_role_menu values ('2', '2105');
insert into sys_role_menu values ('2', '2110');
insert into sys_role_menu values ('2', '2111');
insert into sys_role_menu values ('2', '2112');
insert into sys_role_menu values ('2', '2113');

-- =============================================
-- 字典类型
-- =============================================
insert into sys_dict_type values(100, '教师状态',   'tea_teacher_status',  '0', 'admin', sysdate(), '', null, '教师在职状态');
insert into sys_dict_type values(101, '民族',       'tea_nation',          '0', 'admin', sysdate(), '', null, '民族列表');
insert into sys_dict_type values(102, '政治面貌',   'tea_political',       '0', 'admin', sysdate(), '', null, '政治面貌列表');
insert into sys_dict_type values(103, '学历',       'tea_education_level', '0', 'admin', sysdate(), '', null, '学历列表');
insert into sys_dict_type values(104, '学位类型',   'tea_degree_type',     '0', 'admin', sysdate(), '', null, '学位类型列表');
insert into sys_dict_type values(105, '职称系列',   'tea_title_series',    '0', 'admin', sysdate(), '', null, '职称系列列表');
insert into sys_dict_type values(106, '职称名称',   'tea_title_name',      '0', 'admin', sysdate(), '', null, '职称名称列表');
insert into sys_dict_type values(107, '薪资发放状态','tea_salary_status',  '0', 'admin', sysdate(), '', null, '薪资发放状态');
insert into sys_dict_type values(108, '一级学科',   'tea_first_discipline','0', 'admin', sysdate(), '', null, '一级学科列表');

-- =============================================
-- 字典数据
-- =============================================
-- 教师状态
insert into sys_dict_data values(1000, 1, '在职',  '0', 'tea_teacher_status', '', 'success', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1001, 2, '离职',  '1', 'tea_teacher_status', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1002, 3, '退休',  '2', 'tea_teacher_status', '', 'info',    'N', '0', 'admin', sysdate(), '', null, '');

-- 民族
insert into sys_dict_data values(1010, 1,  '汉族',     '汉族',     'tea_nation', '', '', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1011, 2,  '蒙古族',   '蒙古族',   'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1012, 3,  '回族',     '回族',     'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1013, 4,  '藏族',     '藏族',     'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1014, 5,  '维吾尔族', '维吾尔族', 'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1015, 6,  '苗族',     '苗族',     'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1016, 7,  '彝族',     '彝族',     'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1017, 8,  '壮族',     '壮族',     'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1018, 9,  '满族',     '满族',     'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1019, 10, '其他',     '其他',     'tea_nation', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 政治面貌
insert into sys_dict_data values(1020, 1, '中共党员',     '中共党员',     'tea_political', '', '', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1021, 2, '中共预备党员', '中共预备党员', 'tea_political', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1022, 3, '共青团员',     '共青团员',     'tea_political', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1023, 4, '民主党派',     '民主党派',     'tea_political', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1024, 5, '群众',         '群众',         'tea_political', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 学历
insert into sys_dict_data values(1030, 1, '博士研究生', '博士', 'tea_education_level', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1031, 2, '硕士研究生', '硕士', 'tea_education_level', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1032, 3, '本科',       '本科', 'tea_education_level', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1033, 4, '大专',       '大专', 'tea_education_level', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 学位类型
insert into sys_dict_data values(1040, 1, '博士', '博士', 'tea_degree_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1041, 2, '硕士', '硕士', 'tea_degree_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1042, 3, '学士', '学士', 'tea_degree_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 职称系列
insert into sys_dict_data values(1050, 1, '高校教师系列', '高校教师系列', 'tea_title_series', '', '', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1051, 2, '实验技术系列', '实验技术系列', 'tea_title_series', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1052, 3, '工程技术系列', '工程技术系列', 'tea_title_series', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1053, 4, '图书资料系列', '图书资料系列', 'tea_title_series', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 职称名称
insert into sys_dict_data values(1060, 1, '教授',     '教授',     'tea_title_name', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1061, 2, '副教授',   '副教授',   'tea_title_name', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1062, 3, '讲师',     '讲师',     'tea_title_name', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1063, 4, '助教',     '助教',     'tea_title_name', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1064, 5, '高级实验师','高级实验师','tea_title_name', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1065, 6, '实验师',   '实验师',   'tea_title_name', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 薪资发放状态
insert into sys_dict_data values(1070, 1, '未发放', '0', 'tea_salary_status', '', 'warning', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1071, 2, '已发放', '1', 'tea_salary_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');

-- 一级学科
insert into sys_dict_data values(1080, 1,  '哲学',       '哲学',       'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1081, 2,  '经济学',     '经济学',     'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1082, 3,  '法学',       '法学',       'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1083, 4,  '教育学',     '教育学',     'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1084, 5,  '文学',       '文学',       'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1085, 6,  '历史学',     '历史学',     'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1086, 7,  '理学',       '理学',       'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1087, 8,  '工学',       '工学',       'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1088, 9,  '农学',       '农学',       'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1089, 10, '医学',       '医学',       'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1090, 11, '管理学',     '管理学',     'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1091, 12, '艺术学',     '艺术学',     'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1092, 13, '交叉学科',   '交叉学科',   'tea_first_discipline', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- =============================================
-- 初始薪资配置数据
-- =============================================
insert into tea_salary_config values(1, '教授',     5000.00, 3000.00, 4000.00, 8000.00, 2000.00, '0', 'admin', sysdate(), '', null, '教授薪资标准');
insert into tea_salary_config values(2, '副教授',   4000.00, 2500.00, 3200.00, 6000.00, 1500.00, '0', 'admin', sysdate(), '', null, '副教授薪资标准');
insert into tea_salary_config values(3, '讲师',     3000.00, 2000.00, 2400.00, 3000.00, 1000.00, '0', 'admin', sysdate(), '', null, '讲师薪资标准');
insert into tea_salary_config values(4, '助教',     2500.00, 1500.00, 2000.00, 0.00,    800.00,  '0', 'admin', sysdate(), '', null, '助教薪资标准');
