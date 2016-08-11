<!-- div class="row border-bottom">
<div class="row border-bottom" style="margin-bottom:25px;">
	<nav class="headerBG gradient" role="navigation">
		<img alt="Panasonic Smart Cloud" id="companyLogo"
			src="../assets/img/Panasonic-Smart-cloud_RW.png" />
	</nav>
	<div class="menuBG gradient">
		<div class='tabs' onmouseleave="menuLeave(this)">
			<ul class='horizontal'>
				<li id="home"><a href="../home/homeScreen.htm"
					onmouseover="menuDashBoard('home')"><i class="fa fa-home"
						style="font-size: 15px;"></i> HOME</a></li>
				<li id="acsettings"><a href="../acconfig/viewAcConfig.htm"
					onmouseover="menuDashBoard('acsettings')"><i
						class="fa fa-home" style="font-size: 15px;"></i> AC Settings</a></li>
				<li id="visualization"><a href="../stats/viewVisualization.htm"
					onmouseover="menuDashBoard('visualization')"><i
						class="fa fa-bar-chart" style="font-size: 15px;"></i>
						Visualization</a></li>
				<li id="notification"><a
					href="../notification/viewNotification.htm"
					onmouseover="menuDashBoard('notification')"><i
						class="fa fa-bell" style="font-size: 15px;"></i> Notification</a></li>
				<li id="schedule"><a href="../schedule/viewSchedule.htm"
					onmouseover="menuDashBoard('schedule')"><i
						class="fa fa-calendar" style="font-size: 15px;"></i> Schedule</a></li>
				<li id="settings"><a href="#"
					onmouseover="menuDashBoard('settings')"><i class="fa fa-cogs"
						style="font-size: 15px;"></i> System Settings</a></li>
				<li id="user"><a href="../notification/viewAccount.htm"
					onmouseover="menuDashBoard('user')"><i class="fa fa-user"
						style="font-size: 15px;"></i> User Account</a></li>
			</ul>
		</div>
	</div>
</div -->
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<nav class="navbar navbar-header1 navbar-static-top gradient">
	<div class="container-fuild">
		<div class="navbar-header">
			<a class="navbar-brand" href="../home/homeScreen.htm" style="padding-top:25px;padding-left: 50px;"><img
				alt="Panasonic Smart Cloud"
				src="../assets/img/PanasonicAC_SmartCloud_logo.png" /></a>
		</div>
		<ul class="nav navbar-nav navbar-right userinfo">
			<li class="dropdown"><a href="#" class="dropdown-toggle" style="margin-right:30px;"
				data-toggle="dropdown" role="button" aria-expanded="false"><c:out value="${sessionInfo.loginId}" />, <c:out value="${sessionInfo.userRole}" /> <span class="caret"></span>
			</a>
				<ul class="dropdown-menu">
					<li><a href="../login/logout.htm"><spring:message code="label.logout"/></a></li>
				</ul></li>
		</ul>
	</div>
