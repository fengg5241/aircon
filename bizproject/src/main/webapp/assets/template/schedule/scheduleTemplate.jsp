<div class="wrapper wrapper-content">
	<div class="row animated fadeInDown">
		<div class="col-lg-12">
			<div class="ibox float-e-margins" style="margin-bottom: 0px;">
				<div class="ibox-title ibox-title-curves2"
					style="padding-bottom: 0px;">
					<div class="wrapper wrapper-content animated fadeInDown" id="inner">
						<div class="row">
							<ul class="nav nav-tabs">
										<li class="active "><a id="acDetailTabLink" class="greyGradient bizTab" data-toggle="tab" href="#acDetailTab">Mode Directory</a></li>
										<li class=""><a id="scheduleOverviewTabLink" class="greyGradient bizTab" data-toggle="tab" href="#scheduleOverviewTab">Daily Schedule Overview</a></li>
										<li class=""><a id="acCADetailTabLink" class="greyGradient bizTab" data-toggle="tab" href="#acCADetailTab">Schedule Summary</a></li>
										<li class=""><a id="acMaintenanceTabLink" class="greyGradient bizTab" data-toggle="tab" href="#acMaintenanceTab">Time Settings</a></li>
							</ul>

							<div class="tab-content">
									<div id="acDetailTab" class="tab-pane ">
										<div class="panel-body no-padding">
<%-- 											<%@ include file="acMonitoring.jsp"%> --%>
										</div>
									</div>
									<div id="scheduleOverviewTab" class="tab-pane active">
										<div class="panel-body no-padding">
											<%@ include file="scheduleOverview.jsp"%>
										</div>
									</div>
									<div id="acCADetailTab" class="tab-pane">
										<div class="panel-body no-padding">
<%-- 											<%@ include file="acCADetail.jsp"%> --%>
										</div>
									</div>
									<div id="acMaintenanceTab" class="tab-pane">
										<div class="panel-body no-padding">
<%-- 											<%@ include file="acMaintenance.jsp"%> --%>
										</div>
									</div>
									<div id="acMapTab" class="tab-pane">
										<div class="panel-body no-padding">
											<div class="row">
												<div id="floorMapFrame" class="col-lg-12" style="margin-bottom:10px;">
													<div class=" float-e-margins">
														<div class="ibox-title font-bold ibox-title-curve">
															<select class="btns btn-mini" id="groupSelects" style="font-size: 14px;">
															</select>
														</div>
														<div class="ibox-content no-padding ibox-content-curve">
															<div id="map" style="height: 774px;"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>

	</div>
</div>
