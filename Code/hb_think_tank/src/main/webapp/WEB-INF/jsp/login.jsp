<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta charset="utf-8" />
<title>湖北智库网</title><base target="_blank" />
<link rel="icon" href="../../images/favicon.ico" type="image/x-icon"/>
<link rel="shortcut icon" href="../../images/favicon.ico" type="image/x-icon"/>
<link rel="Bookmark" href="../../images/favicon.ico">
<link rel="stylesheet" type="text/css" href="../../easyui/1.3.4/themes/default/easyui.css"/>
<link rel="stylesheet" type="text/css" href="../../easyui/1.3.4/themes/icon.css"/>
<script type="text/javascript" src="../../easyui/1.3.4/jquery.min.js"></script>
<script type="text/javascript" src="../../easyui/1.3.4/jquery.serializejson.min.js"></script>
<script type="text/javascript" src="../../easyui/1.3.4/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="../../easyui/1.3.4/jquery.easyui.min.js"></script>
<style>
*{
	padding:0px;
	margin:0px;
	}
    a{color:White}
body{
    width: 100%;
    height: 100%;
	font-family:Arial, Helvetica, sans-serif;
	background:url(../../images/grass.jpg) no-repeat  center;
	font-size:13px;
	}
img{
	border:0;
	}
.lg{width:468px; height:468px; margin-bottom: 300px ; background: no-repeat;}
.lg_top{ height:200px; width:468px;}
.lg_main{width:400px; height:180px; margin:0 25px;}
.lg_m_1{
	width:290px;
	height:100px;
	padding:60px 55px 20px 55px;
}
.ur{
	height:37px;
	border:0;
	color:#666;
	width:236px;
	margin:4px 28px;
	background:url(../../images/user.png) no-repeat;
	padding-left:10px;
	font-size:16pt;
	font-family:Arial, Helvetica, sans-serif;
}
.pw{
	height:37px;
	border:0;
	color:#666;
	width:236px;
	margin:4px 28px;
	background:url(../../images/password.png) no-repeat;
	padding-left:10px;
	font-size:16pt;
	font-family:Arial, Helvetica, sans-serif;
}
.bn{width:160px; height:37px; background: no-repeat; border:0; display:block; font-size:18px; color:#FFF; font-family:Arial, Helvetica, sans-serif; font-weight:bolder;}
.lg_foot{
	height:80px;
	width:330px;
	padding: 6px 68px 0 68px;
}
</style>
    <script>
        function login() {
            $.ajax({
                cache: true,
                type: "POST",
                url:'<%= basePath%>' + 'login?type=0',
                data:$('#loginform').serialize(),
                async: false,
                success: function(request) {
                    alert("Connection error");
                },
                error: function(data) {
                    $("#commonLayout_appcreshi").parent().html(data);
                }
            });
        }
    </script>
</head>
<body class="b">
<div class="lg" style="float: left">

    <div class="lg_top"></div>
    <div class="lg_main">
        <div style="text-align: center; color: #787878"><font size="6" style=""><b>湖北智库网后台管理系统</b></font></div>
        <div class="lg_m_1">
            <form id="loginform" method="POST" >
                <font style="color: #CC2222">${msg}</font>
                <input name="username" type="text" placeholder="请输入用户名" class="ur" />
                <input name="password" type="password" placeholder="请输入密码" class="pw" />
                <div class="lg_foot">
                    <input type="submit" onclick="login()" style="background-color: #0092DC;border-radius: 5px" value="登录" class="bn"/>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
