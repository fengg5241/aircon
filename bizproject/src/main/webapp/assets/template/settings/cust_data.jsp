
			<div class="col-md-12" style="background-color: #ccc;padding-left: 1px;padding-right: 1px;">
				<div
					style="font-weight: bold; background-color: #e6e6e6; padding-top: 15px;padding-bottom: 15px;"
					class="text-center">
					
						<spring:message code="label.custdetail" />
					

				</div>



				<div class="col-md-12"
					style="font-weight: bold; background-color: #fff; padding: 5px; padding-bottom: 5%;"">

					<div class="col-sm-3"></div>


					<div class="col-sm-2">
						<label><spring:message code="label.custn" /></label> <select
							class="form-contro" name="customer_name" id="cstmr_name">

						</select>
					</div>
					<div class="col-sm-3" style="padding-bottom: 15%;">
						<label><spring:message code="label.bui" /></label>
						<div class="form-control text-center" readonly="">
							<spring:message code="label.selectsite" />
							<span class="fa fa-caret-down pull-right"></span>
						</div>
						<div class="insert_all_sites"></div>
					</div>
					<div class="col-sm-3"></div>
					<br> <br> <br> <br> <br> <br> <br>
					<br> <br> <br>
					<br> <br>



					<div class="center-block" style="margin-top: 10px; width: 160px;">
						<button id="register_site" type="button"
							class="btn btn-default btn-sm btn-block  greyGradient center-block">
							<spring:message code="label.register" />
							&nbsp;<span class="fa fa-caret-right"></span>
						</button>
					</div>




				</div>





			</div>



<input id="curPageNameHidden" type="hidden" value="homeScreen">