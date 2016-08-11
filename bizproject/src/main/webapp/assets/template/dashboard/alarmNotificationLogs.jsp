<div class="col-md-6 nopadding-right" id="notification">
								<div class="ibox float-e-margins">
									<div class="ibox-content" style="height:370px;margin-top:-22px;">
											<ul class="nav nav-tabs">
												<li class="active"><a data-toggle="tab"
													href="#sort_notifications"><i
														class="fa fa-sort-amount-desc"></i>Notification Rank</a></li>
												<li class=""><a data-toggle="tab" href="#notifications"><i
														class="fa fa-bars"></i>Notification Detail</a></li>
											</ul>
											<div class="tab-content">
												<div id="sort_notifications" class="tab-pane active">
													<br>
													<div class="row  m-t-sm" style="margin-top: -5px;">
														<div class="col-sm-8">
															<div class="form-group" id="data_5">
																<div class="input-daterange input-group" id="datepicker">
																	<span class="input-group-addon"><i
																		class="fa fa-calendar" title="Select Dates"></i></span> <input
																		type="text" class="input-sm form-control" name="start"
																		value="05/14/2014" /> <span class="input-group-addon">to</span>
																	<input type="text" class="input-sm form-control"
																		name="end" value="05/22/2014" />
																</div>
															</div>
														</div>
														<div class="col-sm-4" id="table">
															<div class="icon-type pull-right">
																<i class="pull-middle fa fa-table" title="Table View"
																	style="font-size: 25px;"> </i>
															</div>
														</div>
														<div class="col-sm-4" style="display: none;" id="graph">
															<div class="icon-type pull-right">
																<i class="pull-middle fa fa-bar-chart-o"
																	title="Graph View" style="font-size: 25px;"> </i>
															</div>
														</div>
														<div class="col-sm-12" id="graphs">
															<div id="statistics_graphs"></div>
														</div>
														<div class="col-sm-12" id="tables"
															style="display: none;">
															<div class="jqGrid_wrapper">
																<table id="table_list_1"></table>
															</div>
														</div>
													</div>
												</div>
												<div id="notifications" class="tab-pane" style="height:350px;">
													<br>
													<div class="row  m-t-sm">
														<div class="row m-t-sm" style="margin-top: -11px;">
															<div class="col-sm-1">
																<div class="button">
																	<i class=" fa fa-filter" title="Edit filter section"
																		data-toggle="modal" data-target="#myModal5"
																		style="font-size: 30px;<!-- color:orange; -->"> </i>
																</div>
															</div>
															<div class="modal inmodal fade" id="myModal5"
																tabindex="-1" role="dialog" aria-hidden="true">
																<div class="modal-dialog ">
																	<div class="modal-content">
																		<div class="modal-header">
																			<button type="button" class="close"
																				data-dismiss="modal">
																				<span aria-hidden="true">&times;</span><span
																					class="sr-only">Close</span>
																			</button>
																			<h4 class="modal-title">Select Required Filters</h4>
																		</div>
																		<div class="modal-body">
																			<div class="input-group" style="width: 100%">
																				<select data-placeholder="Choose filter..."
																					class="chosen-select" multiple
																					style="width: 350px;" tabindex="4">
																					<option value=""></option>
																					<option value="New">New</option>
																					<option value="On hold">On hold</option>
																					<option value="Fixed">Fixed</option>
																					<option value="IDUs">IDUs</option>
																					<option value="ODUs">ODUs</option>
																					<option value="All Alarms">All Alarms</option>
																					<option value="All Subgroups">All Subgroups</option>
																				</select>
																			</div>
																		</div>
																		<div class="modal-footer">
																			<button type="button" class="btn btn-white"
																				data-dismiss="modal">Close</button>
																			<button type="button" class="btn btn-primary">Filter</button>
																		</div>
																	</div>
																</div>
															</div>
															<div class="col-sm-8">
																<div class="form-group" id="data_5">
																	<div class="input-daterange input-group"
																		id="datepicker">
																		<span class="input-group-addon"><i
																			class="fa fa-calendar" title="Select Dates"></i></span> <input
																			type="text" class="input-sm form-control"
																			name="start" value="05/14/2014" /> <span
																			class="input-group-addon">to</span> <input
																			type="text" class="input-sm form-control" name="end"
																			value="05/22/2014" />
																	</div>
																</div>

															</div>

														</div>
														<br>
														<div class="table-height">
															<div class="table-responsive" style="height:220px;">
																<table class="table table-striped table-hover">
																	<tbody>
																		<tr>
																			<td class="client-status"><span
																				class="label label-danger">New</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: red;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>
																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-danger">New</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-warning">On hold</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: red;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Engine Maintance due on 04/17/2015</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Lorem ipsum dolor sit amet</td>

																		</tr>
																		<tr>
																			<td class="client-status"><span
																				class="label label-primary">Fixed</span></td>
																			<td class="icon-type"><i
																				class="pull-middle fa fa-bell"
																				style="font-size: 20px; color: orange;"> </i></td>
																			<td>Alaram Reference</td>
																			<td>Ac Unit Reference</td>
																			<td>Filter is dirty</td>
																			<td>11/5/2015 10:35</td>

																		</tr>
																	</tbody>
																</table>
															</div>
														</div>
													</div>
												</div>
											</div>
										</div>
								</div>
							</div>