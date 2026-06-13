package com.ruoyi.system.domain.teacher;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 薪资配置表 tea_salary_config
 * 
 * @author ruoyi
 */
public class TeaSalaryConfig extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 配置ID */
    private Long configId;

    /** 职称等级 */
    @Excel(name = "职称等级")
    private String titleLevel;

    /** 岗位工资标准 */
    @Excel(name = "岗位工资标准")
    private BigDecimal postSalary;

    /** 薪级工资标准 */
    @Excel(name = "薪级工资标准")
    private BigDecimal scaleSalary;

    /** 基础性绩效标准 */
    @Excel(name = "基础性绩效标准")
    private BigDecimal basePerformance;

    /** 人才津贴标准 */
    @Excel(name = "人才津贴标准")
    private BigDecimal talentAllowance;

    /** 住房补贴标准 */
    @Excel(name = "住房补贴标准")
    private BigDecimal housingAllowance;

    /** 状态 */
    private String status;

    public Long getConfigId()
    {
        return configId;
    }

    public void setConfigId(Long configId)
    {
        this.configId = configId;
    }

    public String getTitleLevel()
    {
        return titleLevel;
    }

    public void setTitleLevel(String titleLevel)
    {
        this.titleLevel = titleLevel;
    }

    public BigDecimal getPostSalary()
    {
        return postSalary;
    }

    public void setPostSalary(BigDecimal postSalary)
    {
        this.postSalary = postSalary;
    }

    public BigDecimal getScaleSalary()
    {
        return scaleSalary;
    }

    public void setScaleSalary(BigDecimal scaleSalary)
    {
        this.scaleSalary = scaleSalary;
    }

    public BigDecimal getBasePerformance()
    {
        return basePerformance;
    }

    public void setBasePerformance(BigDecimal basePerformance)
    {
        this.basePerformance = basePerformance;
    }

    public BigDecimal getTalentAllowance()
    {
        return talentAllowance;
    }

    public void setTalentAllowance(BigDecimal talentAllowance)
    {
        this.talentAllowance = talentAllowance;
    }

    public BigDecimal getHousingAllowance()
    {
        return housingAllowance;
    }

    public void setHousingAllowance(BigDecimal housingAllowance)
    {
        this.housingAllowance = housingAllowance;
    }

    public String getStatus()
    {
        return status;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("configId", getConfigId())
            .append("titleLevel", getTitleLevel())
            .append("postSalary", getPostSalary())
            .append("scaleSalary", getScaleSalary())
            .append("basePerformance", getBasePerformance())
            .append("talentAllowance", getTalentAllowance())
            .append("housingAllowance", getHousingAllowance())
            .append("status", getStatus())
            .toString();
    }
}
