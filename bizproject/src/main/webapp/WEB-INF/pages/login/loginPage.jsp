<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1920, initial-scale=1.0">
<title><spring:message code="label.logipagetitle" /></title>
<link rel="shortcut icon" href="../assets/img/favicon.ico">
<link href="../assets/css/bootstrap.min.css" rel="stylesheet">
<link href="../assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="../assets/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="../assets/css/plugins/iCheck/custom.css" rel="stylesheet">

<link href="../assets/css/animate.css" rel="stylesheet">
<link href="../assets/css/style.css" rel="stylesheet">
<link href="../assets/css/notification.css" rel="stylesheet">

</head>
<body class="gray-bg">
	<div class="middle-box text-center loginscreen animated fadeInDown">
		<div class="ibox-content6">
			<img alt="image"
				src="../assets/img/PanasonicAC_SmartCloud_logo_black.png"
				style="margin-top: 10px; margin-bottom: 40px;">
			<form:form class="form-horizontal" method="post"
				commandName="loginFormBean" autocomplete="off">
				<div class="form-group">
					<label class="col-xs-3 control-label"><spring:message
							code="label.userid" /></label>
					<div class="col-xs-8">
						<form:input path="loginId" type="text" class="form-control2"
							name="username" autocomplete="off" id="username"
							placeholder="Enter UserID" style="text-align: center;"
							minlength="4" maxlength="20"></form:input>
						<p id="userred" style="color: red"></p>
					</div>
				</div>
				<fmt:setLocale value="de" scope="session" />
				<div class="form-group">
					<label class="col-xs-3 control-label"><spring:message
							code="label.password" /></label>
					<div class="col-xs-8">
						<form:errors path="password" />
						<form:input path="password" type="text" class="form-control2"
							name="password" autocomplete="off" onfocus="this.type='password'"
							id="password" placeholder="Enter Password"
							style="text-align: center;" minlength="8" maxlength="20"></form:input>
						<p id="passwordred" style="color: red"></p>
<%-- 						<c:forEach items="${errorMessage}" var="error"> --%>
<!-- 							<span style="color: red"> <c:out value="${error}" /> -->
<%-- 								<spring:message code="${error}" /> --%>
<!-- 							</span> -->
<%-- 						</c:forEach> --%>
<%-- 						<c:forEach items="${loginFormBean.errorList}" var="msg"> --%>
<%-- 							<span style="color: blue"> <spring:message code="${msg}" /> --%>
<!-- 							</span> -->
<!-- 							<br> -->
<%-- 						</c:forEach> --%>

					</div>
				</div>
				<br>
				<div class="form-group">
					<label class="col-xs-3 control-label"><spring:message
							code="label.language" /></label>
					<div class="col-xs-2">

						<select class="form-control text-center" name="language"
							id="language">
							<option value="en">English</option>
							<option value="de">Germany</option>
						</select>

					</div>
				</div>
				<form:hidden path="userTimeZone" id="user_timezone" />
				<br>
				<div class="form-group">
					<label class="col-xs-3 control-label"></label>
					<div class="col-xs-6">
						<form:button type="submit" class="btn bizButtons greyGradient"
							onclick="form.action='./loginProcess.htm'" id="signin">
							<spring:message code="label.submit" />
							<span class="fa fa-caret-right"></span>
						</form:button>
					</div>
				</div>
				<form:button type="submit" onclick="form.action='./changePro.htm'"
					class="btn btn-link" id="signin" style="color:blue;">
					<spring:message code="label.change.password" />
				</form:button>

			</form:form>
			<br> <br>
			<p class="m-t">
				<%@ include file="../../../assets/template/common/footer.jsp"%>
			</p>
		</div>
	</div>
	<!-- Mainly scripts -->
	<script src="../assets/js/jquery-2.1.1.js"></script>
	<script src="../assets/js/bootstrap.min.js"></script>
	<script src="../assets/js/util.js"></script>
	<script src="../assets/js/plugins/chosen/chosen.jquery.js"></script>
	<script src="../assets/js/clearGroupSelection.js"></script>
	<script src="../assets/js/login.js"></script>
	<script src="../assets/js/timezone.js"></script>
	<script>
		$(document).ready(function() {
			var tz = jstz.determine();
			$('#user_timezone').val(tz.name());
		});
	</script>
</body>
</html>