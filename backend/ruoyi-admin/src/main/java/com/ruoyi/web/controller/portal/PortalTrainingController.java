package com.ruoyi.web.controller.portal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.training.TeaTrainingEnrollment;
import com.ruoyi.system.domain.training.TeaTrainingPlan;
import com.ruoyi.system.domain.training.TeaTrainingResult;
import com.ruoyi.system.service.training.ITeaTrainingPlanService;
import com.ruoyi.system.service.training.ITeaTrainingEnrollmentService;
import com.ruoyi.system.service.training.ITeaTrainingResultService;
import com.ruoyi.system.utils.TeacherContext;

/**
 * 教师端 - 培训报名与学时
 */
@RestController
@RequestMapping("/portal/training")
public class PortalTrainingController extends BaseController
{
    @Autowired
    private ITeaTrainingPlanService trainingPlanService;
    @Autowired
    private ITeaTrainingEnrollmentService enrollmentService;
    @Autowired
    private ITeaTrainingResultService trainingResultService;

    /**
     * 查看可报名的培训计划（status=2 进行中）
     */
    @PreAuthorize("@ss.hasPermi('portal:training:query')")
    @GetMapping("/plans")
    public TableDataInfo plans()
    {
        TeaTrainingPlan query = new TeaTrainingPlan();
        query.setStatus("2"); // 进行中
        startPage();
        List<TeaTrainingPlan> list = trainingPlanService.selectTrainingPlanList(query);
        return getDataTable(list);
    }

    /**
     * 自主报名培训
     */
    @PreAuthorize("@ss.hasPermi('portal:training:enroll')")
    @Log(title = "教师端-培训报名", businessType = BusinessType.INSERT)
    @PostMapping("/enroll")
    public AjaxResult enroll(@RequestBody TeaTrainingEnrollment enrollment)
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        enrollment.setTeacherId(teacherId);
        enrollment.setApproveStatus("0"); // 待审批
        return toAjax(enrollmentService.insertEnrollment(enrollment));
    }

    /**
     * 我的报名记录
     */
    @PreAuthorize("@ss.hasPermi('portal:training:query')")
    @GetMapping("/enrollments")
    public TableDataInfo enrollments()
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        TeaTrainingEnrollment query = new TeaTrainingEnrollment();
        query.setTeacherId(teacherId);
        startPage();
        List<TeaTrainingEnrollment> list = enrollmentService.selectEnrollmentList(query);
        return getDataTable(list);
    }

    /**
     * 我的结业成绩
     */
    @PreAuthorize("@ss.hasPermi('portal:training:query')")
    @GetMapping("/results")
    public TableDataInfo results()
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        TeaTrainingResult query = new TeaTrainingResult();
        query.setTeacherId(teacherId);
        startPage();
        List<TeaTrainingResult> list = trainingResultService.selectTrainingResultList(query);
        return getDataTable(list);
    }

    /**
     * 我的学时统计
     */
    @PreAuthorize("@ss.hasPermi('portal:training:query')")
    @GetMapping("/hours")
    public AjaxResult hours()
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        Map<String, Object> params = new HashMap<>();
        params.put("teacherId", teacherId);
        List<Map<String, Object>> statList = trainingResultService.selectHoursStat(params);
        // 汇总
        int totalHours = 0;
        int passCount = 0;
        for (Map<String, Object> item : statList)
        {
            Object hours = item.get("totalHours");
            if (hours != null) totalHours += ((Number) hours).intValue();
            Object pass = item.get("passCount");
            if (pass != null) passCount += ((Number) pass).intValue();
        }
        Map<String, Object> result = new HashMap<>();
        result.put("detail", statList);
        result.put("totalHours", totalHours);
        result.put("passCount", passCount);
        return success(result);
    }
}
