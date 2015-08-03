<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>spend reward</title>

</head>
<body>
spend reward 
<form id="searchForm" action="/nfsmw/admin/doUpdateSpendReward?spendRewardId=${spendReward.id}" method="post">
		<table border=1>
			<tr>
				<td>Gold Amount:</td>
				<td><input type="text" name="goldAmount" value="${spendReward.goldAmount}" /></td>
			</tr>
			<tr>
				<td>Add Gold:</td>
				<td><input type="text" name="addGold" value="${spendReward.addGold}" /></td>
			</tr>
			<tr>
				<td>Add Money:</td>
				<td><input type="text" name="addMoney" value="${spendReward.addMoney}" /></td>
			</tr>
			
			<tr>
				<td>Add Energy:</td>
				<td><input type="text" name="addEnergy" value="${spendReward.addEnergy}" /></td>
			</tr>
			<tr>
				<td>Display Name:</td>
				<td><input type="text" name="displayName" value="${spendReward.displayName}" /></td>
			</tr>
		
		</table>
		<input type="submit" value="update spendReward" />
	</form>
	${erro}
${message}
<a href="/nfsmw/admin/operateSpendActivity" >return</a>
</body>
</html>