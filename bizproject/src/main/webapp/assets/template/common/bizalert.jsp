<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="bizalert" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 id = "bizalertTitle" class="modal-title text-center"></h4>
      </div>
      <div class="modal-body">
        <p id="bizalertBody" class="text-center"></p>
      </div>
      <div class="modal-footer text-center">
        <button id = "bizalertCloseButton" type="button" class="btn bizButton greyGradient" data-dismiss="modal" style="margin:0">Cancel &nbsp;<span class="fa fa-caret-right"></span></button>
        <button id = "bizalertConfirmButton" type="button" class="btn bizButton greyGradient" data-dismiss="modal" style="margin:0;display:none"><spring:message code="label.cnf"/> &nbsp;<span class="fa fa-caret-right"></span></button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="bizTimeOutalert" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4  class="modal-title text-center"><spring:message code="label.sessiontitle" /></h4>
      </div>
      <div class="modal-body">
        <p id="bizalertBody" class="text-center"><spring:message code="label.sessiontimeouttitle" />?.</p>
      </div>
      <div class="modal-footer text-center">
        <button id = "bizTimeOutAlertCloseButton" type="button" class="btn bizButton greyGradient" data-dismiss="modal" style="margin:0"><spring:message code="label.companycancel" /> &nbsp;<span class="fa fa-caret-right"></span></button>
        <button id = "bizTimeOutAlertConfirmButton" type="button" class="btn bizButton greyGradient" data-dismiss="modal" style="margin:0;"><spring:message code="label.cnf"/> &nbsp;<span class="fa fa-caret-right"></span></button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<div id="bizInvalidalert" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4  class="modal-title text-center"></h4>
      </div>
      <div class="modal-body">
        <p id="bizalertBody" class="text-center"><spring:message code="label.invalidstate" /></p>
      </div>
      <div class="modal-footer text-center">
        <button id = "bizInvalidalertCloseButton" type="button" class="btn bizButton greyGradient" data-dismiss="modal" style="margin:0"><spring:message code="label.companycancel" /> &nbsp;<span class="fa fa-caret-right"></span></button>
        <button id = "bizInvalidalertConfirmButton" type="button" class="btn bizButton greyGradient" data-dismiss="modal" style="margin:0;"><spring:message code="label.cnf"/> &nbsp;<span class="fa fa-caret-right"></span></button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div id="bizinfo" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 id = "bizinfoTitle" class="modal-title text-center"></h4>
      </div>
      <div class="modal-body">
        <p id="bizinfoBody" class="text-center"></p>
      </div>
      <div class="modal-footer text-center">
        <button id = "bizinfoOKButton" type="button" class="btn bizButton greyGradient" data-dismiss="modal" style="margin:0;"><spring:message code="label.oktitle" /> &nbsp;<span class="fa fa-caret-right"></span></button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->