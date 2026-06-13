package com.ruoyi.system.service.assessment;
import java.util.List;
import com.ruoyi.system.domain.assessment.TeaAssessmentAudit;
public interface ITeaAssessmentAuditService {
    public List<TeaAssessmentAudit> selectAuditTrail(Long recordId);
    public int doAudit(Long recordId, String auditNode, Long auditorId, String auditorName, String result, String opinion);
}
