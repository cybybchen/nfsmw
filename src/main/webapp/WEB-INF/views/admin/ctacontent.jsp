<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>ctacontent</title>
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
comments
</th>
<th>
operate
</th>
</tr>
<c:forEach items="${ctacontentList}" var="item" varStatus="i">
<tr>
<td>
${item.id }
</td>
<td>
${item.content }
</td>
<td>
${item.comments }
</td>
<td>
<a href="/nfsmw/admin/ctacontentUpdate?id=${item.id}">update</a>
</td>
<td>
</td>
</tr>
</c:forEach>
</table>
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>