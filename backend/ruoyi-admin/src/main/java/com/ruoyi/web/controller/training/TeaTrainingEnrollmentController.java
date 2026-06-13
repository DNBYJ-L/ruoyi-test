package com.ruoyi.web.controller.training;

import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.training.TeaTrainingEnrollment;
import com.ruoyi.system.service.training.ITeaTrainingEnrollmentService;

@RestController
@RequestMapping("/teacher/training-enrollment")
public class TeaTrainingEnrollmentController extends BaseController
{
    @Autowired
    private ITeaTrainingEnrollmentService enrollmentService;

    @PreAuthorize("@ss.hasPermi('teacher:enrollment:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaTrainingEnrollment enrollment) { startPage(); return getDataTable(enrollmentService.selectEnrollmentList(enrollment)); }

    @PreAuthorize("@ss.hasPermi('teacher:enrollment:query')")
    @GetMapping("/{enrollmentId}")
    public AjaxResult getInfo(@PathVariable Long enrollmentId) { return success(enrollmentService.selectEnrollmentById(enrollmentId)); }

    @PreAuthorize("@ss.hasPermi('teacher:enrollment:add')")
    @Log(title = "培训报名", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaTrainingEnrollment enrollment) { enrollment.setApplyDate(new Date()); enrollment.setApproveStatus("0"); enrollment.setCreateBy(getUsername()); return toAjax(enrollmentService.insertEnrollment(enrollment)); }

    @PreAuthorize("@ss.hasPermi('teacher:enrollment:approve')")
    @Log(title = "报名审批", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{enrollmentId}/{approveStatus}")
    public AjaxResult approve(@PathVariable Long enrollmentId, @PathVariable String approveStatus, @RequestParam(required = false) String approveRemark)
    { return toAjax(enrollmentService.approveEnrollment(enrollmentId, approveStatus, getUsername(), approveRemark)); }
}
