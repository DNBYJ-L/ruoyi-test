package com.ruoyi.web.controller.portal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.teacher.TeaTeacher;
import com.ruoyi.system.service.teacher.ITeaTeacherService;
import com.ruoyi.system.utils.TeacherContext;

/**
 * 教师端 - 个人中心
 */
@RestController
@RequestMapping("/portal/info")
public class PortalInfoController extends BaseController
{
    @Autowired
    private ITeaTeacherService teacherService;

    /**
     * 获取当前教师个人信息
     */
    @PreAuthorize("@ss.hasPermi('portal:info:query')")
    @GetMapping
    public AjaxResult getInfo()
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        return success(teacherService.selectTeacherById(teacherId));
    }

    /**
     * 修改当前教师个人信息
     */
    @PreAuthorize("@ss.hasPermi('portal:info:edit')")
    @Log(title = "教师端-个人信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody TeaTeacher teacher)
    {
        // 强制只能修改自己的信息
        Long teacherId = TeacherContext.getCurrentTeacherId();
        teacher.setTeacherId(teacherId);
        if (!teacherService.checkTeacherNoUnique(teacher))
        {
            return error("修改失败，工号'" + teacher.getTeacherNo() + "'已存在");
        }
        teacher.setUpdateBy(getUsername());
        return toAjax(teacherService.updateTeacher(teacher));
    }

    /**
     * 校验工号是否可用（编辑时排除自己）
     */
    @PreAuthorize("@ss.hasPermi('portal:info:query')")
    @GetMapping("/checkTeacherNo")
    public AjaxResult checkTeacherNo(TeaTeacher teacher)
    {
        Long teacherId = TeacherContext.getCurrentTeacherId();
        teacher.setTeacherId(teacherId);
        return success(teacherService.checkTeacherNoUnique(teacher));
    }
}
