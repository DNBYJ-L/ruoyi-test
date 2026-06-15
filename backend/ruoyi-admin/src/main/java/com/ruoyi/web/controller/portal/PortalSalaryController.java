package com.ruoyi.web.controller.portal;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.system.domain.teacher.TeaSalaryRecord;
import com.ruoyi.system.service.teacher.ITeaSalaryRecordService;
import com.ruoyi.system.utils.TeacherContext;

/**
 * 教师端 - 薪资查询
 */
@RestController
@RequestMapping("/portal/salary")
public class PortalSalaryController extends BaseController
{
    @Autowired
    private ITeaSalaryRecordService salaryRecordService;

    /**
     * 查询个人薪资列表
     */
    @PreAuthorize("@ss.hasPermi('portal:salary:query')")
    @GetMapping("/list")
    public AjaxResult list()
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        List<TeaSalaryRecord> list = salaryRecordService.selectSalaryRecordByTeacherId(teacherId);
        return success(list);
    }

    /**
     * 查询薪资明细
     */
    @PreAuthorize("@ss.hasPermi('portal:salary:query')")
    @GetMapping(value = "/{recordId}")
    public AjaxResult getInfo(@PathVariable Long recordId)
    {
        TeaSalaryRecord record = salaryRecordService.selectSalaryRecordById(recordId);
        // 校验数据归属
        if (record != null)
        {
            Long teacherId = TeacherContext.getCurrentTeacherId();
            if (!teacherId.equals(record.getTeacherId()))
            {
                return error("无权查看该薪资记录");
            }
        }
        return success(record);
    }
}
