package com.ruoyi.system.domain.recruit;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 人才引进计划表 tea_recruit_plan
 */
public class TeaRecruitPlan extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long planId;

    private Long deptId;

    @Excel(name = "学科方向")
    private String discipline;

    @Excel(name = "计划人数")
    private Integer planCount;

    @Excel(name = "已录用人数")
    private Integer actualCount;

    @Excel(name = "人才层次")
    private String talentLevel;

    @Excel(name = "年度")
    private Integer planYear;

    @Excel(name = "状态", readConverterExp = "0=草稿,1=已申报,2=已审批,3=已发布")
    private String status;

    /** 院系名称（关联查询） */
    @Excel(name = "院系")
    private String deptName;

    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public Long getDeptId() { return deptId; }
    public void setDeptId(Long deptId) { this.deptId = deptId; }
    public String getDiscipline() { return discipline; }
    public void setDiscipline(String discipline) { this.discipline = discipline; }
    public Integer getPlanCount() { return planCount; }
    public void setPlanCount(Integer planCount) { this.planCount = planCount; }
    public Integer getActualCount() { return actualCount; }
    public void setActualCount(Integer actualCount) { this.actualCount = actualCount; }
    public String getTalentLevel() { return talentLevel; }
    public void setTalentLevel(String talentLevel) { this.talentLevel = talentLevel; }
    public Integer getPlanYear() { return planYear; }
    public void setPlanYear(Integer planYear) { this.planYear = planYear; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
            .append("planId", getPlanId())
            .append("discipline", getDiscipline())
            .append("planCount", getPlanCount())
            .append("talentLevel", getTalentLevel())
            .append("planYear", getPlanYear())
            .append("status", getStatus())
            .toString();
    }
}
