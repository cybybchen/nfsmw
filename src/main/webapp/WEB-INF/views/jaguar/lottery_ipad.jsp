<%@page contentType="text/html;charset=UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title></title>
<style type="text/css">
html,body,div,span,applet,object,iframe,h1,h2,h3,h4,h5,h6,p,blockquote,pre,a,abbr,acronym,address,big,cite,code,del,dfn,em,img,ins,kbd,q,s,samp,small,strike,strong,sub,sup,tt,var,b,u,i,center,dl,dt,dd,ol,ul,li,fieldset,form,label,legend,table,caption,tbody,tfoot,thead,tr,th,td,article,aside,canvas,details,embed,figure,figcaption,footer,header,hgroup,menu,nav,output,ruby,section,summary,time,mark,audio,video
	{
	margin: 0px auto 0px auto;
	padding: 0px;
}

ul,li,ol {
	list-style-type: none;
}

img {
	border: none;
	vertical-align: middle;
	outline: none;
}

body {
	text-align: center;
	font-size: 62.5%;
	line-height: n;
}

#main {
	width: 1024px;
}

a:link,a:visited {
	text-decoration: none;
}

a:hover,a:active,a:foucs {
	text-decoration: underline;
	color: #0FF;
}
/* a:focus {color: #00FF00}   */
.align-left {
	text-align: left;
}

.align-center {
	text-align: center;
}

.align-right {
	text-align: right;
}

.float_left {
	float: left;
}

.float_right {
	float: right;
}

.float_none {
	float: none;
}

.clear {
	clear: both;
}

.clearfix {
	display: block;
}

.clearfix:after {
	content: ".";
	display: block;
	height: 0;
	clear: both;
	visibility: hidden;
}

* html .clearfix {
	height: 1%;
}

.display_bk {
	display: block;
}

.display_ib {
	display: inline-block
}

.display_ie {
	display: inline;
}

.display_ne {
	display: none;
}

.font_wb {
	font-weight: bold;
}

.v_hn {
	visibility: hidden;
}
/*
(-_-)(-_-)(-_-)(-_-)(-_-)(-_-)(-_-)(-_-)
*/
.container {
	width: 100%;
	position: relative;
}

.banner01 {
	background: url(/nfsmw/images/banner01_1024.jpg) no-repeat;
	width: 100%;
	height: 140px;
	position: relative;
}

.logo01 {
	position: absolute;
	top: 10px;
	left: 20px;
}

.logo02 {
	position: absolute;
	right: 30px;
	top: 10px;
}

.bg01 {
	background: url(/nfsmw/images/bg01_1024.jpg) no-repeat;
	width: 100%;
	height: 472px;
}

.ftbg01 {
	background: url(/nfsmw/images/ftbg01_1024.jpg) no-repeat;
	height: 156px;
	width: 100%;
}

.bt0102 {
	margin-left: 40px;
}

.box01 {
	width: 54%;
	margin-right: 200px;
	padding: 30px 0px 0px 0px;
}

.input_box01 {
	width: 100%;
	margin-bottom: 16px;
}

.ibfl01 {
	float: left;
	display: inline;
	width: 20%;
}

.ibfr01 {
	float: left;
	display: inline;
	width: 79%;
}

.ibfr01b {
	float: left;
	display: inline;
	width: 50%;
}

.ibfr01b select {
	width: 100%;
	border: 1px solid #5e5e5e;
	background: #2c2c2c;
	color: #E4E4E4;
	padding: 6px 0px 6px 0px;
	margin: 0px auto;
}

.ibfr01b input {
	width: 100%;
	border: 1px solid #5e5e5e;
	background: #2c2c2c;
	color: #E4E4E4;
	padding: 6px 0px 6px 0px;
	margin: 0px auto;
}

.input_box02 {
	width: 100%;
}

.ibfl02 {
	float: left;
	display: inline;
	width: 48%;
}

.ibfl02 input {
	width: 100%;
	border: 1px solid #5e5e5e;
	background: #2c2c2c;
	color: #E4E4E4;
	padding: 6px 0px 6px 0px;
	margin: 0px auto;
}

.ibfl02 select {
	border: 1px solid #5e5e5e;
	width: 100%;
	background: #2c2c2c;
	padding: 6px 0px 6px 0px;
	margin: 0px auto;
	color: #E4E4E4;
}

.ibfr02,.ibfr02b {
	float: right;
	display: inline;
	width: 48%;
}

.ibfr02 select {
	border: 1px solid #5e5e5e;
	width: 100%;
	background: #2c2c2c;
	padding: 6px 0px 6px 0px;
	margin: 0px auto;
	color: #E4E4E4;
}

.ibfr02b select {
	border: 1px solid #5e5e5e;
	float: right;
	width: 66%;
	background: #2c2c2c;
	padding: 6px 0px 6px 0px;
	margin: 0px auto;
	color: #E4E4E4;
	display: inline;
}

.ft01 {
	font-size: 18px;
	color: #E4E4E4;
	line-height: 24px;
}

.ft02 {
	font-size: 18px;
	color: #999;
	line-height: 24px;
}

.banner02 {
	background: url(/nfsmw/images/banner02_1024.jpg) no-repeat;
	height: 190px;
	width: 100%;
}

.bg02 {
	background: url(/nfsmw/images/bg02_1024.jpg) no-repeat;
	height: 422px;
	width: 100%;
}

.box02 {
	width: 36%;
	padding: 80px 0px 0px 0px;
}

.input_box03 {
	width: 100%;
	margin-bottom: 16px;
}

.ibfl03 {
	width: 28%;
	float: left;
	display: inline;
}

.ibfr03 {
	float: right;
	display: inline;
	width: 71%;
}

