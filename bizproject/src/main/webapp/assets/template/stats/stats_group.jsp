<div class="panel panel-stats">
	<div class="panel-heading"><spring:message code="label.stastsgrpheading"/>:</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-xs-12  form-group row" style="line-height:34px;">
				<div class="col-xs-3">
					<label class="control-label"><spring:message code="label.parameter"/></label>
					<select class="form-control inline form-select-visualization" name="group_parameter" id="group_parameter">
						<option value="power_consumption">Power Consumption</option>
						<option value="capacity">Capacity</option>
						<option value="efficiency">Operation
							Efficiency</option>
						<!-- option value="gas_meter_value">Gas Meter Value</option -->
					</select>
				</div>
				<div class="col-xs-3">
					<label class="control-label"><spring:message code="label.display"/></label>
					<select class="form-control inline form-select-visualization" name="group_type" id="group_type">
						<option value="accumulated">Accumulated</option>
						<option value="chronological">Chronological</option>
					</select>
				</div>
				<div class="col-xs-3">
					<label class="control-label"><spring:message code="label.compares"/></label>
					<select class="form-control inline form-select-visualization" name="group_grouplevel" id="group_grouplevel">
					</select>
				</div>
			</div>
			<div class="col-xs-12 form-group" style="line-height:34px;">
				<label class="control-label">
					<spring:message code="label.period"/>
					<label class="radio-inline i-checks"> 
						<input type="radio" value="thisyear" name="group_period" id="group_period">
						<spring:message code="label.year"/>
					</label> 
					<label class="radio-inline i-checks"> 
						<input type="radio" value="thismonth" name="group_period" id="group_period">
						<spring:message code="label.month"/>
					</label>
					<label class="radio-inline i-checks">
						<input type="radio"	value="thisweek" name="group_period" id="group_period">
						<spring:message code="label.week"/>
					</label>
					<label class="radio-inline i-checks">
						<input type="radio" value="today" name="group_period" id="group_period">
						<spring:message code="label.day"/>
					</label> 
					<label class="radio-inline i-checks"> 
						<input type="radio" value="past24hours" name="group_period" id="group_period">
						<spring:message code="label.past"/>
					</label>
					<label class="radio-inline i-checks"> 
						<input type="radio" checked="checked" value="date_range" name="group_period" id="group_period"> 
						<spring:message code="label.date"/>
						<label class="radio-inline i-checks">
							<div class="input-daterange input-group date" id="group_datepicker">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="input-xs form-control" name="group_start" value="01/01/2015" readonly="true" style="background-color: #fff;" /> 
								<span class="input-group-addon"><spring:message code="label.to"/></span>
								<input type="text" class="input-xs form-control" name="group_end" value="09/11/2015" readonly="true" style="background-color: #fff;" />
							</div>
						</label>
					</label>
				</label>
			</div>
			<div class="col-xs-12 text-center">
				<button id="groupButton" class="btn btn-default greyGradient" style="margin:0px;">
					<strong><spring:message code="label.selection"/> &#x25b8;</strong>
				</button>
			</div>
		</div>
	</div>
</div>
<div class="panel panel-stats margin-bottom">
	<div class="panel-heading text-center">
		<span id="group_type_title">Power Consumption</span> by Groups
		<span id="group_total_current_capacity" class="pull-right"></span>
	</div>
	<div class="panel-body">
		<div id="stats_group_graph"></div>
		<div class="col-xs-12 text-center">
				<button id="groupDownloadButton" class="btn btn-default greyGradient" style="margin:0px;">
					<strong><spring:message code="label.statsdownload"/> &#x25b8;</strong>
				</button>
		</div>
	</div>
</div>