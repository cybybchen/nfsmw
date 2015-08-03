<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>Operate Activity</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
operate activity list
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
Career Rp Times
</th>
<th>
Tournament Rp Times
</th>
<th>
Career Gold Times
</th>
<th>
Tournament Gold Times
</th>
<th>
Career Money Times
</th>
<th>
Tournament Money Times
</th>
<th>
Status
</th>
<th>
Operate
</th>
</tr>
<c:forEach items="${operateActivityList}" var="item" varStatus="i">
<tr>
<td>
${item.id }
</td>
<td>
${item.startTime }
</td>
<td>
${item.endTime }
</td>
<td>
${item.careerRpTimes}
</td>
<td>
${item.tournamentRpTimes}
</td>
<td>
${item.careerGoldTimes}
</td>
<td>
${item.tournamentGoldTimes}
</td>
<td>
${item.careerMoneyTimes}
</td>
<td>
${item.tournamentMoneyTimes}
</td>
<td>
${item.status}
</td>
<td>
<c:choose>
<c:when test="${item.status=='UnActivity'}">
<a href="/nfsmw/admin/deleteOperateActivity?operateActivityId=${item.id}" >delete OperateActivity</a>
<a href="/nfsmw/admin/updateOperateActivity?operateActivityId=${item.id}" >update OperateActivity</a>
</c:when>
<c:otherwise>
<a href="/nfsmw/admin/stopOperateActivity?operateActivityId=${item.id}" >stop OperateActivity</a>
</c:otherwise>
</c:choose>
</td>
</tr>
</c:forEach>
</table>

<form id="searchForm" action="/nfsmw/admin/addNewOperateActivity" method="post">
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
				<td>CareerRpTimes:</td>
				<td><input type="text" name="careerRpTimes" value="" /></td>
			</tr>
			<tr>
				<td>TournamentRpTimes:</td>
				<td><input type="text" name="tournamentRpTimes" value="" /></td>
			</tr>
			<tr>
				<td>CareerGoldTimes:</td>
				<td><input type="text" name="careerGoldTimes" value="" /></td>
			</tr>
			<tr>
				<td>TournamentGoldTimes:</td>
				<td><input type="text" name="tournamentGoldTimes" value="" /></td>
			</tr>
		<tr>
				<td>CareerMoneyTimes:</td>
				<td><input type="text" name="careerMoneyTimes" value="" /></td>
			</tr>
			<tr>
				<td>TournamentMoneyTimes:</td>
				<td><input type="text" name="tournamentMoneyTimes" value="" /></td>
			</tr>
		
		</table>
		<input type="submit" value="add operate activity" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>