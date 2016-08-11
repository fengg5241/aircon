<div class="userscrren1panel panel-default">
	<div class="userscrren1panel-heading">
		<div class="row">
			<div class="col-xs-4 text-center"></div>
			<div class="col-xs-4 text-centers">
				<strong><spring:message code="label.user.header"/></strong>
			</div>
			<div class="col-xs-4 text-center"></div>
		</div>
	</div>
	<div class="userscrren1panel-body">
		<div class="row">
			<div class="col-xs-3"></div>
			<div class="col-xs-3">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.user.generateid"/>:</label>
						<div class="btn-group">
							<button type="button" class="btn bizButtons greyGradient"
								id="passwordgeneration">
								<spring:message code="label.user.generate"/> &nbsp;<span class="fa fa-caret-right"></span>
							</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>
</div>
<div class="userscrren2panel panel-default">
	<div class="userscrren2panel-heading">
		<div class="row">
			<div class="col-xs-4 text-center"></div>
			<div class="col-xs-4 text-centers">
				<strong><spring:message code="label.user.useracceseheader"/></strong>
			</div>
			<div class="col-xs-4 text-center"></div>
		</div>
	</div>
	<div class="userscrren2panel-body">
		<div class="row">
			<div class="col-xs-3"></div>
			<div class="col-xs-3">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.userid"/>:</label> <input type="text"
							placeholder="Enter UserID" style="text-align: center;" id="userid"
							class="form-control">
					</div>
				</form>
			</div>
			<div class="col-xs-3">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.role"/>:</label> <select class="form-control"
							placeholder="Select Role" name="role" id="rolelist">

						</select>
						
					</div>
				</form>
			</div>			
		</div>
		<div class="row">
			<!-- <div class="col-xs-3" id="companylistids" style="display:none;">
			<button type="button" class="btn btn-outline btn-default" id="companylistid" style="display:none;">Please Select Customer ID</button>
			</div>
			<div class="col-xs-3" id="companylist" style="display:none;">
			</div> -->
			<div class="col-xs-3">
			</div>
			<div class="col-xs-6">
				<!-- <img alt="image"  class="userimage" src="../assets/img/userlogo.PNG"> -->
				<button type="button" class="btn btn-outline btn-default" id="roleregistid"><spring:message code="label.plsel"/></button>
				<div id="treeFrame" class="greyBackground bizFontColor">
					<div id="treeContent">
						<div id="treeBody" style="height:450px;">
							<div id="checkboxMenuTrees" class="userpanel font13"></div>							
						</div>
					</div>
				</div>

			</div>
		</div><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br><br>
		
		<div class="row">
			<div class="col-xs-4"></div>
			<div class="col-xs-4">
				<form role="form" class="form-group">
					<div class="form-group">
						<button id="resetselectionuser" type="button" 
					class="btn bizButtons greyGradient">
					<spring:message code="label.user.reset"/> &nbsp;<span class="fa fa-caret-right"></span>
				</button>
				<button id="registerUser" type="button"
					class="btn bizButtons greyGradient">
					<spring:message code="label.register"/> &nbsp;<span class="fa fa-caret-right"></span>
				</button>
					</div>
				</form>
			</div>			
		</div>		
	</div>
</div>

