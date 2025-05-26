<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.leg.list.label.flightNumber" path="flightNumber" width="25%" sortable="false"/>
	<acme:list-column code="manager.leg.list.label.scheduledDeparture" path="scheduledDeparture" width="25%" sortable="true"/>
	<acme:list-column code="manager.leg.list.label.scheduledArrival" path="scheduledArrival" width="25%" sortable="true"/>
	<acme:list-column code="manager.leg.list.label.draftMode" path="draftMode" width="25%" sortable="false"/>
	<acme:list-payload path="payload"/>
</acme:list>
<acme:button code="manager.leg.form.button.create" action="/manager/leg/create"/>