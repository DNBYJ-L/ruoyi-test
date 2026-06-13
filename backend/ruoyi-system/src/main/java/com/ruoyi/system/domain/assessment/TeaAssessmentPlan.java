package com.ruoyi.system.domain.assessment;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaAssessmentPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long planId;
    @Excel(name = "方案名称")
    private String planName;
    @Excel(name = "考核类型", readConverterExp = "1=年度,2=聘期,3=职称,4=专项")
    private String assessmentType;
    @Excel(name = "考核年度")
    private Integer assessmentYear;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    @Excel(name = "状态", readConverterExp = "0=草稿,1=进行中,2=已结束")
    private String status;

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String assessmentType) { this.assessmentType = assessmentType; }
    public Integer getAssessmentYear() { return assessmentYear; }
    public void setAssessmentYear(Integer assessmentYear) { this.assessmentYear = assessmentYear; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
