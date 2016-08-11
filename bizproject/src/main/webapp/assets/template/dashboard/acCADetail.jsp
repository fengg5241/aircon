<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-lg-12" id="air">
	<div class="ibox float-e-margins">
<!-- 		<div class="ibox-content ibox-content-curve"> -->
<!-- 			<div class="table-responsive"> -->
				<table id= "CADetailTable" width="100%" class="table table-striped table-hover text-center bizTable complexHeaderTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center acDetailHeaderCheckbox" rowspan="2"><input type="checkbox" class="bizCheckbox" id="caDetailHeaderCheckbox" /><label for="caDetailHeaderCheckbox"></label></th>
							<th class="text-center acDetailCus" rowspan="2"><spring:message code="label.custn"/></th>
							<th class="text-center acDetailSite" rowspan="2"><spring:message code="label.sitename"/></th>
							<th class="text-center acDetailCa" rowspan="2"><spring:message code="label.caid"/></th>
							<!-- CA Status -->
							<th class="text-center acDetailPulse1" colspan="4"><spring:message code="label.pulsemeter"/> 1</th>
							<th class="text-center acDetailPulse2" colspan="4"><spring:message code="label.pulsemeter"/> 2</th>
							<th class="text-center acDetailPulse3" colspan="4"><spring:message code="label.pulsemeter"/> 3</th>
							<th class="text-center acDetailCAStatus" rowspan="2"><spring:message code="label.alarmcode"/></th>

						</tr>
						
						<tr>
							<th class="text-center acDetailPulse1Id"><spring:message code="label.id"/></th>
							<th class="text-center acDetailPulse1Type"><spring:message code="label.type"/></th>
							<th class="text-center acDetailPulse1Value"><spring:message code="label.value"/></th>
							<th class="text-center acDetailPulse1Factor"><spring:message code="label.factor"/></th>
							<th class="text-center acDetailPulse2Id"><spring:message code="label.id"/></th>
							<th class="text-center acDetailPulse2Type"><spring:message code="label.type"/></th>
							<th class="text-center acDetailPulse2Value"><spring:message code="label.value"/></th>
							<th class="text-center acDetailPulse2Factor"><spring:message code="label.factor"/></th>
							<th class="text-center acDetailPulse3Id"><spring:message code="label.id"/></th>
							<th class="text-center acDetailPulse3Type"><spring:message code="label.type"/></th>
							<th class="text-center acDetailPulse3Value"><spring:message code="label.value"/></th>
							<th class="text-center acDetailPulse3Factor"><spring:message code="label.factor"/></th>
						</tr>
						
					</thead>
					<tbody id="acCADetailTbody">
					</tbody>
				</table>
				<div id="acDetailButtonGroup" class="text-right whiteBackground" style="margin-bottom:10px;">

					<button id = "caControlButton" type="button" class="btn bizButton greyGradient"><spring:message code="label.download"/> &nbsp;<span class="fa fa-caret-right"></span></button>
				</div>
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
</div>
 
    
<div id ="acControlFileDiv" class="hide">
	<%@ include file="acControl.jsp"%>
</div>

<%@ include file="operationLog.jsp"%>
