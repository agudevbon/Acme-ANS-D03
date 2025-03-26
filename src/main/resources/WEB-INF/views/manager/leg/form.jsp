<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="manager.leg.form.label.flightNumber" path="flightNumber" />
	<acme:input-moment code="manager.leg.form.label.scheduledDeparture" path="scheduledDeparture" />
	<acme:input-moment code="manager.leg.form.label.scheduledArrival" path="scheduledArrival" />
	<acme:input-integer code="manager.leg.form.label.duration" path="duration" />
	<acme:input-select code="manager.leg.form.label.status" path="status" choices="${statuss}" />
	<acme:input-select code="manager.leg.form.label.departure" path="departure" choices="${departures}"/>
	<acme:input-select code="manager.leg.form.label.arrival" path="arrival" choices="${arrivals}"/>
	<acme:input-select code="manager.leg.form.label.aircraft" path="aircraft" choices="${aircrafts}"/>
	<acme:input-select code="manager.leg.form.label.flight" path="flight" choices="${flights}"/>
		
	
	<jstl:choose>	 
		<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
			<acme:submit code="manager.leg.form.button.publish" action="/manager/leg/publish"/>
			<acme:submit code="manager.leg.form.button.delete" action="/manager/leg/delete"/>
			<acme:submit code="manager.leg.form.button.update" action="/manager/leg/update"/>
		</jstl:when>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="manager.leg.form.button.create" action="/manager/leg/create"/> 
		</jstl:when>		
	</jstl:choose>	
</acme:form>	