package com.ithtima.考试.文件单词统计系统;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/**
 * HeiMa_JavaAI_20250401
 *
 * @author: 张博尧
 * @Date: 2025/04/27    下午3:13
 * @Description:
 */

public class FileWordCountingSystem {

    private static final Logger log = LoggerFactory.getLogger(FileWordCountingSystem.class);

    private String dirPath;
    private File dir;
    private boolean isTxt = false;
    private Map<String, Integer> wordCountMap = new HashMap<>();

    public FileWordCountingSystem(String dirPath) {
        this.dirPath = dirPath;
    }

    public boolean whetherItExists() {
        dir = new File(dirPath);
        return dir.exists();
    }

    public void findTxtFile(String filePath) {
        File file = new File(filePath);
        String dirName = file.getName();
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isFile() && f.getName().endsWith(".txt")) {
                if (!isTxt) isTxt = true;
                String txtPath = filePath + "\\" + f.getName();
                int count = wordCount(txtPath);
                wordCountMap.put(dirName + "/" + f.getName(), count);
            } else {
                findTxtFile(filePath + "\\" + f.getName());
            }
        }
    }

    private int wordCount(String txtPath) {
        int count = 0;
        try (
                FileReader fr = new FileReader(txtPath);
                BufferedReader br = new BufferedReader(fr);
        ) {
            String line;

            while ((line = br.readLine()) != null) {
                String[] split = line.split(" ");
                count += split.length;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    public void start() {
        if (whetherItExists()) {
            findTxtFile(dirPath);
            if (isTxt) {
                log.info("文件统计完成");
                log.info("文件统计结果如下：");
                wordCountMap.forEach((k, v) -> {
                    log.info("文件名:" + k + ",单词数量:" + v);
                });
            } else {
                log.info("该路径下没有txt文件");
            }
        } else {
            log.info("{}路径不存在",  dirPath);
        }
    }

}
