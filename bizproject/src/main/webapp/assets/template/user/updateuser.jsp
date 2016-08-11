<div class="userscrren1panel panel-default updatepanel">
	<div class="userscrren1panel-heading">
		<div class="row">
			<div class="col-xs-4 text-center"></div>
			<div class="col-xs-4 text-centers">
				<strong><spring:message code="label.user.update.header"/></strong>
			</div>
			<div class="col-xs-4 text-center"></div>
		</div>
	</div>
	<div class="userscrren1panel-body">
		<div class="row">
			<div class="col-xs-3"></div>


			<div class="col-xs-2">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.userid"/>:</label> <select class="form-control"
							placeholder="Select Role" name="role" id="updateuserid">
                     
						</select>
					</div>
				</form>
			</div>
			
			<div class="col-xs-2">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.role"/>:</label> <select class="form-control"
							placeholder="Select Role" name="role" id="updaterolelist">
						</select>
						
					</div>
				</form>
			</div>
			<div class="col-xs-2">
				<form role="form" class="form-group">
					<div class="form-group">
						<label><spring:message code="label.account.state"/>:</label> <select class="form-control" id="updateaccountstate"
							placeholder="Select Role" name="role">
                      <option value="" disabled selected>Please Select</option>
                      <option value="Valid">Valid</option>
                      <option value="Invalid">Invalid</option>
                      <option value="Locked">Locked</option>
						</select>
					</div>
				</form>
			</div>



		</div>

	</div>

</div>
<div class="userscrren2panel panel-default updatepanel">
	<div class="userscrren2panel-heading">
		<div class="row">
			<div class="col-xs-4 text-center"></div>
			<div class="col-xs-4 text-centers">
				<strong><spring:message code="label.user.update.access"/></strong>
			</div>
			<div class="col-xs-4 text-center"></div>
		</div>
	</div>
	<div class="userscrren2panel-body">
		<div class="row">
			<div class="col-xs-3"></div>
			<div class="col-xs-6">
				<!-- <img alt="image" class="userimage" src="../assets/img/userlogo2.PNG"> -->
				<button type="button" class="btn btn-outline btn-default" id="roleregistid"><spring:message code="label.plsel"/></button>
				<div id="treeFrames" class="greyBackground bizFontColor">
					<div id="treeContent">
						<div id="treeBody" style="height:450px;">
							<div id="checkboxMenuTree" class="font13"></div>
						</div>
					</div>
				</div>

			</div>


		</div>
		<br> <br> <br> <br> <br> <br> <br>
		<br> <br> <br> <br> <br> <br> <br>
		<br> <br> <br> <br> <br> <br> <br>
		<br> <br> <br> <br> <br> <br>
		<div class="row">
			<div class="col-xs-4"></div>
			<div class="col-xs-6">
				<form role="form" class="form-group">
					<div class="form-group">
						<button id="resetgroup" type="button"
							class="btn bizButtons greyGradient">
							<spring:message code="label.resetgroup"/> &nbsp;<span class="fa fa-caret-right"></span>
						</button>
						<button id="updateuser" type="button" 
							class="btn bizButtons greyGradient">
							<spring:message code="label.update"/> &nbsp;<span class="fa fa-caret-right"></span>
						</button>
						<button  type="button" data-toggle="modal"
							data-target="#operationLogModal"
							class="btn bizButtons greyGradient">
							<spring:message code="label.history"/> &nbsp;<span class="fa fa-caret-right"></span>
						</button>
						<button id="updateresetpassword" type="submit" disabled
							class="btn bizButtons greyGradient">
							<spring:message code="label.resetpassword"/> &nbsp;<span class="fa fa-caret-right"></span>
						</button>
						<%@ include file="userupdatepopup.jsp"%>						
					</div>
				</form>
			</div>
		</div>

	</div>
</div>

