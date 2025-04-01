<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:form>	
	<acme:input-textbox code="technician.maintenance-record.form.label.moment" path="moment" />
	<acme:input-select code="technician.maintenance-record.form.label.status" path="status" choices="${statuss}" />
	<acme:input-textbox code="technician.maintenance-record.form.label.inspectionDueDate" path="inspectionDueDate" />
	<acme:input-money code="technician.maintenance-record.form.label.estimatedCost" path="estimatedCost" />
	<acme:input-textarea code="technician.maintenance-record.form.label.notes" path="notes" />
	<acme:input-select code="technician.maintenance-record.form.label.aircraft" path="aircraft" choices="${aircrafts}"/>
	
</acme:form>	