package com.ruoyi.system.utils;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.spring.SpringUtils;
import com.ruoyi.system.domain.teacher.TeaTeacher;
import com.ruoyi.system.service.teacher.ITeaTeacherService;

/**
 * 教师身份上下文工具类
 * 根据当前登录用户自动识别对应的教师记录
 */
public class TeacherContext
{
    /**
     * 获取当前登录用户对应的教师ID
     */
    public static Long getCurrentTeacherId()
    {
        TeaTeacher teacher = getCurrentTeacher();
        if (teacher == null)
        {
            throw new ServiceException("当前账号未关联教师信息，请联系管理员在「个人信息管理」中为您的账号关联教师记录");
        }
        return teacher.getTeacherId();
    }

    /**
     * 获取当前登录用户对应的教师信息（含子表）
     */
    public static TeaTeacher getCurrentTeacher()
    {
        Long userId = SecurityUtils.getUserId();
        ITeaTeacherService teacherService = SpringUtils.getBean(ITeaTeacherService.class);
        return teacherService.selectTeacherByUserId(userId);
    }
}
