<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link href="bootstrap/css/bootstrap.css" rel="stylesheet" />
<link href="datepicker/css/datepicker.css" rel="stylesheet" />
</head>
<body>

<script src="jquery-1.8.3.js">
<script src="bootstrap/js/bootstrap.js"></script>
<script src="datepicker/js/bootstrap-datepicker.js"></script>

<c:set var="registrationError" value="${registrationError }" />
<c:if test="${not empty registrationError}">
	<div><c:out value="${registrationError}" /></div>
</c:if>

<form:form commandName="userRegistration" action="/registration" method="POST">
	<div>
		<span>Username: </span>
		<form:input path="username"/>
		<form:errors path="username" cssClass="error"/>
	</div>
	
	<div>
		<span>Password: </span>
		<form:password path="password" />
		<form:errors path="password" cssClass="error"/>
	</div>
	
	<div>
		<span>Repeat Password: </span>
		<form:password path="passwordRepeat" />
		<form:errors path="passwordRepeat" cssClass="error"/>
	</div>
	
	<div>
		<span>Zip Code: </span>
		<form:input path="zipCode" />
		<form:errors path="zipCode" cssClass="error"/>
	</div>
	
	<div>
		<span>Email: </span>
		<form:input path="email" />
		<form:errors path="email" cssClass="error"/>
	</div>
	
	<div>
		<span>Repeat Email: </span>
		<form:input path="emailRepeat" />
		<form:errors path="emailRepeat" cssClass="error"/>
	</div>
	
	<div>
		<span>Gender: </span>
		<c:forEach items="${genders}" var="gender">
			<div>
				<span><c:out value="${gender}" /></span>
				<form:radiobutton path="gender" value="${gender}" />
			</div>	
		</c:forEach>
		<form:errors path="gender" cssClass="error" />
	</div>
	
	<div class="" data-date-format="mm/dd/yyyy" data-date-viewmode="years">
		<span>Birth Date: </span>
		<form:input type="text" class=""											
			path="birthDate" id="dateOfBirthInput"
			placeholder="Date of Birth" />
		<form:errors path="birthDate" cssClass="error" />
	</div>
	
	<div>
		<input type="submit" value="submit"/>
	</div>
</form:form>

<script>
	$(function() {
		$('#dateOfBirthInput').datepicker();
	});
</script>

</body>
</html>