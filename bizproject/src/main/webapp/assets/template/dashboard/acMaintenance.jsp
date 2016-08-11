<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-lg-12" id="air">
	<div class="ibox float-e-margins">
<!-- 		<div class="ibox-content ibox-content-curve"> -->
<!-- 			<div class="table-responsive"> -->
				<table id= "MainDetailTable" width="100%" class="table table-striped table-hover text-center bizTable complexHeaderTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center acDetailHeaderCheckbox" rowspan="2"></th>
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
					<tbody id="acMainDetailTbody">
					</tbody>
				</table>
				<div id="maintenceStatusUpdateDiv" class="text-center whiteBackground" style="margin-bottom:10px;">
					<p class="blackBackground bold"><spring:message code="label.man"/></p>
					<table width="100%" style="box-sizing:border-box">
						<thead>
							<th></th>
							<th class="bold paddingleft50 text-left"><span class="text-center"><spring:message code="label.ne"/></br>&nbsp;&nbsp;&nbsp;<spring:message code="label.cd"/></span></th>
						</thead>
						<tbody id="acMainCompressorTbody">
							<tr>
								<td  width="50%" class="paddingright50">
									<label for="ckComp1" class="floatright"><spring:message code="label.com"/>1</label><input id="ckComp1" class='bizCheckbox' type='checkbox' data-mainId='1'/><label for="ckComp1" class="floatright"></label>
								</td>
								<td width="50%" class="text-left paddingleft80">
									<Span id="comp1Value">20000</Span>
								</td>
							</tr>
							<tr>
								<td width="50%" class="paddingright8">
									<label for="ckComp2" class="floatright"><spring:message code="label.com"/>2</label><input id="ckComp2" class='bizCheckbox' type='checkbox' data-mainId='2'/><label for="ckComp2" class="floatright"></label>
								</td>
								<td width="50%" class="text-left paddingleft80">
									<Span id="comp2Value">20000</Span>
								</td>
							</tr>
							<tr>
								<td width="50%" class="paddingright50">
									<label for="ckComp3" class="floatright"><spring:message code="label.com"/>3</label><input id="ckComp3" class='bizCheckbox' type='checkbox' data-mainId='3'/><label for="ckComp3" class="floatright"></label>
								</td>
								<td  width="50%" class="text-left paddingleft80">
									<Span id="comp3Value">20000</Span>
								</td>
							</tr>
						</tbody>
					</table>
				<div id="acMainDetailButtonGroup" class="whiteBackground" style="margin-bottom:10px;">
					<button id = "resetConfirmButton" style="width:150px;" type="button" class="btn bizButton greyGradient"><spring:message code="label.cnf"/>&nbsp;<span class="fa fa-caret-right"></span></button>
				</div>
				</div>
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
</div>
