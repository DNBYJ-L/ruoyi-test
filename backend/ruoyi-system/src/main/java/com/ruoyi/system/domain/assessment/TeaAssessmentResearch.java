package com.ruoyi.system.domain.assessment;

import java.math.BigDecimal;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaAssessmentResearch extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long recordId;
    private String researchType;
    private String title;
    private String journalOrSource;
    private String level;
    private BigDecimal amount;
    private String role;
    private Integer citations;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public String getResearchType() { return researchType; }
    public void setResearchType(String researchType) { this.researchType = researchType; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getJournalOrSource() { return journalOrSource; }
    public void setJournalOrSource(String journalOrSource) { this.journalOrSource = journalOrSource; }
    public String getLevel() { return level; }
    public void setLevel(String level) { this.level = level; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal amount) { this.amount = amount; }
    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
    public Integer getCitations() { return citations; }
    public void setCitations(Integer citations) { this.citations = citations; }
}
