package com.bdql.test.servlet;

import com.alibaba.fastjson.JSON;
import com.bdql.test.dao.StudentDao;
import com.bdql.test.entity.StudentInfo;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @AUTHOR: 小于
 * @DATE: [2020/3/14  19:09]
 * @DESC:
 */
public class StuQueryServlet extends HttpServlet {

    private static final Logger LOGGER = Logger.getLogger(StuQueryServlet.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.valueOf(req.getParameter("id"));
        LOGGER.info("学生信息查询:id=" + id);
        List<StudentInfo> rs = new StudentDao().queryStuInfo(id);
        returnJson(resp, JSON.toJSONString(rs));

    }




    public void returnJson(HttpServletResponse resp, String jsonStr) {
        resp.setContentType("text/json;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");
        try {
            resp.getWriter().println(jsonStr);
        } catch (IOException e) {
            LOGGER.error("返回json数据,发生异常", e);
        }
    }

}
