<div class="col-md-12 nopadding" id="compare">
									<div class="ibox float-e-margins">										
										<div class="ibox-content" style="height:370px;margin-top:-22px;">
										<div class="clients-list nopadding">
											<ul class="nav nav-tabs">
												<li class="active"><a data-toggle="tab"
													href="#statistics"><i class="fa fa-bars"></i>Statistics</a></li>
												<li class=""><a data-toggle="tab"
													href="#comapre_statistics"><i
														class="fa fa-sort-amount-desc"></i>Compare Statistics</a></li>
											</ul>
											<div class="tab-content" style="height:350px;">
												<div id="statistics" class="tab-pane active">
													<br>
													<div class="row">
														<div class="col-sm-4">
															<div class="form-group">
																<label class="control-label" for="parameter">Select Parameter</label> <select class="form-control" id="parameter">
																	<option value="1" selected>Efficiency</option>
																	<option value="2">Capacity</option>
																	<option value="3">Energy Consumption</option>
																	<option value="4">Operational Hours</option>
																	<option value="5">CO2 Emission</option>
																	<option value="6">Setting Temperature</option>
																	<option value="7">Room Temperature</option>

																</select>
															</div>
														</div>

														<div class="col-sm-4">
															<div class="form-group" id="data_5" style="width: 155%">
																<label class="control-label">DateRange selection</label>
																<div class="input-daterange input-group" id="datepicker">
																	<span class="input-group-addon"><i
																		class="fa fa-calendar"></i></span> <input type="text"
																		class="input-sm form-control" name="start"
																		value="05/14/2014" /> <span class="input-group-addon">to</span>
																	<input type="text" class="input-sm form-control"
																		name="end" value="05/22/2014" />
																</div>
															</div>
														</div>
														<div class="col-sm-12">
															<div id="statistics_graph"></div>
														</div>

													</div>

												</div>
												<div id="comapre_statistics" class="tab-pane" style="height:150px;">
													<br>
													<div class="col-lg-12">
														<div class="col-lg-3" style="margin-top:-15px;">
															<ul class="stat-list">
																<li>
																	<div class="form-group">
																		<label class="control-label" for="stats_parameter">Select
																			Parameter</label> <select class="form-control"
																			id="stats_parameter">
																			<option value="1">Efficiency</option>
																			<option value="2">Capacity</option>
																			<option value="3">Energy Consumption</option>
																			<option value="4">Operational Hours</option>
																			<option value="5">CO2 Emission</option>
																			<option value="6">Setting Temperature</option>
																			<option value="7">Room Temperature</option>
																		</select>
																	</div>
																</li>
																<li style="margin-top:-15px;"><label>Compare :</label>
																	<div class="radio">
																		<label> <input type="radio" checked="checked"
																			value="option1" id="optionsRadios1"
																			name="optionsRadios">Years
																		</label>
																	</div>
																	<div class="radio">
																		<label> <input type="radio" value="option2"
																			id="optionsRadios2" name="optionsRadios">Months
																		</label>
																	</div>
																	<div class="radio">
																		<label> <input type="radio" value="option3"
																			id="optionsRadios3" name="optionsRadios">Weeks
																		</label>
																	</div>
																	<div class="radio">
																		<label> <input type="radio" value="option4"
																			id="optionsRadios4" name="optionsRadios">Days
																		</label>
																	</div></li>
																<li>
																	<div class="input-group" id="year"
																		style="display: none; width: 100%">
																		<label class="control-label"> Year View</label> <select
																			data-placeholder="Choose Years" class="chosen-select"
																			multiple style="width: 350px;" tabindex="4">
																			<option value=""></option>
																			<option value="2015">2015</option>
																			<option value="2014">2014</option>
																			<option value="2013">2013</option>
																			<option value="2012">2012</option>
																			<option value="2011">2011</option>
																			<option value="2010">2010</option>
																			<option value="2009">2009</option>
																			<option value="2008">2008</option>
																			<option value="2007">2007</option>
																			<option value="2006">2006</option>
																			<option value="2005">2005</option>
																			<option value="2004">2004</option>
																			<option value="2003">2003</option>
																			<option value="2002">2002</option>
																			<option value="2001">2001</option>
																			<option value="2000">2000</option>																			
																		</select>
																	</div>
																	<div class="form-group" id="month"
																		style="display: none;">
																		<label class="control-label">Month view</label>
																		<div class="input-group date">
																			<span class="input-group-addon"><i
																				class="fa fa-calendar"></i></span> <input type="text"
																				class="form-control" value="07/01/2014">
																		</div>
																	</div>
																	<div class="form-group" id="week"
																		style="display: none;">
																		<label class="control-label">Week view</label>
																		<div class="week-picker">
																			<input data-weekpicker="weekpicker" data-months="1" />
																		</div>
																	</div>
																	<div class="form-group" id="date"
																		style="display: none;width: 155%">
																		<label class="control-label">Range Selection</label>
																		<div class="input-daterange input-group"
																			id="datepicker">
																			<span class="input-group-addon" ><i
																				class="fa fa-calendar"></i></span> <input type="text"
																				class="input-sm form-control" name="start"
																				value="05/14/2014" /> <span
																				class="input-group-addon">to</span> <input
																				type="text" class="input-sm form-control" name="end"
																				value="05/22/2014" />
																		</div>
																	</div>
																</li>
															</ul>
														</div>
														<div class="col-lg-9">
															<div id="compare_statistics_graph"></div>
														</div>
													</div>
												</div>
											</div>
										</div>
									</div>
									</div>								
							</div>