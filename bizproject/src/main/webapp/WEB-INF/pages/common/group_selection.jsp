<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:choose>
	<c:when test="${fn:length(groupVoMapCompanyWise) > 0}">
		<c:forEach var="company" items="${groupVoMapCompanyWise}"
			varStatus="companyStatus">
			<c:set var="companyId" value="${company.value[0].getCompanyId()}" />
			<c:set var="companyName" value="${company.value[0].getCompanyName()}" />
			<li id="company${companyId}" class="companyItem groupMenuItem"
				data-id="${companyId}" style="display: none;"
				group-criteria="${company.value[0].groupCriteria}"><c:choose>
					<c:when test="${company.value[0].groupName != null}">
						<a href="#"><input class="menuCheckbox" type="checkbox" /> <span
							class="nav-label">${companyName}</span><span class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<c:forEach var="group" items="${company.value}"
								varStatus="groupStatus">
								<%@ include file="group_selection_recursive.jsp"%>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<a href="#"><i class="fa fa-map-marker"></i> <span
							class="nav-label">${companyName}</span></a>
					</c:otherwise>
				</c:choose></li>
		</c:forEach>
	</c:when>
	<c:otherwise>
		<c:forEach var="group1" items="${groupList}" varStatus="groupStatus">
			<li id="company${companyId}" class="companyItem groupMenuItem"
				style="display: none;" data-id="${group1.companyId}"
				group-criteria="${group1.groupCriteria}"><c:choose>
					<c:when test="${group1.groupName != ''}">
						<a href="#" data-id="${group1.groupId}"><input
							class="menuCheckbox" type="checkbox" /> <span class="nav-label">${group1.groupName}</span><span
							class="fa arrow"></span></a>
						<ul class="nav nav-second-level">
							<c:forEach var="group" items="${group1.groups}"
								varStatus="subgroupStatus">
								<%@ include file="group_selection_recursive.jsp"%>
							</c:forEach>
						</ul>
					</c:when>
					<c:otherwise>
						<a href="#" class="groupMenuItem"><i class="fa fa-map-marker"></i>
							<span class="nav-label">${group1.groupName}</span></a>
					</c:otherwise>
				</c:choose></li>
		</c:forEach>
	</c:otherwise>
</c:choose>
