<!-- <div class="userscrren1panel panel-default updatepanel"> -->
<div class="panel-default white-bg">
<!-- 	<div class="userscrren1panel-body"> -->
	<div class="">
		<div class="row">
			<div class="col-xs-4"></div>
			<div class="col-xs-4">
				<form role="form" class="form-group">
					<label><spring:message code="label.bil"/>:</label> 
					<div class="input-daterange input-group date" id="datepicker">
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						<input id="cutOffStartDate" type="text" class="input-xs form-control" name="group_start"/> 
						<span class="input-group-addon"><spring:message code="label.to"/></span>
						<input id="cutOffEndDate" type="text" class="input-xs form-control" name="group_end"/>
					</div>
				</form>
			</div>
			
			<div class="col-xs-4"></div>
		</div>
	 	<div class="text-center">
			<button id = "cutOffRequestButton" type="button" class="btn bizButton greyGradient"><spring:message code="label.req"/> &nbsp;<span class="fa fa-caret-right"></span></button>
		</div>
	
	</div>
</div>
<div class="bizBlackSeparatedBar text-center"><strong><spring:message code="label.bill"/></strong></div>
<div class="panel-default dialogGreyBackground">
	<div class="row">
		<div class="col-xs-1"></div>
		<div class="col-xs-10">
			<div class="ibox float-e-margins">
				<table id= "cutOffTable" width="100%" class="table table-striped table-hover text-center bizTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center"  width="12%"><spring:message code="label.trasn"/></th>
							<th class="text-center"  width="13%"><spring:message code="label.alsite"/></th>
							<th class="text-center"  width="12%"><spring:message code="label.res"/></th>
							<th class="text-center"  width="17%"><spring:message code="label.trs"/></th>
							<th class="text-center" width="11%"><spring:message code="label.trsst"/></th>
							<th class="text-center" width="13%"><spring:message code="label.trsend"/></th>
							<th class="text-center" width="9%"><spring:message code="label.trspow"/></th>
							<th class="text-center" width="9%"><spring:message code="label.trspdw"/></th>
						</tr>
					</thead>
					<tbody id="cutOffTableTbody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-xs-1"></div>
	</div>
</div>


