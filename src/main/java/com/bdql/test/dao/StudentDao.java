package com.bdql.test.dao;

import com.bdql.test.entity.StudentInfo;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.*;

/**
 * @AUTHOR: 小于
 * @DATE: [2020/3/15  10:27]
 * @DESC:
 */
public class StudentDao extends BaseDao<StudentInfo> {

    private static Logger LOGGER = Logger.getLogger(StudentDao.class);

    private static final Map<String, String> sqlMap = new HashMap<>();
    private static final String QUERY_ALL = "queryAll";
    private static final String QUERY_STUDENT_INFO_BY_ID = "queryStuInfoById";
    private static final String UPDATE_STU_INFO = "updateStuInfo";

    static {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader
                    (new FileInputStream(Thread.currentThread().getContextClassLoader().getResource("stu.sql")
                            .getFile()), "utf-8"), 1024 * 8);

            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) break;
                if (line.endsWith("--")) {
                    String[] arr = line.split("--");
                    System.out.println(Arrays.asList(arr));
                    sb.append(arr[0]);

                    sqlMap.put(arr[1], sb.toString());
                    sb.setLength(0);

                } else {
                    sb.append(line);
                }
            }

        } catch (FileNotFoundException e) {
            LOGGER.error("sql文件读取失败", e);
        } catch (IOException e) {
            LOGGER.error("sql文件读取失败", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    LOGGER.error("sql文件读取关闭输入流失败!!!", e);
                }
            }
        }
    }


    public List<StudentInfo> queryStuInfo(Integer id) {
        String sql = "";
        List<StudentInfo> list = null;
        if (id.equals(-1)) {
            sql = sqlMap.get(QUERY_ALL);
            list = query(sql, null);
        } else {
            sql = sqlMap.get(QUERY_STUDENT_INFO_BY_ID);
            list = query(sql, id);
        }
        return list;
    }

    public void updateStu(StudentInfo studentInfo) throws Exception {
        String sql = sqlMap.get(UPDATE_STU_INFO);
        update(sql, studentInfo.getName(), studentInfo.getGender(), studentInfo.getAge(), studentInfo.getAddress(), studentInfo.getEmail(), studentInfo.getId());

    }


}
