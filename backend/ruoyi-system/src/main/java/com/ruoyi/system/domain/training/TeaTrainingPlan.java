package com.ruoyi.system.domain.training;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaTrainingPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long planId;
    @Excel(name = "培训名称")
    private String planName;
    @Excel(name = "培训类型", readConverterExp = "1=岗前,2=教学,3=科研,4=管理")
    private String trainingType;
    private String targetAudience;
    private Integer maxCount;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date endDate;
    @Excel(name = "总课时")
    private Integer totalHours;
    private String location;
    private String instructorName;
    private String instructorTitle;
    private java.math.BigDecimal budget;
    @Excel(name = "状态", readConverterExp = "0=草稿,1=已发布,2=进行中,3=已结束")
    private String status;
    private String syllabus;
    private List<TeaTrainingCourse> courses;

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getTrainingType() { return trainingType; }
    public void setTrainingType(String trainingType) { this.trainingType = trainingType; }
    public String getTargetAudience() { return targetAudience; }
    public void setTargetAudience(String targetAudience) { this.targetAudience = targetAudience; }
    public Integer getMaxCount() { return maxCount; }
    public void setMaxCount(Integer maxCount) { this.maxCount = maxCount; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public Integer getTotalHours() { return totalHours; }
    public void setTotalHours(Integer totalHours) { this.totalHours = totalHours; }
    public String getLocation() { return location; }
    public void setLocation(String location) { this.location = location; }
    public String getInstructorName() { return instructorName; }
    public void setInstructorName(String instructorName) { this.instructorName = instructorName; }
    public String getInstructorTitle() { return instructorTitle; }
    public void setInstructorTitle(String instructorTitle) { this.instructorTitle = instructorTitle; }
    public java.math.BigDecimal getBudget() { return budget; }
    public void setBudget(java.math.BigDecimal budget) { this.budget = budget; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getSyllabus() { return syllabus; }
    public void setSyllabus(String syllabus) { this.syllabus = syllabus; }
    public List<TeaTrainingCourse> getCourses() { return courses; }
    public void setCourses(List<TeaTrainingCourse> courses) { this.courses = courses; }
}
