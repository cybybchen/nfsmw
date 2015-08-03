<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>userbind</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
<form id="searchForm" action="/nfsmw/admin/checkIapInfo" method="post">
		<table border=1>
			<tr>
				<td>IapInfo:</td>
				<td><input type="text" name="iapinfo" value="" /></td>
			</tr>
		
		
		</table>
		<input type="submit" value="check iap" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>