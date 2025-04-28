package com.ithtima.考试.课程管理系统;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.*;

/**
 * HeiMa_JavaAI_20250401
 *
 * @author: 张博尧
 * @Date: 2025/04/27    下午4:20
 * @Description:
 */
public class CourseManagementSystemImpl implements CourseManagementSystem {

    private String coursePath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\Course.txt";
    private String teacherPath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\Teacher.txt";
    private String studentPath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\Student.txt";

    // 判断是否有这个名称 1:课程 2:老师 3:学生
    @SneakyThrows
    public boolean isName(String name, int type) {
        BufferedReader br = null;
        switch (type) {
            case 1:
                br = new BufferedReader(new FileReader(coursePath));
                break;
            case 2:
                br = new BufferedReader(new FileReader(teacherPath));
                break;
            case 3:
                br = new BufferedReader(new FileReader(studentPath));
                break;
        }
        String line;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            if (name.equals(split[1])) {
                return true;
            }
        }
        br.close();
        return false;
    }

    // 根据课程名称查询课程id   根据学生姓名查询学生id   根据教师姓名查询教师id
    @SneakyThrows
    public String getIdByName(String name, int type) {
        BufferedReader br = null;
        switch (type) {
            case 1:
                br = new BufferedReader(new FileReader(coursePath));
                break;
            case 2:
                br = new BufferedReader(new FileReader(studentPath));
                break;
            case 3:
                br = new BufferedReader(new FileReader(teacherPath));
                break;
        }
        String line;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            if (name.equals(split[1])) {
                br.close();
                return split[0];
            }
        }
        br.close();
        return null;
    }

    // 添加学生或老师(1是学生，2是老师)
    @Override
    public List<String> addStudentORTeacher(int type, String id) {
        Scanner scanner = new Scanner(System.in);
        List<String> courseIds = new ArrayList<>();
        BufferedWriter bw = null;
        String s = null;
        try {
            switch (type) {
                case 1:
                    bw = new BufferedWriter(new FileWriter(studentPath, true));
                    s = "学生";
                    break;
                case 2:
                    bw = new BufferedWriter(new FileWriter(teacherPath, true));
                    s = "老师";
                    break;
            }

            while (true) {
                System.out.println("请输入" + s + "信息：");
                System.out.println("请输入" + s + "姓名：");
                String name = scanner.nextLine();
                while (true) {
                    System.out.println("请输入" + s + "（选修/教授）的课程（886退出选课）：");
                    String course = scanner.nextLine();
                    if (course.equals("886")) break;
                    if (isName(course, 1)) {
                        String courseId = getIdByName(course, 1);
                        courseIds.add(courseId);
                    } else {
                        System.out.println("该课程不存在，请重新输入");
                    }
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < courseIds.size(); i++) {
                    if (i == courseIds.size() - 1) {
                        sb.append(courseIds.get(i));
                    } else {
                        sb.append(courseIds.get(i)).append("-");
                    }
                }
                bw.write(id + "," + name + "," + sb.toString());
                bw.newLine();
                bw.close();
                System.out.println("添加成功");
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseIds;
    }

    // 添加课程信息
    @Override
    public List addCourse(String id) {
        Scanner scanner = new Scanner(System.in);
        List list = new ArrayList();
        try (
                BufferedWriter bw = new BufferedWriter(new FileWriter(coursePath, true));
        ) {
            while (true) {
                System.out.println("请输入课程信息：");
                System.out.println("请输入课程名称：");
                String name = scanner.nextLine();
                String teacherId = null;
                while (true) {
                    System.out.println("请输入授课老师姓名：");
                    String teacherName = scanner.nextLine();
                    if (!isName(teacherName, 2)) {
                        System.out.println("该老师不存在，请重新输入");
                    } else {
                        teacherId = getIdByName(teacherName, 3);
                        break;
                    }
                }

                List<String> studentIds = new ArrayList<>();// 选修这门课的学生id集合
                while (true) {
                    System.out.println("请输入选修这门课的学生姓名（886退出选课）：");
                    String studentName = scanner.nextLine();
                    if (studentName.equals("886")) break;
                    if (isName(studentName, 3)) {
                        String studentId = getIdByName(studentName, 2);
                        studentIds.add(studentId);
                    } else {
                        System.out.println("该学生不存在，请重新输入");
                    }
                }

                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < studentIds.size(); i++) {
                    if (i == studentIds.size() - 1) {
                        sb.append(studentIds.get(i));
                    } else {
                        sb.append(studentIds.get(i)).append("-");
                    }
                }
                bw.write(id + "," + name + "," + teacherId + "," + sb.toString());
                bw.newLine();
                bw.flush();
                System.out.println("添加成功");
                Collections.addAll(list, Arrays.asList(teacherId),studentIds);
                break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // 查询某位教师教授的所有课程 查询某个学生选修的所有课程
    @Override
    public void viewCourse(int type) {
        Scanner scanner = new Scanner(System.in);
        BufferedReader br = null;
        String s = null;
        try {
            switch (type) {
                case 1:
                    br = new BufferedReader(new FileReader(teacherPath));
                    System.out.println("请输入教师姓名：");
                    s = scanner.nextLine();
                    if (!isName(s, 2)) {
                        System.out.println("该教师不存在，请重新输入");
                        return;
                    }
                    break;
                case 2:
                    br = new BufferedReader(new FileReader(studentPath));
                    System.out.println("请输入学生姓名：");
                    s = scanner.nextLine();
                    if (!isName(s, 3)) {
                        System.out.println("该学生不存在，请重新输入");
                        return;
                    }
                    break;
            }
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split[1].equals(s)) {
                    String[] courseIds = split[2].split("-");
                    List<String> list = getNameByIds(courseIds, 1);
                    System.out.println("(选修/教授)的课程有：" + list);
                    break;
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据课程ids查询课程名称  根据学生ids查询学生姓名  根据老师ids查询老师姓名
    public List<String> getNameByIds(String[] ids, int type) {
        List<String> courseNames = new ArrayList<>();
        BufferedReader br = null;
        try {
            switch (type) {
                case 1:
                    br = new BufferedReader(new FileReader(coursePath));
                    break;
                case 2:
                    br = new BufferedReader(new FileReader(studentPath));
                    break;
                case 3:
                    br = new BufferedReader(new FileReader(teacherPath));
                    break;
            }
            List<String> list = Arrays.asList(ids);
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (list.contains(split[0])) {
                    courseNames.add(split[1]);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return courseNames;
    }

    // 查询某门课程的所有学生名单
    @Override
    public void viewCourseStudent() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入课程名称：");
        String courseName = scanner.nextLine();
        if (!isName(courseName, 1)) {
            System.out.println("该课程不存在，请重新输入");
            return;
        }
        try (
                BufferedReader br = new BufferedReader(new FileReader(coursePath));
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split[1].equals(courseName)) {
                    String[] studentIds = split[3].split("-");
                    List<String> list = getNameByIds(studentIds, 2);
                    System.out.println("该课程的选修学生有：" + list);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 查询某位学生的老师
    @Override
    public void viewStudentTeacher() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入学生姓名：");
        String studentName = scanner.nextLine();
        if (!isName(studentName, 3)) {
            System.out.println("该学生不存在，请重新输入");
            return;
        }
        try {
            BufferedReader br = new BufferedReader(new FileReader(studentPath));
            // 先获得到该学生选课的课程id
            String line;
            List<String> courseIdsList = null;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split[1].equals(studentName)) {
                    String[] courseIds = split[2].split("-");
                    courseIdsList = Arrays.asList(courseIds);
                    break;
                }
            }
            br.close();

            // 根据课程id查询授课老师的id
            br = new BufferedReader(new FileReader(coursePath));
            List<String> techerIdList = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (courseIdsList.contains(split[0])) {
                    techerIdList.add(split[2]);
                }
            }
            br.close();

            // 根据老师id查询老师姓名
            List<String> teacherNameList = getNameByIds(techerIdList.toArray(new String[0]), 3);

            System.out.println("该学生的老师有：" + teacherNameList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 根据课程名称查询相关课程
    @Override
    public void byCourseNameSearchAllMessage() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入课程名称：");
        String courseName = scanner.nextLine();
        if (!isName(courseName, 1)) {
            System.out.println("该课程不存在，请重新输入");
            return;
        }
        try (
                BufferedReader br = new BufferedReader(new FileReader(coursePath));
        ) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] split = line.split(",");
                if (split[1].equals(courseName)) {
                    System.out.println("课程名称：" + split[1]);

                    String[] teacherId = new String[]{split[2]};
                    System.out.println("授课老师：" + getNameByIds(teacherId, 3));

                    String[] studentIds = split[3].split("-");
                    List<String> list = getNameByIds(studentIds, 2);
                    System.out.println("选修学生：" + list);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
