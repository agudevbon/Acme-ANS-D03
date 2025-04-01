<%@page contentType="text/html;charset=UTF-8" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="acme" uri="http://acme-framework.org/" %>

<acme:form>

    <acme:input-select code="member.assignment.form.label.duty" path="duty" choices="${dutyTypes}" />
    <acme:input-select code="member.assignment.form.label.leg" path="leg" choices="${legs}" />
    <acme:input-select code="member.assignment.form.label.member" path="member" choices="${members}" />
    <acme:input-textarea code="member.assignment.form.label.remarks" path="remarks" />

    <jstl:choose>
        <jstl:when test="${_command == 'create'}">
            <acme:submit code="member.assignment.form.button.create" action="/member/assignment/create"/>
        </jstl:when>
        <jstl:when test="${_command == 'update'}">
            <acme:submit code="member.assignment.form.button.update" action="/member/assignment/update"/>
        </jstl:when>
    </jstl:choose>

</acme:form>
