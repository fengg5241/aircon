<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="panel-default white-bg">
	<div class="row">
		<div class="col-lg-12">
			<div class="col-lg-4">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.selectsite" />:</label> <br />
						<select id="dropdown-menu-customer-site"
							style="height: 30px; width: 267px; display: inline-block; margin-bottom: 2px; margin-top: 3px;"
							multiple>

						</select>
					</div>
				</form>
			</div>
			<div class="col-lg-4">

				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.catitle" />:</label> <br /> <select
							id="dropdown-menu-customer-cloud-adatpor"
							style="height: 30px; width: 267px; display: inline-block; margin-bottom: 2px; margin-top: 3px;"
							multiple>

						</select>
					</div>
				</form>
			</div>
			<div class="col-lg-4"></div>
		</div>
	</div>

</div>
<div class="bizBlackSeparatedBar text-center">
	<strong><spring:message code="label.distpum" /></strong>
</div>

<table id="table_distribution_group_pls_id_final_window_customer"
	class="table-striped table-hover text-center bizTable mCustomScrollbar"
	style="margin-bottom: 0px" width="100%">
	<thead>
		<tr class='greyGradient'>
			<th class='text-center'><spring:message code="label.porttitle" /></th>
			<th class='text-center'><spring:message code="label.devicename" /></th>
			<th class='text-center'><spring:message code="label.metertitle" /></th>
			<th class='text-center'><spring:message code="label.distr" /></th>
		</tr>
	</thead>
	<tbody>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>
		<tr>
			<td></td>
			<td></td>
			<td></td>
			<td></td>
		</tr>

	</tbody>

</table>

<div class="bizBlackSeparatedBar text-center">
	<strong><spring:message code="label.diiptitle" /></strong>
</div>

<div class="table-responsive_distribution_group">
	<table id="table_distribution_group_idu_id_final_window_customer"
		class="table-striped table-hover text-center bizTable mCustomScrollbar"
		style="margin-bottom: 0px" width="100%">

		<thead>
			<tr class='greyGradient'>
				<th class='text-center'><spring:message code="label.slink" /></th>
				<th class='text-center'><spring:message code="label.devicename" /></th>
				<th class='text-center'><spring:message
						code="label.actypetitle" /></th>
				<th class='text-center'><spring:message code="label.distr" /></th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>
			<tr>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
			</tr>

		</tbody>
	</table>
</div>

<div class="row">
	<div class="col-sm-3"></div>
	<div class="col-sm-3">
		<button id="final_add_update" type="button"
			class="btn Add-Update greyGradient">
			&nbsp;
			<spring:message code="label.au" />
		</button>
	</div>
	<div class="col-sm-3"></div>
	<div class="col-sm-3"></div>
</div>