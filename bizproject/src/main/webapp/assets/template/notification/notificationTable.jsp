<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-lg-12" id="air">
	<div class="ibox float-e-margins">
		<table id="IDUDetailTable" width="100%"
			class="table table-striped table-hover text-center bizTable complexHeaderTable"
			style="margin-bottom: 0px">
			<thead>
				<tr class="greyGradient">
					<th class="text-center acDetailHeaderCheckbox"><input
						type="checkbox" class="bizCheckbox" id="acDetailHeaderCheckbox" /><label
						for="acDetailHeaderCheckbox"></label></th>
					<th class="text-center acDetailsCC" id="company" style="display:none;"><spring:message code="label.comps"/></th>																															
					<th class="text-center acDetailsPC"><spring:message code="label.site"/></th>
					<th class="text-center acDetailsName"><spring:message code="label.location"/></th>
					<th class="text-center acDetailsStatus"><spring:message code="label.devicename"/></th>
					<th class="text-center acDetailsTemp"><spring:message code="label.category"/></th>
					<th class="text-center acDetailsRoomTemp"><spring:message code="label.alarmcode"/></th>
					<th class="text-center acDetailsLoc"><spring:message code="label.notificationid"/></th>
					<th class="text-center acDetailsSite"><spring:message code="label.occurdate"/></th>
					<th class="text-center acDetailsWind"><spring:message code="label.fixeddate"/></th>					
					<th class="text-center acDetailsESaving"><spring:message code="label.status"/></th>
					<th class="text-center acDetailsViewMap"><spring:message code="label.viewmap"/></th>
				</tr>
			</thead>
			<tbody id="acDetailTbody">
			</tbody>
		</table>		
		<div id="notificationdetailsdownloads" class="text-right " style="margin-bottom:10px;">
			<button id="notificationdetailsdownload" type="button"
				class="btn bizButton greyGradient">
				<spring:message code="label.download"/> &nbsp;<span class="fa fa-caret-right"></span>
			</button></div>
	</div>
	<%@ include file="alarmDetailPopup.jsp"%>
</div>

