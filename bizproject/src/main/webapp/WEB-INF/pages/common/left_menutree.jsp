<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="treeFrame" class="greyBackground bizFontColor"><div id="treeContent">
	<div id = "treeTitle"><p class="font16"><spring:message code="label.acconfig"/></p><p class="font13"><spring:message code="label.locations"/></p></div>
	<div id="treeBody">
		<div id="checkboxMenuTree"  class="font13"></div>
		<div class="text-center" style="border-top:10px solid #cccccc"><button id = "displayButton" type="button" class="btn bizButton greyGradient"><spring:message code="label.selection"/> &nbsp;<span class="fa fa-caret-right"></span></button></div>
	</div>
</div></div>
