<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=1920, initial-scale=1.0">
<title><spring:message code="label.firstlogintitle" /></title>
<link rel="shortcut icon" href="../assets/img/favicon.ico">
<link href="../assets/css/bootstrap.min.css" rel="stylesheet">
<link href="../assets/font-awesome/css/font-awesome.css"
	rel="stylesheet">

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
						<spring:message code="label.change.first"/>
						
					</h3>
					<div class="col-sm-2">						
					</div>
					<div class="col-sm-8">
						<div class="col-sm-12">
							<hr style="border: 1px solid #B9B5B5;">
						</div>
					</div>
					<div class="col-sm-2">						
					</div> <!-- <img
				alt="image" src="../assets/img/firsttimelogin.png"
				style="margin-top: -15px; margin-bottom: 25px;"> -->
			<form:form class="form-horizontal" action="./firstLoginProcess.htm"  
				method="post" commandName="firstLoginFormBean">
				<div class="form-group">
					<div class="col-xs-7">					
						<label class="control-label" style="margin-left: 42px";><spring:message code="label.current.userid"/></label>
						<form:errors path="currentLoginId"/> 
							<form:input path="currentLoginId" type="text" class="form-control3" name="username" value="${sessionInfo.loginId}" readonly="true"
                            id="firstusername" placeholder="Enter UserID" style="text-align: center;" minlength="4"  maxlength="20"></form:input>
	                         <p id="firstusernamered" style="color:red"></p>
	                         
	                         <c:if test="${currentloginidMatch ne null}">
								<p id="currentloginidMatch" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
								<spring:message code="currentLoginId.not.match"/>
								</p>	
								</c:if>
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-8">
						<label class="control-label" style="margin-left: -29px"><spring:message code="label.new.userid"/></label>
						<form:errors path="newLoginId"/> 
							<form:input  path="newLoginId" type="text" class="form-control3" name="newuserid"
                            id="firstnewuserid" placeholder="Enter New UserID" style="text-align: center;" minlength="4"  maxlength="20"></form:input>
                              <p id="firstnewuseridred" style="color:red">
								<c:if test="${newuseridMinimumLength ne null}">
								<p id="newuseridMinimumLength" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
								<spring:message code="newuserid.minimum.length"/>
								</p>	
								</c:if>
								<c:if test="${currentloginidNotMatch ne null}">
								<p id="currentloginidNotMatch" style="color:red;margin-top:16px;margin-left:154px;width:294px;">
								<spring:message code="newLoginId.not.match"/>
								</p>	
								</c:if>
                              </p>                        							
					</div>
				</div>
				<div class="form-group">
					<div class="col-xs-8">
						<label class="control-label" style="margin-left: 7px"><spring:message code="label.current.password"/></label>
						<form:errors path="currentPassword"/>  
							<form:input  path="currentPassword" type="password"  class="form-control3" name="currentpassword" onfocus="this.type='password'"
                            id="firstcurrentpassword" placeholder='Enter Current Password' style="text-align: center;" minlength="8"  maxlength="20"></form:input>
                             <p id="firstcurrentpasswordred" style="color:red">
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
						<label class="control-label" style="margin-left: -15px"><spring:message code="label.new.password"/></label>
						<form:errors path="newPassword"/>  							
							<form:input  path="newPassword" type="password"  class="form-control3" name="newpassword" onfocus="this.type='password'"
                            id="firstnewpassword" placeholder='Enter New Password' style="text-align: center;" minlength="8"  maxlength="20"></form:input> 
                             <p id="firstnewpasswordred" style="color:red">
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
						<label class="control-label" style="margin-left: 15px"><spring:message code="label.confirm.password"/></label>
						<form:errors path="confirmPassword"/>  
						<form:input  path="confirmPassword" type="password"  class="form-control3" onfocus="this.type='password'"
							placeholder='Enter New Password to Confirm'   name="confirmpassword" id="firstconfirmpassword"
							style="text-align: center" minlength="8"  maxlength="20"></form:input>
							 <p id="firstconfirmpasswordred" style="color:red"></p>                        							
							
							
					</div>
				</div>


				<%-- <spring:message code="user.timezone" var="timezone" />
				<form:hidden path="userTimeZone" value="${timezone}" /> --%>
				<div class="form-group">
					<label class="col-xs-3 control-label"></label>
					<div class="col-xs-6">
						<button type="submit" class="btn bizButtonss greyGradient"
							id="firstchangesubmit" style="margin-left: -21px";>
							<spring:message code="label.change"/> &nbsp;<span class="fa fa-caret-right"></span>
										
					</div>
				</div>
				<p class="m-t" style="margin-top: 10px;">
				<small><spring:message code="label.copy"/> &copy; 2016  <spring:message code="label.panasonic"/> <spring:message code="label.corp"/></small>
				</p>
				<!-- <div class="form-group">
					<label class="col-xs-2"></label>
					<div class="col-xs-5">
						<span class="text"><label><span style="color: red;">*</span>
								Mandatory fields</label></span>

					</div>
				</div> -->
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