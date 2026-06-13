package com.ruoyi.system.mapper.recruit;

import java.util.List;
import com.ruoyi.system.domain.recruit.TeaApplicantWork;

public interface TeaApplicantWorkMapper
{
    public List<TeaApplicantWork> selectWorkByApplicantId(Long applicantId);
    public int insertApplicantWork(TeaApplicantWork work);
    public int deleteWorkByApplicantId(Long applicantId);
}
