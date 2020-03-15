<%--
  Created by IntelliJ IDEA.
  User: 01
  Date: 2020/3/14
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <meta charset="UTF-8">

    <%--<link rel="icon" href="${pageContext.request.contextPath}/imgs/bird.ico" type="image/x-icon"/>--%>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/imgs/bird.ico" type="image/x-ico"/>


    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>学生信息修改</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <style>

        .blueTr {
            background-color: #2f96b4;
            font-weight: bold;
        }
    </style>

</head>
<body style="margin-left: 200px;margin-right: 200px;margin-top: 50px">
<input type="hidden" value="${pageContext.request.contextPath}" id="contextpath">
<input type="hidden" readonly value="${stu.id}" id="sid">

<table border="1" style="border-color: white;"
       width="100%">
    <tr class="blueTr" style="height: 150px;text-align: center;font-size: 26px">
        <td colspan="2">学生信息</td>
    </tr>
    <tr style="height: 50px">
        <td class="blueTr" width="20%" align="center">姓名</td>
        <td width="80%" style="margin: 0px;padding: 0px;alignment: center">
            <input name="name" id="name" value="${stu.name}"
                   style="width: 80%;height: 30px;margin-left: 30px;border-width: 2px;border-radius: 2px"/>
        </td>
    </tr>


    <tr style="height: 50px">
        <td class="blueTr" width="20%" align="center">年龄</td>
        <td width="80%" style="margin: 0px;padding: 0px;alignment: center">
            <input name="age" id="age" value="${stu.age}"
                   style="width: 80%;height: 30px;margin-left: 30px;border-width: 2px;border-radius: 2px"/>
        </td>
    </tr>

    <tr style="height: 50px">
        <td class="blueTr" width="20%" align="center">性别</td>
        <td width="80%" style="margin: 0px;padding: 0px;alignment: center">
            <input name="gender" id="gender" value="${stu.gender}"
                   style="width: 80%;height: 30px;margin-left: 30px;border-width: 2px;border-radius: 2px"/>
        </td>
    </tr>

    <tr style="height: 50px">
        <td class="blueTr" width="20%" align="center">家庭住址</td>
        <td width="80%" style="margin: 0px;padding: 0px;alignment: center">
            <input name="address" id="address" value="${stu.address}"
                   style="width: 80%;height: 30px;margin-left: 30px;border-width: 2px;border-radius: 2px"/>
        </td>
    </tr>

    <tr style="height: 50px">
        <td class="blueTr" width="20%" align="center">Email</td>
        <td width="80%" style="margin: 0px;padding: 0px;alignment: center">
            <input name="email" id="email" value="${stu.email}"
                   style="width: 80%;height: 30px;margin-left: 30px;border-width: 2px;border-radius: 2px"/>
        </td>
    </tr>

    <tr style="height: 50px" align="right">
        <td colspan="2">
            <a class="btn btn-success span2 offset6 edit">修改</a>
            <a class="btn btn-primary span2 reset">重置</a>
        </td>
    </tr>
</table>


<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title" id="myModalLabel">来自网页的消息</h4>
            </div>
            <div class="modal-body">
                <div style="height: 100px;margin-top:20px;margin-left:20px;font-size: 18px">
                    <img style="height: 30px" src="${pageContext.request.contextPath}/imgs/warn.png">
                    请填写完整的学员信息在修改
                </div>
                <div style="float: right">
                    <a class="btn btn-primary" id="closeModel" onclick="$('#myModal').modal('hide')">确定</a>
                </div>
            </div>
        </div>
    </div>
</div>

</body>
<script>


    $(function () {
        $(".edit").bind('click', function () {
            var pass = true;
            if (!$("#name").val() || !$("#age").val() || !$("#gender").val() || !$("#email").val() || !$("#address").val()) {

                pass = false;
            }


            if (pass) {
                $.post($('#contextpath').val() + "/editStu",
                    {
                        'id': $("#sid").val(),
                        'name': $("#name").val(),
                        'age': $('#age').val(),
                        'gender': $('#gender').val(),
                        'address': $('#address').val(),
                        'email': $('#email').val()
                    }, function (data) {
                        window.location.href = $("#contextpath").val() + "/index.jsp?update=" + data.code;
                    });
            } else {
                $("#myModal").modal("show");
            }


        });

        $(".reset").bind('click', function () {
            $.post($('#contextpath').val() + "/queryStu", {id: $("#sid").val()}, function (data) {
                if (data && data[0]) {
                    $("#name").val(data[0].name);
                    $("#age").val(data[0].age);
                    $("#gender").val(data[0].gender);
                    $("#email").val(data[0].email);
                    $("#address").val(data[0].address);
                }
            });
        });

    });


</script>
</html>
