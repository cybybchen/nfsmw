<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>leaderboard</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
<form id="searchByUserId" action="/nfsmw/admin/leaderboard/searchByUserId" method="post">
		<table border=1>
			<tr>
				<td>User id</td>
				<td><input type="text" name="userid" value="" /></td>
			</tr>		
		</table>
		
		<input type="submit" value="searchByUserId" />
</form>
<form id="searchByModeId" action="/nfsmw/admin/leaderboard/searchByModeId" method="post">
		<table border=1>
			<tr>
				<td>UserId</td>
				<td><input type="text" name="modeId" value="" /></td>
			</tr>		
		</table>	
		<input type="submit" value="searchByModeId" />
</form>
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>