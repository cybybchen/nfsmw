<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ipad</title>
<link href="/nfsmw/css/ipad_1style.css" rel="stylesheet" type="text/css" />
<script src="/nfsmw/js/common.js" language="javascript"></script>
</head>
<body>
	<form action="/nfsmw/jaguar/rent/submit" method="post"
		onsubmit="return valiData();">
		<input type="hidden" name="display" value="${display}"/>
		<div id="main">
			<div class="container ">
				<div class="top_Banner banner02">
					<span class="logo02"><a href="#"><img
							src="/nfsmw/images/logo02_1024.png" /></a></span>
				</div>
				<div class="middle_Content bg02">
					<div class="box02">
						<div class="input_box03 clearfix align-left">
							<div class="ibfl03 ft01">
								姓<span class="v_hn">名姓</span>名
							</div>
							<div class="ibfr03">
								<input name="name" id="name" type="text" />
							</div>
						</div>
						<div class="input_box03 clearfix align-left">
							<div class="ibfl03 ft01">称谓</div>
							<div class="ibfr03">
								<select name="gender">
									<option value="1" selected="selected">先生</option>
									<option value="2">女生</option>
								</select>
							</div>
						</div>
						<div class="input_box03 clearfix align-left">
							<div class="ibfl03 ft01">年龄</div>
							<div class="ibfr03">
								<select name="age">
									<option value="1" selected="selected">小于18岁</option>
									<option value="2">19-25岁</option>
									<option value="3">26-35岁</option>
									<option value="4">36-45岁</option>
									<option value="5">45岁以上</option>
								</select></div>
							</div>
						<div class="input_box03 clearfix align-left">
							<div class="ibfl03 ft01">手机号码</div>
							<div class="ibfr03">
								<input name="mobile" id="mobile" type="text" />
							</div>
						</div>
						<div class="input_box03 clearfix align-left no_margin">
							<div class="ibfl03 ft01">电子邮箱</div>
							<div class="ibfr03">
								<input name="email" id="email" type="text" />
							</div>
						</div>
					</div>
				</div>
				<div class="bottom_Footer ftbg01">
					<div class="bt0102 align-left">
						<table class="bt0102 align-left">
							<tr>
								<td><a href="/nfsmw/jaguar/lottery?display=${display}"><img src="/nfsmw/images/bt01_1024.png" /></a>&nbsp;</td>
								<td><input type="image" src="/nfsmw/images/bt02_1024.png" /></td>
							</tr>
							<tr>
								<td><img src="/nfsmw/images/bt01b_1024.png" />&nbsp;</td>
								<td><img src="/nfsmw/images/bt02b_1024.png" /></td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</form>
</body>
</html>
