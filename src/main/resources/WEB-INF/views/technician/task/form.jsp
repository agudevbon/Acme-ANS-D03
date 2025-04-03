<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
		<acme:input-textbox code="technician.task.form.label.technician" path="technician.identity.name" readonly="true"/>	
	</jstl:if>
	<acme:input-select code="technician.task.form.label.type" path="type" choices="${types}"/>
	<acme:input-integer code="technician.task.form.label.priority" path="priority"/>
	<acme:input-double code="technician.task.form.label.estimated-duration" path="estimatedDuration"/>
	<acme:input-textarea code="technician.task.form.label.description" path="description"/>
	
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="technician.maintenance-record.form.button.publish" action="/technician/maintenance-record/publish"/>
			<acme:submit code="technician.maintenance-record.form.button.update" action="/technician/maintenance-record/update"/>
			
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="technician.maintenance-record.form.button.create" action="/technician/maintenance-record/create"/> 
		</jstl:when>		
	</jstl:choose>	
</acme:form>	