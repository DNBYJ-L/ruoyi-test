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
import com.ruoyi.system.domain.assessment.TeaAssessmentAudit;
import com.ruoyi.system.service.assessment.ITeaAssessmentAuditService;

@RestController
@RequestMapping("/teacher/assessment-audit")
public class TeaAssessmentAuditController extends BaseController
{
    @Autowired
    private ITeaAssessmentAuditService auditService;

    @PreAuthorize("@ss.hasPermi('teacher:assess:list')")
    @GetMapping("/trail/{recordId}")
    public AjaxResult trail(@PathVariable Long recordId) { return success(auditService.selectAuditTrail(recordId)); }

    @PreAuthorize("@ss.hasPermi('teacher:assess:audit')")
    @Log(title = "考核审核", businessType = BusinessType.UPDATE)
    @PutMapping("/do-audit/{recordId}")
    public AjaxResult doAudit(@PathVariable Long recordId, @RequestParam String auditNode,
        @RequestParam(required = false) Long auditorId, @RequestParam(required = false) String auditorName,
        @RequestParam String result, @RequestParam(required = false) String opinion)
    { return toAjax(auditService.doAudit(recordId, auditNode, auditorId, auditorName, result, opinion)); }
}
