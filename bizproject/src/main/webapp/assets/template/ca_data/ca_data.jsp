
<div class="container-fluid">
	<div class="row">

		<div class="col-md-2">
			<div class="col-md-12 thumbnail"
				style="background-color: #ccc; padding: 10px; border-radius: 5px; height: 760px;">
				<div class="text-center top-buffer"
					style="font-weight: bold; background-color: #e6e6e6; border-radius: 5px; height: 250px;">
					<div>
						<spring:message code="label.changes" />
						:
					</div>

					<div class="center-block" style="margin-top: 10px; width: 160px;">
						<button id="displayButton" type="button"
							class="btn btn-default btn-sm btn-block  greyGradient center-block"
							data-toggle="modal" data-target="#myModal1">
							<spring:message code="label.newca" />
							&nbsp;<span class="fa fa-caret-right"></span>
						</button>
					</div>


					<div class="center-block" style="margin-top: 10px; width: 160px;">
						<button id="displayButton" type="button"
							class="btn btn-default btn-sm btn-block  greyGradient center-block"
							data-toggle="modal" data-target="#myModal2">
							<spring:message code="label.caass" />
							&nbsp;<span class="fa fa-caret-right"></span>
						</button>
					</div>


				</div>


				<div class="text-center top-buffer"
					style="font-weight: bold; background-color: #e6e6e6; border-radius: 5px; height: 250px;">
					<div style="margin-top: 10px; margin-bottom: 20px;">
						<spring:message code="label.changes" />
						:
					</div>

					<div class="center-block" style="margin-top: 10px; width: 160px;">
						<button id="displayButtonRetrive" data-toggle="modal"
							data-target="#RetriveTopologyId" type="button"
							class="btn btn-default btn-sm btn-block  greyGradient center-block">
							<spring:message code="label.retrive" />
							&nbsp;<span class="fa fa-caret-right"></span>
						</button>
					</div>
					<div class="center-block" style="margin-top: 10px; width: 160px;">
						<button id="displayButtonDistribution" type="button"
							data-toggle="modal" data-target="#DBgroup"
							class="btn btn-default btn-sm btn-block  greyGradient center-block">
							<spring:message code="label.distr" />
							&nbsp;<span class="fa fa-caret-right"></span>
						</button>
					</div>

				</div>

			</div>


		</div>


		<div class="col-md-10">
            <div class="row">
			<div class="col-md-12" style="background-color: #ccc; padding: 10px;">
				<div
					style="font-weight: bold; background-color: #e6e6e6; padding: 10px;">
					<div>
						<spring:message code="label.cadetail" />
						:
					</div>

				</div>
				<div class="col-md-12"
					style="font-weight: bold; background-color: #fff; padding: 5px;">

					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.custn" /></label> <select
							class="form-control all_select" name="cust_name_search"
							id="cust_name_search">

						</select>

					</div>

					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.camac" /></label> <select
							class="form-control all_select ca_mac_show" name="ca_mac" id="ca_mac">

						</select>
					</div>


					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.caname" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="ca_name" id="ca_name" />
					</div>
					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.bui" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="building" id="building" />
					</div>
					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.location" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="location" id="location" />
					</div>
				</div>
				<div class="col-sm-12"
					style="font-weight: bold; background-color: #fff; padding: 5px;">
					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.install" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="installation_date" id="installation_date" />
					</div>
					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.service" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="service_start" id="service_start" />
					</div>
					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.serveend" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="service_end" id="service_end" />
					</div>
					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.camodel" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="ca_model" id="ca_model" />
					</div>
					<div class="col-md-15 col-sm-3">
						<label><spring:message code="label.status" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="status" id="status" />
					</div>
				</div>
				<div class="col-sm-12 "
					style="font-weight: bold; background-color: #fff; padding: 0px; height: 100px;">
					<div class="col-sm-12">
						<label><spring:message code="label.fw" /></label> <input
							class="form-control all_search_display" readonly type="text"
							name="fw_version" id="fw_version" />
					</div>

				</div>
			</div>
            </div>
            <%@ include
						file="../../../../assets/template/dashboard/caDeviceDetail.jsp"%> 
            
            
        </div>

	</div>
