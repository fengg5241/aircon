<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<div class="container-fluid">
	<div class="row">




		<div class="col-md-12">
			<div class="col-md-12" style="background-color: #ccc; padding: 10px;">
				<div
					style="font-weight: bold; background-color: #ccc; padding: 0px;"
					class="col-sm-12">
					<div class="col-sm-2"
						style="background-color: #324B8C; padding-top: 5px; padding-bottom: 5px; padding-left: 50px; color: #fff;">
						<spring:message code="label.custreg" />
					</div>

				</div>
				<div
					style="font-weight: bold; background-color: #e6e6e6; padding-top: 15px;"
					class="text-center">
					<div style="padding-top: 21px; padding-bottom: 7px;">
						<spring:message code="label.custdetail" />
					</div>

				</div>



				<div class="col-md-12"
					style="font-weight: bold; background-color: #fff; padding: 5px; padding-bottom: 5%;"">

					<div class="col-sm-4"></div>


					<div class="col-sm-2">
						<label><spring:message code="label.custn" /></label> <select
							class="form-contro" name="customer_name" id="cstmr_name">

						</select>
					</div>
					<div class="col-sm-2" style="padding-bottom: 15%;">
						<label><spring:message code="label.bui" /></label>
						<div class="form-control text-center" readonly="">
							<spring:message code="label.selectsite" />
							<span class="fa fa-caret-down pull-right"></span>
						</div>
						<div class="insert_all_sites"></div>
					</div>
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
		</div>

	</div>
</div>

<input id="curPageNameHidden" type="hidden" value="homeScreen">