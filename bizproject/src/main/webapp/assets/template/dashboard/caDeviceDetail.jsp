

<link href="../assets/css/plugins/jPaginate/css/style.css" rel="stylesheet"/>
<link href="../assets/css/acconfiguration.css" rel="stylesheet"> 
<script src="../assets/js/util.js"></script>
	<!-- 	pagination -->
<script src="../assets/js/plugins/jPaginate/jquery.paginate.js"></script>
<!-- File Download -->
<script src="../assets/js/plugins/FileSaver/Blob.js"></script>
<script src="../assets/js/plugins/FileSaver/FileSaver.min.js"></script>
<script src="../assets/js/plugins/FileSaver/FileSaverService.js"></script>


	<div class="row animated fadeInDown">
		<div class="col-md-12" style="padding:0px;">
			<div class="ibox float-e-margins" style="margin-bottom: 0px;">
				<div class="ibox-title ibox-title-curves2"
					style="padding-bottom: 0px;">
					<div class="wrapper wrapper-content animated fadeInDown" id="inner" style="
    padding-left: 0px;
    padding-right: 0px;
">
						<div class="row">
							<ul class="nav nav-tabs" style="margin-left: 15px">
								<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-AC Settings/IDU Details'}"> 	
										<li class="active "><a id="acDetailTabLink" class="greyGradient bizTab" data-toggle="tab" href="#acDetailTab"><spring:message code="label.idudetails"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-AC Settings/ODU Details'}"> 	
										<li class=""><a id="acODUDetailTabLink" class="greyGradient bizTab" data-toggle="tab" href="#acODUDetailTab"><spring:message code="label.odudetails"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-AC Settings/Cloud Adapter Details'}"> 
										<li class=""><a id="acCADetailTabLink" class="greyGradient bizTab" data-toggle="tab" href="#acCADetailTab"><spring:message code="label.cadetails"/></a></li>
									</c:when>
									</c:choose>
								</c:forEach>
							</ul>

							<div class="tab-content">
							<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<c:choose>
								<c:when test="${permission.permissionName == 'tab-AC Settings/IDU Details'}"> 	
									<div id="acDetailTab" class="tab-pane active">
										<div class="panel-body no-padding">
											<%@ include file="caAcMonitoring.jsp"%>
										</div>
									</div>
								</c:when>
								<c:when test="${permission.permissionName == 'tab-AC Settings/ODU Details'}"> 	
									<div id="acODUDetailTab" class="tab-pane">
										<div class="panel-body no-padding">
											<%@ include file="caODUDetail.jsp"%>
										</div>
									</div>
								</c:when>
								<c:when test="${permission.permissionName == 'tab-AC Settings/Cloud Adapter Details'}"> 
									<div id="acCADetailTab" class="tab-pane">
										<div class="panel-body no-padding">
											<%@ include file="acCADetail.jsp"%>
										</div>
									</div>
								</c:when>
								</c:choose>
								</c:forEach>
							</div>

						</div>
						<!-- 	<div class="row">  -->
						<!-- 		<div id = "floorMapFrame" class="col-lg-12"> -->
						<!-- 			<div class="ibox float-e-margins"> -->
						<!-- 				<div class="ibox-title font-bold ibox-title-curve"> -->
						<!-- 					Map -->
						<!-- 				</div> -->
						<!-- 				<div class="ibox-content no-padding ibox-content-curve"> -->
						<!-- 					<div id="map" style="height: 263px;"></div> -->
						<!-- 				</div> -->
						<!-- 			</div> -->
						<!-- 		</div> -->
						<!-- 	</div> -->

					</div>
				</div>
			</div>
		</div>

	</div>

