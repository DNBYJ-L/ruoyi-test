package com.ruoyi.system.domain.teacher;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 薪资调整记录表 tea_salary_adjust
 * 
 * @author ruoyi
 */
public class TeaSalaryAdjust extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private Long adjustId;
    private Long teacherId;
    private String adjustReason;
    private BigDecimal beforeAmount;
    private BigDecimal afterAmount;
    private String effectiveMonth;

    public Long getAdjustId() { return adjustId; }
    public void setAdjustId(Long adjustId) { this.adjustId = adjustId; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public String getAdjustReason() { return adjustReason; }
    public void setAdjustReason(String adjustReason) { this.adjustReason = adjustReason; }
    public BigDecimal getBeforeAmount() { return beforeAmount; }
    public void setBeforeAmount(BigDecimal beforeAmount) { this.beforeAmount = beforeAmount; }
    public BigDecimal getAfterAmount() { return afterAmount; }
    public void setAfterAmount(BigDecimal afterAmount) { this.afterAmount = afterAmount; }
    public String getEffectiveMonth() { return effectiveMonth; }
    public void setEffectiveMonth(String effectiveMonth) { this.effectiveMonth = effectiveMonth; }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("adjustId", getAdjustId())
            .append("teacherId", getTeacherId())
            .append("adjustReason", getAdjustReason())
            .append("beforeAmount", getBeforeAmount())
            .append("afterAmount", getAfterAmount())
            .append("effectiveMonth", getEffectiveMonth())
            .toString();
    }
}
