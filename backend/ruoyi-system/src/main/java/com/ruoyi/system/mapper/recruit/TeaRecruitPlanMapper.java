package com.ruoyi.system.mapper.recruit;

import java.util.List;
import com.ruoyi.system.domain.recruit.TeaRecruitPlan;

public interface TeaRecruitPlanMapper
{
    public List<TeaRecruitPlan> selectRecruitPlanList(TeaRecruitPlan plan);
    public TeaRecruitPlan selectRecruitPlanById(Long planId);
    public int insertRecruitPlan(TeaRecruitPlan plan);
    public int updateRecruitPlan(TeaRecruitPlan plan);
    public int deleteRecruitPlanByIds(Long[] planIds);
}
