<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserInfo</title>
</head>
<body>
<table>
<tr>
<th>
UserId
</th>
<th>
UserName
</th>
<th>
ModeId
</th>
<th>
Result
</th>
<th>
RankType
</th>
<th>
Operate
</th>
</tr>
<c:forEach items="${leaderBoardList}" var="item" varStatus="i">
<tr>
<td>
${item.userId }
</td>
<td>
${item.userName }
</td>
<td>
${item.modeType }
</td>
<td>
${item.result }
</td>
<td>
${item.rankType }
</td>
<td>
<a href="/nfsmw/admin/leaderboard/delete?id=${item.id }">删除</a>
</td>
</tr>
</c:forEach>
</table>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>