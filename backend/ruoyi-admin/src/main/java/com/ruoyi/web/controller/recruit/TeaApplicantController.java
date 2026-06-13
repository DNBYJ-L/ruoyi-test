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
import com.ruoyi.system.domain.recruit.TeaApplicant;
import com.ruoyi.system.service.recruit.ITeaApplicantService;

@RestController
@RequestMapping("/teacher/applicant")
public class TeaApplicantController extends BaseController
{
    @Autowired
    private ITeaApplicantService applicantService;

    @PreAuthorize("@ss.hasPermi('teacher:applicant:list')")
    @GetMapping("/list")
    public TableDataInfo list(TeaApplicant applicant) { startPage(); return getDataTable(applicantService.selectApplicantList(applicant)); }

    @PreAuthorize("@ss.hasPermi('teacher:applicant:query')")
    @GetMapping("/{applicantId}")
    public AjaxResult getInfo(@PathVariable Long applicantId) { return success(applicantService.selectApplicantById(applicantId)); }

    @PreAuthorize("@ss.hasPermi('teacher:applicant:add')")
    @Log(title = "应聘管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TeaApplicant applicant) { applicant.setCreateBy(getUsername()); return toAjax(applicantService.insertApplicant(applicant)); }

    @PreAuthorize("@ss.hasPermi('teacher:applicant:edit')")
    @Log(title = "应聘管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TeaApplicant applicant) { applicant.setUpdateBy(getUsername()); return toAjax(applicantService.updateApplicant(applicant)); }

    @PreAuthorize("@ss.hasPermi('teacher:applicant:remove')")
    @Log(title = "应聘管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{applicantIds}")
    public AjaxResult remove(@PathVariable Long[] applicantIds) { return toAjax(applicantService.deleteApplicantByIds(applicantIds)); }

    @PreAuthorize("@ss.hasPermi('teacher:applicant:approve')")
    @Log(title = "应聘审批", businessType = BusinessType.UPDATE)
    @PutMapping("/approve/{applicantId}/{targetStatus}")
    public AjaxResult approve(@PathVariable Long applicantId, @PathVariable String targetStatus) { return toAjax(applicantService.approveApplicant(applicantId, targetStatus, getUsername())); }
}
