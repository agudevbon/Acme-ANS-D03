<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="manager.leg.form.label.flightNumber" path="flightNumber" />
	<acme:input-moment code="manager.leg.form.label.scheduledDeparture" path="scheduledDeparture" />
	<acme:input-moment code="manager.leg.form.label.scheduledArrival" path="scheduledArrival" />
	<acme:input-integer code="manager.leg.form.label.duration" path="duration" />
	<acme:input-textbox code="manager.leg.form.label.status" path="status" />
	<acme:input-textbox code="manager.leg.form.label.departure" path="departure" />
	<acme:input-textbox code="manager.leg.form.label.arrival" path="arrival" />
	<acme:input-textbox code="manager.leg.form.label.aircraft" path="aircraft" />
	
</acme:form>	