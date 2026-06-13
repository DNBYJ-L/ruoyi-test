package com.ruoyi.system.domain.assessment;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaAssessmentTeaching extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long recordId;
    private String courseName;
    private Integer classHours;
    private Integer studentCount;
    private BigDecimal evalScore;
    private String awards;
    private String projects;
    private String guidedStudents;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public String getCourseName() { return courseName; }
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public Integer getClassHours() { return classHours; }
    public void setClassHours(Integer classHours) { this.classHours = classHours; }
    public Integer getStudentCount() { return studentCount; }
    public void setStudentCount(Integer studentCount) { this.studentCount = studentCount; }
    public BigDecimal getEvalScore() { return evalScore; }
    public void setEvalScore(BigDecimal evalScore) { this.evalScore = evalScore; }
    public String getAwards() { return awards; }
    public void setAwards(String awards) { this.awards = awards; }
    public String getProjects() { return projects; }
    public void setProjects(String projects) { this.projects = projects; }
    public String getGuidedStudents() { return guidedStudents; }
    public void setGuidedStudents(String guidedStudents) { this.guidedStudents = guidedStudents; }
}
