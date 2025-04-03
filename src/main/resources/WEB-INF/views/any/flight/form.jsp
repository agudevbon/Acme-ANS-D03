<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="any.flight.form.label.tag" path="tag" />
	<acme:input-checkbox code="any.flight.form.label.indication" path="indication" />
	<acme:input-money code="any.flight.form.label.cost" path="cost" />
	<acme:input-textarea code="any.flight.form.label.description" path="description" />
	<acme:input-textbox code="any.flight.form.label.scheduledDeparture" path="scheduledDeparture" readonly="true" />
	<acme:input-textbox code="any.flight.form.label.scheduledArrival" path="scheduledArrival" readonly="true" />
	<acme:input-textbox code="any.flight.form.label.departureCity" path="departureCity" readonly="true" />
	<acme:input-textbox code="any.flight.form.label.arrivalCity" path="arrivalCity" readonly="true" />
	<acme:input-textbox code="any.flight.form.label.layovers" path="layovers" readonly="true" />

	<acme:button code="any.flight.form.button.list-legs" action="/any/leg/list-by?masterId=${id}"/>

	
	
</acme:form>	