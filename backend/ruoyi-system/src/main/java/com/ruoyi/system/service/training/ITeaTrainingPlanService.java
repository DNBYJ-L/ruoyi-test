package com.ruoyi.system.service.training;
import java.util.List;
import com.ruoyi.system.domain.training.TeaTrainingPlan;
public interface ITeaTrainingPlanService {
    public List<TeaTrainingPlan> selectTrainingPlanList(TeaTrainingPlan plan);
    public TeaTrainingPlan selectTrainingPlanById(Long planId);
    public int insertTrainingPlan(TeaTrainingPlan plan);
    public int updateTrainingPlan(TeaTrainingPlan plan);
    public int deleteTrainingPlanByIds(Long[] planIds);
}
