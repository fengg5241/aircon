<div class="wrapper wrapper-content">
	<div class="row animated fadeInDown">
		<div class="col-xs-12">
			<div class="ibox float-e-margins" style="margin-bottom: 0px;">
				<div class="ibox-title ibox-title-curves2"
					style="padding-bottom: 0px;">
					<div class="wrapper wrapper-content animated fadeInDown" id="inner">
						<div class="row">
							<ul class="nav nav-tabs">
								<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-Visualisation/Statistics(Group)'}"> 	
										<li class="active"><a data-toggle="tab" class="greyGradient bizTab" href="#statsGroup"><spring:message code="label.stssatisticsgroup"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Visualisation/Statistics(Refrigerant)'}"> 	
										<li><a data-toggle="tab" class="greyGradient bizTab" href="#statsRef"><spring:message code="label.refrigerantcircuit"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Visualisation/Statistics(Air Con)'}"> 
										<li><a data-toggle="tab" class="greyGradient bizTab" href="#statsIdu"><spring:message code="label.stssatisticsaircon"/></a></li>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Visualisation/Eiiiciency Ranking'}"> 
										<li><a data-toggle="tab" class="greyGradient bizTab" href="#efficiencyRanking"><spring:message code="label.efficiencyranking"/></a></li>
									</c:when>
									</c:choose>
								</c:forEach>
							</ul>
							<div class="tab-content">
								<c:forEach items="${sessionInfo.permissionsList}" var="permission">
								<!-- this permission varibale represents the PermissionVO class object-->
									<c:choose>
									<c:when test="${permission.permissionName == 'tab-Visualisation/Statistics(Group)'}"> 	
										<div id="statsGroup" class="tab-pane active">
											<%@ include file="stats_group.jsp"%>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Visualisation/Statistics(Refrigerant)'}"> 	
										<div id="statsRef" class="tab-pane">
											<%@ include file="stats_refrigerant.jsp"%>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Visualisation/Statistics(Air Con)'}"> 
										<div id="statsIdu" class="tab-pane">
											<%@ include file="stats_idu.jsp"%>
										</div>
									</c:when>
									<c:when test="${permission.permissionName == 'tab-Visualisation/Eiiiciency Ranking'}"> 
										<div id="efficiencyRanking" class="tab-pane">
											<%@ include file="stats_efficiency_ranking.jsp"%>
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