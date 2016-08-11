<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1920, initial-scale=1.0">
<title><spring:message code="label.changepasswordtitle" /></title>
<!-- <link rel="shortcut icon" href="../assets/img/favicon.ico"> -->
<link href="../assets/css/bootstrap.min.css" rel="stylesheet">
<link href="../assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">
<link href="../assets/css/plugins/chosen/chosen.css" rel="stylesheet">
<link href="../assets/css/animate.css" rel="stylesheet">
<link href="../assets/css/style.css" rel="stylesheet">
<link href="../assets/css/notification.css" rel="stylesheet">

</head>
<body class="gray-bg">
	<div class="middle-box text-center loginscreen animated fadeInDown">
		<div class="ibox-content9">
			<img alt="image" src="../assets/img/PanasonicAC_SmartCloud_logo_black.png"
				style="margin-top: 10px; margin-bottom: 40px;"><br>
				<h3 class="modal-title center-block" style="font-size: 20px;">
						<spring:message code="label.change.password"/>
						
					</h3>
					<div class="col-sm-2">						
					</div>
					<div class="col-sm-8">
						<div class="col-sm-12">
							<hr style="border: 1px solid #B9B5B5;">
						</div>
					</div>
					<div class="col-sm-2">						
					</div>
				<!-- <img alt="image" src="../assets/img/changepassword.png"
				style="margin-top: -15px; margin-bottom: 25px;"> -->
				<%-- <c:forEach items="${errorMessage}" var="error" >				    
				   				<span style="color:red">
				   				<!--<c:out value="${error}" />-->
				   				<spring:message code="${error}" />
				   				</span>				    				    			
					</c:forEach> --%>
			<form:form class="form-horizontal" action="./changePasswordProcess.htm"
				method="post" commandName="changePasswordFormBean" modelAttribute="changePasswordFormBean">
			

				
				<div class="form-group">					
					<div class="col-xs-7">
					<c:if test="${isPasswordExpired}">
						<p id="isPasswordExpired" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
						<spring:message code="password.expired.change.password"/>
                        </p>	
					</c:if>
					
					<label class="control-label" style="margin-left:-21px";><spring:message code="label.userid"/></label>
						<form:input path="loginId" type="text" class="form-control3" id="changeuser" value="${sessionInfo.loginId}" readonly="true"
							placeholder='Enter UserID'    style="text-align:center;" minlength="4"  maxlength="20"></form:input>
							<p id="changeuserred" style="color:red">
							<c:if test="${currentpasswordNotMatch ne null}">
							<p id="currentpasswordNotMatch" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
							<spring:message code="currentpassword.not.match"/>
							</p>	
							</c:if>
							</p>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-8">
					<label class="control-label" style="margin-left:4px"><spring:message code="label.current.password"/></label>
						<form:input path="currentPassword" type="password" class="form-control3" id="changepass" onfocus="this.type='password'"
							placeholder='Enter Current Password'   style="text-align:center;" minlength="8"  maxlength="20"></form:input>
							<p id="changepassred" style="color:red">

							</p>
					</div>
				</div>
				<div class="form-group">					
					<div class="col-xs-8">
					<label class="control-label" style="margin-left:-15px"><spring:message code="label.new.password"/></label>
						<form:input path="newPassword" type="password" class="form-control3" id="changenewpass" onfocus="this.type='password'"
							placeholder='Enter New Password'    style="text-align:center;" minlength="8"  maxlength="20"></form:input>
							<p id="changenewpassred" style="color:red">
							<c:if test="${newpasswordLengthMismatch ne null}">
								<p id="newpasswordLengthMismatch" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
								<spring:message code="newpassword.length.mismatch"/>
		                        </p>	
	                        </c:if>
							<c:if test="${newpasswordShouldContainAlphabetAndDigit ne null}">
								<p id="newpasswordShouldContainAlphabetAndDigit" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
								<spring:message code="newpassword.should.contain.alphabet.and.digit"/>
								</p>	
	                        </c:if>
							<c:if test="${newpasswordMatchConfirmpassword ne null}">
							<p id="newpasswordMatchConfirmpassword" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
							<spring:message code="newpassword.match.confirmpassword"/>
							</p>	
							</c:if>
							<c:if test="${newpasswordSameWithCurrentpassword ne null}">
							<p id="newpasswordSameWithCurrentpassword" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
							<spring:message code="newpassword.same.with.currentpassword"/>
							</p>	
							</c:if>
							<c:if test="${newpasswordContainsLoginid ne null}">
							<p id="newpasswordContainsLoginid" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
							<spring:message code="newpassword.contains.loginid"/>
							</p>	
							</c:if>							
							
							</p>							
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-8">
					<label class="control-label" style="margin-left:15px"><spring:message code="label.confirm.password"/></label>
						<form:input path="confirmPassword" type="password" class="form-control3" id="changecnfpass" onfocus="this.type='password'"
							placeholder='Enter Confirm New Password'    style="text-align:center;" minlength="8"  maxlength="20"></form:input>
							<p id="changecnfpassred" style="color:red"></p>
					</div>
				</div>
				<div class="form-group">
					<label class="col-xs-3 control-label"></label>
					<div class="col-xs-6">
						<button type="submit" class="btn bizButtonss greyGradient" id="changesumbit" style="margin-left:-21px";>
							<spring:message code="label.change"/> &nbsp;<span class="fa fa-caret-right"></span>
						</button>
					</div>
				</div>				
				<p class="m-t" style="margin-top: 10px;">
				<small><spring:message code="label.copy"/> &copy; 2016  <spring:message code="label.panasonic"/> <spring:message code="label.corp"/></small>
				</p>									
			</form:form>
			
		</div>
	</div>
	<!-- Mainly scripts -->
	<script src="../assets/js/jquery-2.1.1.js"></script>
	<script src="../assets/js/bootstrap.min.js"></script>
	<script src="../assets/js/util.js"></script>
	<script src="../assets/js/plugins/chosen/chosen.jquery.js"></script>
	<!-- <script src="../assets/js/plugins/js/bootstrap-select.js"></script>
	<script src="../assets/js/plugins/js/bootstrap-select.min.js"></script> -->

	

	<script src="../assets/js/login.js"></script>

</body>
</html>