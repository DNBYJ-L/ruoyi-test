-- =============================================
-- 模块2：人才引进
-- =============================================

-- 1、引进计划表
drop table if exists tea_recruit_plan;
create table tea_recruit_plan (
  plan_id           bigint(20)      not null auto_increment    comment '计划ID',
  dept_id           bigint(20)      default null               comment '院系的ID',
  discipline        varchar(100)    default ''                 comment '学科方向',
  plan_count        int(4)          default 0                  comment '计划人数',
  actual_count      int(4)          default 0                  comment '已录用人数',
  talent_level      varchar(20)     default ''                 comment '人才层次要求',
  plan_year         int(4)          default null               comment '年度',
  status            char(1)         default '0'                comment '状态（0草稿 1已申报 2已审批 3已发布）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (plan_id)
) engine=innodb auto_increment=100 comment = '人才引进计划表';

-- 2、应聘者主表
drop table if exists tea_applicant;
create table tea_applicant (
  applicant_id      bigint(20)      not null auto_increment    comment '应聘者ID',
  plan_id           bigint(20)      default null               comment '关联计划ID',
  name              varchar(30)     default ''                 comment '姓名',
  gender            char(1)         default '0'                comment '性别（0男 1女）',
  birth_date        date            default null               comment '出生日期',
  nationality       varchar(30)     default ''                 comment '国籍',
  current_unit      varchar(100)    default ''                 comment '现工作单位',
  current_position  varchar(50)     default ''                 comment '现任职务',
  education_level   varchar(20)     default ''                 comment '最高学历',
  degree_type       varchar(20)     default ''                 comment '最高学位',
  phone             varchar(20)     default ''                 comment '手机号',
  email             varchar(50)     default ''                 comment '邮箱',
  apply_date        date            default null               comment '申请日期',
  talent_level      varchar(20)     default ''                 comment '人才层次',
  status            char(1)         default '0'                comment '状态（0待审 1初审通过 2面试中 3已录用 4已拒绝）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (applicant_id),
  key idx_plan_id (plan_id)
) engine=innodb auto_increment=100 comment = '应聘者信息表';

-- 3、教育背景子表
drop table if exists tea_applicant_education;
create table tea_applicant_education (
  education_id      bigint(20)      not null auto_increment    comment '教育ID',
  applicant_id      bigint(20)      not null                   comment '应聘者ID',
  school            varchar(100)    default ''                 comment '学校',
  major             varchar(100)    default ''                 comment '专业',
  degree            varchar(20)     default ''                 comment '学历/学位',
  start_date        date            default null               comment '开始时间',
  end_date          date            default null               comment '结束时间',
  advisor           varchar(30)     default ''                 comment '导师姓名',
  thesis            varchar(200)    default ''                 comment '论文题目',
  primary key (education_id),
  key idx_applicant_id (applicant_id)
) engine=innodb auto_increment=100 comment = '应聘者教育背景表';

-- 4、工作经历子表
drop table if exists tea_applicant_work;
create table tea_applicant_work (
  work_id           bigint(20)      not null auto_increment    comment '经历ID',
  applicant_id      bigint(20)      not null                   comment '应聘者ID',
  start_date        date            default null               comment '开始时间',
  end_date          date            default null               comment '结束时间',
  unit              varchar(100)    default ''                 comment '单位',
  position          varchar(50)     default ''                 comment '职务',
  description       varchar(500)    default ''                 comment '工作内容',
  primary key (work_id),
  key idx_applicant_id (applicant_id)
) engine=innodb auto_increment=100 comment = '应聘者工作经历表';

-- 5、科研成果子表
drop table if exists tea_applicant_achievement;
create table tea_applicant_achievement (
  achievement_id    bigint(20)      not null auto_increment    comment '成果ID',
  applicant_id      bigint(20)      not null                   comment '应聘者ID',
  achievement_type  varchar(20)     default ''                 comment '成果类型（论文/项目/获奖/专利）',
  title             varchar(200)    default ''                 comment '成果名称',
  detail            varchar(500)    default ''                 comment '详细描述',
  level             varchar(50)     default ''                 comment '级别/分区',
  amount            decimal(10,2)   default 0                  comment '经费/影响因子',
  primary key (achievement_id),
  key idx_applicant_id (applicant_id)
) engine=innodb auto_increment=100 comment = '应聘者科研成果表';

