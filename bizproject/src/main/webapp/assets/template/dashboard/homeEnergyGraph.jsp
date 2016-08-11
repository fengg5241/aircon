<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="notifyhomepanel panel-default" style="height:445px;">
	<div class="notifyhomepanel-heading">
		<div class="row">
			<div class="col-xs-12 text-center">
				<b><spring:message code="label.energyconsumption"/></b>
			</div>
		</div>
	</div>
	<div class="notifyhomepanel-body" style="height:385px;">
		<div class="col-xs-2" style="margin-top:21px;"></div>
		<div id="energy_graph"></div>
		<!-- <label>Period:</label> -->	
		<div class="input-daterange input-group" id="energy_home_datepicker" style="margin-left: 18px;width: 343px;">		 
			<span class="input-group-addon"><i class="fa fa-calendar"></i></span>
			<input type="text" class="input-sm form-control" name="start"
				id="start" readonly="true" style="background-color: #fff;" /> <span class="input-group-addon">to </span> <input
				type="text" class="input-sm form-control" name="end" id="end" readonly="true" style="background-color: #fff;" 
				 />
		</div>
		<button id="energy" type="button"
			class="btn graphButton greyGradient pull-right">
			<spring:message code="label.compare"/>&nbsp;<span class="fa fa-caret-right"></span>
		</button>

	</div>
</div>