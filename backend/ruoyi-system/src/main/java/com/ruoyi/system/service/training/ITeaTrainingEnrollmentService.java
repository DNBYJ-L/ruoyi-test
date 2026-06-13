package com.ruoyi.system.service.training;
import java.util.List;
import com.ruoyi.system.domain.training.TeaTrainingEnrollment;
public interface ITeaTrainingEnrollmentService {
    public List<TeaTrainingEnrollment> selectEnrollmentList(TeaTrainingEnrollment enrollment);
    public TeaTrainingEnrollment selectEnrollmentById(Long enrollmentId);
    public int insertEnrollment(TeaTrainingEnrollment enrollment);
    public int approveEnrollment(Long enrollmentId, String approveStatus, String approveBy, String approveRemark);
}
