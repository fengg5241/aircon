<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
	<h1>Welcome to B2BACNS Biz Portal Login</h1>
	
	Current Language : ${pageContext.response.locale}
	<h3>
		Choose Your Language : <a href="?lang=en">English</a>| <a
			href="?lang=de">Germany</a>
	</h3>

	

	<!-- Sample form -->

	<br>
	<br>
	<br>

	<form:form method="post" modelAttribute="loginFormBean"
		action="./loginProcess.htm">

		<c:if test="${errorMessage ne null}">
			<spring:message code="${errorMessage}" />
			<br>
		</c:if>

		<c:if test="${redirectErrorMessage ne null}">
			<spring:message code="${redirectErrorMessage}" />
			<br>
		</c:if>


		<c:forEach items="${loginFormBean.errorList}" var="msg">
			<spring:message code="${msg}" />
			<br>
		</c:forEach>

		<form:errors path="email" />
		<br>
		<spring:message code="label.email.address" text="Email ID" />
		<form:input path="email" />
		<br>

		<form:errors path="password" />
		<spring:message code="label.password" text="Password" />
		<form:password path="password" />
		<br>

		<spring:message code="label.submit" var="submitText" />
		<form:button type="submit">${submitText}</form:button>

	</form:form>

</body>
</html>