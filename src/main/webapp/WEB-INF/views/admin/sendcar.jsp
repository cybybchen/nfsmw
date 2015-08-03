<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>send car</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
send car
<form id="searchForm" action="/nfsmw/admin/addnewcar" method="post">
		<table border=1>
			<tr>
				<td>userId:</td>
				<td><textarea name="userIdList" value=""></textarea></td>
			</tr>
			
			<tr>
				<td>carId:</td>
				<td><input type="text" name="carId" value="" /></td>
			</tr>
		
		</table>
		<input type="submit" value="send car" />
	</form>
	
	<form id="addMoney" action="/nfsmw/admin/addUserInfo" method="post">
		<table border=1>
			<tr>
				<td>userId:</td>
				<td><textarea name="userIdList" value=""></textarea></td>
			</tr>
			
			<tr>
				<td>add money number:</td>
				<td><input type="text" name="addMoney" value="0" /></td>
			</tr>
			
			<tr>
				<td>add gold number:</td>
				<td><input type="text" name="addGold" value="0" /></td>
			</tr>
			
			<tr>
				<td>add energy number:</td>
				<td><input type="text" name="addEnergy" value="0" /></td>
			</tr>
		
		</table>
		<input type="submit" value="add money" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>