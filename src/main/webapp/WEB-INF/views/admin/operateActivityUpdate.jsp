<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>tournament</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
tournament online 
<form id="searchForm" action="/nfsmw/admin/doUpdateOperateActivity?operateActivityId=${operateActivity.id}" method="post">
		<table border=1>
			<tr>
				<td>id:</td>
				<td>${operateActivity.id}</td>
			</tr>
			<tr>
				<td>startTime:</td>
				<td><input type="text" name="startTime" value="${operateActivity.startTime }" /></td>
			</tr>
			<tr>
				<td>endTime:</td>
				<td><input type="text" name="endTime" value="${operateActivity.endTime }" /></td>
			</tr>
			<tr>
				<td>CareerRpTimes:</td>
				<td><input type="text" name="careerRpTimes" value="${operateActivity.careerRpTimes }" /></td>
			</tr>
			<tr>
				<td>TournamentRpTimes:</td>
				<td><input type="text" name="tournamentRpTimes" value="${operateActivity.tournamentRpTimes }" /></td>
			</tr>
			<tr>
				<td>CareerGoldTimes:</td>
				<td><input type="text" name="careerGoldTimes" value="${operateActivity.careerGoldTimes }" /></td>
			</tr>
			<tr>
				<td>TournamentGoldTimes:</td>
				<td><input type="text" name="tournamentGoldTimes" value="${operateActivity.tournamentGoldTimes }" /></td>
			</tr>
			<tr>
				<td>CareerMoneyTimes:</td>
				<td><input type="text" name="careerMoneyTimes" value="${operateActivity.careerMoneyTimes }" /></td>
			</tr>
			<tr>
				<td>TournamentMoneyTimes:</td>
				<td><input type="text" name="tournamentMoneyTimes" value="${operateActivity.tournamentMoneyTimes }" /></td>
			</tr>
		
		</table>
		<input type="submit" value="update operateActivity" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>