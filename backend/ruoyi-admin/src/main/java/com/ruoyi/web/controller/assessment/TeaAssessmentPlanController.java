package com.ruoyi.web.controller.assessment;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.assessment.TeaAssessmentPlan;
import com.ruoyi.system.service.assessment.ITeaAssessmentPlanService;

@RestController
@RequestMapping("/teacher/assessment-plan")
public class TeaAssessmentPlanController extends BaseController
{
    @Autowired
    private ITeaAssessmentPlanService assessmentPlanService;

    @PreAuthorize("@ss.hasPermi('teacher:assess:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaAssessmentPlan plan) { startPage(); return getDataTable(assessmentPlanService.selectAssessmentPlanList(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:query')")
    @GetMapping("/{planId}")
    public AjaxResult getInfo(@PathVariable Long planId) { return success(assessmentPlanService.selectAssessmentPlanById(planId)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:add')")
    @Log(title = "考核方案", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaAssessmentPlan plan) { plan.setCreateBy(getUsername()); return toAjax(assessmentPlanService.insertAssessmentPlan(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:edit')")
    @Log(title = "考核方案", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaAssessmentPlan plan) { plan.setUpdateBy(getUsername()); return toAjax(assessmentPlanService.updateAssessmentPlan(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:remove')")
    @Log(title = "考核方案", businessType = BusinessType.DELETE)
    @DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds) { return toAjax(assessmentPlanService.deleteAssessmentPlanByIds(planIds)); }
}
