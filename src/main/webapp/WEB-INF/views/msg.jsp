<%@page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>Message Of Day</title>
<link href="/nfsmw/css/mod_ipad.css" rel="stylesheet" type="text/css" />
</head>

<body>
	${css}
	<div class="frame">
		<div class="scrolling">
			<div class="main">
				<div class="title_line" id="ann_logo">
					<div class="title_bg"></div>
					<div class="announce_title">版本更新公告</div>
				</div>
				<div class="announce">${new_notice}</div>
				<!--  -->
				<div class="title_line" id="ann_logo">
					<div class="title_bg"></div>
					<div class="announce_title">活动</div>
				</div>
				<div class="announce">${actions}</div>
				<!--  -->
				<div class="title_line" id="ann_logo">
					<div class="title_bg"></div>
					<div class="announce_title">联系我们</div>
				</div>
				<div class="announce">${contact}</div>
			</div>

			<div class="sidebar">
				<div class="leaderboard" id="lb_1">
					<div class="lb_title">超跑会</div>
					<c:forEach items="${garageList}" var="lb" varStatus="i">
						<c:choose>
							<c:when test="${self.userId==lb.userId}">
								<div class="eachone eachone_m" id="lb_1_2">
									<div class="rank rm${lb.rank}"></div>
									<img class="userhead" alt="" src="${lb.headUrl}" />
									<div class="username username_me">${lb.name}</div>
									<div class="userscore">${lb.carNum}辆车${lb.carTotalScore}总分</div>
								</div>
							</c:when>
							<c:otherwise>
								<div class="eachone eachone_n" id="lb_1_1">
									<div class="rank rn${lb.rank}"></div>
									<img class="userhead" alt="" src="${lb.headUrl}" />
									<div class="username username_n">${lb.name}</div>
									<div class="userscore">${lb.carNum}辆车${lb.carTotalScore}总分</div>
								</div>
							</c:otherwise>
						</c:choose>
					</c:forEach>
			       <c:if test="${inlist==false}">
			       	<div class="eachone eachone_m" id="lb_1_2">
									<div class="rank "></div>
									<img class="userhead" alt="" src="${self.headUrl}" />
									<div class="username username_me">${self.name}</div>
									<div class="userscore">${self.carNum}辆车${self.carTotalScore}总分</div>
								</div>
			       </c:if>

				</div>
			</div>
		</div>
		</div>
</body>
</html>