<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<body>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>UserTrackInfo</title>
<table>
<c:forEach items="${userTrackList}" var="u" varStatus="i">
			<tr>
				<td>TrackId:${u.trackId }</td>
				<td>ModeId:${u.modeId }</td>
				<td>Value:${u.value }</td>
				<td><a href="/nfsmw/admin/qa/changeusertrack?id=${u.id }">Change</a></td>
			</tr>
</c:forEach>
</table>
<a href="/nfsmw/admin/version" >return</a>
</head>


</body>
</html>