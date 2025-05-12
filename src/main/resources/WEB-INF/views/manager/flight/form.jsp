<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="manager.flight.form.label.tag" path="tag" />
	<acme:input-checkbox code="manager.flight.form.label.indication" path="indication" />
	<acme:input-money code="manager.flight.form.label.cost" path="cost" />
	<acme:input-textarea code="manager.flight.form.label.description" path="description" />
	<jstl:if test="${acme:anyOf(_command, 'show|update|delete|publish')}">
		<acme:input-textbox code="manager.flight.form.label.scheduledDeparture" path="scheduledDeparture" readonly="true" />
		<acme:input-textbox code="manager.flight.form.label.scheduledArrival" path="scheduledArrival" readonly="true" />
		<acme:input-textbox code="manager.flight.form.label.departureCity" path="departureCity" readonly="true" />
		<acme:input-textbox code="manager.flight.form.label.arrivalCity" path="arrivalCity" readonly="true" />
		<acme:input-textbox code="manager.flight.form.label.layovers" path="layovers" readonly="true" />
	</jstl:if>
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="manager.flight.form.button.publish" action="/manager/flight/publish"/>
			<acme:submit code="manager.flight.form.button.delete" action="/manager/flight/delete"/>
			<acme:submit code="manager.flight.form.button.update" action="/manager/flight/update"/>
			<acme:button code="manager.flight.form.button.list-legs" action="/manager/leg/list-by?masterId=${id}"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.flight.form.button.create" action="/manager/flight/create"/> 
		</jstl:when>		
	</jstl:choose>
	
	
</acme:form>	