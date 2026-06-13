package com.ruoyi.web.controller.training;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.training.TeaTrainingPlan;
import com.ruoyi.system.service.training.ITeaTrainingPlanService;

@RestController
@RequestMapping("/teacher/training-plan")
public class TeaTrainingPlanController extends BaseController
{
    @Autowired
    private ITeaTrainingPlanService trainingPlanService;

    @PreAuthorize("@ss.hasPermi('teacher:training:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaTrainingPlan plan) { startPage(); return getDataTable(trainingPlanService.selectTrainingPlanList(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:training:query')")
    @GetMapping("/{planId}")
    public AjaxResult getInfo(@PathVariable Long planId) { return success(trainingPlanService.selectTrainingPlanById(planId)); }

    @PreAuthorize("@ss.hasPermi('teacher:training:add')")
    @Log(title = "培训计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaTrainingPlan plan) { plan.setCreateBy(getUsername()); return toAjax(trainingPlanService.insertTrainingPlan(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:training:edit')")
    @Log(title = "培训计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaTrainingPlan plan) { plan.setUpdateBy(getUsername()); return toAjax(trainingPlanService.updateTrainingPlan(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:training:remove')")
    @Log(title = "培训计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds) { return toAjax(trainingPlanService.deleteTrainingPlanByIds(planIds)); }
}
