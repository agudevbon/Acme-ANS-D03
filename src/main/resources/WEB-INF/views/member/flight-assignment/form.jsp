<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>

    <acme:input-select code="member.assignment.form.label.duty" path="duty" choices="${dutyChoices}" readonly="${_command == 'show'}" />
    <acme:input-select code="member.assignment.form.label.leg" path="leg" choices="${legChoices}" readonly="${_command == 'show'}" />
    <acme:input-select code="member.assignment.form.label.member" path="member" choices="${memberChoices}" readonly="${_command == 'show'}" />
    <acme:input-textarea code="member.assignment.form.label.remarks" path="remarks" readonly="${_command == 'show'}"/>

    <jstl:if test="${_command == 'show'}">
        <acme:input-textbox code="member.assignment.form.label.status" path="status" readonly="true"/>
        <acme:input-textbox code="member.assignment.form.label.lastUpdateMoment" path="lastUpdateMoment" readonly="true"/>
        <acme:input-textbox code="member.assignment.form.label.scheduledDeparture" path="scheduledDeparture" readonly="true"/>
        <acme:input-textbox code="member.assignment.form.label.scheduledArrival" path="scheduledArrival" readonly="true"/>
        <acme:input-textbox code="member.assignment.form.label.departureAirport" path="departureAirport" readonly="true"/>
        <acme:input-textbox code="member.assignment.form.label.arrivalAirport" path="arrivalAirport" readonly="true"/>
        
        <acme:print code="member.assignment.form.label.crew-members"/>
        <jstl:forEach var="c" items="${crew}">
            <p>
                <strong><acme:print value="${c.member.userAccount.identity.fullName}"/></strong>
                (${c.duty}) - 
                <acme:print value="${c.member.availabilityStatus}"/>
            </p>
        </jstl:forEach>
    </jstl:if>

    <jstl:choose>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="member.assignment.form.button.create" action="/member/flight-assignment/create"/>
        </jstl:when>
        <jstl:when test="${_command == 'update'}">
    		<acme:submit code="member.assignment.form.button.update" action="/member/flight-assignment/update"/>
   			<acme:submit code="member.assignment.form.button.publish" action="/member/flight-assignment/publish"/>
		</jstl:when>
        <jstl:when test="${_command == 'show' && status != 'CONFIRMED'}">
            <acme:submit code="member.assignment.form.button.edit" action="/member/flight-assignment/update"/>
        </jstl:when>
    </jstl:choose>

</acme:form>
	