-- 6、面试记录表
drop table if exists tea_interview_record;
create table tea_interview_record (
  interview_id      bigint(20)      not null auto_increment    comment '面试ID',
  applicant_id      bigint(20)      not null                   comment '应聘者ID',
  interview_type    varchar(50)     default ''                 comment '面试类型',
  interview_date    datetime        default null               comment '面试时间',
  teaching_score    decimal(5,2)    default 0                  comment '教学试讲得分',
  academic_score    decimal(5,2)    default 0                  comment '学术报告得分',
  defense_score     decimal(5,2)    default 0                  comment '答辩得分',
  total_score       decimal(5,2)    default 0                  comment '综合得分',
  opinion           varchar(500)    default ''                 comment '面试意见',
  result            char(1)         default '0'                comment '结果（0待定 1通过 2不通过）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (interview_id),
  key idx_applicant_id (applicant_id)
) engine=innodb auto_increment=100 comment = '面试记录表';

-- =============================================
-- 菜单：人才引进（挂在教师管理2000下）
-- =============================================
-- 先清理已有菜单（支持重复执行）
delete from sys_role_menu where menu_id in (2005, 2015, 2016, 2017, 2200, 2201, 2202, 2203, 2204, 2205, 2206, 2207, 2208, 2209, 2210, 2211, 2212);
delete from sys_menu where menu_id in (2005, 2015, 2016, 2017, 2200, 2201, 2202, 2203, 2204, 2205, 2206, 2207, 2208, 2209, 2210, 2211, 2212);

insert into sys_menu values('2005', '人才引进', '2000', '5', 'recruit', null, '', '', 1, 0, 'M', '0', '0', '', 'tree', 'admin', sysdate(), '', null, '人才引进目录');
insert into sys_menu values('2015', '引进计划', '2005', '1', 'recruitPlan', 'teacher/recruit/plan', '', '', 1, 0, 'C', '0', '0', 'teacher:recruit:list', 'date', 'admin', sysdate(), '', null, '引进计划菜单');
insert into sys_menu values('2016', '应聘管理', '2005', '2', 'applicant', 'teacher/recruit/applicant', '', '', 1, 0, 'C', '0', '0', 'teacher:applicant:list', 'peoples', 'admin', sysdate(), '', null, '应聘管理菜单');
insert into sys_menu values('2017', '面试管理', '2005', '3', 'interview', 'teacher/recruit/interview', '', '', 1, 0, 'C', '0', '0', 'teacher:interview:list', 'skill', 'admin', sysdate(), '', null, '面试管理菜单');

