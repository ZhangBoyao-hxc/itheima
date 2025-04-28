package com.ithtima.考试.课程管理系统;

import java.util.List;

/**
 * HeiMa_JavaAI_20250401
 *
 * @author: 张博尧
 * @Date: 2025/04/28    上午10:56
 * @Description:
 */
public interface CourseManagementSystem {

    List<String> addStudentORTeacher(int type, String id);

    List addCourse(String id);

    void viewCourse(int type);

    void viewCourseStudent();

    void viewStudentTeacher();

    void byCourseNameSearchAllMessage();
}
