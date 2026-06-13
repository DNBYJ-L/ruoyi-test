package com.ruoyi.system.domain.teacher;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师学历学位表 tea_education
 * 
 * @author ruoyi
 */
public class TeaEducation extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学历ID */
    private Long educationId;

    /** 教师ID */
    private Long teacherId;

    /** 学历 */
    @Excel(name = "学历")
    private String educationLevel;

    /** 毕业院校 */
    @Excel(name = "毕业院校")
    private String graduateSchool;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 学位类型 */
    @Excel(name = "学位类型")
    private String degreeType;

    /** 获得时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "获得时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date obtainDate;

    /** 是否最高学历（0否 1是） */
    @Excel(name = "是否最高学历", readConverterExp = "0=否,1=是")
    private String isHighest;

    public Long getEducationId()
    {
        return educationId;
    }

    public void setEducationId(Long educationId)
    {
        this.educationId = educationId;
    }

    public Long getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(Long teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getEducationLevel()
    {
        return educationLevel;
    }

    public void setEducationLevel(String educationLevel)
    {
        this.educationLevel = educationLevel;
    }

    public String getGraduateSchool()
    {
        return graduateSchool;
    }

    public void setGraduateSchool(String graduateSchool)
    {
        this.graduateSchool = graduateSchool;
    }

    public String getMajor()
    {
        return major;
    }

    public void setMajor(String major)
    {
        this.major = major;
    }

    public String getDegreeType()
    {
        return degreeType;
    }

    public void setDegreeType(String degreeType)
    {
        this.degreeType = degreeType;
    }

    public Date getObtainDate()
    {
        return obtainDate;
    }

    public void setObtainDate(Date obtainDate)
    {
        this.obtainDate = obtainDate;
    }

    public String getIsHighest()
    {
        return isHighest;
    }

    public void setIsHighest(String isHighest)
    {
        this.isHighest = isHighest;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("educationId", getEducationId())
            .append("teacherId", getTeacherId())
            .append("educationLevel", getEducationLevel())
            .append("graduateSchool", getGraduateSchool())
            .append("major", getMajor())
            .append("degreeType", getDegreeType())
            .append("obtainDate", getObtainDate())
            .append("isHighest", getIsHighest())
            .toString();
    }
}
