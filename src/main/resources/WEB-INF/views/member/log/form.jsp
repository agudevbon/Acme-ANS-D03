<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="member.log.form.label.incident-type" path="incidentType" />
	<acme:input-textarea code="member.log.form.label.description" path="description" />
	<acme:input-integer code="member.log.form.label.severity-level" path="severityLevel" />
	<acme:input-select code="member.log.form.label.flight-assignment" path="flightAssignment" choices="${flightAssignments}" />

	<acme:input-moment code="member.log.form.label.registration-moment" path="registrationMoment" readonly="true"/>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="member.log.form.button.create" action="/member/log/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'update'}">
			<acme:submit code="member.log.form.button.update" action="/member/log/update"/>
		</jstl:when>
		<jstl:when test="${_command == 'delete'}">
			<acme:submit code="member.log.form.button.delete" action="/member/log/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'publish'}">
			<acme:submit code="member.log.form.button.publish" action="/member/log/publish"/>
		</jstl:when>
	</jstl:choose>
</acme:form>
