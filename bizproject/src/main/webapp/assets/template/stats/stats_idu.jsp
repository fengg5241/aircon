<div class="panel panel-stats">
	<div class="panel-body" style="padding: 15px 0px;">
		<div class="col-xs-12 form-group row">
			<div class="col-xs-3">
				<label class="control-label"><spring:message code="label.parameter"/></label>
				<select class="form-control inline form-select-visualization" name="idu_parameter" id="idu_parameter">
					<option value="power_consumption">Power Consumption</option>
					<option value="workinghours">Working Hours</option>
					<!-- option value="difftemperature">Indoor Temperature</option -->
				</select>
			</div>
			<div class="col-xs-3">
				<label class="control-label"><spring:message code="label.display"/></label>
				<select class="form-control inline form-select-visualization" name="idu_type" id="idu_type">
					<option value="accumulated">Accumulated</option>
					<option value="chronological">Chronological</option>
				</select>
			</div>
		</div>
		<div class="col-xs-12 form-group">
			<label class="control-label">
				<spring:message code="label.period"/>  
				<label class="radio-inline i-checks"> 
					<input type="radio" value="thisyear" name="idu_period" id="idu_period">
					<spring:message code="label.year"/>
				</label> 
				<label class="radio-inline i-checks"> 
					<input type="radio" value="thismonth" name="idu_period" id="idu_period">
					<spring:message code="label.month"/>
				</label> 
				<label class="radio-inline i-checks">
					<input type="radio" value="thisweek" name="idu_period" id="idu_period">
					<spring:message code="label.week"/>
				</label> 
				<label class="radio-inline i-checks"> 
					<input type="radio" value="today" name="idu_period" id="idu_period">
					<spring:message code="label.day"/>
				</label> <label class="radio-inline i-checks"> 
					<input type="radio" value="past24hours" name="idu_period" id="idu_period">
					<spring:message code="label.past"/>
				</label>
				<label class="radio-inline i-checks"> 
					<input type="radio" checked="checked" value="date_range" name="idu_period" id="idu_period">
					<spring:message code="label.date"/>
					<label class="radio-inline i-checks"> 
						<div class="input-daterange input-group" id="idu_datepicker">
							<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
							<input type="text" class="input-xs form-control" name="idu_start" value="01/01/2015" readonly="true" style="background-color: #fff;" />
							<span class="input-group-addon"><spring:message code="label.to"/></span>
							<input type="text" class="input-xs form-control" name="idu_end"	value="25/11/2015" readonly="true" style="background-color: #fff;" />
						</div>
					</label>
				</label>
			</label>
		</div>
		<div class="row">
			<div class="col-xs-12 form-group">
				<table id="stats_idu_table" width="100%"
					class="table table-striped table-hover text-center bizTable">
					<thead>
						<tr class="greyGradient">
							<td class="text-center iduCheckHeading"><input type="checkbox" class="bizCheckbox" id="iduHeaderCheckbox" /><label for="iduHeaderCheckbox"></label></td>
							<td class="text-center iduNameHeading"><spring:message code="label.iduname"/></td>
							<td class="text-center iduLocationHeading"><spring:message code="label.location"/></td>
						</tr>
					</thead>
					<tbody id="stats_idu_table_body">
					</tbody>
				</table>
			</div>
			<div class="col-xs-12 text-center">
				<button id="iduButton" class="btn btn-default greyGradient" style="margin:0px;">
					<strong><spring:message code="label.selection"/>  &#x25b8;</strong>
				</button>
			</div>
		</div>
	</div>
</div>
<div class="panel panel-stats margin-bottom">
	<div class="panel-heading text-center">
		<span id="idu_type_title">Power Consumption</span> by Indoor Unit
	</div>
	<div class="panel-body">
		<div id="stats_idu_graph"></div>
		<div class="col-xs-12 text-center">
				<button id="iduDownloadButton" class="btn btn-default greyGradient" style="margin:0px;">
					<strong><spring:message code="label.statsdownload"/> &#x25b8;</strong>
				</button>
		</div>
	</div>
</div>