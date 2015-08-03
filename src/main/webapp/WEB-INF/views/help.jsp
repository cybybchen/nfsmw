<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Help</title>
<script type="text/javascript" src="/nfsmw/js/jquery.min.js"></script>
<script type="text/javascript">
	if (navigator.userAgent.match(/iPad/i) != null) {
		document
				.write(unescape("%3Clink  href='css/ipad.css' rel='stylesheet' type='text/css' /%3E"));
	} else {
		document
				.write(unescape("%3Clink  href='css/iphone.css' rel='stylesheet' type='text/css' /%3E"));
		$('.mw_log').attr('src', 'images/iphone/mw-on-logo.png');
		$('.right_bg').attr('src', 'images/iphone/right-bg.png');
	}

	function _resize() {
		var f_width = parseInt($('.title_line').width());
		var p_width = parseInt($('.right_bg').width());
		var width = Math.abs(f_width - p_width);
		$('.title_bg').css('width', width + 'px');
	}

	$(window).load(function() {
		if (navigator.userAgent.match(/iPad/i) == null) {
			$('.mw_log').attr('src', 'images/iphone/mw-on-logo.png');
			$('.right_bg').attr('src', 'images/iphone/right-bg.png');
		}

		_resize();

	});
	$(window).resize(_resize);
</script>
</head>

<body>
	<div class="frame">
		<div class="scrolling">
			<div class="banner"></div>
			<div class="title_line" id="ann_logo">
				<div class="title_bg"></div>
				<div class="announce_title">帮助</div>
				<img alt="" src="images/ipad/mw-on-logo.png" class="mw_log" /> <img
					alt="" src="images/ipad/right-bg.png" class="right_bg" />
			</div>
			<div class="announce">

				<ul>
					<li><a name="pay_failed">很抱歉，您遇到了支付问题。请在苹果AppStore免费下载官方版本。</a>
						<p>如果您仍然遇到问题请联系客服支持:<a href="mailto:kefu@ea.com">kefu@ea.com</a></p></li>
				</ul>
			</div>

		</div>
	</div>
</body>
</html>