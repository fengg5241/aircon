<div class="wrapper wrapper-content">
	<div class="row animated">
		<div class="col-xs-12">
			<div class="ibox float-e-margins" style="margin-bottom: 0px;">
				<div class="ibox-title ibox-title-curves2"
					style="padding-bottom: 0px;">
					<div class="wrapper wrapper-content animated" id="inner">
						<div class="row">
							<ul class="nav nav-tabs col-xs-12" style="border-bottom: #cccccc">
								<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-User Account/new User Registration'}"> 	
										<li class="active nopadding"><a data-toggle="tab"
											class="greyGradient bizTab" href="#newusertab" id="newusertabs"
											style="text-align: center"><spring:message code="label.user.newuser"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-User Account/Update User'}"> 	
										<li class=" nopadding"><a data-toggle="tab"
											class="greyGradient bizTab" id="updateusertabs"
											href="#updateusertab" style="text-align: center;width:247px;"><spring:message code="label.user.updateuser"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-User Account/User list'}"> 
										<li class=" nopadding"><a data-toggle="tab"
											class="greyGradient bizTab" href="#userlisttab" id="userlisttabs"
											style="text-align: center;width:247px;"><spring:message code="label.user.userlist"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-User Account/Role list'}"> 
										<li class=" nopadding"><a data-toggle="tab"
											class="greyGradient bizTab" href="#rolelisttab" id="roletabs"
											style="text-align: center;width:247px;"><spring:message code="label.user.rolelist"/></a></li>
									</c:when>
									</c:choose>
								</c:forEach>
							</ul>
							<div class="tab-content">
								<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-User Account/new User Registration'}"> 	
										<div id="newusertab" class="tab-pane active">
											<div class="panel-body">
												<div class="row">
													<%@ include file="roleregistraion.jsp"%>
												</div>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-User Account/Update User'}"> 	
										<div id="updateusertab" class="tab-pane">
											<div class="panel-body">
												<div class="row">
													<%@ include file="updateuser.jsp"%>
												</div>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-User Account/User list'}"> 
										<div id="userlisttab" class="tab-pane">
											<%@ include file="userlist.jsp"%>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-User Account/Role list'}"> 
										<div id="rolelisttab" class="tab-pane">
											<div class="panel-body">
												<%@ include file="rolelist.jsp"%>
											</div>
										</div>
									</c:when>
									</c:choose>
								</c:forEach>
							</div>
						</div>
						<!-- <div class="col-xs-12 text-center">
							<p class="m-t">
								<small><spring:message code="label.copy"/> &copy; 2016</small>
							</p>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
</div>