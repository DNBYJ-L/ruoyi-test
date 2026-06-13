package com.ruoyi.system.mapper.training;
import java.util.List;
import com.ruoyi.system.domain.training.TeaTrainingCourse;
public interface TeaTrainingCourseMapper {
    public List<TeaTrainingCourse> selectCourseByPlanId(Long planId);
    public int insertTrainingCourse(TeaTrainingCourse course);
    public int deleteCourseByPlanId(Long planId);
}
