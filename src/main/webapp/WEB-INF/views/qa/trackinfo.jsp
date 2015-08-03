<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Track Info</title>
</head>
<body>
<form id="updatetrackinfo" action="/nfsmw/admin/qa/updatetrackinfo?id=${userTrack.id }" method="post">
<table>
<tr>
<td>
TrackId:
</td>
<td>
${userTrack.trackId}
</td>
</tr>
<tr>
<td>
ModeId:
</td>
<td>
${userTrack.modeId}
</td>
</tr>
<tr>
<td>
Value:
</td>
<td>
<input type="text" name="value" value="${userTrack.value}" />
</td>
</tr>
</table>
<input type="submit" value="updateUserTrackInfo" />
</form>
${message}
<a href="/nfsmw/admin/version" >return</a>
</body>
</html>