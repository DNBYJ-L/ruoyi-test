package com.ruoyi.system.domain.assessment;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaAssessmentRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long recordId;
    private Long planId;
    private Long teacherId;
    @Excel(name = "状态", readConverterExp = "0=暂存,1=已提交,2=院系审核,3=部门会审,4=已通过,5=退回")
    private String status;
    @Excel(name = "教学得分")
    private BigDecimal teachingScore;
    @Excel(name = "科研得分")
    private BigDecimal researchScore;
    @Excel(name = "服务得分")
    private BigDecimal serviceScore;
    @Excel(name = "总分")
    private BigDecimal totalScore;
    @Excel(name = "考核等级")
    private String grade;
    private String selfEvaluation;
    private String deptEvaluation;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date submitTime;
    /** 关联查询 */
    @Excel(name = "教师姓名")
    private String teacherName;
    @Excel(name = "工号")
    private String teacherNo;
    @Excel(name = "院系")
    private String deptName;
    @Excel(name = "方案名称")
    private String planName;
    /** 子表 */
    private List<TeaAssessmentTeaching> teachings;
    private List<TeaAssessmentResearch> researches;
    private List<TeaAssessmentService> services;

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public BigDecimal getTeachingScore() { return teachingScore; }
    public void setTeachingScore(BigDecimal teachingScore) { this.teachingScore = teachingScore; }
    public BigDecimal getResearchScore() { return researchScore; }
    public void setResearchScore(BigDecimal researchScore) { this.researchScore = researchScore; }
    public BigDecimal getServiceScore() { return serviceScore; }
    public void setServiceScore(BigDecimal serviceScore) { this.serviceScore = serviceScore; }
    public BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(BigDecimal totalScore) { this.totalScore = totalScore; }
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    public String getSelfEvaluation() { return selfEvaluation; }
    public void setSelfEvaluation(String selfEvaluation) { this.selfEvaluation = selfEvaluation; }
    public String getDeptEvaluation() { return deptEvaluation; }
    public void setDeptEvaluation(String deptEvaluation) { this.deptEvaluation = deptEvaluation; }
    public Date getSubmitTime() { return submitTime; }
    public void setSubmitTime(Date submitTime) { this.submitTime = submitTime; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getTeacherNo() { return teacherNo; }
    public void setTeacherNo(String teacherNo) { this.teacherNo = teacherNo; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public List<TeaAssessmentTeaching> getTeachings() { return teachings; }
    public void setTeachings(List<TeaAssessmentTeaching> teachings) { this.teachings = teachings; }
    public List<TeaAssessmentResearch> getResearches() { return researches; }
    public void setResearches(List<TeaAssessmentResearch> researches) { this.researches = researches; }
    public List<TeaAssessmentService> getServices() { return services; }
    public void setServices(List<TeaAssessmentService> services) { this.services = services; }
}
