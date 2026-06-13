package com.ruoyi.system.domain.assessment;

import com.ruoyi.common.core.domain.BaseEntity;

public class TeaAssessmentService extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long id;
    private Long recordId;
    private String serviceType;
    private String content;
    private String impact;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getRecordId() { return recordId; }
    public void setRecordId(Long recordId) { this.recordId = recordId; }
    public String getServiceType() { return serviceType; }
    public void setServiceType(String serviceType) { this.serviceType = serviceType; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
    public String getImpact() { return impact; }
    public void setImpact(String impact) { this.impact = impact; }
}
