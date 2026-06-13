package com.ruoyi.system.domain.teacher;

import java.util.Date;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师信息表 tea_teacher
 * 
 * @author ruoyi
 */
public class TeaTeacher extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 教师ID */
    private Long teacherId;

    /** 工号 */
    @Excel(name = "工号")
    @NotBlank(message = "工号不能为空")
    @Size(min = 0, max = 20, message = "工号长度不能超过20个字符")
    private String teacherNo;

    /** 姓名 */
    @Excel(name = "姓名")
    @NotBlank(message = "姓名不能为空")
    @Size(min = 0, max = 30, message = "姓名长度不能超过30个字符")
    private String teacherName;

    /** 性别（0男 1女 2未知） */
    @Excel(name = "性别", readConverterExp = "0=男,1=女,2=未知")
    private String gender;

    /** 出生日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "出生日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date birthDate;

    /** 身份证号 */
    @Excel(name = "身份证号")
    private String idCard;

    /** 民族 */
    @Excel(name = "民族")
    private String nation;

    /** 政治面貌 */
    @Excel(name = "政治面貌")
    private String politicalStatus;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 办公地址 */
    @Excel(name = "办公地址")
    private String officeAddress;

    /** 紧急联系人 */
    private String emergencyContact;

    /** 紧急联系人电话 */
    private String emergencyPhone;

    /** 照片地址 */
    private String avatar;

    /** 所属院系ID */
    @Excel(name = "所属院系ID")
    private Long deptId;

    /** 状态（0在职 1离职 2退休） */
    @Excel(name = "状态", readConverterExp = "0=在职,1=离职,2=退休")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    /** 所属院系名称 */
    @Excel(name = "所属院系")
    private String deptName;

    /** 当前职称 */
    @Excel(name = "当前职称")
    private String currentTitle;

    /** 学历列表 */
    private List<TeaEducation> educations;

    /** 职称记录列表 */
    private List<TeaTitleRecord> titleRecords;

    /** 学科信息列表 */
    private List<TeaDiscipline> disciplines;

    public Long getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(Long teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getTeacherNo()
    {
        return teacherNo;
    }

    public void setTeacherNo(String teacherNo)
    {
        this.teacherNo = teacherNo;
    }

    public String getTeacherName()
    {
        return teacherName;
    }

    public void setTeacherName(String teacherName)
    {
        this.teacherName = teacherName;
    }

    public String getGender()
    {
        return gender;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }

    public Date getBirthDate()
    {
        return birthDate;
    }

    public void setBirthDate(Date birthDate)
    {
        this.birthDate = birthDate;
    }

    public String getIdCard()
    {
        return idCard;
    }

    public void setIdCard(String idCard)
    {
        this.idCard = idCard;
    }

    public String getNation()
    {
        return nation;
    }

    public void setNation(String nation)
    {
        this.nation = nation;
    }

    public String getPoliticalStatus()
    {
        return politicalStatus;
    }

    public void setPoliticalStatus(String politicalStatus)
    {
        this.politicalStatus = politicalStatus;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getOfficeAddress()
    {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress)
    {
        this.officeAddress = officeAddress;
    }

    public String getEmergencyContact()
    {
        return emergencyContact;
    }

    public void setEmergencyContact(String emergencyContact)
    {
        this.emergencyContact = emergencyContact;
    }

    public String getEmergencyPhone()
    {
        return emergencyPhone;
    }

    public void setEmergencyPhone(String emergencyPhone)
    {
        this.emergencyPhone = emergencyPhone;
    }

    public String getAvatar()
    {
        return avatar;
    }

    public void setAvatar(String avatar)
    {
        this.avatar = avatar;
    }

    public Long getDeptId()
    {
        return deptId;
    }

    public void setDeptId(Long deptId)
    {
        this.deptId = deptId;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDeptName()
    {
        return deptName;
    }

    public void setDeptName(String deptName)
    {
        this.deptName = deptName;
    }

    public String getCurrentTitle()
    {
        return currentTitle;
    }

    public void setCurrentTitle(String currentTitle)
    {
        this.currentTitle = currentTitle;
    }

    public List<TeaEducation> getEducations()
    {
        return educations;
    }

    public void setEducations(List<TeaEducation> educations)
    {
        this.educations = educations;
    }

    public List<TeaTitleRecord> getTitleRecords()
    {
        return titleRecords;
    }

    public void setTitleRecords(List<TeaTitleRecord> titleRecords)
    {
        this.titleRecords = titleRecords;
    }

    public List<TeaDiscipline> getDisciplines()
    {
        return disciplines;
    }

    public void setDisciplines(List<TeaDiscipline> disciplines)
    {
        this.disciplines = disciplines;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("teacherId", getTeacherId())
            .append("teacherNo", getTeacherNo())
            .append("teacherName", getTeacherName())
            .append("gender", getGender())
            .append("birthDate", getBirthDate())
            .append("idCard", getIdCard())
            .append("nation", getNation())
            .append("politicalStatus", getPoliticalStatus())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("deptId", getDeptId())
            .append("status", getStatus())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("remark", getRemark())
            .toString();
    }
}
