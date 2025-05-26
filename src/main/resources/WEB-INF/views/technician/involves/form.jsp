<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	
	<acme:input-select code="technician.involves.form.task" path="task" choices="${tasks}"/>
	
	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="technician.involves.form.button.link" action="/technician/involves/create?maintenanceRecordId=${maintenanceRecordId}" />
		</jstl:when>
		
		<jstl:when test="${acme:anyOf(_command, 'show|delete')}">
			<acme:submit code="technician.involves.form.button.unlink"  action="/technician/involves/delete?maintenanceRecordId=${maintenanceRecordId}"/>
		</jstl:when>
	</jstl:choose>	
</acme:form>	