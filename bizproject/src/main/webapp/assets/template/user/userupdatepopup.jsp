<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div class="modal fade col-lg-12" id="operationLogModal">
  <div class="modal-dialog modal-lg">
    <div class="modal-content roleModal">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h3 class="modal-title"><spring:message code="label.uh"/></h3>
      </div>
      <div class="modal-body">
        <div class="col-lg-12 nopadding">
				<table id= "operLogTable" width="100%"  class="table table-striped table-hover text-center bizTable"
					style="margin-bottom: 0px">
					<thead>
						<tr>
							<th class="text-center logdate"><spring:message code="label.dt"/></th>
							<th class="text-center logupdateuserid"><spring:message code="label.userid" /></th> 
							<th class="text-center logupdateddata"><spring:message code="label.ud"/></th>							
							<th class="text-center logupdatedby"><spring:message code="label.ub"/></th>
							
						</tr>
					</thead>
					
					<tbody id="acOperLogTbody">
					</tbody>
				</table>
				

		</div>
		
		
      </div>
      
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->