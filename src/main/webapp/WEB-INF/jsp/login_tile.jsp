<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<body onload='document.loginForm.username.focus();'>

	<div class="login-tile">
		<form name='loginForm' action="<c:url value='j_spring_security_check' />" method='POST'>
			<div class="row padding-top5">
				<div class="col-xs-3 col-xs-push-0 login-element-text">User:</div>
				<input class="col-xs-8 login-element-field" type='text' name='username' value=''>
			</div>
			
			<div class="row padding-top5">
				<div class="col-xs-3 col-xs-push-0 login-element-text">Password:</div>
				<input class="col-xs-8 login-element-field" type='password' name='password' />
			</div>
			
			<div class="row padding-top5">
				<input class="col-xs-4 col-xs-offset-0 btn" name="submit" type="submit" value="submit" />
			</div>
		  <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		</form>
	</div>
</body>
</html>