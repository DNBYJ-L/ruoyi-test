package com.ruoyi.system.domain.training;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

public class TeaTrainingEnrollment extends BaseEntity
{
    private static final long serialVersionUID = 1L;
    private Long enrollmentId;
    private Long planId;
    private Long teacherId;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date applyDate;
    @Excel(name = "审批状态", readConverterExp = "0=待审,1=通过,2=拒绝")
    private String approveStatus;
    private String approveBy;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date approveTime;
    private String approveRemark;
    /** 关联查询 */
    @Excel(name = "培训名称")
    private String planName;
    @Excel(name = "教师姓名")
    private String teacherName;
    @Excel(name = "院系")
    private String deptName;

    public Long getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(Long enrollmentId) { this.enrollmentId = enrollmentId; }
    public Long getPlanId() { return planId; }
    public void setPlanId(Long planId) { this.planId = planId; }
    public Long getTeacherId() { return teacherId; }
    public void setTeacherId(Long teacherId) { this.teacherId = teacherId; }
    public Date getApplyDate() { return applyDate; }
    public void setApplyDate(Date applyDate) { this.applyDate = applyDate; }
    public String getApproveStatus() { return approveStatus; }
    public void setApproveStatus(String approveStatus) { this.approveStatus = approveStatus; }
    public String getApproveBy() { return approveBy; }
    public void setApproveBy(String approveBy) { this.approveBy = approveBy; }
    public Date getApproveTime() { return approveTime; }
    public void setApproveTime(Date approveTime) { this.approveTime = approveTime; }
    public String getApproveRemark() { return approveRemark; }
    public void setApproveRemark(String approveRemark) { this.approveRemark = approveRemark; }
    public String getPlanName() { return planName; }
    public void setPlanName(String planName) { this.planName = planName; }
    public String getTeacherName() { return teacherName; }
    public void setTeacherName(String teacherName) { this.teacherName = teacherName; }
    public String getDeptName() { return deptName; }
    public void setDeptName(String deptName) { this.deptName = deptName; }
}
