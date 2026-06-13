package com.ruoyi.system.mapper.recruit;

import java.util.List;
import com.ruoyi.system.domain.recruit.TeaApplicantEducation;

public interface TeaApplicantEducationMapper
{
    public List<TeaApplicantEducation> selectEducationByApplicantId(Long applicantId);
    public int insertApplicantEducation(TeaApplicantEducation education);
    public int deleteEducationByApplicantId(Long applicantId);
}
