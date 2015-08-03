<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>add fragment</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
add fragment
<form id="searchForm" action="/nfsmw/admin/addfragment" method="post">
		<table border=1>
			<tr>
				<td>userId:</td>
				<td><textarea name="userIdList" value=""></textarea></td>
			</tr>
			
			<tr>
				<td>fragmentId:</td>
				<td><input type="text" name="fragmentId" value="" /></td>
			</tr>
			
			<tr>
				<td>amount:</td>
				<td><input type="text" name="amount" value="" /></td>
			</tr>
		
		</table>
		<input type="submit" value="add fragment" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>