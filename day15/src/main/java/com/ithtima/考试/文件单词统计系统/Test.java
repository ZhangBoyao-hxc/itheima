package com.ithtima.考试.文件单词统计系统;

/**
 * HeiMa_JavaAI_20250401
 *
 * @author: 张博尧
 * @Date: 2025/04/27    下午3:52
 * @Description:
 */
public class Test {

    public static void main(String[] args) {
        String dirPath = "day15\\src\\main\\java\\com\\ithtima\\考试\\文件单词统计系统\\文件夹A";
        FileWordCountingSystem fileWordCountingSystem = new FileWordCountingSystem(dirPath);
        fileWordCountingSystem.start();
    }

}
