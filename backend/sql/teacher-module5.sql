-- =============================================
-- 模块5：教师考核
-- =============================================

-- 1、考核方案表
drop table if exists tea_assessment_plan;
create table tea_assessment_plan (
  plan_id           bigint(20)      not null auto_increment    comment '方案ID',
  plan_name         varchar(100)    default ''                 comment '方案名称',
  assessment_type   char(1)         default '1'                comment '考核类型（1年度 2聘期 3职称 4专项）',
  assessment_year   int(4)          default null               comment '考核年度',
  start_date        date            default null               comment '开始日期',
  end_date          date            default null               comment '结束日期',
  status            char(1)         default '0'                comment '状态（0草稿 1进行中 2已结束）',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  remark            varchar(500)    default null               comment '备注',
  primary key (plan_id)
) engine=innodb auto_increment=100 comment = '考核方案表';

-- 2、考核记录主表
drop table if exists tea_assessment_record;
create table tea_assessment_record (
  record_id         bigint(20)      not null auto_increment    comment '记录ID',
  plan_id           bigint(20)      not null                   comment '方案ID',
  teacher_id        bigint(20)      not null                   comment '教师ID',
  status            char(1)         default '0'                comment '状态（0暂存 1已提交 2院系审核 3部门会审 4已通过 5退回）',
  teaching_score    decimal(5,2)    default 0                  comment '教学得分',
  research_score    decimal(5,2)    default 0                  comment '科研得分',
  service_score     decimal(5,2)    default 0                  comment '服务得分',
  total_score       decimal(5,2)    default 0                  comment '总分',
  grade             varchar(20)     default ''                 comment '考核等级（优秀/合格/基本合格/不合格）',
  self_evaluation   text                                       comment '自我评价',
  dept_evaluation   text                                       comment '院系评价',
  submit_time       datetime        default null               comment '提交时间',
  create_by         varchar(64)     default ''                 comment '创建者',
  create_time       datetime                                   comment '创建时间',
  update_by         varchar(64)     default ''                 comment '更新者',
  update_time       datetime                                   comment '更新时间',
  primary key (record_id),
  key idx_plan_id (plan_id),
  key idx_teacher_id (teacher_id)
) engine=innodb auto_increment=100 comment = '考核记录表';

-- 3、教学业绩子表
drop table if exists tea_assessment_teaching;
create table tea_assessment_teaching (
  id                bigint(20)      not null auto_increment    comment 'ID',
  record_id         bigint(20)      not null                   comment '考核记录ID',
  course_name       varchar(100)    default ''                 comment '课程名称',
  class_hours       int(4)          default 0                  comment '课时',
  student_count     int(4)          default 0                  comment '学生数',
  eval_score        decimal(5,2)    default 0                  comment '评教分',
  awards            varchar(200)    default ''                 comment '教学获奖',
  projects          varchar(200)    default ''                 comment '教改项目',
  guided_students   varchar(200)    default ''                 comment '指导学生',
  primary key (id),
  key idx_record_id (record_id)
) engine=innodb auto_increment=100 comment = '考核教学业绩表';

-- 4、科研业绩子表
drop table if exists tea_assessment_research;
create table tea_assessment_research (
  id                bigint(20)      not null auto_increment    comment 'ID',
  record_id         bigint(20)      not null                   comment '考核记录ID',
  research_type     varchar(20)     default ''                 comment '类型（论文/项目/获奖/专利）',
  title             varchar(200)    default ''                 comment '名称',
  journal_or_source varchar(200)    default ''                 comment '期刊/来源',
  level             varchar(50)     default ''                 comment '级别',
  amount            decimal(10,2)   default 0                  comment '经费',
  role              varchar(50)     default ''                 comment '角色',
  citations         int(4)          default 0                  comment '引用次数',
  primary key (id),
  key idx_record_id (record_id)
) engine=innodb auto_increment=100 comment = '考核科研业绩表';

-- 5、社会服务子表
drop table if exists tea_assessment_service;
create table tea_assessment_service (
  id                bigint(20)      not null auto_increment    comment 'ID',
  record_id         bigint(20)      not null                   comment '考核记录ID',
  service_type      varchar(20)     default ''                 comment '类型（学术兼职/成果转化/科普/其他）',
  content           varchar(500)    default ''                 comment '内容描述',
  impact            varchar(200)    default ''                 comment '影响/效果',
  primary key (id),
  key idx_record_id (record_id)
) engine=innodb auto_increment=100 comment = '考核社会服务表';

-- 6、审核记录表
drop table if exists tea_assessment_audit;
create table tea_assessment_audit (
  audit_id          bigint(20)      not null auto_increment    comment '审核ID',
  record_id         bigint(20)      not null                   comment '考核记录ID',
  audit_node        varchar(20)     default ''                 comment '审核节点（院系/教务/科研/审定）',
  auditor_id        bigint(20)      default null               comment '审核人ID',
  auditor_name      varchar(30)     default ''                 comment '审核人姓名',
  audit_time        datetime        default null               comment '审核时间',
  result            char(1)         default '0'                comment '结果（0待审 1通过 2退回）',
  opinion           varchar(500)    default ''                 comment '审核意见',
  primary key (audit_id),
  key idx_record_id (record_id)
) engine=innodb auto_increment=100 comment = '考核审核记录表';

-- =============================================
-- 菜单：教师考核（挂在教师管理2000下）
-- =============================================
-- 先清理已有菜单（支持重复执行）
delete from sys_role_menu where menu_id in (2007, 2022, 2023, 2024, 2025, 2230, 2231, 2232, 2233, 2234);
delete from sys_menu where menu_id in (2007, 2022, 2023, 2024, 2025, 2230, 2231, 2232, 2233, 2234);

