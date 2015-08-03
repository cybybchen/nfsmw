<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>userbind</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
<form id="searchForm" action="/nfsmw/admin/changeuserstatus" method="post">
		<table border=1>
			<tr>
				<td>UserIdList:</td>
				<td><textarea name="idList" value=""></textarea></td>
				<td>Is change</td>
			</tr>
			<tr>
				<td>ban status:</td>
				<td><select name="ban">
				<option value=0>normal</option>
				<option value=1>ban</option>
				</select></td>
				<td><input name="isBanChanged" type="checkbox" value=1 ></td>
			</tr>
			<tr>
				<td>leaderboard status:</td>
				<td><select name="norecord">
				<option value=0>in leaderboard</option>
				<option value=1>not in leaderboard</option>
				</select></td>
				<td><input name="isRecordChanged" type="checkbox" value=1 ></td>
			</tr>
			<tr>
				<td>ghost status:</td>
				<td><select name="noghost">
				<option value=0>other people can see</option>
				<option value=1>other people not can see</option>
				</select></td>
				<td><input name="isNoGhostChanged" type="checkbox" value=1 ></td>
			</tr>
			<tr>
				<td>ghost record status:</td>
				<td><select name="ghostRecord">
				<option value=0>no record ghost info</option>
				<option value=1>record ghost info</option>
				</select></td>
				<td><input name="isRecordGhostChanged" type="checkbox" value=1 ></td>
			</tr>
			<tr>
				<td>show mod status:</td>
				<td><select name="showMod">
				<option value=0>no show mod </option>
				<option value=1>show mod</option>
				</select></td>
				<td><input name="isShowModChanged" type="checkbox" value=1 ></td>
			</tr>
		
		</table>
		<input type="submit" value="change user status" />
	</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>