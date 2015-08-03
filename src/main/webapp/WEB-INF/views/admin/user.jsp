<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>version user list</title>
</head>
<body>
	<%@ include file="head.inc"%><br /> testers
	<table border=1>
		<tr>
			<td>id:</td>
			<td>name:</td>
			<td>version:</td>
		</tr>
		<c:forEach items="${users}" var="u" varStatus="i">
			<tr>
				<td>${u.id }</td>
				<td>${u.name }</td>
				<td>${u.version }</td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<form id="searchForm" action="/nfsmw/admin/user" method="get">
		<table border=1>
			<tr>
				<td>id:</td>
				<td><input type="text" name="id" value="" /></td>
				<td><input type="submit" value="search" /></td>
			</tr>
		</table>
	</form>
	<form action="/nfsmw/admin/userUpdate" method="post">
		<table border=1>
			<tr>
				<td>id:</td>
				<td>name:</td>
				<td>headurl:</td>
				<td>level:</td>
				<td>money:</td>
				<td>version:</td>
				<td>操作</td>
			</tr>
			<tr>
				<td><input type="text" name="txtId" value="${user.id}"
					readonly="readonly" /></td>
				<td>${user.name}</td>
				<td>${user.headUrl}</td>
				<td>${user.level}</td>
				<td>${user.money}</td>
				<td><input type="text" name="txtVersion"
					value="${user.version}" /></td>
				<td><input type="submit" value="修改版本" /> ${message}</td>
			</tr>
		</table>
	</form>
	<br/>
	<form action="/nfsmw/admin/user/addFilter" method="post">
	添加过滤用户：<br/>
		<table border=1>
			<tr>
				<td>userId:</td>
				<td>过滤项</td>
				<td>操作</td>
			</tr>
			<tr>
				<td><input type="text" name="userId" value=""/></td>
				<td>
				<select name="option" id="option">
					<option value="1" selected="selected">超跑会</option>
				</select>
				</td>
				<td><input type="submit" value="添加" /> ${message}</td>
			</tr>
		</table>
	</form>

</body>
</html>
