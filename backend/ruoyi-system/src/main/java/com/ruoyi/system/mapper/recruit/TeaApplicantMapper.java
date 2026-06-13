package com.ruoyi.system.mapper.recruit;

import java.util.List;
import com.ruoyi.system.domain.recruit.TeaApplicant;

public interface TeaApplicantMapper
{
    public List<TeaApplicant> selectApplicantList(TeaApplicant applicant);
    public TeaApplicant selectApplicantById(Long applicantId);
    public int insertApplicant(TeaApplicant applicant);
    public int updateApplicant(TeaApplicant applicant);
    public int deleteApplicantByIds(Long[] applicantIds);
}
