<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="modal fade col-lg-12" id="roleLogModal">
  <div class="modal-dialog modal-lg">
    <div class="modal-content roleModal">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title"><spring:message code="label.rolehistory"/></h3>
      </div>
      <div class="modal-body">
        <div class="col-lg-12 nopadding">
				<table id= "operLogTables" width="100%"  class="table table-striped table-hover text-center bizTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center roleaction"><spring:message code="label.a"/></th>
							<th class="text-center rolename"><spring:message code="label.rn"/></th>							
							<th class="text-center rolefunction"><spring:message code="label.fg"/></th>
							<th class="text-center roleactionby"><spring:message code="label.ab"/></th>							
							<th class="text-center roledateandtime"><spring:message code="label.dt"/></th>
							
						</tr>
					</thead>
					
					<tbody id="acOperLogTbodys">
					</tbody>
				</table>
				

		</div>
		
		
      </div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->










<!-- <div class="modal fade col-lg-12" id="roleModal">
  <div class="modal-dialog modal-lg">
    <div class="modal-content roleModal">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title">Role History</h3>
      </div>
      <div class="modal-body">
        <div class="col-lg-12">
				<table id="role_table"  class="table table-striped table-hover text-center bizTables complexHeaderTable"
						width="100%" style="margin-bottom: 0px">
					<thead>
						<tr class="greyGradient">
							<th class="text-center roleaction">Action</th>
							<th class="text-center rolename">Role Name</th>							
							<th class="text-center rolefunction">Function Group</th>
							<th class="text-center roleactionby">Action By</th>							
							<th class="text-center roledateandtime">Date/Time</th>							
						</tr>
					</thead>					
					<tbody id="role_table_body">
					</tbody>
				</table>

		</div>

      </div>
      
    </div>
  </div>
</div>
 -->