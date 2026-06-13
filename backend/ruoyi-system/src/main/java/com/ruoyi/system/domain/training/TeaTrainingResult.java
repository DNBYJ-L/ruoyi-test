package com.ruoyi.system.domain.training;

import java.math.BigDecimal;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaTrainingResult extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long resultId;
    private Long planId;
    private Long teacherId;
    @Excel(name = "考核成绩")
    private BigDecimal examScore;
    @Excel(name = "考核方式", readConverterExp = "1=考试,2=作业,3=实践,4=综合")
    private String assessmentType;
    @Excel(name = "合格状态", readConverterExp = "0=不合格,1=合格")
    private String passStatus;
    @Excel(name = "证书编号")
    private String certificateNo;
    @Excel(name = "认定学时")
    private Integer creditHours;
    private BigDecimal satisfactionScore;
    private String remark;
    /** 关联查询 */
    @Excel(name = "培训名称")
    private String planName;
    @Excel(name = "教师姓名")
    private String teacherName;

    public Long getResultId() { return resultId; }
    public void setResultId(Long resultId) { this.resultId = resultId; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public BigDecimal getExamScore() { return examScore; }
    public void setExamScore(BigDecimal examScore) { this.examScore = examScore; }
    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }
    public String getPassStatus() { return passStatus; }
    public void setPassStatus(String passStatus) { this.passStatus = passStatus; }
    public String getCertificateNo() { return certificateNo; }
    public void setCertificateNo(String certificateNo) { this.certificateNo = certificateNo; }
    public Integer getCreditHours() { return creditHours; }
    public void setCreditHours(Integer creditHours) { this.creditHours = creditHours; }
    public BigDecimal getSatisfactionScore() { return satisfactionScore; }
    public void setSatisfactionScore(BigDecimal satisfactionScore) { this.satisfactionScore = satisfactionScore; }
    public String getRemark() { return remark; }
    public void setRemark(String remark) { this.remark = remark; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
}
