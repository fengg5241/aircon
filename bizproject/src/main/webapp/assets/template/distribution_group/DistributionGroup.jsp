<script src="../assets/js/sumoselect.js"></script>
<div class="modal fade col-lg-12" id="DBgroup">
	<div class="modal-dialog modal-lg">
		<div class="modal-content bizModal_dgroup">
			<div class="modal-header">
				<button type="button" class="close" data-dismiss="modal"
					aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
				<h3 class="modal-title text-center">
					<spring:message code="label.distr" />
				</h3>
			</div>
			<div class="modal-body">
				<div class="row ">
					<div class="col-lg-12 ">
						<div class="row ">
							<div class="col-sm-4">
								<label class="dst-label"><spring:message
										code="label.customername" /></label>
							</div>
							<div class="col-sm-4"></div>
							<div class="col-sm-4"></div>

						</div>
						<div class="row">
							<div class="col-lg-12 ">
								<div class="col-sm-3">
									<select name="cost_name" class="form-control"
										id="costomer_name_distribution">


									</select>


								</div>
							</div>
							<div class="col-sm-3"></div>
							<div class="col-sm-3"></div>
							<div class="col-sm-3"></div>

						</div>
					</div>
				</div>
				<div class="row">
					<ul class="nav nav-tabs group-tabs group_tab">
						<li class="active"><a data-toggle="tab"
							class="greyGradient bizTab" href="#groupView"><spring:message
									code="label.dsov" /></a></li>
						<!-- li><a data-toggle="tab" class="greyGradient bizTab" href="#statsRef">Statistics
										(Refrigerant Circuit)</a></li -->
						<li><a data-toggle="tab" id="DistributionGroupDetails"
							class="greyGradient bizTab" href="#gropDetails"><spring:message
									code="label.dsdt" /></a></li>

					</ul>
					<div class="tab-content">
						<div id="groupView" class="tab-pane active">

							<div class="panel-heading"></div>
							<div class="panel-body no-padding">
								<%@ include file="../settings/distributionGroupOverview.jsp"%>
							</div>


						</div>
						<div id="gropDetails" class="tab-pane">
							<div class="panel-body no-padding">
								<%@ include file="../settings/distributionGroupDetails.jsp"%>
							</div>

						</div>
					</div>
					<!-- End of Tab content-->
				</div>
			</div>
			<!--Modal Body-->
		</div>
	</div>
</div>


