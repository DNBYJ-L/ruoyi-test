package com.ruoyi.system.domain.teacher;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师学科专业表 tea_discipline
 * 
 * @author ruoyi
 */
public class TeaDiscipline extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 学科ID */
    private Long disciplineId;

    /** 教师ID */
    private Long teacherId;

    /** 一级学科 */
    @Excel(name = "一级学科")
    private String firstDiscipline;

    /** 二级学科 */
    @Excel(name = "二级学科")
    private String secondDiscipline;

    /** 研究方向 */
    @Excel(name = "研究方向")
    private String researchDirection;

    public Long getDisciplineId()
    {
        return disciplineId;
    }

    public void setDisciplineId(Long disciplineId)
    {
        this.disciplineId = disciplineId;
    }

    public Long getTeacherId()
    {
        return teacherId;
    }

    public void setTeacherId(Long teacherId)
    {
        this.teacherId = teacherId;
    }

    public String getFirstDiscipline()
    {
        return firstDiscipline;
    }

    public void setFirstDiscipline(String firstDiscipline)
    {
        this.firstDiscipline = firstDiscipline;
    }

    public String getSecondDiscipline()
    {
        return secondDiscipline;
    }

    public void setSecondDiscipline(String secondDiscipline)
    {
        this.secondDiscipline = secondDiscipline;
    }

    public String getResearchDirection()
    {
        return researchDirection;
    }

    public void setResearchDirection(String researchDirection)
    {
        this.researchDirection = researchDirection;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("disciplineId", getDisciplineId())
            .append("teacherId", getTeacherId())
            .append("firstDiscipline", getFirstDiscipline())
            .append("secondDiscipline", getSecondDiscipline())
            .append("researchDirection", getResearchDirection())
            .toString();
    }
}
