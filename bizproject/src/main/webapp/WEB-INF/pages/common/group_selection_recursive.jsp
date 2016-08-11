<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<li id="groupMenuItem${group.groupId}" class="groupMenuItem"
	group-id="${group.groupId}" group-criteria="${group.groupCriteria}"
	group-svg="${group.svgPath}"><c:choose>
		<c:when test="${group.groups != null}">
			<a href="#"><input class="menuCheckbox" type="checkbox" />${group.groupName}<span
				class="fa arrow"></span></a>
			<ul class="nav nav-${group.level}-level">
				<c:forEach var="group" items="${group.groups}">
					<c:set var="group" value="${group}" scope="request" />
					<jsp:include page="../common/group_selection_recursive.jsp" />
				</c:forEach>
			</ul>
		</c:when>
		<c:otherwise>
			<a href="#"><input class="menuCheckbox" type="checkbox" />${group.groupName}</a>
		</c:otherwise>
	</c:choose></li>
