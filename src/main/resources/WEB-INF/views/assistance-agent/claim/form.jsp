<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>
	<acme:input-moment code="assistance-agent.claim.form.label.registrationDate" path="registrationDate" readonly="true"/>
	<acme:input-email code="assistance-agent.claim.form.label.passengerEmail" path="passengerEmail"/>
	<acme:input-textbox code="assistance-agent.claim.form.label.description" path="description"/>
	<acme:input-select code="assistance-agent.claim.form.label.type" path="type"  choices="${claimTypes}"/>
	<acme:input-textbox code="assistance-agent.claim.form.label.isAccepted" path="isAccepted" readonly="true"/>
	<jstl:if test="${pending == true || pending == null }">
		<acme:input-select code="assistance-agent.claim.form.label.flightLeg" path="flightLeg" choices="${flightLegs}"/>
	</jstl:if>

	
	<jstl:choose>
			<jstl:when test="${_command == 'show' && draftMode == false}">
				<acme:submit code="assistance-agent.tracking-log.form.button.list" action="/assistance-agent/tracking-log/list?id=${id}" method="get"/>
			</jstl:when>
			<jstl:when test="${acme:anyOf(_command, 'show|update|delete|publish') && draftMode == true}">
				<acme:submit code="assistance-agent.claim.form.button.update" action="/assistance-agent/claim/update"/>
				<acme:submit code="assistance-agent.claim.form.button.delete" action="/assistance-agent/claim/delete"/>
				<acme:submit code="assistance-agent.claim.form.button.publish" action="/assistance-agent/claim/publish" method="post"/>
				<acme:submit code="assistance-agent.tracking-log.form.button.list" action="/assistance-agent/tracking-log/list?id=${id}" method="get"/>
			</jstl:when>
			<jstl:when test="${_command == 'create'}">
				<acme:submit code="assistance-agent.claim.form.button.create" action="/assistance-agent/claim/create"/>
			</jstl:when>		
	</jstl:choose>	
		
</acme:form>