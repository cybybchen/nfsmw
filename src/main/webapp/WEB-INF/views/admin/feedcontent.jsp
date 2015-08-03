<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>feed content</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
ctacontent list
<table>
<tr>
<th>
Id
</th>
<th>
content
</th>
<th>
operate
</th>
</tr>
<c:forEach items="${feedcontentList}" var="item" varStatus="i">
<tr>
<td>
${item.id }
</td>
<td>
${item.content }
</td>
<td>
<a href="/nfsmw/admin/feedcontentUpdate?id=${item.id}">update  </a>
<a href="/nfsmw/admin/feedcontentDelete?id=${item.id}">delete</a>
</td>
<td>
</td>
</tr>
</c:forEach>
</table>
<form id="searchForm" action="/nfsmw/admin/addnewfeed" method="post">
		<table border=1>
			<tr>
				<td>content:</td>
				<td><input type="text" name="content" value="" /></td>
			</tr>
		
		</table>
		<input type="submit" value="add feed" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>