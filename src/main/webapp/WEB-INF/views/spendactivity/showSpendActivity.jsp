<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>Spend Activity</title>

</head>
<body>
spend activity 
<table border=1>
<tr>

<th>
Activity Id
</th>
<th>
Start Time
</th>
<th>
End Time
</th>
<th>
Display name
</th>
<th>
Status
</th>

</tr>

<tr>
<td>
${spendActivity.id }

</td>
<td>
${spendActivity.startTime }
</td>
<td >
${spendActivity.endTime }
</td>
<td>
${spendActivity.displayName}
</td>
<td>
${spendActivity.status}
</td>

</tr>
</table>


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
</tr>
</c:forEach>			
</table>

<a href="/nfsmw/admin/operateSpendActivity" >return</a>
</body>
</html>