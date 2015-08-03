<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>userbind</title>


</head>
<body>
<form id="searchForm" action="/nfsmw/admin/changeAccountStatus?userId=${userId}" method="post">
		<table border=1>
			<tr><td>UserId: </td><td>${userId}</td><td></td></tr>
			<tr><td>IsBan: </td><td>${isban}</td><td><input name="ban" type="checkbox" value=1 <c:if test="${isBan=='1'}">checked="checked"</c:if>></td></tr>
			<tr><td>IsNoRecord: </td><td>${isNoRecord}</td><td><input name="norecord" type="checkbox" value=1 checked="checked"></td></tr>
			<tr><td>IsNoGhost: </td><td>${isNoGhost}</td><td><input name="noghost" type="checkbox" value=1 checked="checked"></td></tr>
			<tr><td>GhostRecord: </td><td>${ghostRecord}</td><td><input name="ghostRecord" type="checkbox" value=1 checked="checked"></td></tr>
			<tr><td>ShowMod: </td><td>${showMod}</td><td><input name="showMod" type="checkbox" value=1 <c:if test="${showMod=='1'}">checked="checked"</c:if>></td></tr>
			<tr><td>UserName: </td><td>${user.name}</td><td></td></tr>
			<tr><td>level: </td><td>${user.level}</td><td></td></tr>
			<tr><td>eol: </td><td>${user.eol}</td><td></td></tr>
			<tr><td>money: </td><td>${user.money}</td><td></td></tr>
			<tr><td>energy: </td><td>${user.energy}</td><td></td></tr>
			<tr><td>mwNum: </td><td>${user.starNum}</td><td></td></tr>
			<tr><td>headIndex: </td><td>${user.headIndex}</td><td></td></tr>
			<tr><td>headImg: </td><td><img src="${user.headUrl}"/></td><td></td></tr>
			<tr><td>tier: </td><td>${user.tier}</td><td></td></tr>
			<tr><td>RpNum: </td><td>${user.rpNum}</td><td></td></tr>
			<tr><td>gold: </td><td>${user.gold}</td><td></td></tr>
	
		</table>
		<input type="submit" value="change user status" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>