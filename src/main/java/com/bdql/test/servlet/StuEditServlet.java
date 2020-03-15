package com.bdql.test.servlet;

import com.alibaba.fastjson.JSON;
import com.bdql.test.dao.StudentDao;
import com.bdql.test.entity.StudentInfo;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @AUTHOR: 小于
 * @DATE: [2020/3/15  17:28]
 * @DESC:
 */
@WebServlet(urlPatterns = "/editStu")
public class StuEditServlet extends HttpServlet {


    private static final Logger LOGGER = Logger.getLogger(com.bdql.test.servlet.StuQueryServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Integer id = Integer.valueOf(req.getParameter("id"));
        LOGGER.info("学生信息查询:id=" + id);
        List<StudentInfo> rs = new StudentDao().queryStuInfo(id);
        StudentInfo studentInfo = null;
        if (rs.isEmpty()) {
            studentInfo = new StudentInfo();
        } else {
            studentInfo = rs.get(0);
        }
        req.setAttribute("stu", studentInfo);
        req.getRequestDispatcher("/editStu.jsp").forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try {
            Integer id = Integer.valueOf(req.getParameter("id"));
            LOGGER.info("学生信息修改:id=" + id);
            Integer age = Integer.valueOf(req.getParameter("age"));
            String gender = req.getParameter("gender");
            String name = req.getParameter("name");
            String address = req.getParameter("address");
            String email = req.getParameter("email");

            StudentInfo studentInfo = new StudentInfo();

            studentInfo.setId(id);
            studentInfo.setName(name);
            studentInfo.setEmail(email);
            studentInfo.setAddress(address);
            studentInfo.setGender(gender);
            studentInfo.setAge(age);

            new StudentDao().updateStu(studentInfo);
            returnJson(resp, "{\"code\":200}");
        } catch (Exception e) {
            LOGGER.info("学生信息修改发生异常", e);
            returnJson(resp, "{\"code\":100}");
        }


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
