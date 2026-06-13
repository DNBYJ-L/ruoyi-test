package com.ruoyi.system.service.recruit;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.recruit.TeaRecruitPlan;
import com.ruoyi.system.mapper.recruit.TeaRecruitPlanMapper;

@Service
public class TeaRecruitPlanServiceImpl implements ITeaRecruitPlanService
{
    @Autowired
    private TeaRecruitPlanMapper recruitPlanMapper;

    @Override
    public List<TeaRecruitPlan> selectRecruitPlanList(TeaRecruitPlan plan) { return recruitPlanMapper.selectRecruitPlanList(plan); }

    @Override
    public TeaRecruitPlan selectRecruitPlanById(Long planId) { return recruitPlanMapper.selectRecruitPlanById(planId); }

    @Override
    public int insertRecruitPlan(TeaRecruitPlan plan) { return recruitPlanMapper.insertRecruitPlan(plan); }

    @Override
    public int updateRecruitPlan(TeaRecruitPlan plan) { return recruitPlanMapper.updateRecruitPlan(plan); }

    @Override
    public int deleteRecruitPlanByIds(Long[] planIds) { return recruitPlanMapper.deleteRecruitPlanByIds(planIds); }
}
