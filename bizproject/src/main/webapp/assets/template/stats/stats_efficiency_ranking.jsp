<div class="panel panel-stats">
	<div class="panel-heading"><spring:message code="label.stastsgrpheading"/>:</div>
	<div class="panel-body">
		<div class="row">
			<div class="col-xs-12 form-group">
				<label class="control-label">
					<spring:message code="label.period"/>
					<label class="radio-inline i-checks">
						<input type="radio" value="thisyear" name="efficiency_period" id="efficiency_period">
						<spring:message code="label.year"/>
					</label>
					<label class="radio-inline i-checks"> 
						<input type="radio" value="thismonth" name="efficiency_period" id="efficiency_period">
						<spring:message code="label.month"/>
					</label>
						<label class="radio-inline i-checks"> 
						<input type="radio" value="thisweek" name="efficiency_period" id="efficiency_period">
						<spring:message code="label.week"/>
					</label>
					<label class="radio-inline i-checks"> 
						<input type="radio" value="today" name="efficiency_period" id="efficiency_period">
						<spring:message code="label.day"/>
					</label>
					<label class="radio-inline i-checks">
						<input type="radio" value="past24hours" name="efficiency_period" id="efficiency_period">
						<spring:message code="label.past"/>
					</label>
					<label class="radio-inline i-checks"> 
						<input type="radio" checked="checked" value="date_range" name="efficiency_period" id="efficiency_period">
						<spring:message code="label.date"/>
						<label class="radio-inline i-checks"> 
							<div class="input-daterange input-group" id="efficiency_datepicker">
								<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
								<input type="text" class="input-xs form-control" name="efficiency_start" value="05/14/2014" readonly="true" style="background-color: #fff;" />
								<span class="input-group-addon"><spring:message code="label.to"/></span>
								<input type="text" class="input-xs form-control" name="efficiency_end"	value="05/22/2014" readonly="true" style="background-color: #fff;" />
							</div>
						</label>
					</label>
				</label>
			</div>
			<div class="col-xs-12 form-group">
				<div class="col-xs-3 row">
					<label class="control-label"><spring:message code="label.compares"/></label>
					<select class="form-control inline form-select-visualization" name="efficiency_grouplevel" id="efficiency_grouplevel"></select>
				</div>
			</div>
			<div class="col-xs-12 text-center">
				<button class="btn btn-default greyGradient" id="efficiencyButton" style="margin: 0px;">
					<strong><spring:message code="label.selection"/> &#x25b8;</strong>
				</button>
			</div>
		</div>
	</div>
</div>
<div class="panel panel-stats margin-bottom">
	<div class="panel-heading text-center">
		<span id="type_title">Top Ranking</span>
	</div>
	<div class="panel-body">
		<div id="stats_efficiency_graph"></div>
				<div class="col-xs-12 text-center">
				<button id="efficiencyDownloadButton" class="btn btn-default greyGradient" style="margin:0px;">
					<strong><spring:message code="label.statsdownload"/>  &#x25b8;</strong>
				</button>
		</div>
	</div>
</div>