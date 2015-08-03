<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>Spend Activity</title>

</head>
<body>
spend activity
<form id="searchForm" action="/nfsmw/admin/doUpdateSpendActivity?spendActivityId=${spendActivity.id}" method="post">
		<table border=1>
			<tr>
				<td>Id:</td>
				<td>${spendActivity.id}</td>
			</tr>
			<tr>
				<td>startTime:</td>
				<td><input type="text" name="startTime" value="${spendActivity.startTime }" /></td>
			</tr>
			<tr>
				<td>endTime:</td>
				<td><input type="text" name="endTime" value="${spendActivity.endTime }" /></td>
			</tr>
			<tr>
				<td>displayName:</td>
				<td><input type="text" name="displayName" value="${spendActivity.displayName}" /></td>
			</tr>
		
		</table>
		<input type="submit" value="update spend activity" />
	</form> 


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
Operate
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
<a href="/nfsmw/admin/deleteSpendRewardByRewardAndActivity?spendActivityId=${spendActivity.id}&spendRewardId=${item.id}" >delete spend reward</a>
</td>
</tr>
</c:forEach>			
</table>
<form id="searchForm" action="/nfsmw/admin/addNewSpendRewardByRewardAndActivity?spendActivityId=${spendActivity.id}" method="post">
<select name="spendRewardId">
<c:forEach items="${allSpendRewardList}" var="item" varStatus="i">

<option value=${item.id} >${item.displayName}</option>


</c:forEach>
</select>
<input type="submit" value="select spendReward" />
</form>
<a href="/nfsmw/admin/operateSpendActivity" >return</a>
</body>
</html>