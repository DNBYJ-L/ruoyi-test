package com.ruoyi.system.domain.assessment;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaAssessmentAudit extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long auditId;
    private Long recordId;
    private String auditNode;
    private Long auditorId;
    private String auditorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date auditTime;
    private String result;
    private String opinion;

    public Long getAuditId() { return auditId; }
    public void setAuditId(Long auditId) { this.auditId = auditId; }
    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public String getAuditNode() { return auditNode; }
    public void setAuditNode(String auditNode) { this.auditNode = auditNode; }
    public Long getAuditorId() { return auditorId; }
    public void setAuditorId(Long auditorId) { this.auditorId = auditorId; }
    public String getAuditorName() { return auditorName; }
    public void setAuditorName(String auditorName) { this.auditorName = auditorName; }
    public Date getAuditTime() { return auditTime; }
    public void setAuditTime(Date auditTime) { this.auditTime = auditTime; }
    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }
    public String getOpinion() { return opinion; }
    public void setOpinion(String opinion) { this.opinion = opinion; }
}
