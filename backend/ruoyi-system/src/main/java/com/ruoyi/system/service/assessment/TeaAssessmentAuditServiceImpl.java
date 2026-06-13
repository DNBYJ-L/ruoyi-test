package com.ruoyi.system.service.assessment;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.assessment.TeaAssessmentAudit;
import com.ruoyi.system.domain.assessment.TeaAssessmentRecord;
import com.ruoyi.system.mapper.assessment.TeaAssessmentAuditMapper;
import com.ruoyi.system.mapper.assessment.TeaAssessmentRecordMapper;

@Service
public class TeaAssessmentAuditServiceImpl implements ITeaAssessmentAuditService
{
    @Autowired
    private TeaAssessmentAuditMapper auditMapper;
    @Autowired
    private TeaAssessmentRecordMapper recordMapper;

    @Override
    public List<TeaAssessmentAudit> selectAuditTrail(Long recordId) { return auditMapper.selectByRecordId(recordId); }

    @Override
    public int doAudit(Long recordId, String auditNode, Long auditorId, String auditorName, String result, String opinion)
    {
        TeaAssessmentAudit audit = new TeaAssessmentAudit();
        audit.setRecordId(recordId);
        audit.setAuditNode(auditNode);
        audit.setAuditorId(auditorId);
        audit.setAuditorName(auditorName);
        audit.setAuditTime(new Date());
        audit.setResult(result);
        audit.setOpinion(opinion);
        int rows = auditMapper.insert(audit);
        // 更新考核记录状态
        TeaAssessmentRecord record = new TeaAssessmentRecord();
        record.setRecordId(recordId);
        if ("1".equals(result))
        {
            // 通过：根据当前节点决定下一状态
            if ("院系".equals(auditNode)) record.setStatus("3"); // 院系通过->部门会审
            else if ("审定".equals(auditNode)) record.setStatus("4"); // 审定通过->已通过
            else record.setStatus("3"); // 默认部门会审
        }
        else if ("2".equals(result))
        {
            record.setStatus("5"); // 退回
        }
        recordMapper.updateAssessmentRecord(record);
        return rows;
    }
}
