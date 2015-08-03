<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>news list</title>
</head>
<body>
	<%@ include file="head.inc"%><br />
	<table border=1>
		<tr>
			<td>MOD当前状态:</td>
			<td>
				<c:if test="${modStatus==1}">开启中</c:if>
				<c:if test="${modStatus==0}">关闭中</c:if>
				<c:if test="${modStatus==2}">自动弹出</c:if>
			</td>
			<td><a href="/nfsmw/admin/system/config/mod?status=0">close</a></td>
			<td><a href="/nfsmw/admin/system/config/mod?status=1">open</a></td>
			<td><a href="/nfsmw/admin/system/config/mod?status=2">popup</a></td>
		</tr>
		</table>
		<br/>
	<form action="/nfsmw/admin/news/save" method="post">
	<table border=1>
		<tr>
			<td>type</td>
			<td>html code</td>
		</tr>
			<tr>
				<td>公告</td>
				<td><textarea id="board" name="board" rows="24"
						cols="100">${board}</textarea></td>
			</tr>
			<tr>
				<td>活动</td>
				<td><textarea id="action" name="action" rows="24"
						cols="100">${action}</textarea></td>
			</tr>
			<tr>
				<td>联系我们</td>
				<td><textarea id="contact" name="contact" rows="16"
						cols="100">${contact}</textarea></td>
			</tr>
			<tr>
				<td>css</td>
				<td><textarea id="css" name="css" rows="8"
						cols="100">${css}</textarea></td>
			</tr>
			<tr>
				<td><input type="submit" value="save" /></td>
			</tr>
	</table>

	</form>
</body>
</html>
