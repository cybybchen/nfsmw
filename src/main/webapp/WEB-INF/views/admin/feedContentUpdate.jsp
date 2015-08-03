<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>ctacontentUpdate</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
<form id="searchForm" action="/nfsmw/admin/updateFeedContent?id=${feedContent.id}" method="post">
		<table border=1>
			<tr>
				<td>Id:</td>
				<td>${feedContent.id}</td>
			</tr>
			<tr>
				<td>content</td>
				<td><input type="text" name="content" value="${feedContent.content}" /></td>
			</tr>
			
		
		</table>
		<input type="submit" value="update feedcontent" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>