<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-lg-12" id="air">
	<div class="ibox float-e-margins">
<!-- 		<div class="ibox-content ibox-content-curve"> -->
<!-- 			<div class="table-responsive"> -->
				<table id= "ODUDetailTable" width="100%" class="table table-striped table-hover text-center bizTable complexHeaderTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center acDetailHeaderCheckbox" rowspan="2"><input type="checkbox" class="bizCheckbox" id="acOUDDetailHeaderCheckbox" /><label for="acOUDDetailHeaderCheckbox"></label></th>
							<th class="text-center acODUDetailSite" rowspan="2"><spring:message code="label.site"/></th>
							<th class="text-center acODUDetailSlink" rowspan="2"><spring:message code="label.slink"/></br> <spring:message code="label.address"/></th>
							<th class="text-center acODUDetailName" rowspan="2"><spring:message code="label.ODU"/></br> <spring:message code="label.name"/></th>
							<th class="text-center acODUDetailAlarmCode" rowspan="2"><spring:message code="label.alarm"/></br><spring:message code="label.code"/></th>
							<th class="text-center acODUDetailOutTemp" rowspan="2"><spring:message code="label.outdoor"/> <spring:message code="label.temp"/>.</th>
<!-- 							<th class="text-center acODUDetailDemand" rowspan="2"><spring:message code="label.demand"/></th> -->
<!-- 							<th class="text-center acODUDetailOilCheck" rowspan="2">GHP Oil</br> Check Countdown</th> -->
<!-- 							<th class="text-center acODUDetailEngineServ" rowspan="2">GHP Engine Service Countdown</th> -->
<!-- 							<th class="text-center acODUDetailPowerGen" rowspan="2">Power Generation</th> -->
							<th class="text-center acODUDetailMaintenance" colspan="3"><spring:message code="label.maintenance"/></th>
							<th class="text-center acODUDetailType" rowspan="2"><spring:message code="label.odutype"/></th>

						</tr>
						
						<tr>
							<th class="text-center acODUDetailComp1"><spring:message code="label.comp"/>1</th>
							<th class="text-center acODUDetailComp2"><spring:message code="label.comp"/>2</th>
							<th class="text-center acODUDetailComp3"><spring:message code="label.comp"/>3</th>
						</tr>
					</thead>
					<tbody id="acODUDetailTbody">
					</tbody>
				</table>
				<div id="acODUDetailButtonGroup" class="text-right whiteBackground" style="margin-bottom:10px;">
					<button id = "downODUButton" type="button" class="btn bizButton greyGradient"><spring:message code="label.downloadodulog"/> &nbsp;<span class="fa fa-caret-right"></span></button>
				</div>
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
</div>
