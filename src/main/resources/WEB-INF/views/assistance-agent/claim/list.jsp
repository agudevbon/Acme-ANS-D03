<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="assistance-agent.claim.list.label.registrationDate" path="registrationDate" width="16.67%"/>
	<acme:list-column code="assistance-agent.claim.list.label.passengerEmail" path="passengerEmail" width="16.67%"/>
	<acme:list-column code="assistance-agent.claim.list.label.type" path="type" width="16.7%"/>
	<acme:list-column code="assistance-agent.claim.list.label.isAccepted" path="isAccepted" width="16.67%"/>
	<acme:list-column code="assistance-agent.claim.list.label.flightLeg" path="flightLeg" width="16.67%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<jstl:if test="${_command == 'list-undergoing'}">
	<acme:button code="assistance-agent.claim.list.button.create" action="/assistance-agent/claim/create"/>
</jstl:if>

<jstl:if test="${_command == 'list-completed'}">
	<acme:button code="assistance-agent.claim.list.button.create" action="/assistance-agent/claim/create"/>
</jstl:if>