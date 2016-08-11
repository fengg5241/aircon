<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-lg-12" id="air">
	<div class="ibox float-e-margins">
<!-- 		<div class="ibox-content ibox-content-curve"> -->
<!-- 			<div class="table-responsive"> -->
				<table id= "IDUDetailTable" width="100%" class="table table-striped table-hover text-center bizTable complexHeaderTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center acDetailHeaderCheckbox" rowspan="2"><input type="checkbox" class="bizCheckbox" id="acDetailHeaderCheckbox" /><label for="acDetailHeaderCheckbox"></label></th>
							<th class="text-center acDetailSite" rowspan="2"><spring:message code="label.site"/></th>
							<th class="text-center acDetailLoc" rowspan="2"><spring:message code="label.location"/></th>
							
							<th class="text-center acDetailName" rowspan="2"><spring:message code="label.iduname"/></th>
							<th class="text-center acDetailStatus" rowspan="2"><spring:message code="label.onoffstatus"/></th>
							<!-- temp -->
							<th class="text-center acDetailTemp" rowspan="2"><img src="../assets/img/Temp-table.png" class="small-icon" /></th>
							<!-- room temp -->
							<th class="text-center acDetailRoomTemp" rowspan="2"><img src="../assets/img/Room-temp-table.png" class="small-icon" /></th>
							<!-- mode -->
							<th class="text-center acDetailMode" rowspan="2"><img src="../assets/img/Mode-icon.png" class="small-icon" /></th>
							<!-- fanSpeed -->
							<th class="text-center acDetailFanSpeed" rowspan="2"><img src="../assets/img/Fan-Mode-Table.png" class="small-icon" /></th>
							<!-- flap -->
							<th class="text-center acDetailWind" rowspan="2"><img src="../assets/img/Flap_Table.png" class="small-icon" /></th>
							<!-- energy saving -->
							<th class="text-center acDetailESaving" rowspan="2"><img src="../assets/img/EnergySaving_icon.png" class="small-icon" /></th>
							<!-- ECONAVI -->
<!-- 							<th class="text-center acDetailEconavi">ECONAVI</th> -->
							<!-- CA Status -->
							<th class="text-center acDetailAlarmCode" rowspan="2"><spring:message code="label.alarmcode"/></th>
							<th class="text-center acDetailProhibitions" colspan="6"><spring:message code="label.prohibition"/></th>
							<th class="text-center acDetailPC" rowspan="2"><spring:message code="label.idutype"/></th>
							<th class="text-center acDetailCAStatus" rowspan="2"><spring:message code="label.castatus"/></th>
							
						</tr>
						
						<tr>
							<th class="text-center acDetailpro1"><a class="prohibitionTitle" href="javascript:void(0);" data-proname="Oper.">1</a></th>
							<th class="text-center acDetailpro2"><a class="prohibitionTitle" href="javascript:void(0);" data-proname="Set Temp.">2</a></th>
							<th class="text-center acDetailpro3"><a class="prohibitionTitle" href="javascript:void(0);" data-proname="Mode">3</a></th>
							<th class="text-center acDetailpro4"><a class="prohibitionTitle" href="javascript:void(0);" data-proname="Fan Speed">4</a></th>
							<th class="text-center acDetailpro5"><a class="prohibitionTitle" href="javascript:void(0);" data-proname="Flap">5</a></th>
							<th class="text-center acDetailpro6"><a class="prohibitionTitle" href="javascript:void(0);" data-proname="Energy Saving">6</a></th>
						
						</tr>
						
					</thead>
					<tbody id="acDetailTbody">
					</tbody>
				</table>
				<div id="acDetailButtonGroup" class="text-right whiteBackground" style="margin-bottom:10px;">

					<button id = "iduDownloadButton" type="button" class="btn bizButton greyGradient"><spring:message code="label.download"/> &nbsp;<span class="fa fa-caret-right"></span></button>
					<button id = "detailLog" type="button" class="btn bizButton greyGradient"><spring:message code="label.viewlog"/> &nbsp;<span class="fa fa-caret-right"></span></button>
				</div>
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
</div>

<%@ include file="operationLog.jsp"%>
