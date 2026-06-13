package com.ruoyi.system.mapper.training;
import java.util.List;
import com.ruoyi.system.domain.training.TeaTrainingEnrollment;
public interface TeaTrainingEnrollmentMapper {
    public List<TeaTrainingEnrollment> selectEnrollmentList(TeaTrainingEnrollment enrollment);
    public TeaTrainingEnrollment selectEnrollmentById(Long enrollmentId);
    public int insertEnrollment(TeaTrainingEnrollment enrollment);
    public int updateEnrollment(TeaTrainingEnrollment enrollment);
}
