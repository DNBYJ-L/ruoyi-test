package com.ruoyi.system.mapper.assessment;
import java.util.List;
import com.ruoyi.system.domain.assessment.TeaAssessmentAudit;
public interface TeaAssessmentAuditMapper {
    public List<TeaAssessmentAudit> selectByRecordId(Long recordId);
    public int insert(TeaAssessmentAudit audit);
}
