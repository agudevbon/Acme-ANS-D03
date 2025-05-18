<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="manager.flight.list.label.tag" path="tag" width="25%"/>
	<acme:list-column code="manager.flight.list.label.description" path="description" width="25%"/>
	<acme:list-column code="manager.flight.list.label.cost" path="cost" width="25%"/>
	<acme:list-column code="manager.flight.list.label.draftMode" path="draftMode" width="25%"/>
	<acme:list-payload path="payload"/>
</acme:list>
<acme:button code="manager.flight.form.button.create" action="/manager/flight/create"/>