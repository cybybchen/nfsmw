<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>leaderboard</title>


</head>
<body>
<!--<form id="selectTier" action="/nfsmw/admin/selectTier" method="post">
<table border=1>
<tr>
<td>
Tier:
</td>
<td>
	<select name="tier">
	<option value=1 <c:if test="${selectedTierId==1}">
selected="selected"</c:if>>T1</option>
	<option value=2<c:if test="${selectedTierId==2}">
selected="selected"</c:if>>T2</option>
	<option value=3<c:if test="${selectedTierId==3}">
selected="selected"</c:if>>T3</option>
	<option value=4<c:if test="${selectedTierId==4}">
selected="selected"</c:if>>T4</option>
	</select>
</td>
</tr>
</table>
<input type="submit" value="select tier" />
</form>-->
<form id="selectTier" action="/nfsmw/admin/leaderboardhome" method="post">
<table>
<tr>
<td>
Mode:
</td>
<td>
<select name="modeId">
<c:forEach items="${modeList}" var="item" varStatus="i">

<option value=${item.id} <c:if test="${selectedModeId==item.id}">
selected="selected"</c:if>>${item.displayName}</option>

</c:forEach>
</select>
</td>
</tr>
</table>

<table border=1>
<tr>
<th>UserId</th><th>UserName</th><th>CarId</th><th>RaceTime</th><th>RegularRaceTime</th>
<th>RegularAverageSpeed</th><th>AverageSpeed</th><th>FisrtConsumble</th>
<th>SecondConsumble</th><th>ThirdConsumble</th><th>Operate</th>
</tr>
<c:forEach items="${leaderboardInfoList}" var="item" varStatus="i">
<tr>
<td>${item.userId}</td><td>${item.userName}</td><td>${item.carId}</td><td>${item.raceTime}</td>
<td>${item.standardRaceTime}</td><td>${item.regularAverageSpeed}</td>
<td>${item.averageSpeed}</td><td>${item.firstConsumble}</td><td>${item.secondConsumble}</td>
<td>${item.thirdConsumble}</td><td><a href="/nfsmw/admin/updateStatus?userId=${item.userId}" >change status</a></td>
</tr>

</c:forEach>
</table>
<input type="submit" value="select mode" />
<a href="/nfsmw/admin/refreshleaderboard?modeId=${selectedModeId}" >refresh</a>
</form>
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>