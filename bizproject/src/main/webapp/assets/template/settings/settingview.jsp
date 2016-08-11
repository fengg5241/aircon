<div class="wrapper wrapper-content">
	<div class="row animated fadeInDown">
		<div class="col-lg-12">
			<div class="ibox float-e-margins" style="border-bottom: 25px solid#cccccc;">
				<div class="ibox-title ibox-title-curves2"
					style="padding-bottom: 0px;">
					<div class="wrapper wrapper-content animated fadeInDown">
						<div class="row">
							<ul class="nav nav-tabs">
								<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-System Settings/System Configuration'}"> 	
										<li class="active"><a id="systemconfigTabLink"
											class="greyGradient bizTab" data-toggle="tab"
											href="#systemconfigTab"><spring:message code="label.sist"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Distribution Group Overview'}"> 	
										<li class=""><a id="disGroupOverviewLink"
											class="greyGradient bizTab" data-toggle="tab"
											href="#disGroupOverviewTab"><spring:message code="label.dsov"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Distribution Group Details'}"> 	
										<li class=""><a id="disGroupDetailsLink"
											class="greyGradient bizTab" data-toggle="tab"
											href="#disGroupDetailsTab"><spring:message code="label.dsdt"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Area Allocation'}"> 	
										<li class=""><a id="areaAllocationLink"
											class="greyGradient bizTab" data-toggle="tab"
											href="#areaAllocationTab"><spring:message code="label.ar"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Cut Off Request'}"> 	
										<li class=""><a id="cutOffRequestLink"
											class="greyGradient bizTab" data-toggle="tab"
											href="#cutOffRequestTab"><spring:message code="label.ct"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Customer Registration'}"> 	
										<li class=""><a id="custRegistrationLink"
											class="greyGradient bizTab" data-toggle="tab"
											href="#custRegistrationTab"><spring:message code="label.custreg"/></a></li>
									</c:when>
									</c:choose>
								</c:forEach>
							</ul>
							<div class="tab-content">
							<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-System Settings/System Configuration'}"> 	
										<div id="systemconfigTab" class="tab-pane active">
												<%@ include file="co2Factor.jsp"%>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Distribution Group Overview'}"> 	
										<div id="disGroupOverviewTab" class="tab-pane">
											<div class="panel-body no-padding">
												<%@ include file="distributionGroupOverview.jsp"%>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Distribution Group Details'}"> 	
										<div id="disGroupDetailsTab" class="tab-pane">
											<div class="panel-body no-padding">
												<%@ include file="distributionGroupDetails.jsp"%>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Area Allocation'}"> 	
										<div id="areaAllocationTab" class="tab-pane">
											<div class="panel-body no-padding">
												<%@ include file="areaallocation.jsp"%>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Cut Off Request'}"> 	
										<div id="cutOffRequestTab" class="tab-pane">
											<div class="panel-body no-padding">
												<%@ include file="cutOffRequest.jsp"%>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-System Settings/Customer Registration'}"> 
										<div id="custRegistrationTab" class="tab-pane">
											<div class="panel-body no-padding">
												<%@ include file="cust_data.jsp"%>
											</div>
										</div>
									</c:when>
									</c:choose>
								</c:forEach>
								</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	
</div>
</div>
