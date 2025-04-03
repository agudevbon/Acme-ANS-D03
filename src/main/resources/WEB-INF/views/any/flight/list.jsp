<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<acme:list>
	<acme:list-column code="any.flight.list.label.tag" path="tag" width="33%"/>
	<acme:list-column code="any.flight.list.label.indication" path="indication" width="33%"/>
	<acme:list-column code="any.flight.list.label.cost" path="cost" width="33%"/>
	<acme:list-payload path="payload"/>
</acme:list>