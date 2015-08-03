<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>tournament</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
tournament online list
<table border=1>
<tr>
<th>
Id
</th>
<th>
Tournament Id
</th>
<th>
Start Time
</th>
<th>
End Time
</th>
<th>
Operation
</th>
</tr>
<c:forEach items="${tournamentOnlineList}" var="item" varStatus="i">
<tr>
<td>
${item.id }
</td>
<td>
${item.tournamentId }
</td>
<td>
${item.startTime }

</td>
<td>
${item.endTime }
</td>
<td>
<a href="/nfsmw/admin/updateTournament?tournamentOnlineId=${item.id}" >update</a>
</td>
</tr>
</c:forEach>
</table>

<form id="searchForm" action="/nfsmw/admin/addNewTournamentOnlineId" method="post">
		<table border=1>
			<tr>
				<td>tournamentIdList:</td>
				<td><input type="text" name="tournamentIdList" value="" /></td>
			</tr>
			<tr>
				<td>startTime:</td>
				<td><input type="text" name="startTime" value="" /></td>
			</tr>
			<tr>
				<td>endTime:</td>
				<td><input type="text" name="endTime" value="" /></td>
			</tr>
			<tr>
				<td>info:</td>
				<td><input type="text" name="info" value="" /></td>
			</tr>
			<tr>
				<td>endContent:</td>
				<td><input type="text" name="endContent" value="" /></td>
			</tr>
			<tr>
				<td>startContent:</td>
				<td><input type="text" name="startContent" value="" /></td>
			</tr>
			<tr>
				<td>weiboShareContent:</td>
				<td><input type="text" name="weiboShareContent" value="" /></td>
			</tr>
			<tr>
				<td>orderId:</td>
				<td><input type="text" name="orderId" value="" /></td>
			</tr>
			<tr>
				<td>addSum:</td>
				<td><input type="text" name="addSum" value="" /></td>
			</tr>
		
		</table>
		<input type="submit" value="add tournamentOnline" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>