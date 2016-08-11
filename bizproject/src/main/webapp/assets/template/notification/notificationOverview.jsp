<div class="panel-body">
	<div class="row">
		<div id="notificationoverviewFrame" class="col-xs-12 "
			style="margin-left: 13px;">
			<div class="ibox float-e-margins">
				<div class="ibox-title font-bold ibox-title-curve">
					<label class="filter-col"><spring:message
							code="label.select" />:</label>
				</div>
				<div class="ibox-updateusers no-padding ibox-content-curve">
					<br>
					<div class="row">
						<div class="col-xs-12">
							<div class="col-xs-2 form-group">
								<div class="form-group">
									<label><spring:message code="label.categorylevel" />:</label><select
										class="form-control" id="categorylevel">
									</select>
								</div>
							</div>
							<div class="col-xs-6 form-group">
								<div class="col-xs-12">
									<div class="form-group">
										<label style="margin-left: -25px;"><spring:message
												code="label.period" /> :</label>
										<div class="form-group" style="margin-left: -24px;">
											<label class="radio-inline"> <input type="radio"
												value="thisyear" name="period" id="period"> <i></i>
												<spring:message code="label.year" />
											</label> <label class="radio-inline "> <input type="radio"
												value="thismonth" name="period" id="period"> <i></i>
												<spring:message code="label.month" />
											</label> <label class="radio-inline "> <input type="radio"
												value="thisweek" name="period" id="period"> <i></i>
												<spring:message code="label.week" />
											</label> <label class="radio-inline"> <input type="radio"
												value="today" name="period" id="period"> <i></i> <spring:message
													code="label.day" />
											</label> <label class="radio-inline " style="margin-left: 14px;">
												<input type="radio" checked="checked" value="date_range"
												name="period" id="period"> <i></i> <spring:message
													code="label.date" />
											</label>
											<!-- <label class="radio-inline "> <input
											type="radio" checked="checked" value="date_range"
											name="period" id="period"> <i></i> <spring:message code="label.past"/>
										</label> -->
										</div>
									</div>
								</div>
							</div>
							<div class="col-xs-4 form-inline notify"
								style="margin-left: 0px;">
								<div class="form-inline col-xs-12"
									style="margin-top: 18px;">
									<div class="form-group">

										<div class="input-daterange input-group"
											id="notification_overview_datepicker">
											<span class="input-group-addon"><i
												class="fa fa-calendar"></i></span> <input type="text"
												class="input-xs form-control" name="start" id="start"
												readonly="true" style="background-color: #fff;" /> <span
												class="input-group-addon">to</span> <input type="text"
												class="input-xs form-control" name="end" id="end"
												readonly="true" style="background-color: #fff;" />
										</div>
									</div>
								</div>
							</div>
						</div>
						<div class="col-xs-12 text-center">
							<button id="graphapply" class="btn btn-default greyGradient"
								style="margin: 0px;">
								<strong><spring:message code="label.selection" />
									&nbsp;<span class="fa fa-caret-right"></span></strong>
							</button>


						</div>
					</div>
				</div>
				<div class="graphpanel panel-default">
					<div class="graphpanel-heading text-center">
						<spring:message code="label.overivew" />
					</div>
					<div class="graphpanel-body">
						<div id="notify_overview_graph"></div>
					</div>

				</div>
			</div>

		</div>
		<div class="text-right " style="margin-bottom: 10px;">
			<button id="notificationoverviewdownload" type="button"
				class="btn bizButton greyGradient">
				<spring:message code="label.download" />
				&nbsp;<span class="fa fa-caret-right"></span>
			</button>
		</div>
	</div>
</div>


