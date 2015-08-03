<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>car</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
car list
<table border=1>
<tr>
<th>
Id
</th>
<th>
Visible
</th>
<th>
Price
</th>
<th>
PriceType
</th>
<th>
StartTime
</th>
<th>
EndTime
</th>
<th>
Operation
</th>
</tr>
<c:forEach items="${carExtList}" var="item" varStatus="i">
<tr>
<td>
${item.carId }
</td>
<td>
${item.visible }
</td>
<td>
${item.price }
</td>
<td>
${item.priceType }
</td>
<td>
${item.startTime }

</td>
<td>
${item.endTime }

</td>
<td>
<a href="/nfsmw/admin/updateCarExt?carId=${item.carId}" >update</a>
</td>
</tr>
</c:forEach>
</table>

<a href="/nfsmw/admin/version" >return</a>
</body>
</html>