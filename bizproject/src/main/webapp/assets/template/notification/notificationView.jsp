<div class="wrapper wrapper-content">
	<div class="row animated fadeInDown">
		<div class="col-lg-12">
			<div class="ibox float-e-margins" style="margin-bottom: 0px;">
				<div class="ibox-title ibox-title-curves2"
					style="padding-bottom: 0px;">
					<div class="wrapper wrapper-content animated fadeInDown" id="inner">
						<div class="row">
							<ul class="nav nav-tabs col-xs-12">
								<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-Notification/Notification Details'}"> 	
										<li class="active"><a id="acDetailTabLink"
											class="greyGradient bizTab" data-toggle="tab"
											href="#acDetailTab"><spring:message code="label.notification.detail"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Notification/Notification Overview'}"> 	
										<li><a id="acODUDetailTabLink" class="greyGradient bizTab"
											data-toggle="tab" href="#acODUDetailTab"><spring:message code="label.notification.overview"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Notification/Mainenance Settings'}"> 
										<li><a id="acMaintenanceTabLink"
											class="greyGradient bizTab" data-toggle="tab"
											href="#acMaintenanceTab"><spring:message code="label.notification.maintenance"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Notification/Map View'}"> 
										<li><a id="acMapTabLink" class="greyGradient bizTab"
											data-toggle="tab" href="#acMapTab" style="text-align: center"><spring:message code="label.mapview"/></a></li>
									</c:when>
									</c:choose>
								</c:forEach>
								<!-- <li class="col-xs-2 nopadding"><a data-toggle="tab"
									class="greyGradient bizTab" href="#notificationTab">Notification
										Settings</a></li> -->
							</ul>

							<div class="tab-content">
								<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-Notification/Notification Details'}"> 	
										<div id="acDetailTab" class="tab-pane active">
											<div class="panel-body no-padding">
												<%@ include file="notificationTable.jsp"%>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Notification/Notification Overview'}"> 	
										<div id="acODUDetailTab" class="tab-pane">
											<div class="panel-body no-padding">
												<%@ include file="notificationOverview.jsp"%>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Notification/Mainenance Settings'}"> 
										<div id="acMaintenanceTab" class="tab-pane">
											<div class="panel-body no-padding">
												<%@ include file="maintenanceSetting.jsp"%>
											</div>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Notification/Map View'}"> 
										<div id="acMapTab" class="tab-pane">
											<div class="panel-body no-padding">
												<div class="row">
													<div id="floorMapFrame" class="col-lg-12">
														<div class="float-e-margins" style="margin-bottom:10px;">
															<div class="ibox-title font-bold ibox-title-curve">
																<select class="btns btn-mini" id="groupSelects">
																</select>
															</div>
															<div class="ibox-content no-padding ibox-content-curve">
																<div id="map" style="height: 764px;"></div>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</c:when>
									</c:choose>
								</c:forEach>
								<%-- <div id="notificationTab" class="tab-pane">
									<div class="panel-body">
										<%@ include file="notificationSetting.jsp"%>
									</div>
								</div> --%>
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



<%-- <div class="wrapper wrapper-content">
	<div class="row animated fadeInDown">
		<div class="col-xs-12">
			<div class="ibox float-e-margins" style="margin-bottom: 0px;">
				<div class="ibox-title ibox-title-curves2"
					style="padding-bottom: 0px;">
					<div class="wrapper wrapper-content animated fadeInDown" id="inner">
						<div class="row">
							<ul class="nav nav-tabs col-xs-12">
								<li class="active col-xs-2 nopadding"><a data-toggle="tab" id="notificationDetailLink"
									class="greyGradient bizTab" href="#notificationDetailTab">Notification
										Detail</a></li>
								<li class="col-xs-2 nopadding"><a data-toggle="tab" id="overviewTabLink"
									class="greyGradient bizTab" href="#overviewTab">Notification
										Overview</a></li>
								<li class="col-xs-2 nopadding"><a id="notificationmaplink" data-toggle="tab" 
									class="greyGradient bizTab" href="#notificationmapTab"
									style="text-align: center">Map View</a></li>
								<li class="col-xs-2 nopadding"><a data-toggle="tab" id="maintenanceTabLink"
									class="greyGradient bizTab" href="#maintenanceTab">Maintenance
										Settings</a></li>
								<li class="col-xs-2 nopadding"><a data-toggle="tab" id="notificationTabLink"
									class="greyGradient bizTab" href="#notificationTab">Notification
										Settings</a></li>
							</ul>
							<div class="tab-content">
								<div id="notificationDetailTab" class="tab-pane active">
									<div class="panel-body">
										<div class="row">
											<%@ include file="notificationTable.jsp"%>
										</div>
									</div>
								</div>
								<div id="overviewTab" class="tab-pane">
									<div class="panel-body">
										<%@ include file="notificationOverview.jsp"%>
									</div>
								</div>
								<div id="notificationmapTab" class="tab-pane">
									<div class="panel-body no-padding">
										<div class="row">
											<div id="floorMapFrame" class="col-lg-12">
												<div class="ibox float-e-margins">
													<div class="ibox-title font-bold ibox-title-curve">
														<select class="btns btn-mini" id="selects">
														</select>
													</div>
													<div class="ibox-content no-padding ibox-content-curve">
														<div id="map" style="height: 650px;"></div>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
								<div id="maintenanceTab" class="tab-pane">
									<%@ include file="maintenanceSetting.jsp"%>
								</div>
								<div id="notificationTab" class="tab-pane">
									<div class="panel-body">
										<%@ include file="notificationSetting.jsp"%>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div> --%>