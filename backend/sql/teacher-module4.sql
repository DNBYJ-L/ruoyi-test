-- =============================================
-- 模块4：教师培训
-- =============================================

-- 1、培训计划表
drop table if exists tea_training_plan;
create table tea_training_plan (
  plan_id           bigint(20)      not null auto_increment    comment '计划ID',
  plan_name         varchar(100)    default ''                 comment '培训名称',
  training_type     char(1)         default '1'                comment '培训类型（1岗前 2教学 3科研 4管理）',
  target_audience   varchar(100)    default ''                 comment '培训对象',
  max_count         int(4)          default 0                  comment '人数限制',
  start_date        date            default null               comment '开始时间',
  end_date          date            default null               comment '结束时间',
  total_hours       int(4)          default 0                  comment '总课时',
  location          varchar(100)    default ''                 comment '地点/平台',
  instructor_name   varchar(30)     default ''                 comment '主讲人',
  instructor_title  varchar(50)     default ''                 comment '主讲人职称',
  budget            decimal(10,2)   default 0                  comment '预算',
  status            char(1)         default '0'                comment '状态（0草稿 1已发布 2进行中 3已结束）',
  syllabus          text                                       comment '课程大纲',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (plan_id)
) engine=innodb auto_increment=100 comment = '教师培训计划表';

-- 2、培训课程子表
drop table if exists tea_training_course;
create table tea_training_course (
  course_id         bigint(20)      not null auto_increment    comment '课程ID',
  plan_id           bigint(20)      not null                   comment '计划ID',
  course_name       varchar(100)    default ''                 comment '课程名称',
  instructor        varchar(30)     default ''                 comment '授课教师',
  hours             int(4)          default 0                  comment '课时',
  sort_order        int(4)          default 0                  comment '排序',
  description       varchar(500)    default ''                 comment '描述',
  primary key (course_id),
  key idx_plan_id (plan_id)
) engine=innodb auto_increment=100 comment = '培训课程表';

-- 3、报名记录表
drop table if exists tea_training_enrollment;
create table tea_training_enrollment (
  enrollment_id     bigint(20)      not null auto_increment    comment '报名ID',
  plan_id           bigint(20)      not null                   comment '计划ID',
  teacher_id        bigint(20)      not null                   comment '教师ID',
  apply_date        date            default null               comment '报名日期',
  approve_status    char(1)         default '0'                comment '审批状态（0待审 1通过 2拒绝）',
  approve_by        varchar(64)     default ''                 comment '审批人',
  approve_time      datetime        default null               comment '审批时间',
  approve_remark    varchar(500)    default ''                 comment '审批意见',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  primary key (enrollment_id),
  key idx_plan_id (plan_id),
  key idx_teacher_id (teacher_id)
) engine=innodb auto_increment=100 comment = '培训报名记录表';

-- 4、结业记录表
drop table if exists tea_training_result;
create table tea_training_result (
  result_id         bigint(20)      not null auto_increment    comment '结业ID',
  plan_id           bigint(20)      not null                   comment '计划ID',
  teacher_id        bigint(20)      not null                   comment '教师ID',
  exam_score        decimal(5,2)    default 0                  comment '考核成绩',
  assessment_type   char(1)         default '1'                comment '考核方式（1考试 2作业 3实践 4综合）',
  pass_status       char(1)         default '0'                comment '合格状态（0不合格 1合格）',
  certificate_no    varchar(30)     default ''                 comment '证书编号',
  credit_hours      int(4)          default 0                  comment '认定学时',
  satisfaction_score decimal(3,2)   default 0                  comment '满意度评分',
  remark            varchar(500)    default ''                 comment '备注',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (result_id),
  key idx_plan_id (plan_id),
  key idx_teacher_id (teacher_id)
) engine=innodb auto_increment=100 comment = '培训结业记录表';

-- =============================================
-- 菜单：教师培训（挂在教师管理2000下）
-- =============================================
-- 先清理已有菜单（支持重复执行）
delete from sys_role_menu where menu_id in (2006, 2018, 2019, 2020, 2021, 2220, 2221, 2222, 2223, 2224, 2225, 2226, 2227, 2228);
delete from sys_menu where menu_id in (2006, 2018, 2019, 2020, 2021, 2220, 2221, 2222, 2223, 2224, 2225, 2226, 2227, 2228);

