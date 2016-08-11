<div class="panel panel-default">
	<div class="rolepanel-heading">
		<div class="table-responsive" style="height: 750px;">
			<table class="table table-striped table-hover text-center "
				id="tab_logic">
				<thead>
					<tr style="background-color:#E6E6E6;">
						<th class="text-center textRole"><spring:message code="label.role"/></th>
						<th class="text-center textRoletype" ><spring:message code="label.roletype"/></th> 
						<th class="text-center textGroup"><spring:message code="label.functiongroup"/></th>
						<th class="text-center hideone" style="display: none;" id="triggerclick"></th>
						<th class="text-center textnewRole" title='Add Role' id="add_row"><spring:message code="label.addrole"/> &nbsp;<img
							alt="image" src="../assets/img/add.png" > 
						</th>
					</tr>
				</thead>
				
				<tr id='addr0' data-id="0" class="hidden" >
					<td data-name="name"><input type="text" name='name0' id="rolename" 
						placeholder='Enter Role' class="form-control rolename" 
						style="text-align: center;" disabled/></td>						
					 <td data-name="mails">
					 <select data-placeholder="Please Select" 
						class="chosen-select roletype"  tabindex="4" id="" >
							<!-- <option value="select">Select RoleType</option>
							<option value="1">Panasonic</option>
							<option value="2">Installer AC/CA</option>
							<option value="3">Customer</option>-->
			
					</select>
					</td>	 
					<td data-name="mail"><select data-placeholder="Please Select"
						class="chosen-select roll" multiple tabindex="4" id="functionalgroups">
							<option value="1">Home_Cust</option>
							<option value="2">AC Settings_Cust</option>
							<option value="3">Visualization_Cust</option>
							<option value="4">Notification_Cust</option>
							<option value="5">System Settings_Cust_Admin</option>
							<option value="6">UserAcct_Cust_Admin</option>							
							<option value="7">AC Settings_InstMain</option>
							<option value="8">AC Maintenance_InstMain</option>
							<option value="9">Notification_InstMain</option>
							<option value="10">CA Installer_Maintenance</option>	
							<option value="15">AC Settings_Panasonic</option>
							<option value="16">Visualization_Panasonic</option>
							<option value="17">Notification_Panasonic</option>							
							<option value="18">System Settings_Panasonic</option>
							<option value="19">User Acct_Panasonic</option> 
							
							
								
								
					</select></td>
					<td data-name="del"><img alt="image"
						src="../assets/img/edit.png" title='Edit Role' class="edit_row"
						id="edit_row" style="display: none;margin: 0px 20px;"><img alt="image"
						src="../assets/img/save.png" title='Save Role' class="save_row"
						style="margin: 0px 20px;" id="save_row"><img
						alt="image" src="../assets/img/save_edit.png" title='Save Edited Role' style="display:none;margin: 0px 20px;"
						class="delete" id="delete"><img
						alt="image" src="../assets/img/delete.png" title='Delete Role'
						class="row-remove" id="row-remove"></td>
				</tr>
				
			</table>
		</div>
		<br>
		<div class="col-xs-4"></div>
		<div class="btn-group">			
			<button  type="button" data-toggle="modal"
							data-target="#roleLogModal"
							class="btn bizButtons greyGradient" style="margin-left:47px;">
							<spring:message code="label.rolehistory"/> &nbsp;<span class="fa fa-caret-right"></span>
						</button>
			<%@ include file="rolepopup.jsp"%>
		</div>
		<div class="col-xs-1"></div>
	</div>
</div>


</div>

