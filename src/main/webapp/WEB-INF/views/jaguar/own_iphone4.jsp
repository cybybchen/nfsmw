<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>iphone4</title>
<link href="/nfsmw/css/iphone_4style.css" rel="stylesheet" type="text/css" />
<script src="/nfsmw/js/common.js" language="javascript"></script>
</head>
<body>
	<form action="/nfsmw/jaguar/own/submit" method="post"
		onsubmit="return valiData();">
		<input type="hidden" name="display" value="${display}"/>
		<div id="main">
			<div class="container ">
				<div class="top_Banner banner01">
					<span class="logo02"><a href="#"><img
							src="/nfsmw/images/logo02_960.png" /></a></span>
				</div>
				<div class="middle_Content bg01">
					<div class="box01 ">
						<div class="input_box01 clearfix align-left">
							<div class="ibfl01 ft01">
								姓<span class="v_hn">姓名</span>名
							</div>
							<div class="ibfr01">
								<div class="input_box02 clearfix">
									<div class="ibfl02">
										<input name="name" id="name" type="text" />
									</div>
									<div class="ibfr02b ft01 clearfix">
										称谓 <select name="gender">
											<option value="0" selected="selected">请选择</option>
											<option value="1">先生</option>
											<option value="2">女士</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="input_box01 clearfix align-left">
							<div class="ibfl01 ft01">
										年<span class="v_hn">年龄</span>龄 </div>
										<div class="ibfl02">
								<select name="age">
									<option value="1" selected="selected">小于18岁</option>
									<option value="2">19-25岁</option>
									<option value="3">26-35岁</option>
									<option value="4">36-45岁</option>
									<option value="5">45岁以上</option>
								</select></div>
							</div>
						<div class="input_box01 clearfix align-left">
							<div class="ibfl01 ft01">手机号码</div>
							<div class="ibfr01b">
								<input name="mobile" id="mobile" type="text" />
							</div>
						</div>
						<div class="input_box01 clearfix align-left">
							<div class="ibfl01 ft01">电子邮箱</div>
							<div class="ibfr01b">
								<input name="email" id="email" type="text" />
							</div>
						</div>
						<div class="input_box01 clearfix align-left">
							<div class="ibfl01 ft01">所在城市</div>
							<div class="ibfr01">
								<div class="input_box02 clearfix">
									<div class="ibfl02">
										<select id='s1' name="province">
											<option>请选择省份</option>
										</select>
									</div>
									<div class="ibfr02 ft01 clearfix">
										<select id='s2' name="city">
											<option>请选择城市</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="input_box01 clearfix align-left">
							<div class="ibfl01 ft01">购车时间</div>
							<div class="ibfr01">
								<div class="input_box02 clearfix">
									<div class="ibfl02">
										<select name="year" id="year">
											<option value="">请选择年份</option>
											<option value="0">没有购车打算</option>
											<option value="2013">2013年</option>
											<option value="2014">2014年</option>
										</select>
									</div>
									<div class="ibfr02 ft01 clearfix">
										<select name="month" id="month">
											<option value="0">请选择月份</option>
											<option value="1">1-3月</option>
											<option value="2">4-6月</option>
											<option value="3">7-9月</option>
											<option value="4">10-12月</option>
										</select>
									</div>
								</div>
							</div>
						</div>
						<div class="input_box01 clearfix align-left">
							<div class="ibfl01 ft01">试驾地区</div>
							<div class="ibfr01b">
								<select name="try" id="try">
									<option value="">请选择</option>
									<option value="北京市">北京市</option>
									<option value="天津市">天津市</option>
									<option value="河北省">河北省</option>
									<option value="山西省">山西省</option>
									<option value="内蒙古">内蒙古</option>
									<option value="辽宁省">辽宁省</option>
									<option value="吉林省">吉林省</option>
									<option value="黑龙江省">黑龙江省</option>
									<option value="上海市">上海市</option>
									<option value=" 江苏省">江苏省</option>
									<option value="浙江省">浙江省</option>
									<option value="安徽省">安徽省</option>
									<option value="福建省">福建省</option>
									<option value="江西省">江西省</option>
									<option value="山东省">山东省</option>
									<option value="河南省">河南省</option>
									<option value="湖北省">湖北省</option>
									<option value="湖南省">湖南省</option>
									<option value="广东省">广东省</option>
									<option value="广西自治区">广西</option>
									<option value="海南 省">海南省</option>
									<option value=">重庆市">重庆市</option>
									<option value="四川省">四川省</option>
									<option value="贵州省">贵州省</option>
									<option value="云南省">云南省</option>
									<option value="西藏自治区">西藏</option>
									<option value="陕西省">陕西省</option>
									<option value="甘肃省">甘肃省</option>
									<option value="青海省">青海省</option>
									<option value="宁夏回族自治区">宁夏</option>
									<option value="新疆维吾尔自治区">新疆</option>
									<option value="香港特别行政区">香港</option>
									<option value="澳门特别行政区">澳门</option>
									<option value="台湾省">台湾</option>
									<option value="其它">其 它</option>
								</select>
							</div>
						</div>
						<div class="input_box01 clearfix align-left">
							<div class="ibfl01 ft01">购车预算</div>
							<div class="ibfr01b">
								<select name="budget" id="budget">
									<option value="">请选择</option>
									<option value="1">50万以下</option>
									<option value="2">50-75万</option>
									<option value="3">75-100万</option>
									<option value="4">100-150万</option>
									<option value="5">150-200万</option>
									<option value="6">200万以上</option>
									<option value="0">不确定</option>
								</select>
							</div>
						</div>
						<div class="input_box01 clearfix align-left no_margin">
							<div class="ibfl01 ft01">&nbsp;</div>
							<div class="ibfr01">
								<div class="input_box02 clearfix">
									<input name="agree" id="agree" type="checkbox" value="" />&nbsp;<span
										class="ft02">我已阅读及同意隐私协议</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="bottom_Footer ftbg01">
					<div class="bt0102 align-left">
						<table class="bt0102">
							<tr>
								<td><a href="/nfsmw/jaguar/lottery?display=${display}"><img src="/nfsmw/images/bt01_960.png" /></a>&nbsp;</td>
								<td><input type="image" src="/nfsmw/images/bt02_960.png" /></td>
							</tr>
							<tr>
								<td><img src="/nfsmw/images/bt01b_960.png" />&nbsp;</td>
								<td><img src="/nfsmw/images/bt02b_960.png" /></td>
							</tr>
						</table>
    
					</div>
				</div>
			</div>
		</div>
	</form>
	<script language="javascript">
		var s = [ "s1", "s2" ];
		var opt0 = [ "请选择省份", "请选择城市" ];
		function setup() {
			for (i = 0; i < s.length - 1; i++)
				document.getElementById(s[i]).onchange = new Function("change("
						+ (i + 1) + ")");
			change(0);
		}
		setup();
	</script>
</body>
</html>
