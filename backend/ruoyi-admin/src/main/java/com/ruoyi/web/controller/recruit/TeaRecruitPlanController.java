package com.ruoyi.web.controller.recruit;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.recruit.TeaRecruitPlan;
import com.ruoyi.system.service.recruit.ITeaRecruitPlanService;

@RestController
@RequestMapping("/teacher/recruit-plan")
public class TeaRecruitPlanController extends BaseController
{
    @Autowired
    private ITeaRecruitPlanService recruitPlanService;

    @PreAuthorize("@ss.hasPermi('teacher:recruit:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaRecruitPlan plan) { startPage(); return getDataTable(recruitPlanService.selectRecruitPlanList(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:recruit:query')")
    @GetMapping("/{planId}")
    public AjaxResult getInfo(@PathVariable Long planId) { return success(recruitPlanService.selectRecruitPlanById(planId)); }

    @PreAuthorize("@ss.hasPermi('teacher:recruit:add')")
    @Log(title = "引进计划", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaRecruitPlan plan) { plan.setCreateBy(getUsername()); return toAjax(recruitPlanService.insertRecruitPlan(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:recruit:edit')")
    @Log(title = "引进计划", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaRecruitPlan plan) { plan.setUpdateBy(getUsername()); return toAjax(recruitPlanService.updateRecruitPlan(plan)); }

    @PreAuthorize("@ss.hasPermi('teacher:recruit:remove')")
    @Log(title = "引进计划", businessType = BusinessType.DELETE)
    @DeleteMapping("/{planIds}")
    public AjaxResult remove(@PathVariable Long[] planIds) { return toAjax(recruitPlanService.deleteRecruitPlanByIds(planIds)); }
}
