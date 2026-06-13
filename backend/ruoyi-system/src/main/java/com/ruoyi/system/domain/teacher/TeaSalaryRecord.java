package com.ruoyi.system.domain.teacher;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 薪资发放记录表 tea_salary_record
 * 
 * @author ruoyi
 */
public class TeaSalaryRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 记录ID */
    private Long recordId;

    /** 教师ID */
    private Long teacherId;

    /** 年份 */
    @Excel(name = "年份")
    private Integer salaryYear;

    /** 月份 */
    @Excel(name = "月份")
    private Integer salaryMonth;

    /** 岗位工资 */
    @Excel(name = "岗位工资")
    private BigDecimal postSalary;

    /** 薪级工资 */
    @Excel(name = "薪级工资")
    private BigDecimal scaleSalary;

    /** 基础性绩效 */
    @Excel(name = "基础性绩效")
    private BigDecimal basePerformance;

    /** 奖励性绩效 */
    @Excel(name = "奖励性绩效")
    private BigDecimal rewardPerformance;

    /** 人才津贴 */
    @Excel(name = "人才津贴")
    private BigDecimal talentAllowance;

    /** 住房补贴 */
    @Excel(name = "住房补贴")
    private BigDecimal housingAllowance;

    /** 其他津贴 */
    @Excel(name = "其他津贴")
    private BigDecimal otherAllowance;

    /** 代扣社保 */
    @Excel(name = "代扣社保")
    private BigDecimal socialInsurance;

    /** 代扣公积金 */
    @Excel(name = "代扣公积金")
    private BigDecimal housingFund;

    /** 代扣个税 */
    @Excel(name = "代扣个税")
    private BigDecimal incomeTax;

    /** 实发金额 */
    @Excel(name = "实发金额")
    private BigDecimal actualSalary;

    /** 税前应发 */
    @Excel(name = "税前应发")
    private BigDecimal grossSalary;

    /** 发放状态 */
    @Excel(name = "发放状态", readConverterExp = "0=未发放,1=已发放")
    private String issueStatus;

    /** 教师工号（关联查询） */
    @Excel(name = "教师工号")
    private String teacherNo;

    /** 教师姓名（关联查询） */
    @Excel(name = "教师姓名")
    private String teacherName;

    /** 所属院系（关联查询） */
    @Excel(name = "所属院系")
    private String deptName;

    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public Integer getSalaryYear() { return salaryYear; }
    public void setSalaryYear(Integer salaryYear) { this.salaryYear = salaryYear; }
    public Integer getSalaryMonth() { return salaryMonth; }
    public void setSalaryMonth(Integer salaryMonth) { this.salaryMonth = salaryMonth; }
    public BigDecimal getPostSalary() { return postSalary; }
    public void setPostSalary(BigDecimal postSalary) { this.postSalary = postSalary; }
    public BigDecimal getScaleSalary() { return scaleSalary; }
    public void setScaleSalary(BigDecimal scaleSalary) { this.scaleSalary = scaleSalary; }
    public BigDecimal getBasePerformance() { return basePerformance; }
    public void setBasePerformance(BigDecimal basePerformance) { this.basePerformance = basePerformance; }
    public BigDecimal getRewardPerformance() { return rewardPerformance; }
    public void setRewardPerformance(BigDecimal rewardPerformance) { this.rewardPerformance = rewardPerformance; }
    public BigDecimal getTalentAllowance() { return talentAllowance; }
    public void setTalentAllowance(BigDecimal talentAllowance) { this.talentAllowance = talentAllowance; }
    public BigDecimal getHousingAllowance() { return housingAllowance; }
    public void setHousingAllowance(BigDecimal housingAllowance) { this.housingAllowance = housingAllowance; }
    public BigDecimal getOtherAllowance() { return otherAllowance; }
    public void setOtherAllowance(BigDecimal otherAllowance) { this.otherAllowance = otherAllowance; }
    public BigDecimal getSocialInsurance() { return socialInsurance; }
    public void setSocialInsurance(BigDecimal socialInsurance) { this.socialInsurance = socialInsurance; }
    public BigDecimal getHousingFund() { return housingFund; }
    public void setHousingFund(BigDecimal housingFund) { this.housingFund = housingFund; }
    public BigDecimal getIncomeTax() { return incomeTax; }
    public void setIncomeTax(BigDecimal incomeTax) { this.incomeTax = incomeTax; }
    public BigDecimal getActualSalary() { return actualSalary; }
    public void setActualSalary(BigDecimal actualSalary) { this.actualSalary = actualSalary; }
    public BigDecimal getGrossSalary() { return grossSalary; }
    public void setGrossSalary(BigDecimal grossSalary) { this.grossSalary = grossSalary; }
    public String getIssueStatus() { return issueStatus; }
    public void setIssueStatus(String issueStatus) { this.issueStatus = issueStatus; }
    public String getTeacherNo() { return teacherNo; }
    public void setTeacherNo(String teacherNo) { this.teacherNo = teacherNo; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("recordId", getRecordId())
            .append("teacherId", getTeacherId())
            .append("salaryYear", getSalaryYear())
            .append("salaryMonth", getSalaryMonth())
            .append("grossSalary", getGrossSalary())
            .append("actualSalary", getActualSalary())
            .append("issueStatus", getIssueStatus())
            .toString();
    }
}
