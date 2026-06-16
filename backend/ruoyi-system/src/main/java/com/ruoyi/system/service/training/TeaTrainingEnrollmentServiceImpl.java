package com.ruoyi.system.service.training;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.ruoyi.system.domain.training.TeaTrainingEnrollment;
import com.ruoyi.system.domain.training.TeaTrainingResult;
import com.ruoyi.system.mapper.training.TeaTrainingEnrollmentMapper;
import com.ruoyi.system.mapper.training.TeaTrainingResultMapper;

@Service
public class TeaTrainingEnrollmentServiceImpl implements ITeaTrainingEnrollmentService
{
    @Autowired
    private TeaTrainingEnrollmentMapper enrollmentMapper;
    @Autowired
    private TeaTrainingResultMapper trainingResultMapper;

    @Override
    public List<TeaTrainingEnrollment> selectEnrollmentList(TeaTrainingEnrollment enrollment) { return enrollmentMapper.selectEnrollmentList(enrollment); }

    @Override
    public TeaTrainingEnrollment selectEnrollmentById(Long enrollmentId) { return enrollmentMapper.selectEnrollmentById(enrollmentId); }

    @Override
    public int insertEnrollment(TeaTrainingEnrollment enrollment) { return enrollmentMapper.insertEnrollment(enrollment); }

    @Override
    @Transactional
    public int approveEnrollment(Long enrollmentId, String approveStatus, String approveBy, String approveRemark)
    {
        TeaTrainingEnrollment enrollment = new TeaTrainingEnrollment();
        enrollment.setEnrollmentId(enrollmentId);
        enrollment.setApproveStatus(approveStatus);
        enrollment.setApproveBy(approveBy);
        enrollment.setApproveTime(new Date());
        enrollment.setApproveRemark(approveRemark);
        int rows = enrollmentMapper.updateEnrollment(enrollment);

        // 审批通过时自动在结业管理中创建记录（防重复）
        if ("1".equals(approveStatus))
        {
            TeaTrainingEnrollment full = enrollmentMapper.selectEnrollmentById(enrollmentId);
            if (full != null)
            {
                // 检查是否已有该教师+该计划的结业记录
                TeaTrainingResult check = new TeaTrainingResult();
                check.setPlanId(full.getPlanId());
                check.setTeacherId(full.getTeacherId());
                List<TeaTrainingResult> existing = trainingResultMapper.selectTrainingResultList(check);
                if (existing == null || existing.isEmpty())
                {
                    TeaTrainingResult result = new TeaTrainingResult();
                    result.setPlanId(full.getPlanId());
                    result.setTeacherId(full.getTeacherId());
                    result.setExamScore(java.math.BigDecimal.ZERO);
                    result.setAssessmentType("1");
                    result.setPassStatus("0");
                    result.setCertificateNo("");
                    result.setCreditHours(0);
                    result.setSatisfactionScore(java.math.BigDecimal.ZERO);
                    result.setRemark("");
                    result.setCreateBy(approveBy);
                    trainingResultMapper.insertTrainingResult(result);
                }
            }
        }

        return rows;
    }
}
