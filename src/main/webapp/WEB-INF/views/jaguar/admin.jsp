<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>Jaguar admin</title>
</head>
<body>
<br/>
	<form name="queryform" id="queryform" action="/nfsmw/jaguar/admin"
		method="post">
		查询： <select name="type">
			<option value="1" <c:if test="${type==1}">selected="selected"</c:if>>永久获得</option>
			<option value="2" <c:if test="${type==2}">selected="selected"</c:if>>租用获得</option>
		</select> 邮箱:<input type="text" value="" name="email" /> 手机:<input type="text"
			value="" name="mobile" /> <input type="submit" value="查询" />
			&nbsp;&nbsp;&nbsp;&nbsp;<a href="/nfsmw/jaguar/admin/logout">退出</a>
	</form>
	<table border=0>
		<tr>
			<c:forEach items="${counts}" var="data" varStatus="i">
				<td>${data.deviceName}人数: ${data.count}</td>
			</c:forEach>
		</tr>
	</table>
	<br/>
	<!-- own form data -->
	<c:choose>
		<c:when test="${type==1}">
			<table border=1>
				<tr>
					<td>姓名</td>
					<td>性别</td>
					<td>手机</td>
					<td>邮箱</td>
					<td>设备</td>
					<td>省份</td>
					<td>城市</td>
					<td>购买年份</td>
					<td>购买月份</td>
					<td>预算</td>
				</tr>
				<c:forEach items="${ownDatas}" var="data" varStatus="i">
					<tr>
						<td>${data.name}</td>
						<td>${data.genderName}</td>
						<td>${data.mobile}</td>
						<td>${data.email}</td>
						<td>${data.deviceName}</td>
						<td>${data.province} &nbsp;</td>
						<td>${data.city} &nbsp;</td>
						<td>${data.buyYear}</td>
						<td>${data.seasonName}</td>
						<td>${data.money}</td>
					</tr>
				</c:forEach>
			</table>
		</c:when>
		<c:otherwise>
			<!-- rent form data -->
			<table border=1>
				<tr>
					<td>姓名</td>
					<td>性别</td>
					<td>手机</td>
					<td>邮箱</td>
					<td>设备</td>
				</tr>
				<c:forEach items="${rentDatas}" var="data" varStatus="i">
					<tr>
						<td>${data.name}</td>
						<td>${data.genderName}</td>
						<td>${data.mobile}</td>
						<td>${data.email}</td>
						<td>${data.deviceName}</td>
					</tr>
				</c:forEach>
			</table>
		</c:otherwise>
	</c:choose>
	<br/>
	<div align="left">
	<a href="/nfsmw/jaguar/admin?from=0">首页</a>
	<c:if test="${from>0}">
		<a href="/nfsmw/jaguar/admin?from=${from-50}">上一页</a>
	</c:if>
	<c:if test="${from+100<total}">
		<a href="/nfsmw/jaguar/admin?from=${from+50}">下一页</a>
	</c:if>
	<a href="/nfsmw/jaguar/admin?from=${total-50}">末页</a>
	</div>
</body>
</html>
