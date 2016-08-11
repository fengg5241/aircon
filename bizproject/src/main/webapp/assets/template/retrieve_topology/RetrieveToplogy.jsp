<style>
#RetriveTopologyId .bizTable>thead>tr>th, #RetriveTopologyId .bizTable>tbody>tr>td{
width: 16%;
}

.head_me>thead>tr>th{

position:relative;
width: 2%;

}
</style>

<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal fade col-lg-12" id="RetriveTopologyId">
	<div class="modal-dialog modal-lg">
		<div class="modal-content bizModal_topology">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title text-center">
					<spring:message code="label.retrive" />
				</h3>

			</div>
			<div class="modal-body">
				<div>
					<div class="col-lg-12 ">
						<div class="row">
							<div class="col-sm-3">
								<label><spring:message code="label.customername" /></label>
							</div>
							<div class="col-sm-3">
								<label><spring:message code="label.site" /></label>
							</div>
							<div class="col-sm-3">
								<label><spring:message code="label.caname" /></label>
							</div>

						</div>
						<div class="row">
							<div class="col-sm-3">
								<select name="cost_name" class="tropology_gropdown"
									id="costomer_name">


								</select>
							</div>
							<div class="col-sm-3">
								<select name="site_name123" class="tropology_gropdown"
									id="site_name_topolgy">


								</select>
							</div>
							<div class="col-sm-3">
								<select name="ca_name_topology" class="tropology_gropdown"
									id="topology_ca_name">


								</select>
							</div>

							<div class="col-sm-3">
								<button id="TopologyRetrieve" type="button"
									class="btn bizButton-retrieve greyGradient">
									<spring:message code="label.retive" />
									&nbsp;<span class="fa fa-caret-right"></span>
								</button>
							</div>
						</div>

					</div>

				</div>
				<div class=row></div>
				<div class="col-lg-12 ">
					<div class="panel panel_toplogy">
						<div class="panel-toplogy-heading text-center">
							<spring:message code="label.caass" />
						</div>

					</div>
				</div>
				<div class="bs-example">
				<table class="head_me" style="text-align:center;width:98.8%">
				<thead>
				<tr class='greyGradient'>
					<th class='text-center'><spring:message code="label.slink" /></th>
					<th class='text-center'><spring:message code="label.dtypetitle" /></th>
					<th class='text-center'><spring:message code="label.mstitle" /></th>
					<th class='text-center'><spring:message code="label.dmtitle" /></th><th class='text-center'><spring:message code="label.datitle" /></th>
					<th class='text-center'><spring:message code="label.porttitle" /></th><th class='text-center'><spring:message code="label.lgtitle" /></th>
				</tr>
				</thead> 
				</table>
				<div id="tablebody" style="float: left;height: 300px;width: 100%;"> <!--css{overflow:auto}-->
					<table id="table_topology_id1"
						class="table table-striped table-hover text-center bizTable">
					</table>
				</div>
					<div class="col-lg-12 ">
						<div class="row">
							<div class="col-sm-5"></div>
							<div class="col-sm-3">
								<button id="topology_confirm" type="button"
									class="btn bizButton-retrieve greyGradient status">
									<spring:message code="label.cnf" />
									&nbsp;
								</button>
							</div>
							<div class="col-sm-3"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
