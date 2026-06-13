package com.ruoyi.system.service.recruit;

import java.util.List;
import com.ruoyi.system.domain.recruit.TeaApplicant;

public interface ITeaApplicantService
{
    public List<TeaApplicant> selectApplicantList(TeaApplicant applicant);
    public TeaApplicant selectApplicantById(Long applicantId);
    public int insertApplicant(TeaApplicant applicant);
    public int updateApplicant(TeaApplicant applicant);
    public int deleteApplicantByIds(Long[] applicantIds);
    /** 审批状态流转 */
    public int approveApplicant(Long applicantId, String targetStatus, String updateBy);
}