insert into sys_menu values('2007', '教师考核', '2000', '7', 'assessment', null, '', '', 1, 0, 'M', '0', '0', '', 'form', 'admin', sysdate(), '', null, '教师考核目录');
insert into sys_menu values('2022', '考核方案', '2007', '1', 'assessPlan', 'teacher/assessment/plan', '', '', 1, 0, 'C', '0', '0', 'teacher:assess:list', 'date', 'admin', sysdate(), '', null, '考核方案菜单');
insert into sys_menu values('2023', '考核填报', '2007', '2', 'assessRecord', 'teacher/assessment/record', '', '', 1, 0, 'C', '0', '0', 'teacher:assess:list', 'edit', 'admin', sysdate(), '', null, '考核填报菜单');
insert into sys_menu values('2024', '审核管理', '2007', '3', 'assessAudit', 'teacher/assessment/audit', '', '', 1, 0, 'C', '0', '0', 'teacher:assess:list', 'guide', 'admin', sysdate(), '', null, '审核管理菜单');
insert into sys_menu values('2025', '结果统计', '2007', '4', 'assessResult', 'teacher/assessment/result', '', '', 1, 0, 'C', '0', '0', 'teacher:assess:list', 'chart', 'admin', sysdate(), '', null, '结果统计菜单');

-- 按钮权限
insert into sys_menu values('2230', '考核查询', '2022', '1', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:assess:query',  '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2231', '考核新增', '2022', '2', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:assess:add',    '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2232', '考核修改', '2022', '3', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:assess:edit',   '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2233', '考核删除', '2022', '4', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:assess:remove', '#', 'admin', sysdate(), '', null, '');
insert into sys_menu values('2234', '考核审核', '2022', '5', '', '', '', '', 1, 0, 'F', '0', '0', 'teacher:assess:audit',  '#', 'admin', sysdate(), '', null, '');

-- 角色菜单关联
insert into sys_role_menu values ('2', '2007');
insert into sys_role_menu values ('2', '2022');
insert into sys_role_menu values ('2', '2023');
insert into sys_role_menu values ('2', '2024');
insert into sys_role_menu values ('2', '2025');
insert into sys_role_menu values ('2', '2230');
insert into sys_role_menu values ('2', '2231');
insert into sys_role_menu values ('2', '2232');
insert into sys_role_menu values ('2', '2233');
insert into sys_role_menu values ('2', '2234');

-- =============================================
-- 字典类型
-- =============================================
-- 先清理已有字典（支持重复执行）
delete from sys_dict_data where dict_type in ('tea_assessment_type','tea_assessment_status','tea_assessment_grade','tea_audit_node','tea_audit_result') or dict_code between 1300 and 1342;
delete from sys_dict_type where dict_type in ('tea_assessment_type','tea_assessment_status','tea_assessment_grade','tea_audit_node','tea_audit_result') or dict_id in (130, 131, 132, 133, 134);

insert into sys_dict_type values(130, '考核类型',   'tea_assessment_type',   '0', 'admin', sysdate(), '', null, '考核类型');
insert into sys_dict_type values(131, '考核状态',   'tea_assessment_status', '0', 'admin', sysdate(), '', null, '考核记录状态');
insert into sys_dict_type values(132, '考核等级',   'tea_assessment_grade',  '0', 'admin', sysdate(), '', null, '考核等级');
insert into sys_dict_type values(133, '审核节点',   'tea_audit_node',        '0', 'admin', sysdate(), '', null, '审核节点');
insert into sys_dict_type values(134, '审核结果',   'tea_audit_result',      '0', 'admin', sysdate(), '', null, '审核结果');

-- 字典数据
-- 考核类型
insert into sys_dict_data values(1300, 1, '年度考核', '1', 'tea_assessment_type', '', '', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1301, 2, '聘期考核', '2', 'tea_assessment_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1302, 3, '职称考核', '3', 'tea_assessment_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1303, 4, '专项考核', '4', 'tea_assessment_type', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 考核状态
insert into sys_dict_data values(1310, 1, '暂存',     '0', 'tea_assessment_status', '', 'info',    'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1311, 2, '已提交',   '1', 'tea_assessment_status', '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1312, 3, '院系审核', '2', 'tea_assessment_status', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1313, 4, '部门会审', '3', 'tea_assessment_status', '', '',        'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1314, 5, '已通过',   '4', 'tea_assessment_status', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1315, 6, '已退回',   '5', 'tea_assessment_status', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '');

-- 考核等级
insert into sys_dict_data values(1320, 1, '优秀',     '优秀',     'tea_assessment_grade', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1321, 2, '合格',     '合格',     'tea_assessment_grade', '', 'primary', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1322, 3, '基本合格', '基本合格', 'tea_assessment_grade', '', 'warning', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1323, 4, '不合格',   '不合格',   'tea_assessment_grade', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '');

-- 审核节点
insert into sys_dict_data values(1330, 1, '院系审核', '院系', 'tea_audit_node', '', '', 'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1331, 2, '教务审核', '教务', 'tea_audit_node', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1332, 3, '科研审核', '科研', 'tea_audit_node', '', '', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1333, 4, '最终审定', '审定', 'tea_audit_node', '', '', 'N', '0', 'admin', sysdate(), '', null, '');

-- 审核结果
insert into sys_dict_data values(1340, 1, '待审', '0', 'tea_audit_result', '', 'info',    'Y', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1341, 2, '通过', '1', 'tea_audit_result', '', 'success', 'N', '0', 'admin', sysdate(), '', null, '');
insert into sys_dict_data values(1342, 3, '退回', '2', 'tea_audit_result', '', 'danger',  'N', '0', 'admin', sysdate(), '', null, '');
