<%@page%>
<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<jstl:choose>
    <jstl:when test="${_command == 'list-planned'}">
        <h2><acme:print code="member.assignment.list.label.planned"/></h2>
    </jstl:when>
    <jstl:when test="${_command == 'list-completed'}">
        <h2><acme:print code="member.assignment.list.label.completed"/></h2>
    </jstl:when>
    <jstl:otherwise>
        <h2><acme:print code="member.assignment.list.title"/></h2>
    </jstl:otherwise>
</jstl:choose>

<acme:list>
    <acme:list-column code="member.assignment.list.label.duty" path="duty" width="30%"/>
    <acme:list-column code="member.assignment.list.label.leg" path="leg" width="40%"/>
    <acme:list-column code="member.assignment.list.label.status" path="status" width="30%"/>
    <acme:list-payload path="payload"/>
</acme:list>

<acme:button code="member.assignment.form.button.create" action="/member/flight-assignment/create"/>
