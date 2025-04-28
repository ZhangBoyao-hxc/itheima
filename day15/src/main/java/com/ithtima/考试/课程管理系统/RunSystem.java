package com.ithtima.考试.课程管理系统;

import java.util.Scanner;

/**
 * HeiMa_JavaAI_20250401
 *
 * @author: 张博尧
 * @Date: 2025/04/28    下午2:34
 * @Description:  启动类
 */
public class RunSystem {

    public static void main(String[] args) {
        CourseManagementSystem system = ProxyUtil.createProxy(new CourseManagementSystemImpl());
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("1.添加学生");
            System.out.println("2.添加老师");
            System.out.println("3.添加课程");
            System.out.println("4.查询某位老师教授的所有课程");
            System.out.println("5.查询某个学生选修的所有课程");
            System.out.println("6.查询某门课程的所有学生名单");
            System.out.println("7.查询某位学生的老师");
            System.out.println("8.根据课程名称查询相关课程");
            System.out.println("9.退出");
            System.out.println("请输入您的选择：");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    system.addStudentORTeacher(1,null);
                    break;
                case "2":
                    system.addStudentORTeacher(2,null);
                    break;
                case "3":
                    system.addCourse(null);
                    break;
                case "4":
                    system.viewCourse(1);
                    break;
                case "5":
                    system.viewCourse(2);
                    break;
                case "6":
                    system.viewCourseStudent();
                    break;
                case "7":
                    system.viewStudentTeacher();
                    break;
                case "8":
                    system.byCourseNameSearchAllMessage();
                    break;
                case "9":
                    System.out.println("退出成功");
                    break;
                default:
                    System.out.println("您的输入有误，请重新输入");
            }
        }
    }
}
