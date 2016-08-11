<!-- <div class="userscrren1panel panel-default updatepanel"> -->
<div class="panel-default white-bg">
<!-- 	<div class="userscrren1panel-body"> -->
	<div class="">
		<div class="row">
			<div class="col-xs-1"></div>
			<div class="col-xs-2">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.site"/>:</label> <select class="form-control"
							placeholder="Select site" name="area" id="areaSiteSelect">
						</select>
					</div>
				</form>
			</div>
			
			<div class="col-xs-2">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.distr"/>:</label> <select class="form-control"
							placeholder="Select Distribution Group" name="area" id="distributionGrouplist">
						</select>
					</div>
				</form>
			</div>
			<div class="col-xs-1"></div>
			<div class="col-xs-2">
				<form role="form" class="form-group">
					<div class="form-group">
						<button id = "applySearchButton" type="button" class="btn bizButton greyGradient"><spring:message code="label.selection"/> &nbsp;<span class="fa fa-caret-right"></span></button>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="bizBlackSeparatedBar text-center"><strong><spring:message code="label.some"/></strong></div>
<div class="panel-default dialogGreyBackground">
	<div class="row">
		<div class="col-xs-1"></div>
		<div class="col-xs-10">
			<div class="ibox float-e-margins">
				<table id= "areaDistributionGroupTable" width="100%" class="table table-striped table-hover text-center bizTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center"  width="15%"><spring:message code="label.iadr"/></th>
							<th class="text-center"  width="15%"><spring:message code="label.category"/></th>
							<th class="text-center"  width="15%"><spring:message code="label.devicename"/></th>
							<th class="text-center"  width="30%"><spring:message code="label.distr"/></th>
							<th class="text-center" width="25%"><spring:message code="label.are"/></th>
						</tr>
					</thead>
					<tbody id="areaDistributionGroupTableTbody">
					</tbody>
				</table>
			</div>
		</div>
		<div class="col-xs-1"></div>
	</div>
	
	<div id="applyButtonGroup" class="row">
		<div class="col-xs-9"></div>
		<div class="col-xs-2">
			<form role="form" class="form-group">
				<div class="form-group">
					<button id = "areaApplyButton" type="button" class="btn bizButton greyGradient" style="margin-top:0px;margin-bottom:0px;"><spring:message code="label.selection"/> &nbsp;<span class="fa fa-caret-right"></span></button>
				</div>
			</form>
		</div>
		<div class="col-xs-1"></div>
	</div>
	
	<div class="bizBlackSeparatedBar text-center"><strong><spring:message code="label.altitle" /></strong></div>
	
	<div id="operationGroup" class="row">
		<div class="col-xs-1"></div>
		<div class="col-xs-2">
			<form role="form" class="form-group">
				<div class="form-group">
					<label><spring:message code="label.arop"/></label>
					 <select class="form-control" placeholder="Please Select" id="areaOperationSelect" style="margin-bottom:32px">
						 <option value=""><spring:message code="label.pleaseselect"/></option>
						 	<option value="Create Area"><spring:message code="label.arcr"/></option>
						 	<option value="Remove Area"><spring:message code="label.ardr"/></option>
						</select>
				</div>
			</form>
		</div>
		<div class="col-xs-4">
			<form id="createAreaOperationForm" style="display:none" role="form" class="form-group createAreaOperationForm">
				<div class="form-group">
					<label><spring:message code="label.arar"/>:</label>
					<input id="newAreaNameInput" class="form-control" placeholder="Please input new area name" style="height:32px;"/>
				</div>
			</form>
			<form id="removeAreaOperationForm" style="display:none" role="form" class="form-group removeAreaOperationForm">
				<div class="form-group">
					<label><spring:message code="label.arar"/>:</label>
					 <select id = "areaListSelect" class="form-control" placeholder="Please Select">
					</select>
				</div>
			</form>
		</div>
		<div class="col-xs-4"></div>
		<div class="col-xs-2">
			<form role="form" class="form-group">
				<div class="form-group">
					<button id = "areaAddButton" type="button" class="btn bizButton greyGradient createAreaOperationForm" style="display:none;"><spring:message code="label.arcr"/> &nbsp;<span class="fa fa-caret-right"></span></button>
					<button id = "areaRemoveButton" type="button" class="btn bizButton greyGradient removeAreaOperationForm" style="display:none;"><spring:message code="label.ardr"/> &nbsp;<span class="fa fa-caret-right"></span></button>
				</div>
			</form>
		</div>
		<div class="col-xs-1"></div>
	</div>
</div>


