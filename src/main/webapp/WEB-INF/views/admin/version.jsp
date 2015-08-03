<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>login admin</title>
<script>
	function update() {
		var form = document.updateForm;
		if (window.confirm("确定要修改版本的状态吗？一旦出错后果自负！")) {
			var id = document.getElementById("txtId").value;
			var status = document.getElementById("selStatus").value;
			var gameEdition = document.getElementById("txtGameEdition").value;
			form.action = "/nfsmw/admin/versionUpdate?id=" + id + "&status="
					+ status + "&gameEdition=" + gameEdition;
			form.submit();
		}
	}
</script>
</head>
<body>
	<%@ include file="head.inc"%><br />
	<form name="addform" id="addform" action="/nfsmw/admin/version/add"
		method="post">
		<table>
			<tr>
				<td>DLC版本号:<input type="text" value="" name="version" size="10"/>&nbsp;&nbsp;
				</td>
				<td>游戏版本号:<input type="text" value="" name="gameEdition" size="10"/>&nbsp;&nbsp;</td>
				<td>状态: <select id="status" name="status">
						<option value="-1" selected="selected">内测</option>
						<option value="1">Live</option>
				</select>&nbsp;&nbsp;</td>
				<td>复制资源版本：
					<select id="targetVersion" name="targetVersion">
							<option value="0" selected="selected">--不复制--</option>
							<c:forEach items="${versions}" var="version" varStatus="i">
									<option value="${version.version}" >${version.version}</option>
							</c:forEach>
					</select>&nbsp;&nbsp;
				</td>
				<td><input type="submit" value="添加" />${msg}</td>
			</tr>
		</table>
	</form>
	<p>注意：<br/>
	1.添加功能一般只用于给多个游戏版本发布不同DLC时；<br/>
	2.游戏版本号来源于client传的头信息，不是通常描述的1.1，1.3,比如1.1实际上是0，不要填错；<br/>
	3.可以选择一个已发布的版本资源进行复制，不需要任何下载则无需复制。</p>
	<p><a href="/nfsmw/admin/version/clearcache">清缓存</a></p>
	<table border=1>
		<tr>
			<td>ID</td>
			<td>DLC版本号</td>
			<td>游戏版本号</td>
			<td>Status</td>
			<td>CreateTime</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${versions}" var="version" varStatus="i">
			<tr>
				<td>${version.id}</td>
				<td>${version.version}</td>
				<td>${version.gameEdition}</td>
				<td><c:choose>
						<c:when test="${version.status==1}">
									LIVE
								</c:when>
						<c:when test="${version.status==-1}">
									内部测试
								</c:when>
						<c:otherwise>
						</c:otherwise>
					</c:choose></td>
				<td><fmt:formatDate value="${version.createTime}" type="both" /></td>
				<td><a href="/nfsmw/admin/version?id=${version.id}">修改</a></td>
			</tr>
		</c:forEach>
	</table>
	<br />
	<c:if test="${!empty version}">
		<form name="updateForm" id="updateForm" action="" method="post">
			<table border=1>
				<tr>
					<td>ID：</td>
					<td><input type="text" value="${version.id}" id="txtId"
						readonly="readonly" /></td>
				</tr>
				<tr>
					<td>DLC版本号：</td>
					<td>${version.version}</td>
				</tr>
				<tr>
					<td>游戏版本号：</td>
					<td><input type="text" value="${version.gameEdition}"
						id="txtGameEdition" /></td>
				</tr>
				<tr>
					<td>状态：</td>
					<td><select id="selStatus">
							<option value="1"
								<c:if test="${version.status==1}">selected="selected"</c:if>>Live</option>
							<option value="-1"
								<c:if test="${version.status==-1}">selected="selected"</c:if>>内测</option>
					</select></td>
				</tr>
				<tr>
					<td>发布时间：</td>
					<td><fmt:formatDate value="${version.createTime}" type="both" /></td>
				</tr>
			</table>
			<input type="submit" value="修改" onclick="return update();" />
		</form>
	</c:if>

</body>
</html>
