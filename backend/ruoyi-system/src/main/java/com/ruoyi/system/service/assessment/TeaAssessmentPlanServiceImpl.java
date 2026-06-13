package com.ruoyi.system.service.assessment;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.assessment.TeaAssessmentPlan;
import com.ruoyi.system.mapper.assessment.TeaAssessmentPlanMapper;

@Service
public class TeaAssessmentPlanServiceImpl implements ITeaAssessmentPlanService
{
    @Autowired
    private TeaAssessmentPlanMapper assessmentPlanMapper;

    @Override
    public List<TeaAssessmentPlan> selectAssessmentPlanList(TeaAssessmentPlan plan) { return assessmentPlanMapper.selectAssessmentPlanList(plan); }

    @Override
    public TeaAssessmentPlan selectAssessmentPlanById(Long planId) { return assessmentPlanMapper.selectAssessmentPlanById(planId); }

    @Override
    public int insertAssessmentPlan(TeaAssessmentPlan plan) { return assessmentPlanMapper.insertAssessmentPlan(plan); }

    @Override
    public int updateAssessmentPlan(TeaAssessmentPlan plan) { return assessmentPlanMapper.updateAssessmentPlan(plan); }

    @Override
    public int deleteAssessmentPlanByIds(Long[] planIds) { return assessmentPlanMapper.deleteAssessmentPlanByIds(planIds); }
}
