<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="member.assignment.list.label.duty" path="duty" width="25%"/>
	<acme:list-column code="member.assignment.list.label.leg" path="leg" width="25%"/>
	<acme:list-column code="member.assignment.list.label.status" path="status" width="25%"/>
	<acme:list-column code="member.assignment.list.label.member" path="member" width="25%"/>
	<acme:list-payload path="payload"/>
</acme:list>

<acme:button code="member.assignment.form.button.create" action="/member/assignment/create"/>