-- 引进计划按钮
insert into sys_menu values('2200', '计划查询', '2015', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:recruit:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2201', '计划新增', '2015', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:recruit:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2202', '计划修改', '2015', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:recruit:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2203', '计划删除', '2015', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:recruit:remove', '#', 'admin', sysdate(), '', null, '');
-- 应聘管理按钮
insert into sys_menu values('2204', '应聘查询', '2016', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:applicant:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2205', '应聘新增', '2016', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:applicant:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2206', '应聘修改', '2016', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:applicant:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2207', '应聘删除', '2016', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:applicant:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2208', '应聘审批', '2016', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:applicant:approve','#', 'admin', sysdate(), '', null, '');
-- 面试管理按钮
insert into sys_menu values('2209', '面试查询', '2017', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:interview:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2210', '面试新增', '2017', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:interview:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2211', '面试修改', '2017', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:interview:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2212', '面试删除', '2017', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:interview:remove', '#', 'admin', sysdate(), '', null, '');

-- 角色菜单关联（角色2）
insert into sys_role_menu values ('2', '2005');
insert into sys_role_menu values ('2', '2015');
insert into sys_role_menu values ('2', '2016');
insert into sys_role_menu values ('2', '2017');
insert into sys_role_menu values ('2', '2200');
insert into sys_role_menu values ('2', '2201');
insert into sys_role_menu values ('2', '2202');
insert into sys_role_menu values ('2', '2203');
insert into sys_role_menu values ('2', '2204');
insert into sys_role_menu values ('2', '2205');
insert into sys_role_menu values ('2', '2206');
insert into sys_role_menu values ('2', '2207');
insert into sys_role_menu values ('2', '2208');
insert into sys_role_menu values ('2', '2209');
insert into sys_role_menu values ('2', '2210');
insert into sys_role_menu values ('2', '2211');
insert into sys_role_menu values ('2', '2212');

-- =============================================
-- 字典类型
-- =============================================
-- 先清理已有字典（支持重复执行）
delete from sys_dict_data where dict_type in ('tea_talent_level','tea_recruit_status','tea_applicant_status','tea_interview_result','tea_achievement_type') or dict_code between 1100 and 1143;
delete from sys_dict_type where dict_type in ('tea_talent_level','tea_recruit_status','tea_applicant_status','tea_interview_result','tea_achievement_type') or dict_id in (110, 111, 112, 113, 114);

insert into sys_dict_type values(110, '人才层次',   'tea_talent_level',    '0', 'admin', sysdate(), '', null, '人才层次分类');
insert into sys_dict_type values(111, '招聘状态',   'tea_recruit_status',  '0', 'admin', sysdate(), '', null, '招聘计划状态');
insert into sys_dict_type values(112, '应聘状态',   'tea_applicant_status','0', 'admin', sysdate(), '', null, '应聘者状态');
insert into sys_dict_type values(113, '面试结果',   'tea_interview_result','0', 'admin', sysdate(), '', null, '面试结果');
insert into sys_dict_type values(114, '成果类型',   'tea_achievement_type','0', 'admin', sysdate(), '', null, '科研成果类型');

-- =============================================
-- 字典数据
-- =============================================
-- 人才层次
insert into sys_dict_data values(1100, 1, '顶尖人才', '顶尖人才', 'tea_talent_level', '', 'danger', 'N', '0', 'admin', sysdate(), '', null, '院士、发达国家院士');
insert into sys_dict_data values(1101, 2, '领军人才', '领军人才', 'tea_talent_level', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '长江学者、杰青');
insert into sys_dict_data values(1102, 3, '拔尖人才', '拔尖人才', 'tea_talent_level', '', '', 'N', '0', 'admin', sysdate(), '', null, '优青、青年长江');
insert into sys_dict_data values(1103, 4, '骨干人才', '骨干人才', 'tea_talent_level', '', '', 'N', '0', 'admin', sysdate(), '', null, '教授45岁以下');
insert into sys_dict_data values(1104, 5, '优秀青年', '优秀青年', 'tea_talent_level', '', '', 'N', '0', 'admin', sysdate(), '', null, '副教授/讲师35岁以下');

-- 招聘计划状态
insert into sys_dict_data values(1110, 1, '草稿',   '0', 'tea_recruit_status', '', 'info',    'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1111, 2, '已申报', '1', 'tea_recruit_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1112, 3, '已审批', '2', 'tea_recruit_status', '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1113, 4, '已发布', '3', 'tea_recruit_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');

-- 应聘状态
insert into sys_dict_data values(1120, 1, '待审',     '0', 'tea_applicant_status', '', 'info',    'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1121, 2, '初审通过', '1', 'tea_applicant_status', '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1122, 3, '面试中',   '2', 'tea_applicant_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1123, 4, '已录用',   '3', 'tea_applicant_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1124, 5, '已拒绝',   '4', 'tea_applicant_status', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '');

-- 面试结果
insert into sys_dict_data values(1130, 1, '待定',   '0', 'tea_interview_result', '', 'info',    'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1131, 2, '通过',   '1', 'tea_interview_result', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1132, 3, '不通过', '2', 'tea_interview_result', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '');

-- 成果类型
insert into sys_dict_data values(1140, 1, '论文', '论文', 'tea_achievement_type', '', '', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1141, 2, '项目', '项目', 'tea_achievement_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1142, 3, '获奖', '获奖', 'tea_achievement_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1143, 4, '专利', '专利', 'tea_achievement_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
