package com.ruoyi.system.service.teacher;

import java.math.BigDecimal;
import com.ruoyi.system.domain.teacher.TeaSalaryRecord;
import com.ruoyi.system.domain.teacher.TeaSalaryConfig;

/**
 * 薪资计算工具类
 * 
 * @author ruoyi
 */
public class SalaryCalculator
{
    /** 社保扣除比例（个人部分） */
    private static final BigDecimal SOCIAL_INSURANCE_RATE = new BigDecimal("0.105");
    /** 公积金扣除比例（个人部分） */
    private static final BigDecimal HOUSING_FUND_RATE = new BigDecimal("0.12");

    /**
     * 计算薪资
     * 
     * @param record 薪资记录（需要已设置各项收入）
     * @param config 薪资配置
     */
    public static void calculate(TeaSalaryRecord record, TeaSalaryConfig config)
    {
        if (config != null)
        {
            if (record.getPostSalary() == null || record.getPostSalary().compareTo(BigDecimal.ZERO) == 0)
            {
                record.setPostSalary(config.getPostSalary());
            }
            if (record.getScaleSalary() == null || record.getScaleSalary().compareTo(BigDecimal.ZERO) == 0)
            {
                record.setScaleSalary(config.getScaleSalary());
            }
            if (record.getBasePerformance() == null || record.getBasePerformance().compareTo(BigDecimal.ZERO) == 0)
            {
                record.setBasePerformance(config.getBasePerformance());
            }
            if (record.getTalentAllowance() == null || record.getTalentAllowance().compareTo(BigDecimal.ZERO) == 0)
            {
                record.setTalentAllowance(config.getTalentAllowance());
            }
            if (record.getHousingAllowance() == null || record.getHousingAllowance().compareTo(BigDecimal.ZERO) == 0)
            {
                record.setHousingAllowance(config.getHousingAllowance());
            }
        }
        // 确保各项不为null
        BigDecimal postSalary = nvl(record.getPostSalary());
        BigDecimal scaleSalary = nvl(record.getScaleSalary());
        BigDecimal basePerformance = nvl(record.getBasePerformance());
        BigDecimal rewardPerformance = nvl(record.getRewardPerformance());
        BigDecimal talentAllowance = nvl(record.getTalentAllowance());
        BigDecimal housingAllowance = nvl(record.getHousingAllowance());
        BigDecimal otherAllowance = nvl(record.getOtherAllowance());

        // 税前应发 = 基本工资 + 绩效工资 + 津补贴
        BigDecimal grossSalary = postSalary.add(scaleSalary)
            .add(basePerformance).add(rewardPerformance)
            .add(talentAllowance).add(housingAllowance)
            .add(otherAllowance);
        record.setGrossSalary(grossSalary);

        // 代扣项
        BigDecimal socialInsurance = nvl(record.getSocialInsurance());
        BigDecimal housingFund = nvl(record.getHousingFund());
        BigDecimal incomeTax = nvl(record.getIncomeTax());

        // 如果社保和公积金未设置，按比例自动计算
        if (socialInsurance.compareTo(BigDecimal.ZERO) == 0)
        {
            BigDecimal base = postSalary.add(scaleSalary);
            socialInsurance = base.multiply(SOCIAL_INSURANCE_RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
            record.setSocialInsurance(socialInsurance);
        }
        if (housingFund.compareTo(BigDecimal.ZERO) == 0)
        {
            BigDecimal base = postSalary.add(scaleSalary);
            housingFund = base.multiply(HOUSING_FUND_RATE).setScale(2, BigDecimal.ROUND_HALF_UP);
            record.setHousingFund(housingFund);
        }

        // 实发 = 税前 - 代扣
        BigDecimal actualSalary = grossSalary.subtract(socialInsurance).subtract(housingFund).subtract(incomeTax);
        record.setActualSalary(actualSalary);
    }

    private static BigDecimal nvl(BigDecimal value)
    {
        return value == null ? BigDecimal.ZERO : value;
    }
}
