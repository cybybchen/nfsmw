<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>censor word</title>
</head>
<body>
	<%@ include file="head.inc"%><br /> 
	<form id="searchForm" action="/nfsmw/admin/word" method="get">
		<table border=1>
			<tr>
				<td><input type="text" name="word" value="" /></td>
				<td><input type="submit" value="add" /></td>
			</tr>
		</table>
	</form>
	${msg}

</body>
</html>
