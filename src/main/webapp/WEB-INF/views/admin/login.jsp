<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>version admin</title>
</head>
<body>
	<form id="loginForm" action="/nfsmw/admin/login" method="post">
		<table border=1>
			<tr>
				<td>username:</td>
				<td><input type="text" name="username" value="" /></td>
			</tr>
			<tr>
				<td>password:</td>
				<td><input type="password" name="password" value="" /></td>
			</tr>
			<tr>
				<td><input type="submit" value="login" /></td>
				<td><input type="reset" value="reset" /></td>
			</tr>
		</table>
	</form>
</body>
</html>
