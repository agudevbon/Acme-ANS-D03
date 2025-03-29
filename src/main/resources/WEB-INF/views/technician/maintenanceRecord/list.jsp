<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="technician.maintenanceRecord.list.label.moment" path="moment" width="25%"/>
	<acme:list-column code="technician.maintenanceRecord.list.label.inspectionDueDate" path="inspectionDueDate" width="25%"/>
	<acme:list-column code="technician.maintenanceRecord.list.label.estimatedCost" path="estimatedCost" width="25%"/>
	<acme:list-column code="technician.maintenanceRecord.list.label.notes" path="notes" width="25%"/>
	<acme:list-column code="technician.maintenanceRecord.list.label.draftMode" path="draftMode" width="25%"/>
	<acme:list-payload path="payload"/>
</acme:list>
