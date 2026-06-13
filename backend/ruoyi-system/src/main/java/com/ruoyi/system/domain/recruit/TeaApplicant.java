package com.ruoyi.system.domain.recruit;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 应聘者信息表 tea_applicant
 */
public class TeaApplicant extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long applicantId;
    private Long planId;
    @Excel(name = "姓名")
    private String name;
    @Excel(name = "性别", readConverterExp = "0=男,1=女")
    private String gender;
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;
    private String nationality;
    @Excel(name = "现工作单位")
    private String currentUnit;
    private String currentPosition;
    @Excel(name = "最高学历")
    private String educationLevel;
    private String degreeType;
    private String phone;
    private String email;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;
    @Excel(name = "人才层次")
    private String talentLevel;
    @Excel(name = "状态", readConverterExp = "0=待审,1=初审通过,2=面试中,3=已录用,4=已拒绝")
    private String status;

    /** 关联查询字段 */
    private String planDiscipline;
    private String planTalentLevel;

    /** 子表 */
    private List<TeaApplicantEducation> educations;
    private List<TeaApplicantWork> works;
    private List<TeaApplicantAchievement> achievements;

    public Long getApplicantId() { return applicantId; }
    public void setApplicantId(Long applicantId) { this.applicantId = applicantId; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }
    public Date getBirthDate() { return birthDate; }
    public void setBirthDate(Date birthDate) { this.birthDate = birthDate; }
    public String getNationality() { return nationality; }
    public void setNationality(String nationality) { this.nationality = nationality; }
    public String getCurrentUnit() { return currentUnit; }
    public void setCurrentUnit(String currentUnit) { this.currentUnit = currentUnit; }
    public String getCurrentPosition() { return currentPosition; }
    public void setCurrentPosition(String currentPosition) { this.currentPosition = currentPosition; }
    public String getEducationLevel() { return educationLevel; }
    public void setEducationLevel(String educationLevel) { this.educationLevel = educationLevel; }
    public String getDegreeType() { return degreeType; }
    public void setDegreeType(String degreeType) { this.degreeType = degreeType; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public Date getApplyDate() { return applyDate; }
    public void setApplyDate(Date applyDate) { this.applyDate = applyDate; }
    public String getTalentLevel() { return talentLevel; }
    public void setTalentLevel(String talentLevel) { this.talentLevel = talentLevel; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getPlanDiscipline() { return planDiscipline; }
    public void setPlanDiscipline(String planDiscipline) { this.planDiscipline = planDiscipline; }
    public String getPlanTalentLevel() { return planTalentLevel; }
    public void setPlanTalentLevel(String planTalentLevel) { this.planTalentLevel = planTalentLevel; }
    public List<TeaApplicantEducation> getEducations() { return educations; }
    public void setEducations(List<TeaApplicantEducation> educations) { this.educations = educations; }
    public List<TeaApplicantWork> getWorks() { return works; }
    public void setWorks(List<TeaApplicantWork> works) { this.works = works; }
    public List<TeaApplicantAchievement> getAchievements() { return achievements; }
    public void setAchievements(List<TeaApplicantAchievement> achievements) { this.achievements = achievements; }
}
