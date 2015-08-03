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
<form id="searchForm" action="/nfsmw/admin/doUpdateTournamentOnline?tournamentOnlineId=${tournamentOnline.id}" method="post">
		<table border=1>
			<tr>
				<td>tournamentId:</td>
				<td><input type="text" name="tournamentId" value="${tournamentOnline.tournamentId}" /></td>
			</tr>
			<tr>
				<td>startTime:</td>
				<td><input type="text" name="startTime" value="${tournamentOnlineView.startTime }" /></td>
			</tr>
			<tr>
				<td>endTime:</td>
				<td><input type="text" name="endTime" value="${tournamentOnlineView.endTime }" /></td>
			</tr>
			<tr>
				<td>info:</td>
				<td><input type="text" name="info" value="${tournamentOnline.info}" /></td>
			</tr>
			<tr>
				<td>endContent:</td>
				<td><input type="text" name="endContent" value="${tournamentOnline.endContent}" /></td>
			</tr>
			<tr>
				<td>startContent:</td>
				<td><input type="text" name="startContent" value="${tournamentOnline.startContent}" /></td>
			</tr>
			<tr>
				<td>weiboShareContent:</td>
				<td><input type="text" name="weiboShareContent" value="${tournamentOnline.weiboShareContent}" /></td>
			</tr>
			<tr>
				<td>orderId:</td>
				<td><input type="text" name="orderId" value="${tournamentOnline.orderId}" /></td>
			</tr>
		
		</table>
		<input type="submit" value="update tournamentOnline" />
	</form>
	${erro}
	<a href="/nfsmw/admin/deleteTournamentOnlineId?tournamentOnlineId=${tournamentOnline.id}" >delete tournamentOnline</a>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>