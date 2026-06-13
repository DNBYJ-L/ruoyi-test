package com.ruoyi.system.domain.recruit;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaApplicantAchievement extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long achievementId;
    private Long applicantId;
    private String achievementType;
    private String title;
    private String detail;
    private String level;
    private BigDecimal amount;

    public Long getAchievementId() { return achievementId; }
    public void setAchievementId(Long achievementId) { this.achievementId = achievementId; }
    public Long getApplicantId() { return applicantId; }
    public void setApplicantId(Long applicantId) { this.applicantId = applicantId; }
    public String getAchievementType() { return achievementType; }
    public void setAchievementType(String achievementType) { this.achievementType = achievementType; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDetail() { return detail; }
    public void setDetail(String detail) { this.detail = detail; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
}
