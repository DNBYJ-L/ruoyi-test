package com.ruoyi.system.service.training;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.domain.training.TeaTrainingEnrollment;
import com.ruoyi.system.mapper.training.TeaTrainingEnrollmentMapper;

@Service
public class TeaTrainingEnrollmentServiceImpl implements ITeaTrainingEnrollmentService
{
    @Autowired
    private TeaTrainingEnrollmentMapper enrollmentMapper;

    @Override
    public List<TeaTrainingEnrollment> selectEnrollmentList(TeaTrainingEnrollment enrollment) { return enrollmentMapper.selectEnrollmentList(enrollment); }

    @Override
    public TeaTrainingEnrollment selectEnrollmentById(Long enrollmentId) { return enrollmentMapper.selectEnrollmentById(enrollmentId); }

    @Override
    public int insertEnrollment(TeaTrainingEnrollment enrollment) { return enrollmentMapper.insertEnrollment(enrollment); }

    @Override
    public int approveEnrollment(Long enrollmentId, String approveStatus, String approveBy, String approveRemark)
    {
        TeaTrainingEnrollment enrollment = new TeaTrainingEnrollment();
        enrollment.setEnrollmentId(enrollmentId);
        enrollment.setApproveStatus(approveStatus);
        enrollment.setApproveBy(approveBy);
        enrollment.setApproveTime(new Date());
        enrollment.setApproveRemark(approveRemark);
        return enrollmentMapper.updateEnrollment(enrollment);
    }
}
