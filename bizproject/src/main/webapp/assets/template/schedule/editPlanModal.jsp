<div class="modal fade col-lg-12" id="editPlanModal">
  <div class="modal-dialog modal-lg">
    <div class="modal-content bizModal dialogGreyBackground">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title text-center">Daily Mode Calendar Setting</h3>
      </div>
      <div class="modal-body">
        <div class="col-lg-12 nopadding">
			<div class="col-xs-1"></div>
			<div class="col-xs-10">
				<div class="row">
					<div class="col-xs-6 white-bg" style="padding-left: 40px; padding-right: 40px;">
						<p class="calendarTitle">Daily Mode Plan Calendar Setting - Month View</p>
						<div id="monthViewCalendar"></div>
						<div class="text-right" style="width:100%;margin-top:20px;">
				        	<button type="button" class="btn bizButton greyGradient" id="monthViewCancelButton">Cancel &nbsp;<span class="fa fa-caret-right"></button>
				        	<button type="button" class="btn bizButton greyGradient" id="monthViewApplyButton">Apply &nbsp;<span class="fa fa-caret-right"></button>
				      	</div>
					</div>
					<div class="col-xs-6 white-bg" style="padding-left: 40px; padding-right: 40px;">
						<div class="row fc-view fc-month-view fc-basic-view">
							<p class="calendarTitle">Daily Mode Plan Calendar Setting - Date Range</p>
							<form role="form" class="form-group">
								<div class="input-daterange input-group date" id="dateRangeDatepicker">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<input id="dateRangeStartDate" type="text" class="input-xs form-control" name="group_start"/> 
									<span class="input-group-addon"><spring:message code="label.to"/></span>
									<input id="dateRangeEndDate" type="text" class="input-xs form-control" name="group_end"/>
								</div>
							</form>
				
							<div id="dateRangeCalendar" class="fc fc-ltr fc-unthemed">
								<div class="fc-view-container" style="">
									<div class="fc-view fc-month-view fc-basic-view" style="">
										<table>
											<tbody>
												<tr>
													<td class="fc-widget-content"><div
															class="fc-day-grid-container" style="">
															<div class="fc-day-grid">
																<div class="fc-row fc-week fc-widget-content">
																	<div class="fc-bg">
																		<table>
																			<tbody>
																				<tr>
																					<td data-date="2016-05-02" data-index= "1"
																						class="fc-day fc-widget-content fc-mon fc-past"></td>
																					<td data-date="2016-05-03" data-index= "2"
																						class="fc-day fc-widget-content fc-tue fc-past"></td>
																					<td data-date="2016-05-04" data-index= "3"
																						class="fc-day fc-widget-content fc-wed fc-past"></td>
																					<td data-date="2016-05-05" data-index= "4"
																						class="fc-day fc-widget-content fc-thu fc-past"></td>
																					<td data-date="2016-05-06" data-index= "5"
																						class="fc-day fc-widget-content fc-fri fc-past"></td>
																					<td data-date="2016-05-07" data-index= "6"
																						class="fc-day fc-widget-content fc-sat fc-past"></td>
																					<td data-date="2016-05-08" data-index= "7"
																						class="fc-day fc-widget-content fc-sun fc-past"></td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																	<div class="fc-content-skeleton">
																		<table>
																			<thead>
																				<tr>
																					<td data-date="2016-05-02"
																						class="fc-day-number fc-mon fc-past">MON</td>
																					<td data-date="2016-05-03"
																						class="fc-day-number fc-tue fc-past">TUE</td>
																					<td data-date="2016-05-04"
																						class="fc-day-number fc-wed fc-past">WED</td>
																					<td data-date="2016-05-05"
																						class="fc-day-number fc-thu fc-past">THU</td>
																					<td data-date="2016-05-06"
																						class="fc-day-number fc-fri fc-past">FRI</td>
																					<td data-date="2016-05-07"
																						class="fc-day-number fc-sat fc-past">SAT</td>
																					<td data-date="2016-05-08"
																						class="fc-day-number fc-sun fc-past">SUN</td>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">1</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">1</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">2</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">4</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">5</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">6</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">4</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																</div>
															</div>
														</div></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								</div><!--Date Range Calendar -->
									<div class="text-right" style="width:100%;margin-top:20px;">
				        	<button type="button" class="btn bizButton greyGradient" id="monthViewCancelButton">Cancel &nbsp;<span class="fa fa-caret-right"></button>
				        	<button type="button" class="btn bizButton greyGradient" id="monthViewApplyButton">Apply &nbsp;<span class="fa fa-caret-right"></button>
				      	</div>
						</div><!-- Date Range Calendar block end-->
						<div class="row white-bg" >
							<div class="row fc-view fc-month-view fc-basic-view">
							<p class="calendarTitle">Daily Mode Plan Calendar Setting - Automatic</p>
							<p class=""><span class="seasonMode">Spring</span><span class="seasonMode">Summer</span><span class="seasonMode">Autumn</span><span class="seasonMode">Winter</span><span class="seasonMode">Others</span></p>
							<form role="form" class="form-group">
								<div class="input-daterange input-group date" id="dateRangeDatepicker">
									<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
									<input id="dateRangeStartDate" type="text" class="input-xs form-control" name="group_start"/> 
									<span class="input-group-addon"><spring:message code="label.to"/></span>
									<input id="dateRangeEndDate" type="text" class="input-xs form-control" name="group_end"/>
								</div>
							</form>
				
							<div id="automaticSettingCalendar" class="fc fc-ltr fc-unthemed">
								<div class="fc-view-container" style="">
									<div class="fc-view fc-month-view fc-basic-view" style="">
										<table>
											<tbody>
												<tr>
													<td class="fc-widget-content"><div
															class="fc-day-grid-container" style="">
															<div class="fc-day-grid">
																<div class="fc-row fc-week fc-widget-content">
																	<div class="fc-bg">
																		<table>
																			<tbody>
																				<tr>
																					<td data-date="2016-05-02" data-index = "1"
																						class="fc-day fc-widget-content fc-mon fc-past"></td>
																					<td data-date="2016-05-03" data-index= "2"
																						class="fc-day fc-widget-content fc-tue fc-past"></td>
																					<td data-date="2016-05-04" data-index= "3"
																						class="fc-day fc-widget-content fc-wed fc-past"></td>
																					<td data-date="2016-05-05" data-index= "4"
																						class="fc-day fc-widget-content fc-thu fc-past"></td>
																					<td data-date="2016-05-06" data-index= "5"
																						class="fc-day fc-widget-content fc-fri fc-past"></td>
																					<td data-date="2016-05-07" data-index= "6"
																						class="fc-day fc-widget-content fc-sat fc-past"></td>
																					<td data-date="2016-05-08" data-index= "7"
																						class="fc-day fc-widget-content fc-sun fc-past"></td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																	<div class="fc-content-skeleton">
																		<table>
																			<thead>
																				<tr>
																					<td data-date="2016-05-02"
																						class="fc-day-number fc-mon fc-past">MON</td>
																					<td data-date="2016-05-03"
																						class="fc-day-number fc-tue fc-past">TUE</td>
																					<td data-date="2016-05-04"
																						class="fc-day-number fc-wed fc-past">WED</td>
																					<td data-date="2016-05-05"
																						class="fc-day-number fc-thu fc-past">THU</td>
																					<td data-date="2016-05-06"
																						class="fc-day-number fc-fri fc-past">FRI</td>
																					<td data-date="2016-05-07"
																						class="fc-day-number fc-sat fc-past">SAT</td>
																					<td data-date="2016-05-08"
																						class="fc-day-number fc-sun fc-past">SUN</td>
																				</tr>
																			</thead>
																			<tbody>
																				<tr>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">1</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">1</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">2</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">4</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">5</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">6</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																					<td class="fc-event-container"><a
																						class="fc-day-grid-event fc-event fc-start fc-end  fc-draggable fc-resizable"><div
																								class="fc-content">
																								<span class="fc-title">4</span>
																							</div>
																							<div class="fc-resizer"></div></a></td>
																				</tr>
																			</tbody>
																		</table>
																	</div>
																</div>
															</div>
														</div></td>
												</tr>
											</tbody>
										</table>
									</div>
								</div>
								</div><!--Automatic Calendar -->
									<div class="text-right" style="width:100%;margin-top:20px;">
									<div  style="float:left" ><input id = "automaticSettingCheckbox" type="checkbox"/><span >Automatically apply this setting on 25th month</div>
									
				        	<button type="button" class="btn bizButton greyGradient" id="monthViewCancelButton">Cancel &nbsp;<span class="fa fa-caret-right"></button>
				        	<button type="button" class="btn bizButton greyGradient" id="monthViewApplyButton">Apply &nbsp;<span class="fa fa-caret-right"></button>
				      	</div>
						</div>
						</div>
						
					</div>
				</div>
				<div class="row">
					<div class="row">
						Daily Plan Modes
					</div>
					<div class="row">
						<div class="col-xs-6">
							<div id="mode_1" class='fc-event row' data-shortname="1">
								<span id="mode-1">Mode 1</span><span>Weekday Summer</span>
							</div>
							<div class='fc-event row' data-shortname="2">
								<span>Mode 2</span><span>Weekday Summer</span>
							</div>
							<div class='fc-event row' data-shortname="3">
								<span>Mode 3</span><span>Weekday Summer</span>
							</div>
						</div>
						<div class="col-xs-6">
							<div class='fc-event row' data-shortname="4">
								<span>Mode 4</span><span>Weekday Summer</span>
							</div>
							<div class='fc-event row' data-shortname="5">
								<span>Mode 5</span><span>Weekday Summer</span>
							</div>
							<div class='fc-event row' data-shortname="6">
								<span>Mode 6</span><span>Weekday Summer</span>
							</div>
						</div>
					</div>
				</div>
			</div>		
			<div class="col-xs-1"></div>	
				
		</div>
      </div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
