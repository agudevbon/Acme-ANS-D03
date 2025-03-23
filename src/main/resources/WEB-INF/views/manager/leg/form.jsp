<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="manager.leg.form.label.flightNumber" path="flightNumber" />
	<acme:input-moment code="manager.leg.form.label.scheduledDeparture" path="scheduledDeparture" />
	<acme:input-moment code="manager.leg.form.label.scheduledArrival" path="scheduledArrival" />
	<acme:input-integer code="manager.leg.form.label.duration" path="duration" />
	<jstl:if test="${_command == 'show' }">
		<acme:input-textbox code="manager.leg.form.label.status" path="status" />
		<acme:input-checkbox code="manager.leg.form.label.draftMode" path="draftMode" />
		<acme:input-textbox code="manager.leg.form.label.departure" path="departure" />
		<acme:input-textbox code="manager.leg.form.label.arrival" path="arrival" />
		<acme:input-textbox code="manager.leg.form.label.aircraft" path="aircraft" />
		<acme:input-textbox code="manager.leg.form.label.flight" path="flight" />
	</jstl:if>
	
	<jstl:if test="${_command == 'create' }">
		<acme:input-select code="manager.leg.form.label.status" path="status" choices="${statuss}" />
		<acme:input-select code="manager.leg.form.label.departure" path="departure" choices="${departures}"/>
		<acme:input-select code="manager.leg.form.label.arrival" path="arrival" choices="${arrivals}"/>
		<acme:input-select code="manager.leg.form.label.aircraft" path="aircraft" choices="${aircrafts}"/>
		<acme:input-select code="manager.leg.form.label.flight" path="flight" choices="${flights}"/>
		<acme:input-checkbox code="manager.leg.form.label.confirmation" path="confirmation"/>	
		<acme:submit code="manager.leg.form.button.create" action="/manager/leg/create"/>
	</jstl:if>	
	
</acme:form>	