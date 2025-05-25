<%@page%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="http://acme-framework.org/"%>

<h2>
	<acme:print code="manager.manager-dasboard.form.title.manager-data"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.ranking"/>
		</th>
		<td>
			<acme:print value = "${ranking}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.retire"/>
		</th>
		<td>
			<acme:print value = "${retire}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.ratio"/>
		</th>
		<td>
			<jstl:if test="${ratio >= 0}">
				<acme:print value = "${ratio}"/>
			</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.most-popular"/>
		</th>
		<td>
			<acme:print value = "${mostPopularAirport}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.less-popular"/>
		</th>
		<td>
			<acme:print value = "${lessPopularAirport}"/>
		</td>
	</tr>
</table>

<h2>
	<acme:print code="manager.manager-dasboard.form.title.total-legs"/>
</h2>

<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.total-time"/>
		</th>
		<td>
			<acme:print value = "${totalOfOnTimeLegs}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.total-delayed"/>
		</th>
		<td>
			<acme:print value = "${totalOfDelayedLegs}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.total-canceled"/>
		</th>
		<td>
			<acme:print value = "${totalOfCancelledLegs}"/>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.total-landed"/>
		</th>
		<td>
			<acme:print value = "${totalOfLandedLegs}"/>
		</td>
	</tr>

</table>


<h2>
	<acme:print code="manager.manager-dasboard.form.title.flight-EUR-cost"/>
</h2>
<table class="table table-sm">
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.flight-average"/>
		</th>
		<td>
			<jstl:if test="${averageFlightCostEUR >= 0}">
				<acme:print value = "${averageFlightCostEUR}"/>
			</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.flight-deviation"/>
		</th>
		<td>
			<jstl:if test="${deviationOfFlightCostEUR >= 0}">
				<acme:print value = "${deviationOfFlightCostEUR}"/>
			</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.flight-minimum"/>
		</th>
		<td>
			<jstl:if test="${minimumFlightCostEUR >= 0}">
				<acme:print value = "${minimumFlightCostEUR}"/>
			</jstl:if>
		</td>
	</tr>
	<tr>
		<th scope="row">
			<acme:print code="manager.manager-dashboard.form.label.flight-maximum"/>
		</th>
		<td>
			<jstl:if test="${maximumFlightCostEUR >= 0}">
				<acme:print value = "${maximumFlightCostEUR}"/>
			</jstl:if>
		</td>
	</tr>
</table>