.ibfr03 input {
	width: 100%;
	border: 1px solid #5e5e5e;
	background: #2c2c2c;
	color: #E4E4E4;
	padding: 6px 0px 6px 0px;
	margin: 0px auto;
}

.ibfr03 select {
	border: 1px solid #5e5e5e;
	width: 66%;
	background: #2c2c2c;
	padding: 6px 0px 6px 0px;
	margin: 0px auto;
	color: #E4E4E4;
}

.no_margin {
	margin: 0px auto 0px auto;
}

.banner03 {
	background: url(/nfsmw/images/banner03_1024.jpg) no-repeat;
	height: 140px;
	width: 100%;
}

.bg03a {
	background: url(/nfsmw/images/bg03a_1024.jpg) no-repeat;
	height: 472px;
	width: 100%;
}

.bg03b {
	background: url(/nfsmw/images/bg03b_1024.jpg) no-repeat;
	height: 472px;
	width: 100%;
}

.banner11 {
	background: #000;
	width: 100%;
	height: 99px;
	position: relative;
}

.bg02 {
	background: #000;
	width: 1024px;
	height: 768px;
}

#text {
	overflow: hidden
}

#text {
	position: absolute;
	width: 796px;
	margin: 0 auto;
	font-size: 14px;
	text-align: left;
	left: 125px;
	top: 93px;
	color: #FFF;
	line-height: 26px;
	height: 600px;
	color: #FFF;
	overFlow-y: scroll;
	overFlow-x: hidden;
	scrollBar-face-color: green;
	scrollBar-hightLight-color: red;
	scrollBar-3dLight-color: orange;
	scrollBar-darkshadow-color: blue;
	scrollBar-shadow-color: yellow;
	scrollBar-arrow-color: purple;
	scrollBar-track-color: black;
	scrollBar-base-color: black;
}
</style>
</head>
<body>
	<div id="main">
		<div class="container ">
			<div class=" ">
				<span class="logo02"><a href="#"><img
						src="/nfsmw/images/logo02_1024.png" /></a></span>
			</div>
			<div class="middle_Content bg02"></div>
			<div id="text">
				<p class="ft01">
					<span class="font_wb ">活动介绍：</span><br />
					2013年3月4日0点至2013年4月21日24点，提交个人信息，就能体验全新捷豹F-TYPE给你带来最激发本性的驾驶体验！更有惊喜好礼等你拿！
				</p>
				<p class="ft01">
					<span class="font_wb ">参与方式：</span><br /> 1．租用全新捷豹F-TYPE：
					填写并提交个人基本信息，即可获得1次使用全新捷豹F-TYPE参加比赛的机会；<br /> 2. 永久拥有全新捷豹F-TYPE：
					填写并提交个人详细试驾信息，即可在个人车库中永久拥有全新捷豹F-TYPE。
				</p>
				<p class="ft01">
					<span class="font_wb ">奖品说明： </span><br /> 租用全新捷豹F-TYPE赢大礼 <br />1)
					参与条件：2013年3月4日至4月21日，填写并提交个人基本信息成功租用全新捷豹F-TYPE的游戏玩家。<br /> 2)
					奖品内容：每周随机抽取符合条件的10名幸运玩家，每位可以获得捷豹F-TYPE钥匙扣一个，活动期间总计70个名额。<br /> 3)
					发放方式：游戏及捷豹官方微博将于每周五公布上周中奖玩家。捷豹中国相关工作人员将与中奖用户联系确认并发放奖品。<br /> 4)
					注意事项：每位符合条件的游戏玩家均有且只有一次获奖机会，中奖用户不再参与到下一轮“解锁捷豹F-TYPE赢大礼”中奖环节（以中奖者微博账号、身份证号及联系方式为准）
				</p>
				<p class="ft01">解锁全新捷豹F-TYPE赢大礼 <br />1)
					参与条件：2013年3月4日至2013年-4月21日，填写并提交个人详细试驾信息成功拥有全新捷豹F-TYPE的游戏玩家。 <br />2)
					奖品内容：每周随机抽取符合条件的10名幸运玩家，每位可获得捷豹赛车手套一副，活动期间总计70个名额。 <br />3)
					发放方式：游戏及捷豹官方微博将于每周五公布上周中奖玩家。捷豹中国相关工作人员将与中奖用户联系确认并发放奖品。</p>
				<p class="ft01">
					<span class="font_wb">补充说明：</span><br /> 1.
					凡参加本活动的中奖者即视为授权捷豹中国及EA中国在此次活动相关的广告和宣传中无偿使用中奖者的名字，任何已提供的个人信息，捷豹中国及EA中国无须再另行征求确认。
					<br />2. 捷豹中国及广告公司、网络合作伙伴的员工及其家属不可参加此次活动，以示公允。 <br />3.
					对于任何通过不正当手段参加活动者，捷豹中国有权在不事先通知的前提下取消其参加活动及得奖资格。 <br />4.
					本活动奖品不得转换、转让或折换现金，并以实物为准。 <br />5.
					如因网络传输原因而导致参加者提交的信息错误或预留信息错误导致无法联系，捷豹中国不承担相应损失。 <br />6.
					如遇不可抗力因素或其他不可控制的原因，本活动无法进行时，捷豹中国及EA中国有权决定取消、终止、修改或暂停本活动。 <br />7.
					中奖个人所得税（如有）由捷豹中国代扣代缴。 <br />8. 中奖名额不得转赠、贩卖、兑换现金、兑换其他任何等值内容。 <br />9.
					年龄限制：未满18周岁的玩家不得参与抽奖。 <br />10. 本活动最终解释权归捷豹中国及EA中国所有。
				</p>
			</div>

		</div>
	</div>
</body>
</html>

