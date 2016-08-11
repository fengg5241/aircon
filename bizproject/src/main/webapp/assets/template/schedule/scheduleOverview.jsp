<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="col-lg-12" id="air">
	<div class="ibox float-e-margins">
<!-- 		<div class="ibox-content ibox-content-curve"> -->
<!-- 			<div class="table-responsive"> -->
				<table id= "scheduleOverviewTable" width="100%" class="table table-striped table-hover text-center bizTable complexHeaderTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center acDetailHeaderCheckbox"></th>
							<th class="text-center acDetailSite"><spring:message code="label.site"/> Name</th>
							<th class="text-center acDetailSite">Top Site</th>
							<th class="text-center" style="width:10%"><p id="scheduleDateMon"></p>Mon</th>
							<th class="text-center "  style="width:10%"><p id="scheduleDateTue"></p>Tue</th>
							<th class="text-center "  style="width:10%"><p id="scheduleDateWed"></p>Wed</th>
							<th class="text-center "  style="width:10%"><p id="scheduleDateThu"></p>Thu</th>
							<th class="text-center "  style="width:10%"><p id="scheduleDateFri"></p>Fri</th>
							<th class="text-center "  style="width:10%"><p id="scheduleDateSat"></p>Sat</th>
							<th class="text-center "  style="width:10%"><p id="scheduleDateSun"></p>Sun</th>
						</tr>
					</thead>
					<tbody id="scheduleOverviewTbody">
					</tbody>
				</table>
				<div id="scheduleOverviewButtonGroup" class="text-right whiteBackground" style="margin-bottom:10px;">
					<button id = "copyButton" type="button" class="btn bizButton greyGradient">Copy &nbsp;<span class="fa fa-caret-right"></span></button>
					<button id = "pasteButton" type="button" class="btn bizButton greyGradient">Paste &nbsp;<span class="fa fa-caret-right"></span></button>
					<button id = "editPlanButton" type="button"  data-toggle="modal" data-target="#editPlanModal" class="btn bizButton greyGradient">Edit plan &nbsp;<span class="fa fa-caret-right"></span></button>
				</div>
<!-- 			</div> -->
<!-- 		</div> -->
	</div>
</div>
<div id ="#editPlanModalDiv" class="hide">
	<%@ include file="editPlanModal.jsp"%>
</div>

<!-- prohibition popups -->
<div id="prohibitionPopup" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 id = "bizalertTitle" class="modal-title text-center"></h4>
      </div>
      <div class="modal-body text-center">
        <p>Prohibition Legends:</p>
        <table style="width:100%">
        	<tr>
        		<td width="33%" style="text-align:left">1: Operation.</td><td width="33%"  style="text-align:left">2: Set Temperature.</td><td width="33%" style="text-align:left">3: Mode.</td>
        	</tr>
        	<tr>
        		<td style="text-align:left">4: Fan Speed.</td><td style="text-align:left">5: Flap.</td><td style="text-align:left">6: Energy Savings.</td>
        	</tr>
        </table>
      </div>
      <div class="modal-footer text-center">
        <button id = "bizalertCloseButton" type="button" class="btn bizButton greyGradient" data-dismiss="modal" style="margin:0"><spring:message code="label.oktitle"/> &nbsp;<span class="fa fa-caret-right"></span></button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%-- <%@ include file="operationLog.jsp"%> --%>
