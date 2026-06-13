package com.ruoyi.system.service.recruit;

import java.util.List;
import com.ruoyi.system.domain.recruit.TeaRecruitPlan;

public interface ITeaRecruitPlanService
{
    public List<TeaRecruitPlan> selectRecruitPlanList(TeaRecruitPlan plan);
    public TeaRecruitPlan selectRecruitPlanById(Long planId);
    public int insertRecruitPlan(TeaRecruitPlan plan);
    public int updateRecruitPlan(TeaRecruitPlan plan);
    public int deleteRecruitPlanByIds(Long[] planIds);
}
