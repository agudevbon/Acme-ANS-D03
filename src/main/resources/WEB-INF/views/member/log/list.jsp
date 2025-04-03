<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="member.log.list.label.incident-type" path="incidentType" width="30%"/>
	<acme:list-column code="member.log.list.label.severity-level" path="severityLevel" width="20%"/>
	<acme:list-column code="member.log.list.label.registration-moment" path="registrationMoment" width="50%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<acme:button code="member.log.form.button.create" action="/member/log/create"/>
