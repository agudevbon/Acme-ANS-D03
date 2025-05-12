<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-textbox code="member.log.form.label.incident-type" path="incidentType" />
	<acme:input-textarea code="member.log.form.label.description" path="description" />
	<acme:input-textbox code="member.log.form.label.severity-level" path="severityLevel"/>

	<jstl:choose>
		<jstl:when test="${_command == 'show'}">
			<acme:input-textbox code="member.log.form.label.flight-assignment" path="flightAssignment" readonly="true" />
		</jstl:when>
		<jstl:otherwise>
			<acme:input-select code="member.log.form.label.flight-assignment" path="flightAssignment" choices="${flightAssignments}" />
		</jstl:otherwise>
	</jstl:choose>

	<acme:input-moment code="member.log.form.label.registration-moment" path="registrationMoment" readonly="true"/>

	<jstl:choose>
		<jstl:when test="${_command == 'create'}">
			<acme:submit code="member.log.form.button.create" action="/member/activity-log/create"/>
		</jstl:when>
		<jstl:when test="${_command == 'update'}">
			<acme:submit code="member.log.form.button.update" action="/member/activity-log/update"/>
		</jstl:when>
		<jstl:when test="${_command == 'delete'}">
			<acme:submit code="member.log.form.button.delete" action="/member/activity-log/delete"/>
		</jstl:when>
		<jstl:when test="${_command == 'publish'}">
			<acme:submit code="member.log.form.button.publish" action="/member/activity-log/publish"/>
		</jstl:when>
	</jstl:choose>
	
	<jstl:if test="${_command == 'show'}">
    	<acme:submit code="member.log.form.button.edit" action="/member/activity-log/update"/>
    	<acme:submit code="member.log.form.button.delete" action="/member/activity-log/delete"/>
	</jstl:if>
</acme:form>
