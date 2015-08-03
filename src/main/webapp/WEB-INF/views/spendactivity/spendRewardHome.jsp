<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>spend reward</title>

</head>
<body>
spend reward list
<table border=1>
<tr>
<th>
Id
</th>
<th>
Gold Amount
</th>
<th>
Add Gold
</th>
<th>
Add Money
</th>
<th>
Add Energy
</th>
<th>
Display Name
</th>
<th>
Operation
</th>
</tr>
<c:forEach items="${spendRewardList}" var="item" varStatus="i">
<tr>
<td>
${item.id }
</td>
<td>
${item.goldAmount }
</td>
<td>
${item.addGold }

</td>
<td>
${item.addMoney }
</td>
<td>
${item.addEnergy }
</td>
<td>
${item.displayName }
</td>
<td>
<a href="/nfsmw/admin/spendReward/updateSpendReward?spendRewardId=${item.id}" >update</a>
</td>
</tr>
</c:forEach>
</table>

<form id="searchForm" action="/nfsmw/admin/spendReward/addNewSpendReward" method="post">
		<table border=1>
			<tr>
				<td>Gold Amount:</td>
				<td><input type="text" name="goldAmount" value="" /></td>
			</tr>
			<tr>
				<td>Add Gold:</td>
				<td><input type="text" name="addGold" value="" /></td>
			</tr>
			<tr>
				<td>Add Money:</td>
				<td><input type="text" name="addMoney" value="" /></td>
			</tr>
			
			<tr>
				<td>Add Energy:</td>
				<td><input type="text" name="addEnergy" value="" /></td>
			</tr>
			<tr>
				<td>Display Name:</td>
				<td><input type="text" name="displayName" value="" /></td>
			</tr>
		
		</table>
		<input type="submit" value="add spend reward" />
	</form>
${message}
<a href="/nfsmw/admin/operateSpendActivity" >return</a>
</body>
</html>