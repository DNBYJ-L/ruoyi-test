package com.ruoyi.system.domain.teacher;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师职称记录表 tea_title_record
 * 
 * @author ruoyi
 */
public class TeaTitleRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 教师ID */
    private Long teacherId;

    /** 职称名称 */
    @Excel(name = "职称名称")
    private String titleName;

    /** 职称系列 */
    @Excel(name = "职称系列")
    private String titleSeries;

    /** 取得时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "取得时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date obtainDate;

    /** 聘任岗位 */
    @Excel(name = "聘任岗位")
    private String hirePosition;

    /** 是否当前职称（0否 1是） */
    @Excel(name = "是否当前职称", readConverterExp = "0=否,1=是")
    private String isCurrent;

    public Long getRecordId()
    {
        return recordId;
    }

    public void setRecordId(Long recordId)
    {
        this.recordId = recordId;
    }

    public Long getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(Long teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getTitleName()
    {
        return titleName;
    }

    public void setTitleName(String titleName)
    {
        this.titleName = titleName;
    }

    public String getTitleSeries()
    {
        return titleSeries;
    }

    public void setTitleSeries(String titleSeries)
    {
        this.titleSeries = titleSeries;
    }

    public Date getObtainDate()
    {
        return obtainDate;
    }

    public void setObtainDate(Date obtainDate)
    {
        this.obtainDate = obtainDate;
    }

    public String getHirePosition()
    {
        return hirePosition;
    }

    public void setHirePosition(String hirePosition)
    {
        this.hirePosition = hirePosition;
    }

    public String getIsCurrent()
    {
        return isCurrent;
    }

    public void setIsCurrent(String isCurrent)
    {
        this.isCurrent = isCurrent;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("teacherId", getTeacherId())
            .append("titleName", getTitleName())
            .append("titleSeries", getTitleSeries())
            .append("obtainDate", getObtainDate())
            .append("hirePosition", getHirePosition())
            .append("isCurrent", getIsCurrent())
            .toString();
    }
}
