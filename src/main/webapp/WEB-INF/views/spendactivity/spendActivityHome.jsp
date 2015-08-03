<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>Spend Activity</title>

</head>
<body>
spend activity list
<table border=1>
<tr>

<th>
Activity Id
</th>
<th>
Start Time
</th>
<th>
End Time
</th>
<th>
Display name
</th>
<th>
Status
</th>
<th>
Operate
</th>
</tr>
<c:forEach items="${spendActivityList}" var="item" varStatus="status">
<tr>
<td>
${item.id }

</td>
<td>
${item.startTime }
</td>
<td >
${item.endTime }
</td>
<td>
${item.displayName}
</td>
<td>
${item.status}
</td>
<td>
<c:choose>
<c:when test="${item.status=='UnActivity'}">
<a href="/nfsmw/admin/deleteSpendActivity?spendActivityId=${item.id}" >delete SpendActivity</a>
<a href="/nfsmw/admin/updateSpendActivity?spendActivityId=${item.id}" >update SpendActivity</a>
<a href="/nfsmw/admin/showSpendActivity?spendActivityId=${item.id}" >show SpendActivity</a>
</c:when>

<c:when test="${item.status=='Ended'}">
<a href="/nfsmw/admin/showSpendActivity?spendActivityId=${item.id}" >show SpendActivity</a>
</c:when>
<c:otherwise>
<a href="/nfsmw/admin/stopSpendActivity?spendActivityId=${item.id}" >stop SpendActivity</a>
<a href="/nfsmw/admin/showSpendActivity?spendActivityId=${item.id}" >show SpendActivity</a>
</c:otherwise>
</c:choose>
</td>
</tr>
</c:forEach>
</table>

<form id="searchForm" action="/nfsmw/admin/addNewSpendActivity" method="post">
		<table border=1>		
			<tr>
				<td>startTime:</td>
				<td><input type="text" name="startTime" value="" /></td>
			</tr>
			<tr>
				<td>endTime:</td>
				<td><input type="text" name="endTime" value="" /></td>
			</tr>
			<tr>
				<td>displayName:</td>
				<td><input type="text" name="displayName" value="" /></td>
			</tr>	
		</table>
		<input type="submit" value="add spend activity" />
	</form>
${message}
<a href="/nfsmw/admin/operateSpendActivity" >return</a>
</body>
</html>