<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>car</title>
<%@ include file="head.inc"%><br/>

</head>
<body>
car info
<form id="searchForm" action="/nfsmw/admin/doUpdateCarExt?carId=${carExt.carId}" method="post">
		<table border=1>
			<tr>
				<td>carId:</td>
				<td>${carExt.carId}</td>
			</tr>
			<tr>
				<td>startTime:</td>
				<td><input type="text" name="startTime" value=${carExt.startTime} /></td>
			</tr>
			<tr>
				<td>endTime:</td>
				<td><input type="text" name="endTime" value=${carExt.endTime} /></td>
			</tr>
			<tr>
				<td>Price:</td>
				<td><input type="text" name="price" value="${carExt.price}" /></td>
			</tr>
			<tr>
				<td>PriceType:</td>
				<td><input type="text" name="priceType" value="${carExt.priceType}" /></td>
			</tr>
			<tr>
				<td>Visible:</td>
				<td><input type="text" name="visible" value="${carExt.visible}" /></td>
			</tr>
		
		</table>
		<input type="submit" value="update car ext" />
	</form>
	${erro}
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>