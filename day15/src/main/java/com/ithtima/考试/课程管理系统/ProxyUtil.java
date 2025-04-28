package com.ithtima.考试.课程管理系统;

import lombok.SneakyThrows;
import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author: 张博尧
 * @Date: 2025/03/22 16:17
 * @Description: 代理工具类
 */
public class ProxyUtil {

    private static String coursePath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\Course.txt";
    private static String courseCopyPath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\CourseCopy.txt";
    private static String teacherPath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\Teacher.txt";
    private static String teacherCopyPath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\TeacherCopy.txt";
    private static String studentPath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\Student.txt";
    private static String studentCopyPath = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\StudentCopy.txt";

    private static String courseID = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\courseID.txt";
    private static String teacherID = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\teacherID.txt";
    private static String studentID = "day15\\src\\main\\java\\com\\ithtima\\考试\\课程管理系统\\txt文件\\studentID.txt";

    // 根据该课程所选的教师id，操作老师文件去添加这个课程的id--------根据该课程所选的学生id集合，操作学生文件去依次添加这个课程的id----------根据该学生所选的课程id集合，操作课程文件去一次添加这个学生的id
    @SneakyThrows
    public static void copy(List<String> ids, String id, int type) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        switch (type) {
            case 1:
                br = new BufferedReader(new FileReader(teacherPath));
                bw = new BufferedWriter(new FileWriter(teacherCopyPath));
                break;
            case 2:
                br = new BufferedReader(new FileReader(studentPath));
                bw = new BufferedWriter(new FileWriter(studentCopyPath));
                break;
            case 3:
                br = new BufferedReader(new FileReader(coursePath));
                bw = new BufferedWriter(new FileWriter(courseCopyPath));
                break;
        }
        String line;
        while ((line = br.readLine()) != null) {
            String[] split = line.split(",");
            if (ids.contains(split[0])) {
                if (type == 3) {
                    String ss = split[3] + "-" + id;
                    bw.write(split[0] + "," + split[1] + "," + split[2] + "," + ss);
                } else {
                    String ss = split[2] + "-" + id;
                    bw.write(split[0] + "," + split[1] + "," + ss);
                }
                bw.newLine();
            } else {
                bw.write(line);
                bw.newLine();
            }
        }
        br.close();
        bw.close();
        FileReader fr = null;
        FileWriter fw = null;
        switch (type) {
            case 1:
                fr = new FileReader(teacherCopyPath);
                fw = new FileWriter(teacherPath);
                break;
            case 2:
                fr = new FileReader(studentCopyPath);
                fw = new FileWriter(studentPath);
                break;
            case 3:
                fr = new FileReader(courseCopyPath);
                fw = new FileWriter(coursePath);
                break;
        }
        IOUtils.copy(fr, fw);
        fr.close();
        fw.close();
    }

    private static String getId(int type) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        Integer id = null;
        String line;
        try {
            switch (type) {
                case 1:
                    br = new BufferedReader(new FileReader(studentID));
                    line = br.readLine();
                    id = Integer.parseInt(line) + 1;
                    br.close();
                    bw = new BufferedWriter(new FileWriter(studentID));
                    bw.write(id.toString());
                    bw.close();
                    break;
                case 2:
                    br = new BufferedReader(new FileReader(teacherID));
                    line = br.readLine();
                    id = Integer.parseInt(line) + 1;
                    br.close();
                    bw = new BufferedWriter(new FileWriter(teacherID));
                    bw.write(id.toString());
                    bw.close();
                    break;
                case 3:
                    br = new BufferedReader(new FileReader(courseID));
                    line = br.readLine();
                    id = Integer.parseInt(line) + 1;
                    br.close();
                    bw = new BufferedWriter(new FileWriter(courseID));
                    bw.write(id.toString());
                    bw.close();
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return id.toString();
    }

    public static <T> T createProxy(T obj) {
        T proxy = (T) Proxy.newProxyInstance(ProxyUtil.class.getClassLoader(),
                obj.getClass().getInterfaces(), new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName().equals("addStudentORTeacher")) {
                            String id = getId((int) args[0]);
                            args[1] = id;

                            List<String> result = (List<String>) method.invoke(obj, args);

                            if ((int) args[0] == 1) copy(result, id, 3);
                            return result;
                        } else if (method.getName().equals("addCourse")) {
                            String id = getId(3);
                            args[0] = id;

                            List result = (List) method.invoke(obj, args);

                            copy((List<String>) result.get(0), id, 1);
                            copy((List<String>) result.get(1), id, 2);
                            return result;
                        } else {
                            return method.invoke(obj, args);
                        }
                    }
                });
        return proxy;
    }
}
