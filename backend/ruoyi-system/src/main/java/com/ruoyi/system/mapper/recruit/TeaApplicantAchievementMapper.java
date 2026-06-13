package com.ruoyi.system.mapper.recruit;

import java.util.List;
import com.ruoyi.system.domain.recruit.TeaApplicantAchievement;

public interface TeaApplicantAchievementMapper
{
    public List<TeaApplicantAchievement> selectAchievementByApplicantId(Long applicantId);
    public int insertApplicantAchievement(TeaApplicantAchievement achievement);
    public int deleteAchievementByApplicantId(Long applicantId);
}
