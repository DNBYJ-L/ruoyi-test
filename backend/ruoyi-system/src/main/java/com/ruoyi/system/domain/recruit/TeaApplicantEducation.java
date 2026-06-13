package com.ruoyi.system.domain.recruit;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaApplicantEducation extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long educationId;
    private Long applicantId;
    private String school;
    private String major;
    private String degree;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date endDate;
    private String advisor;
    private String thesis;

    public Long getEducationId() { return educationId; }
    public void setEducationId(Long educationId) { this.educationId = educationId; }
    public Long getApplicantId() { return applicantId; }
    public void setApplicantId(Long applicantId) { this.applicantId = applicantId; }
    public String getSchool() { return school; }
    public void setSchool(String school) { this.school = school; }
    public String getMajor() { return major; }
    public void setMajor(String major) { this.major = major; }
    public String getDegree() { return degree; }
    public void setDegree(String degree) { this.degree = degree; }
    public Date getStartDate() { return startDate; }
    public void setStartDate(Date startDate) { this.startDate = startDate; }
    public Date getEndDate() { return endDate; }
    public void setEndDate(Date endDate) { this.endDate = endDate; }
    public String getAdvisor() { return advisor; }
    public void setAdvisor(String advisor) { this.advisor = advisor; }
    public String getThesis() { return thesis; }
    public void setThesis(String thesis) { this.thesis = thesis; }
}