</div>
</div>
<input id="curPageNameHidden" type="hidden" value="homeScreen">



<div class="modal fade" id="myModal1" role="dialog">
	<div class="modal-dialog">
		<!-- Modal content-->
		<div class="modal-content"
			style="background-color: #ccc; padding: 10px; border-radius: 0px;">
			<div class="border_popup" style="background-color: #fff;">
				<div class="modal-header text-center">
					<button type="button" class="close" data-dismiss="modal"
						style="color: #000; opacity: 1;">&times;</button>
					<h4 class="modal-title center-block">
						<spring:message code="label.register" />
						<spring:message code="label.newca" />
					</h4>
					<div class="col-sm-12">
						<div class="col-sm-12">
							<hr style="border: 1px solid #000;">
						</div>
					</div>
				</div>
				<div class="modal-body">
					<div class="row">
						<div class="col-sm-12">


							<div class="col-sm-12 padding-5">
								<div class="col-sm-6">
									<label><spring:message code="label.custmers" /></label>
								</div>
								<div class="col-sm-6">
									<select name="customer_name" class="form-control all_select"
										id="customer_name">

									</select>
								</div>
							</div>

							<div class="col-sm-12 padding-5">
								<div class="col-sm-6">
									<label><spring:message code="label.camodel" /></label>
								</div>
								<div class="col-sm-6">
									<select name="ca_model_reg" class="form-control all_select"
										id="ca_model_reg">

									</select>
								</div>
							</div>

							<div class="col-sm-12 padding-5">
								<div class="col-sm-6">
									<label><spring:message code="label.camac" /></label>
								</div>
								<div class="col-sm-6">
									<input type="text" class="form-control" value=""
										name="ca_mac_address_reg" id="ca_mac_address_reg">
								</div>
							</div>


							<div class="col-sm-12 padding-5">
								<div class="col-sm-6">
									<label><spring:message code="label.secdomaoin" /></label>
								</div>
								<div class="col-sm-6">
									<input type="text" value="" readonly
										class="form-control" name="sec_domain" id="sec_domain">
								</div>
							</div>
							<div class="col-sm-12 padding-5">
								<div class="col-sm-6">
									<label><spring:message code="label.pergrps" /></label>
								</div>
								<div class="col-sm-6">
									<input type="text" value="" name="permission_grp" readonly
										class="form-control" id="permission_grp">
								</div>
							</div>
							<div class="col-sm-12 padding-5">
								<div class="col-sm-6">
									<label><spring:message code="label.perm" /></label>
								</div>
								<div class="col-sm-6">
									<input type="text" value="<1:*;*;*;*>" readonly
										name="permission" class="form-control" id="permission">
								</div>
							</div>

						</div>
					</div>
					<div class="modal-footer" style="border-top: 0px;">

						<button id="register" type="button"
							class="btn btn-default btn-sm greyGradient center-block"
							data-toggle="modal" data-target="#myModal1" style="width: 130px;">
							<spring:message code="label.register" />
							&nbsp;<span class="fa fa-caret-right"></span>
						</button>


					</div>
				</div>

			</div>
		</div>
	</div>
</div>

