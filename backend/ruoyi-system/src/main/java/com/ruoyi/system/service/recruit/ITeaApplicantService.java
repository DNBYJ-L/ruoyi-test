package com.ruoyi.system.service.recruit;

import java.util.List;
import java.util.Map;
import com.ruoyi.system.domain.recruit.TeaApplicant;

public interface ITeaApplicantService
{
    public List<TeaApplicant> selectApplicantList(TeaApplicant applicant);
    public TeaApplicant selectApplicantById(Long applicantId);
    public int insertApplicant(TeaApplicant applicant);
    public int updateApplicant(TeaApplicant applicant);
    public int deleteApplicantByIds(Long[] applicantIds);
    /** 审批状态流转，录用时返回生成的账号信息 */
    public Map<String, Object> approveApplicant(Long applicantId, String targetStatus, String updateBy);
}
