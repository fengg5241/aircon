<div class="notifyhomepanel panel-default" style="border: 0px;">
	<div class="notifyhomepanel-heading" style="height: 50px;">
		<div class="col-xs-12  form-group row">
			<label class="control-label"> <spring:message
					code="label.custsiteselect" /> : <select multiple="multiple"
				class="form-control" id="island" style="display: none"></select>
			</label>
		</div>
	</div>
	<div class="notifyhomepanel-body">
		<div id="map" style="height: 742px;">
			<div class="row">
				<div class="col-lg-4">
					<div class="ibox float-e-margins" style="display: none;">
						<div class="ibox-content">
							<div class="col-xs-8 put_all_sites" style="display: none;"
								id="showdisplay"></div>
						</div>
					</div>
				</div>
				<div class="col-lg-4">
					<div class="form-group">
						<div class="form-group" id="data_1">
							<label class="col-lg-5 control-label"><b><spring:message
										code="label.startdate" /></b></label>
							<div class="col-lg-7">
								<div class="input-daterange input-group date" id="datepicker">
									<span class="input-group-addon"><i
										class="fa fa-calendar"></i></span> <input id="startdate" type="text"
										class="input-xs form-control" name="group" readonly="true"
										style="background-color: #fff;" />
								</div>
							</div>
							<br> <br> <br>
							<div class="form-group">
								<label class="col-lg-5 control-label"><spring:message
										code="label.entervrf" /> <span class="_Xbe kno-fv"><spring:message
											code="label.co" /><sub>2</sub></span> <spring:message
										code="label.f" /> </label>
								<div class="col-lg-3">
									<input type="text" name="name"
											style="text-align: center; width: 200px" id="co2factor"
											class="form-control in-line" min="0">
								</div>
								<div class="col-lg-4">
									<label style="margin-top: 6px;">Kg/kWh</label>
								</div>
							</div>
						</div>
						<div class="col-lg-5"></div>
						<div class="form-group col-lg-7">
							<button id="applyco2value" type="button"
								class="btn bizButton greyGradient">
								<spring:message code="label.apply" />
								&nbsp;<span class="fa fa-caret-right"></span>
							</button>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- <div class="col-xs-12 form-group" style="line-height: 34px;">
			<div class="row">
				<label class="control-label"> 
					<b> <spring:message
								code="label.startdate" />
					</b>
				</label>
					<div class="input-daterange input-group date" id="datepicker">
						<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
						<input id="startdate" type="text" class="input-xs form-control"
							name="group" readonly="true" style="background-color: #fff;" />
					</div>

			</div>
			<div class="row">
				<label class="control-label"> <spring:message
						code="label.entervrf" /> <span class="_Xbe kno-fv"> <spring:message
							code="label.co" /> <sub>2</sub>
				</label>
				</span> <spring:message code="label.f" /> <input type="text" name="name"
					style="text-align: center; width: 170px;" id="co2factor"
					class="form-control inline" min="0"> <span>Kg/kWh</span>
			</div>
		</div>
		<div class="form-group">
			<button id="applyco2value" type="button"
				class="btn bizButton greyGradient">
				<spring:message code="label.apply" />
				&nbsp;<span class="fa fa-caret-right"></span>
			</button>
		</div>
	</div> -->
