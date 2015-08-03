<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserInfo</title>
</head>
<body>
<form id="updateuser" action="/nfsmw/admin/qa/updateuser" method="post">
<table>
<tr>
<td>
UserId:
</td>
<td>
<input type="text" name="userId" value="${user.id}" readonly="readonly" />
</td>
</tr>
<tr>
<td>
UserName:
</td>
<td>
<input type="text" name="userName" value="${user.name}" />
</td>
</tr>
<tr>
<td>
HeadIndex:
</td>
<td>
<input type="text" name="userHeadIndex" value="${user.headIndex}" />
</td>
</tr>
<tr>
<td>
HeadUrl:
</td>
<td>
<input type="text" name="userHeadUrl" value="${user.headUrl}" /><img src="${user.headUrl}"/>
</td>
</tr>
<tr>
<td>
UserGold:
</td>
<td>
<input type="text" name="userGold" value="${user.gold}" />
</td>
</tr>
<tr>
<td>
UserSilver:
</td>
<td>
<input type="text" name="userMoney" value="${user.money}" />
</td>
</tr>
<tr>
<td>
UserGas:
</td>
<td>
<input type="text" name="userEnergy" value="${user.energy}" />
</td>
</tr>
<tr>
<td>
UserRpNum:
</td>
<td>
<input type="text" name="userRpNum" value="${user.rpNum}" />
</td>
</tr>
<tr>
<td>
UserMwNum:
</td>
<td>
<input type="text" name="userMwNum" value="${user.starNum}" />
</td>
</tr>
<tr>
<td>
Tier:
</td>
<td>
<input type="text" name="tier" value="${user.tier}" />
</td>
</tr>
</table>
<input type="submit" value="updateUser" />
</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>