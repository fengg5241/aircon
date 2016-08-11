<div class="modal fade col-lg-12" id="operationLogModal">
  <div class="modal-dialog modal-lg">
    <div class="modal-content bizModal">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title"><spring:message code="label.operationlog"/></h3>
      </div>
      <div class="modal-body">
        <div class="col-lg-12 nopadding">
<!--        		<div class="form-group"> -->
<!-- 				<div class="input-daterange input-group text-right" -->
<!-- 					id="operationLog_datepicker"> -->
<!-- 					<span class="input-group-addon">Select Log Period</span> -->
<!-- 					<span class="input-group-addon"><i class="fa fa-calendar" -->
<!-- 						title="Select Dates"></i></span> <input type="text" -->
<!-- 						class="input-sm form-control" name="start" /> -->
<!-- 					<span class="input-group-addon"><i class="fa fa-calendar" -->
<!-- 						title="Select Dates"></i></span>  <input type="text" -->
<!-- 						class="input-sm form-control" name="end"/> -->
<!-- 				</div> -->
<!-- 			</div> -->
				
<!-- 			<div class="table-responsive panel panel-default"> -->
				<table id= "operLogTable" width="100%"  class="table table-striped table-hover text-center bizTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center acOperLogSite" ><spring:message code="label.site"/></th>
							<th class="text-center acOperLogLoc"><spring:message code="label.location"/></th>
							
							<th class="text-center acOperLogName"><spring:message code="label.iduname"/></th>
							<th class="text-center acOperLogStatus"><spring:message code="label.onoffstatus"/></th>
							<!-- temp -->
							<th class="text-center acOperLogTemp"><img src="../assets/img/Temp-table.png" class="small-icon" /></th>
							<!-- mode -->
							<th class="text-center acOperLogMode"><img src="../assets/img/Mode-icon.png" class="small-icon" /></th>
							<!-- fanSpeed -->
							<th class="text-center acOperLogFanSpeed"><img src="../assets/img/Fan-Mode-Table.png" class="small-icon" /></th>
							<!-- flap -->
							<th class="text-center acOperLogWind"><img src="../assets/img/Flap_Table.png" class="small-icon" /></th>
							<!-- energy saving -->
							<th class="text-center acOperLogESaving"><img src="../assets/img/EnergySaving_icon.png" class="small-icon" /></th>
							<!-- ECONAVI -->
							<th class="text-center acOperLogProhibition"><spring:message code="label.prohibitionrc"/></th>
							<!-- CA Status -->
							<th class="text-center acOperLogDate"><spring:message code="label.datetime"/></th>
							<th class="text-center acOperLogUserId"><spring:message code="label.userid"/></th>
						</tr>
					</thead>
					
					<tbody id="acOperLogTbody">
					</tbody>
				</table>
				
<!-- 				<div style="height: 310px" id="acDetailScrollDiv1" class="panel-body"> -->
<!-- 			</div> -->
		</div>
		
		<div class="pull-right" style="width:100%;margin-top:20px;">
			<div id="opeartionLogPagination"></div>
        	<button type="button" class="btn bizButton greyGradient" id="oplogdownload"><spring:message code="label.downloads"/> &nbsp;<span class="fa fa-caret-right"></button>
      	</div>
      </div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
