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
    <title>学生信息列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/jquery.1.8.3.min.js"></script>
    <script type="text/javascript" src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>

    <link rel="stylesheet" href="https://unpkg.com/bootstrap-table@1.15.3/dist/bootstrap-table.min.css">
    <script src="${pageContext.request.contextPath}/js/bootstrap-table.min.js"></script>


    <style>

        #table tr:nth-child(even) {
            background: #fafafa;
        }

        #table th {
            background: #efefef;
        }

        .head-table {
            height: 150px;
            font-size: 30px;
        }
    </style>

</head>
<body style="margin-left: 200px;margin-right: 200px;margin-top: 50px">
<input type="hidden" value="${pageContext.request.contextPath}" id="contextpath">
<div id="tableDiv">
    <table id="table">
    </table>
</div>
<div class="labelForUpdate">
    <div class="sucLabel offset8" style="font-size: 25px;font-weight: bold;color: red;display: none">
        更新成功
    </div>
    <div class="failLabel offset8" style="font-size: 25px;font-weight: bold;color: red;display: none">
        更新失败
    </div>
</div>

</body>
<script>


    $(function () {

        var url = window.location.href;
        var ks = url.split("?");
        if (ks.length == 2) {
            var params = ks[1];
            var code = params.split("=")[1]
            if (code == '200') {
                $(".sucLabel").show();
            } else if (code == '100') {
                $(".failLabel").show();
            }
        }
        $("#tableDiv").css('height', $(window).height() / 2 + 'px');

        function aFormatter(value, row, index) {
            var url = $('#contextpath').val() + "/editStu?id=" + value;
            return [
                '<a href="' + url + '">' + value + '</a>'
            ].join("")
        }

        $("#table").bootstrapTable({

            method: 'post',
            url: $('#contextpath').val() + "/queryStu",
            dataType: "json",
            cache: true,
            contentType: "application/x-www-form-urlencoded; charset=UTF-8",
            striped: true, //是否显示行间隔色
            queryParams: {'id': -1},
            columns: [
                [
                    {
                        title: '学生列表信息',
                        colspan: 6,
                        class: 'head-table',
                        valign: 'middle',
                        align: 'center'
                    }
                ]
                , [

                    {
                        title: '编号',
                        field: 'id',
                        sortable: true,
                        formatter: aFormatter //添加超链接的方法
                    }, {
                        title: '姓名',
                        field: 'name',
                        sortable: false
                    }, {
                        title: '性别',
                        field: 'gender',
                        sortable: false
                    }, {
                        title: '年龄',
                        field: 'age',
                        sortable: true
                    },
                    {
                        title: '住址',
                        field: 'address',
                        sortable: false
                    },
                    {
                        title: 'Email',
                        field: 'email',
                        sortable: false
                    }
                ]]
        })
        ;

    })
</script>
</html>
