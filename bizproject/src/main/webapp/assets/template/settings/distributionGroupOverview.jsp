<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<style>
#distgroupoverview .form-group label {
    font-size:0.9em;
    white-space:nowrap;
}

   
</style>
<div class="panel-default white-bg" id="distgroupoverview">
	<div class="row">
		<div class="col-lg-12 ">

			<div class="col-sm-3">
                <form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.distr" /></label> 
                        <select class="form-control" name="dgroup_gropdown1" id="dgroup_gropdown">
 					        <option value=""><spring:message code="label.pleaseselect" /></option>
					        <option value="NEW"><spring:message code="label.adddist" /></option>
						</select>
					</div>
				</form>
			</div>
            <!--
			<div class="col-sm-1">
				<select class="dgroup_gropdown_customer_name all_select" disabled
					name="cust_name_search" id="cust_name_search">
					<option value=""><spring:message code="label.plsltitle" /></option>
				</select>
			</div>
            -->
			<div class="col-sm-3">

                <form role="form" class="form-group">
					<div class="form-group">
						<label class= "distribution_group_name"><spring:message code="label.endttitle" /></label>
				        <input id="new_group_name" class="form-control" type="text" value="">
					</div>
				</form>
			</div>



			<div class="col-sm-2">
                <form role="form" class="form-group">
					<div class="form-group">
						<label class="type"><spring:message code="label.type" /></label>
					
                    <select name="dgroup_gropdown_type" class="form-control"
                        id="dgroup_gropdown_type">
                        <option value=""><spring:message code="label.pleaseselect" /></option>
                        <option value="VRF"><spring:message code="label.v" /></option>
                        <option value="GHP"><spring:message code="label.g" /></option>

                    </select>
                    </div>
			</div>


			<div class="col-sm-2">
					<div class="form-group">
						<label class="CalculationCode"><spring:message code="label.distc" /></label>
				
                    <select name="Calculation_gropdown_type" class="form-control"
                        id="Calculation_gropdown_type">
                        <option value=""><spring:message code="label.pleaseselect" /></option>
                        <option value="Working Time"><spring:message
                                code="label.working.time" /></option>
                        <option value="Thermo ON"><spring:message
                                code="label.ther" /></option>

                    </select>
                    </div>
			</div>
            <div class="col-sm-2">
                <div class="form-group">
                    <label>&nbsp;</label>
                    <br/>
					<button id="Add" type="button"
						class="btn bizButton greyGradient" style="margin-top: 0px; ">
						<spring:message code="label.a" />
						&nbsp;<span class="fa fa-caret-right"></span>
					</button>
                    <button id="Remove" type="button"
                        class="btn bizButton greyGradient" style="margin-top: 0px; display: none;">
                        <spring:message code="label.remove" />
                        &nbsp;<span class="fa fa-caret-right"></span>
                    </button>
                  </div>
            </div>
		</div>

		<div class="col-lg-12 ">
			<div class="row">

			</div>
		</div>

        <!--Move the row end of div to front-->
        </div>


</div>

        <div class="bizBlackSeparatedBar text-center"><strong><spring:message code="label.distpum" /></strong></div>
        
<div class="panel-default dialogGreyBackground">
		<table id="table_distribution_group_idu_id"
			class="table table-striped table-hover text-center bizTable mCustomScrollbar" style="margin-bottom: 0px" width="100%">
			<thead>
				<tr class="greyGradient">
					<th class="text-center"><spring:message
							code="label.devicename" /></th>
					<th class="text-center"><spring:message code="label.site" /></th>
					<th class="text-center"><spring:message code="label.porttitle" /></th>
					<th class="text-center"><spring:message code="label.caname" /></th>
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

        <div class="bizBlackSeparatedBar text-center"><strong><spring:message code="label.distind" /></strong></div>

<div class="panel-default dialogGreyBackground">
            
			<table id="table_distribution_group_pls_id"
				class="table table-striped table-hover text-center bizTable mCustomScrollbar" style="margin-bottom: 0px" width="100%" >

				<thead>
					<tr class='greyGradient'>
						<th class='text-center'><spring:message
								code="label.devicename" /></th>
						<th class='text-center'><spring:message code="label.site" /></th>
						<th class='text-center'><spring:message code="label.slink" /></th>
						<th class='text-center'><spring:message code="label.caname" /></th>
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
<style>
	#table_distribution_group_pls_id_final_window_customer,
	#table_distribution_group_idu_id_final_window_customer{
		
		width: 100%;
		
	}
	
	.distribution_group_name{
		    margin-top: 0px;
	}
	
	
</style>
<script type="text/javascript">
        var strings = new Array();
		strings['j.spring.message.label.adddist'] = "<spring:message code='label.adddist' javaScriptEscape='true' />";
 </script>
