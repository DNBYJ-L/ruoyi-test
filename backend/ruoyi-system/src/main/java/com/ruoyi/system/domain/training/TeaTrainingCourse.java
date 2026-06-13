package com.ruoyi.system.domain.training;

import com.ruoyi.common.core.domain.BaseEntity;

public class TeaTrainingCourse extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long courseId;
    private Long planId;
    private String courseName;
    private String instructor;
    private Integer hours;
    private Integer sortOrder;
    private String description;

    public Long getCourseId() { return courseId; }
    public void setCourseId(Long courseId) { this.courseId = courseId; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public String getInstructor() { return instructor; }
    public void setInstructor(String instructor) { this.instructor = instructor; }
    public Integer getHours() { return hours; }
    public void setHours(Integer hours) { this.hours = hours; }
    public Integer getSortOrder() { return sortOrder; }
    public void setSortOrder(Integer sortOrder) { this.sortOrder = sortOrder; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
