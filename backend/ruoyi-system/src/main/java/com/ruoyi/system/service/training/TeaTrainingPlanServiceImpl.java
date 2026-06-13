package com.ruoyi.system.service.training;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.training.TeaTrainingPlan;
import com.ruoyi.system.domain.training.TeaTrainingCourse;
import com.ruoyi.system.mapper.training.TeaTrainingPlanMapper;
import com.ruoyi.system.mapper.training.TeaTrainingCourseMapper;

@Service
public class TeaTrainingPlanServiceImpl implements ITeaTrainingPlanService
{
    @Autowired
    private TeaTrainingPlanMapper trainingPlanMapper;
    @Autowired
    private TeaTrainingCourseMapper trainingCourseMapper;

    @Override
    public List<TeaTrainingPlan> selectTrainingPlanList(TeaTrainingPlan plan) { return trainingPlanMapper.selectTrainingPlanList(plan); }

    @Override
    public TeaTrainingPlan selectTrainingPlanById(Long planId)
    {
        TeaTrainingPlan plan = trainingPlanMapper.selectTrainingPlanById(planId);
        if (plan != null) { plan.setCourses(trainingCourseMapper.selectCourseByPlanId(planId)); }
        return plan;
    }

    @Override
    @Transactional
    public int insertTrainingPlan(TeaTrainingPlan plan)
    {
        int rows = trainingPlanMapper.insertTrainingPlan(plan);
        insertCourses(plan);
        return rows;
    }

    @Override
    @Transactional
    public int updateTrainingPlan(TeaTrainingPlan plan)
    {
        trainingCourseMapper.deleteCourseByPlanId(plan.getPlanId());
        insertCourses(plan);
        return trainingPlanMapper.updateTrainingPlan(plan);
    }

    @Override
    @Transactional
    public int deleteTrainingPlanByIds(Long[] planIds)
    {
        for (Long id : planIds) { trainingCourseMapper.deleteCourseByPlanId(id); }
        return trainingPlanMapper.deleteTrainingPlanByIds(planIds);
    }

    private void insertCourses(TeaTrainingPlan plan)
    {
        List<TeaTrainingCourse> courses = plan.getCourses();
        if (StringUtils.isNotEmpty(courses))
        {
            for (int i = 0; i < courses.size(); i++)
            {
                TeaTrainingCourse course = courses.get(i);
                if (StringUtils.isEmpty(course.getCourseName())) continue;
                course.setPlanId(plan.getPlanId());
                course.setSortOrder(i);
                trainingCourseMapper.insertTrainingCourse(course);
            }
        }
    }
}