<div class="container row">
	<div class="modal fade col-sm-12" id="myModal2" role="dialog">
		<div class="modal-dialog" style="width: 950px;">
			<!-- Modal content-->
			<div class="modal-content"
				style="background-color: #ccc; padding: 10px; border-radius: 0px;">
				<div class="border_popup" style="background-color: #fff;">
					<div class="modal-header text-center">
						<button type="button" class="close" data-dismiss="modal"
							style="color: #000; opacity: 1;">&times;</button>
						<h4 class="modal-title center-block">
							<spring:message code="label.caass" />
						</h4>
						<div class="col-sm-12">
							<div class="col-sm-12">
								<hr style="border: 1px solid #000;">
							</div>
						</div>
					</div>
					<div class="modal-body">
						<div class="row">
							<div class="col-sm-12">
								<div class="col-sm-6">
									<label><spring:message code="label.custmers" /></label> <select
										name="cust_name" id="cust_name"
										class="form-control all_select">

									</select>
								</div>

								<div class="col-sm-6">
									<label><spring:message code="label.siteid" /></label> <select
										name="site_id" id="site_id" class="form-control all_select">

									</select>
								</div>


								<div class="col-sm-6">
									<label><spring:message code="label.camac" /></label> <select
										name="ca_mac_address" id="ca_mac_address"
										class="form-control all_select">

									</select>

								</div>

								<div class="col-sm-6">
									<label><spring:message code="label.caname" /></label> <input
										type="text" class="form-control" value=""
										name="ca_name_associate" id="ca_name_associate">

								</div>
							</div>
						</div>

						<div class="row" style="padding: 18px;">
							<div class="col-sm-12"
								style="background-color: #ccc; border-radius: 5px;">
								<div class="modal-header text-center">

									<h4 class="modal-title center-block">
										<spring:message code="label.setings" />
									</h4>
									<div class="col-sm-12">
										<div class="col-sm-12">
											<hr style="border: 1px solid #000;">
										</div>
									</div>
								</div>

								<div class="col-sm-12">

									<div class="col-sm-6 padding-5">
										<label><spring:message code="label.timezone" /></label> <select
											name="timezone" id="Timezone" class="form-control">
										</select>
									</div>
									<div class="col-sm-6 padding-5">
										<label><spring:message code="label.loclat" /></label> <input
											type="text" value="" name="latitude" id="Latitude"
											class="form-control">
									</div>
									<div class="col-sm-6 padding-5">
										<label><spring:message code="label.loclag" /></label> 
										<input type="text" value="" name="longitude" id="Longitude"
											class="form-control">

									</div>
									
									<div class="col-sm-6 padding-5 form-group has-feedback" >
										<label><spring:message code="label.utc" /></label>
										<input type="text" value="" name="longitude" id="installed_at"
											class="form-control">    <i class="fa fa-calendar form-control-feedback" style="top:36px;"></i>
											
									</div>


									<!-- <div class="col-sm-6 padding-5">
									<label><spring:message code="label.utc" /></label>
									
										<div style="width: 407px; float: right;">
											<input type='text' id='installed_at' class="form-control" />
											 <span class="input-group-addon form-control" id="calender-icon" style="width: 10%; float: right;border-radius:1px;"><span
												class="glyphicon glyphicon-calendar"></span>
											</span>
										</div>
									</div>-->

								</div>




								<div class="col-sm-12 modal-header text-center">

									<h4 class="modal-title center-block">
										<spring:message code="label.status" />
									</h4>
									<div class="col-sm-12">
										<div class="col-sm-12">
											<hr style="border: 1px solid #000;">
										</div>
									</div>
								</div>


								<input type="hidden" id="model_name" value="">


								<div class="col-sm-12">


									<div class="col-sm-3">

										<div class="col-sm-2">
											<input type="radio" disabled name="status" id="disable">
										</div>
										<div class="col-sm-8">
											<label><spring:message code="label.dis" /></label>
										</div>

									</div>

									<div class="col-sm-3">

										<div class="col-sm-2">
											<input type="radio" name="status" value="1" checked>
										</div>
										<div class="col-sm-8">
											<label><spring:message code="label.enb" /></label>
										</div>

									</div>

									<div class="col-sm-6">

										<div class="col-sm-1">
											<input type="radio" name="status" value="" disabled>
										</div>
										<div class="col-sm-5">
											<label><spring:message code="label.enbon" /></label>
										</div>
										<div class="col-sm-6">
											<input type="text" class="form-control" value="" disabled
												name="status_enable_at" id="status_enable_at">
										</div>

									</div>


								</div>

								<div class="modal-footer" style="border-top: 0px;">

									<button id="associate" type="button"
										class="btn btn-default btn-sm greyGradient center-block"
										data-toggle="modal" data-target="#myModal2"
										style="width: 130px; border-color: #adadad;">
										<spring:message code="label.ass" />
										&nbsp;<span class="fa fa-caret-right"></span>
									</button>

								</div>

							</div>



						</div>


					</div>

				</div>
			</div>
		</div>
	</div>