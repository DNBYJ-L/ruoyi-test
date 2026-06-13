package com.ruoyi.system.domain.recruit;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaInterviewRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long interviewId;
    private Long applicantId;
    @Excel(name = "面试类型")
    private String interviewType;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "面试时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date interviewDate;
    @Excel(name = "教学试讲得分")
    private BigDecimal teachingScore;
    @Excel(name = "学术报告得分")
    private BigDecimal academicScore;
    @Excel(name = "答辩得分")
    private BigDecimal defenseScore;
    @Excel(name = "综合得分")
    private BigDecimal totalScore;
    private String opinion;
    @Excel(name = "结果", readConverterExp = "0=待定,1=通过,2=不通过")
    private String result;

    /** 关联查询 */
    private String applicantName;
    private String applicantTalentLevel;

    public Long getInterviewId() { return interviewId; }
    public void setInterviewId(Long interviewId) { this.interviewId = interviewId; }
    public Long getApplicantId() { return applicantId; }
    public void setApplicantId(Long applicantId) { this.applicantId = applicantId; }
    public String getInterviewType() { return interviewType; }
    public void setInterviewType(String interviewType) { this.interviewType = interviewType; }
    public Date getInterviewDate() { return interviewDate; }
    public void setInterviewDate(Date interviewDate) { this.interviewDate = interviewDate; }
    public BigDecimal getTeachingScore() { return teachingScore; }
    public void setTeachingScore(BigDecimal teachingScore) { this.teachingScore = teachingScore; }
    public BigDecimal getAcademicScore() { return academicScore; }
    public void setAcademicScore(BigDecimal academicScore) { this.academicScore = academicScore; }
    public BigDecimal getDefenseScore() { return defenseScore; }
    public void setDefenseScore(BigDecimal defenseScore) { this.defenseScore = defenseScore; }
    public BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(BigDecimal totalScore) { this.totalScore = totalScore; }
    public String getOpinion() { return opinion; }
    public void setOpinion(String opinion) { this.opinion = opinion; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getApplicantName() { return applicantName; }
    public void setApplicantName(String applicantName) { this.applicantName = applicantName; }
    public String getApplicantTalentLevel() { return applicantTalentLevel; }
    public void setApplicantTalentLevel(String applicantTalentLevel) { this.applicantTalentLevel = applicantTalentLevel; }
}
