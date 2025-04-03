<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="technician.task.list.label.task" path="id" width="15%"/>
	<acme:list-column code="technician.task.list.label.priority" path="priority" width="15%"/>
	<acme:list-column code="technician.task.list.label.estimatedDuration" path="estimatedDuration" width="15%"/>
	<acme:list-column code="technician.task.list.label.type" path="type" width="35%"/>
	<acme:list-column code="technician.task.list.label.draftMode" path="draftMode" width="25%"/>
	
	<acme:list-payload path="payload"/>
</acme:list>
<acme:button code="technician.task.form.button.create" action="/technician/task/create"/>