</nav>
<nav class="navbar navbar-header2 navbar-static-top gradient" style="margin-bottom:10px;">
	<div class="container-fuild">
		<!-- div class="navbar-header">
			<button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
				data-target=".tabs" aria-expanded="false" aria-controls="navbar">
				<span class="sr-only">Toggle navigation</span> <span
					class="icon-bar"></span> <span class="icon-bar"></span> <span
					class="icon-bar"></span>
			</button>
		</div -->
		<div class='tabs' onmouseleave="menuLeave(this)">
			<ul class='horizontal'>
				<c:forEach items="${sessionInfo.permissionsList}" var="permission">
				<!-- this permission varibale represents the PermissionVO class object-->
					<c:choose>
				<c:when test="${permission.permissionName == 'navi-home'}"> 	
					<li style="width:14%" id="home">
						<a href="../home/homeScreen.htm" onmouseover="menuDashBoard('home')">
							<i class="fa fa-home" style="font-size: 25px;"></i> <spring:message code="label.home"/>
						</a>
					</li>
				</c:when>
				<c:when test="${permission.permissionName == 'navi-AC Settings'}"> 	
					<li style="width:14%" id="acsettings">
						<a href="../acconfig/viewAcConfig.htm" onmouseleave="menuLeave('acsettings')" onmouseover="menuDashBoard('acsettings')">
							<span class="menuAcSettings"></span> <spring:message code="label.ACSeetings"/>
						</a>
					</li>
				</c:when>
				<c:when test="${permission.permissionName == 'navi-Visualisation'}"> 	
					<li style="width:14%" id="visualization">
						<a href="../stats/viewVisualization.htm" onmouseover="menuDashBoard('visualization')">
							<i class="fa fa-bar-chart" style="font-size: 25px;"></i> <spring:message code="label.visualization"/>
						</a>
					</li>
				</c:when>
				<c:when test="${permission.permissionName == 'navi-Notification'}"> 	
					<li style="width:14%" id="notification">
						<a href="../notification/viewNotification.htm" onmouseover="menuDashBoard('notification')">
							<i class="fa fa-bell" style="font-size: 25px;"></i> <spring:message code="label.notification"/>
						</a>
					</li>
				</c:when>
				<c:when test="${permission.permissionName == 'navi-User Account'}"> 	
					<li style="width:14%" id="user">
						<a href="../usermanagement/viewAccount.htm" onmouseover="menuDashBoard('user')">
							<i class="fa fa-user" style="font-size: 25px;"></i> <spring:message code="label.useraccount"/>
						</a>
					</li>
					<li style="width:14%" id="schedule">
						<a href="../schedule/viewSchedule.htm"  onmouseover="menuDashBoard('schedule')">
							<i class="fa fa-cogs" style="font-size: 25px;"></i> Schedule
						</a>
					</li>
				</c:when>
				<c:when test="${permission.permissionName == 'navi-System Settings'}"> 	
					<li style="width:14%" id="settings">
						<a href="../co2Factor/viewSettings.htm"  onmouseover="menuDashBoard('settings')">
							<i class="fa fa-cogs" style="font-size: 25px;"></i> <spring:message code="label.systemsettings"/>
						</a>
					</li>
				</c:when>
				<c:when test="${permission.permissionName == 'navi-System Op.'}"> 	
					<li style="width:14%" id="settings_op">
						<a href="../cust_data/view_cust_data.htm" onmouseover="menuDashBoard('settings_op')">
							<i class="fa fa-cogs" style="font-size: 25px;"></i> <spring:message code="label.systemsettings"/>
						</a>
					</li>
				</c:when>
				<c:when test="${permission.permissionName == 'navi-CA Installation'}"> 	
					<li style="width:14%" id="ca_installtion">
						<a href="../ca_data/viewCa.htm" onmouseover="menuDashBoard('ca_installtion')">
							<i class="fa fa-cogs" style="font-size: 25px;"></i> <spring:message code="label.cai"/>	
						</a>
					</li>
				</c:when>
				</c:choose> 
				</c:forEach>
				<!-- li class="col-xs-2" id="schedule">
					<a href="../schedule/viewSchedule.htm" onmouseover="menuDashBoard('schedule')">
						<i class="fa fa-calendar" style="font-size: 15px;"></i> Schedule
					</a>
				</li -->
				<!-- li class="col-xs-2 dropdown" id="settings">
					<a href="#" class="dropdown-toogle" data-toggle="dropdown" onmouseover="menuDashBoard('settings')">
						<i class="fa fa-ellipsis-v" style="font-size: 15px;"></i> System Settings
					</a>
					<ul class="dropdown-menu">
						<li id="group">
							<a href="#" onmouseover="menuDashBoard('group')">
								<i class="fa fa-cogs" style="font-size: 15px;"></i> Group Display
							</a>
						</li>
						<li id="config">
							<a href="#" onmouseover="menuDashBoard('config')">
								<i class="fa fa-user" style="font-size: 15px;"></i> System Configuration
							</a>
						</li>
						<li id="user">
							<a href="../notification/viewAccount.htm" onmouseover="menuDashBoard('user')">
								<i class="fa fa-user" style="font-size: 15px;"></i> User Account
							</a>
						</li>
					</ul>
				</li -->
			</ul>
		</div>
	</div>
</nav>
<%@ include file="../../../assets/template/common/bizalert.jsp" %>