<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<html>
<head>
<title>resource list</title>
<script type="text/javascript">
	function deleteRes(id){
		var form = document.upForm;
		//var orderid = document.getElementById("orderid").value;
		//var status = document.getElementById("status").value;
		//form.action="/nfsmw/admin/resource?id="+id+"&orderid="+orderid+"&status="+status;
		if (window.confirm("确定要删除此资源吗?")) {
			form.action="/nfsmw/admin/resource/delete?id="+id;
			form.submit();
		}
	}
</script>
</head>
<body>
	<%@ include file="head.inc"%><br/>
	<form name="upForm" action="" method="post">
	查询版本号：<input type="text" name="version" value="" />
  <input type="submit" value="search" />
  <br/>
	<table border=1>
		<tr>
			<td>ID</td>
			<td>name</td>
			<td>fileName</td>
			<td>version</td>
			<!-- <td>frequency</td>
			<td>orderId</td> -->
			<td>status</td>
			<td>操作</td>
		</tr>
		<c:forEach items="${resources}" var="res" varStatus="i">
			<tr>
				<td>${res.id}</td>
				<td>${res.name}</td>
				<td>${res.fileName}</td>
				<td>${res.version}</td>
				<td>${res.status}</td>
				<%-- <td><input type="text" id="orderid" value="${res.orderId}" /></td>
				<td><input type="text" id="status" value="${res.status}" /></td> --%>
				<td><input type="submit" value="删 除" onclick="return deleteRes(${res.id});"/></td>
			</tr>
		</c:forEach>
	</table>
	</form>
	<a href="/nfsmw/admin/user">测试账号管理</a> |
	<a href="/nfsmw/admin/version">返回</a>
</body>
</html>
