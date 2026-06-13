package com.ruoyi.system.mapper.assessment;
import java.util.List;
import com.ruoyi.system.domain.assessment.TeaAssessmentPlan;
public interface TeaAssessmentPlanMapper {
    public List<TeaAssessmentPlan> selectAssessmentPlanList(TeaAssessmentPlan plan);
    public TeaAssessmentPlan selectAssessmentPlanById(Long planId);
    public int insertAssessmentPlan(TeaAssessmentPlan plan);
    public int updateAssessmentPlan(TeaAssessmentPlan plan);
    public int deleteAssessmentPlanByIds(Long[] planIds);
}
