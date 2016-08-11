<div class="panel-body">
	<div class="row">
		<div id="notificationmaintenanceFrames" class="col-xs-15"
			style="margin-left: 15px;">
			<div class="ibox float-e-margins">
				<div class="ibox-title font-bold ibox-title-curve">
					<div class="row">
						<div class="col-xs-4 text-center" style="margin-left: -34px;">
							<label class="filter-col" style="font-size: 16px; display: none;"
								id="selectlabel"><spring:message code="label.selectsite" />:</label><select
								class="btns btn-mini" id="groupSelect" style="display: none;">
							</select>
						</div>
					</div>
				</div>
				<div class="grappanel panel-default">
					<div class="grappanel-heading">
						<div class="row">
							<div class="col-xs-2 text-center"></div>
							<div class="col-xs-3 text-center" id="idtypes"
								style="display: none;">
								<spring:message code="label.types" />
							</div>
							<div class="col-xs-3  text-center" id="idmaintypes"
								style="display: none;">
								<spring:message code="label.maintenancetypes" />
							</div>
							<div class="col-xs-3  text-center">
								<!-- Pre-Maintenance
								Notification Time -->
							</div>
							<div class="col-xs-1 text-center"></div>
						</div>
					</div>
					<div class="grappanel-body">
						<div class="row-center col-xs-12" id="v1" style="display: none;">
							<div class="col-xs-2 text-center"></div>
							<div class="col-xs-3 text-center tops">
								<spring:message code="label.vrf1" />
							</div>
							<div class="col-xs-2 text-center">
								<input id="vrfCompressor1" class="form-controls"
									style="text-align: center">
							</div>
							<div class="col-xs-1 text-center mains">
								<spring:message code="label.hours" />
							</div>
							<!-- <div class="col-xs-2 text-center">
								<input type="number" id="prevrfCompressor1" class="form-cont" style="text-align:center" min="0">
							</div>
							<div class="col-xs-1">
								<h5 class="text">hours</h5>
							</div>
							<div class="col-xs-1 text-center"></div> -->
						</div>
						<div class="row-center col-xs-12" id="v2" style="display: none;">
							<div class="col-xs-2 text-center"></div>
							<div class="col-xs-3 text-center tops">
								<spring:message code="label.vrf2" />
							</div>
							<div class="col-xs-2 text-center">
								<input id="vrfCompressor2" class="form-controls"
									style="text-align: center">
							</div>
							<div class="col-xs-1 text-center mains">
								<spring:message code="label.hours" />
							</div>
							<!-- <div class="col-xs-2 text-center">
								<input type="number" id="prevrfCompressor2" class="form-cont" style="text-align:center" min="0">
							</div>
							<div class="col-xs-1">
								<h5 class="text">hours</h5>
							</div>
							<div class="col-xs-1 text-center"></div> -->
						</div>
						<div class="row-center col-xs-12" id="v3" style="display: none;">
							<div class="col-xs-2 text-center"></div>
							<div class="col-xs-3 text-center tops">
								<spring:message code="label.vrf3" />
							</div>
							<div class="col-xs-2 text-center">
								<input id="vrfCompressor3" class="form-controls"
									style="text-align: center">
							</div>
							<div class="col-xs-1 text-center mains">
								<spring:message code="label.hours" />
							</div>
							<!-- <div class="col-xs-2 text-center">
								<input type="number" id="prevrfCompressor3" class="form-cont" style="text-align:center" min="0">
							</div>
							<div class="col-xs-1">
								<h5 class="text">hours</h5>
							</div>
							<div class="col-xs-1 text-center"></div> -->
						</div>
						<!-- <div class="row-center col-xs-12">
							<br>
						</div>
						<div class="row-center col-xs-12">
							<div class="col-xs-2 text-center"></div>
							<div class="col-xs-3 text-center tops">GHP Engine Operation
								Hours</div>
							<div class="col-xs-2 text-center">
								<input type="number" id="ghpEngOper" class="form-controls" style="text-align:center" min="0">
							</div>
							<div class="col-xs-1 ">
								<h5 class="text">hours</h5>
							</div>
							<div class="col-xs-2 text-center">
								<input type="number" id="preghpEngOper" class="form-cont" style="text-align:center" min="0">
							</div>
							<div class="col-xs-1">
								<h5 class="text">hours</h5>
							</div>
							<div class="col-xs-1 text-center"></div>
						</div>
						<div class="row-center col-xs-12">
							<div class="col-xs-2 text-center"></div>
							<div class="col-xs-3 text-center tops">GHP Time After Oil
								Change</div>
							<div class="col-xs-2 text-center">
								<input type="number" id="th1" class="form-controls"
									style="display: none;">
							</div>
							<div class="col-xs-1 ">
								<h5 class="text" style="display: none;">hours</h5>
							</div>
							<div class="col-xs-2 text-center">
								<input type="number" id="preghptimeOil" class="form-cont" style="text-align:center" min="0">
							</div>
							<div class="col-xs-1">
								<h5 class="text">hours</h5>
							</div>
							<div class="col-xs-1 text-center"></div>
						</div>
						<div class="row-center col-xs-12">
							<br>
						</div>
						<div class="row-center col-xs-12">
							<br>
						</div>
						<div class="row-center col-xs-12">
							<div class="col-xs-2 text-center"></div>
							<div class="col-xs-3 text-center tops"></div>
							<div class="col-xs-3 text-center">
								<label class="font-noraml">CA Communication Error
									Deduction Period</label><input type="number" id="cacommunication"
									class="form-controls" style="text-align:center" min="0">
							</div>
							<div class="col-xs-1 ">
								<h5 class="text" style="margin-top: 33px; margin-left: -138px;">hours</h5>
							</div>
							<div class="col-xs-2 text-center"></div>
							<div class="col-xs-1">
								<h5 class="text"></h5>
							</div>
							<div class="col-xs-1 text-center"></div>
						</div> -->
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="pull-right tooltip-demo">
		<button id="savemaintenancesetting" type="button"
			class="btn bizButton greyGradient">
			<spring:message code="label.apply" />
			&nbsp;<span class="fa fa-caret-right"></span>
		</button>
		<button id="cancelmaintenancesetting" type="button"
			class="btn bizButton greyGradient">
			<spring:message code="label.cancel" />
			&nbsp;<span class="fa fa-caret-right"></span>
		</button>
	</div>
</div>