insert into sys_menu values('2006', '教师培训', '2000', '6', 'training', null, '', '', 1, 0, 'M', '0', '0', '', 'education', 'admin', sysdate(), '', null, '教师培训目录');
insert into sys_menu values('2018', '培训计划', '2006', '1', 'trainingPlan', 'teacher/training/plan', '', '', 1, 0, 'C', '0', '0', 'teacher:training:list', 'date', 'admin', sysdate(), '', null, '培训计划菜单');
insert into sys_menu values('2019', '报名管理', '2006', '2', 'enrollment', 'teacher/training/enrollment', '', '', 1, 0, 'C', '0', '0', 'teacher:enrollment:list', 'peoples', 'admin', sysdate(), '', null, '报名管理菜单');
insert into sys_menu values('2020', '结业管理', '2006', '3', 'trainingResult', 'teacher/training/result', '', '', 1, 0, 'C', '0', '0', 'teacher:result:list', 'validCode', 'admin', sysdate(), '', null, '结业管理菜单');
insert into sys_menu values('2021', '学时统计', '2006', '4', 'trainingHours', 'teacher/training/hours', '', '', 1, 0, 'C', '0', '0', 'teacher:training:list', 'chart', 'admin', sysdate(), '', null, '学时统计菜单');

-- 按钮权限
insert into sys_menu values('2220', '培训查询', '2018', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:training:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2221', '培训新增', '2018', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:training:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2222', '培训修改', '2018', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:training:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2223', '培训删除', '2018', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:training:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2224', '报名查询', '2019', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:enrollment:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2225', '报名审批', '2019', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:enrollment:approve','#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2226', '结业查询', '2020', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:result:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2227', '结业新增', '2020', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:result:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2228', '结业修改', '2020', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:result:edit',   '#', 'admin', sysdate(), '', null, '');

-- 角色菜单关联
insert into sys_role_menu values ('2', '2006');
insert into sys_role_menu values ('2', '2018');
insert into sys_role_menu values ('2', '2019');
insert into sys_role_menu values ('2', '2020');
insert into sys_role_menu values ('2', '2021');
insert into sys_role_menu values ('2', '2220');
insert into sys_role_menu values ('2', '2221');
insert into sys_role_menu values ('2', '2222');
insert into sys_role_menu values ('2', '2223');
insert into sys_role_menu values ('2', '2224');
insert into sys_role_menu values ('2', '2225');
insert into sys_role_menu values ('2', '2226');
insert into sys_role_menu values ('2', '2227');
insert into sys_role_menu values ('2', '2228');

-- =============================================
-- 字典类型
-- =============================================
-- 先清理已有字典（支持重复执行）
delete from sys_dict_data where dict_type in ('tea_training_type','tea_training_status','tea_approve_status','tea_training_assess_type') or dict_code between 1200 and 1233;
delete from sys_dict_type where dict_type in ('tea_training_type','tea_training_status','tea_approve_status','tea_training_assess_type') or dict_id in (120, 121, 122, 123);

insert into sys_dict_type values(120, '培训类型',   'tea_training_type',   '0', 'admin', sysdate(), '', null, '培训类型');
insert into sys_dict_type values(121, '培训状态',   'tea_training_status', '0', 'admin', sysdate(), '', null, '培训计划状态');
insert into sys_dict_type values(122, '审批状态',   'tea_approve_status',  '0', 'admin', sysdate(), '', null, '审批状态');
insert into sys_dict_type values(123, '培训考核方式', 'tea_training_assess_type', '0', 'admin', sysdate(), '', null, '培训考核方式');

-- 字典数据
-- 培训类型
insert into sys_dict_data values(1200, 1, '岗前培训', '1', 'tea_training_type', '', '', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1201, 2, '教学能力', '2', 'tea_training_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1202, 3, '科研能力', '3', 'tea_training_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1203, 4, '管理能力', '4', 'tea_training_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 培训状态
insert into sys_dict_data values(1210, 1, '草稿',   '0', 'tea_training_status', '', 'info',    'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1211, 2, '已发布', '1', 'tea_training_status', '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1212, 3, '进行中', '2', 'tea_training_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1213, 4, '已结束', '3', 'tea_training_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');

-- 审批状态
insert into sys_dict_data values(1220, 1, '待审', '0', 'tea_approve_status', '', 'info',    'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1221, 2, '通过', '1', 'tea_approve_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1222, 3, '拒绝', '2', 'tea_approve_status', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '');

-- 考核方式
insert into sys_dict_data values(1230, 1, '考试', '1', 'tea_training_assess_type', '', '', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1231, 2, '作业', '2', 'tea_training_assess_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1232, 3, '实践', '3', 'tea_training_assess_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1233, 4, '综合', '4', 'tea_training_assess_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
