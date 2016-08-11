<div class="panel panel-stats">
	<div class="panel-body" style="padding: 15px 0px;">
		<div class="col-xs-12 form-group row">
			<div class="col-xs-3">
				<label class="control-label"><spring:message code="label.parameter"/></label>
				<select class="form-control inline form-select-visualization" name="ref_parameter" id="ref_parameter">
					<option value="power_consumption">Power Consumption</option>
					<option value="capacity">Capacity</option>
					<option value="efficiency">Operation Efficiency</option>
					<option value="workinghours">Working Hours</option>
					<!--  option value="gas_meter_value">Gas Meter Value</option -->
				</select>
			</div>
			<div class="col-xs-3">
				<label class="control-label"><spring:message code="label.display"/></label>
				<select class="form-control inline form-select-visualization" name="ref_type" id="ref_type">
					<option value="accumulated">Accumulated</option>
					<option value="chronological">Chronological</option>
				</select>
			</div>
		</div>
		<div class="col-xs-12 form-group" style="line-height:34px;">
			<label class="control-label">
				<spring:message code="label.period"/> 
				<label class="radio-inline i-checks"> 
					<input type="radio" value="thisyear" name="ref_period" id="ref_period">
					<spring:message code="label.year"/>
				</label> 
				<label class="radio-inline i-checks"> 
					<input type="radio" value="thismonth" name="ref_period" id="ref_period">
					<spring:message code="label.month"/>
				</label> 
				<label class="radio-inline i-checks"> 
					<input type="radio" value="thisweek" name="ref_period" id="ref_period">
					<spring:message code="label.week"/>
				</label>
				<label class="radio-inline i-checks" id="today">
					<input type="radio" value="today" name="ref_period" id="ref_period">
					<spring:message code="label.day"/>
				</label>
				<label class="radio-inline i-checks" id="past24hours">
					<input type="radio" value="past24hours" name="ref_period" id="ref_period"> 
					<spring:message code="label.past"/>
				</label>
				<label class="radio-inline i-checks"> 
					<input type="radio" checked="checked" value="date_range" name="ref_period" id="ref_period">
					<spring:message code="label.date"/>
					<label class="radio-inline i-checks">
						<div class="input-daterange input-group" id="ref_datepicker">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							<input type="text" class="input-xs form-control" name="ref_start" value="05/14/2014" readonly="true" style="background-color: #fff;" /> 
							<span class="input-group-addon"><spring:message code="label.to"/></span>
							<input type="text" class="input-xs form-control" name="ref_end" value="05/22/2014" readonly="true" style="background-color: #fff;" />
						</div>
					</label>
				</label>
			</label>
		</div>
		<div class="row">
			<div class="col-xs-12 form-group">
				<table id="stats_ref_table" width="100%" class="table table-striped table-hover text-center bizTable">
					<thead>
						<tr class="greyGradient">
							<td class="text-center refCheckHeading"><input id="refHeaderCheckbox" class="bizCheckbox" type="checkbox" /><label for="refHeaderCheckbox"></label></td>
							<td class="text-center refNameHeading"><spring:message code="label.refcir"/></td>
						</tr>
					</thead>
					<tbody id="stats_ref_table_body">
					</tbody>
				</table>
			</div>
			<div class="col-xs-12 text-center">
				<button id="refButton" class="btn btn-default greyGradient" style="margin:0px;">
					<strong><spring:message code="label.selection"/> &#x25b8;</strong>
				</button>
			</div>
		</div>
	</div>
</div>

<div class="panel panel-stats margin-bottom">
	<div class="panel-heading text-center">
		<span id="ref_type_title">Power Consumption</span> by Refrigerant Circuit
		<span id="ref_total_current_capacity" class="pull-right"></span>
	</div>
	<div class="panel-body">
		<div id="stats_ref_graph"></div>
		<div class="col-xs-12 text-center">
				<button id="refDownloadButton" class="btn btn-default greyGradient" style="margin:0px;">
					<strong><spring:message code="label.statsdownload"/> &#x25b8;</strong>
				</button>
		</div>
	</div>
</div>