<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SerchUser</title>
</head>
<body>
<form id="searchUserId" action="/nfsmw/admin/qa/searchuser" method="post">
		<table border=1>
			<tr>
				<td>User Name</td>
				<td><input type="text" name="username" value="" /></td>
			</tr>		
		</table>
		
		<input type="submit" value="searchUserId" />
</form>
<form id="searchUserTrack" action="/nfsmw/admin/qa/searchusertrack" method="post">
		<table border=1>
			<tr>
				<td>UserId</td>
				<td><input type="text" name="userid" value="" /></td>
			</tr>		
		</table>
		
		<input type="submit" value="searchUserTrackInfo" />
</form>
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>