<%@ page import="com.dream.ImageInfor" %>
<%@ page import="com.dream.BingPic" %><%--
  Created by IntelliJ IDEA.
  User: Long
  Date: 2017/12/3
  Time: 17:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>this is my infor</title>
</head>
<body>
<script type="text/javascript">
    function submit() {
        alert("11111");

    }
</script>
<form id="form1" method="get" action="../index.jsp" >

<label >姓名：<input ></label>
<label >性别：<input></label>
    <button id="submit" onclick="submit()">提交</button>
</form>
<button id="submi" onclick="submit()">提交</button>
<%
    ImageInfor imageInfor=new ImageInfor();
    BingPic bingPic=new BingPic();
    bingPic.Start();

%>
</body>
</html>
