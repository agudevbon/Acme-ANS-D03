
package acme.features.manager.dashboard;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flights.Flight;
import acme.entities.flights.Leg;
import acme.entities.flights.LegStatus;
import acme.forms.ManagerDashboard;
import acme.realms.Manager;

@GuiService
public class ManagerDashboardShowService extends AbstractGuiService<Manager, ManagerDashboard> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerDashboardRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ManagerDashboard dashboard;
		int id = super.getRequest().getPrincipal().getAccountId();
		Manager manager = this.repository.findManagerByAccountId(id);

		Integer ranking;
		Integer retire;
		double ratio;
		String mostPopularAirport = "";
		String lessPopularAirport = "";

		Integer totalOfOnTimeLegs;
		Integer totalOfDelayedLegs;
		Integer totalOfCancelledLegs;
		Integer totalOfLandedLegs;

		double averageFlightCostEUR;
		double deviationOfFlightCostEUR;
		double minimumFlightCostEUR;
		double maximumFlightCostEUR;

		List<Manager> managers = this.repository.getAllManagers();
		managers.sort(Comparator.comparing(Manager::getExperience));
		ranking = managers.indexOf(manager);
		retire = 65 - manager.getExperience();
		ratio = (double) this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.ON_TIME).size() / (double) this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.DELAYED).size();

		if (Double.isNaN(ratio))
			ratio = -1;

		List<Leg> managerLegs = this.repository.getManagerLegs(manager.getId());

		if (managerLegs.isEmpty()) {
			mostPopularAirport = "";
			lessPopularAirport = "";
		} else {
			List<Object[]> departureAirports = this.repository.countLegsByDepartureAirport(manager.getId());
			List<Object[]> arrivalAirports = this.repository.countLegsByArrivalAirport(manager.getId());
			Map<String, Long> airportCount = new HashMap<>();
			for (Object[] row : departureAirports) {
				String country = (String) row[0];
				Long count = (Long) row[1];
				airportCount.put(country, count);
			}

			for (Object[] row : arrivalAirports) {
				String value = (String) row[0];
				Long count = (Long) row[1];

				airportCount.put(value, airportCount.getOrDefault(value, 0L) + count);
			}

			Long maxValor = 0L;
			Long minValor = 100000L;
			for (Map.Entry<String, Long> entry : airportCount.entrySet()) {
				String clave = entry.getKey();
				long valor = entry.getValue();

				if (valor > maxValor) {
					maxValor = valor;
					mostPopularAirport = clave;
				}

				if (valor < minValor) {
					minValor = valor;
					lessPopularAirport = clave;
				}
			}
		}

		totalOfOnTimeLegs = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.ON_TIME).size();
		totalOfDelayedLegs = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.DELAYED).size();
		totalOfCancelledLegs = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.CANCELLED).size();
		totalOfLandedLegs = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.LANDED).size();

		List<Flight> managerFlights = this.repository.getManagerFlights(manager.getId());

		if (managerFlights.isEmpty()) {
			averageFlightCostEUR = -1;
			deviationOfFlightCostEUR = -1;
			minimumFlightCostEUR = -1;
			maximumFlightCostEUR = -1;
		} else {
			averageFlightCostEUR = this.repository.findAverageFlightCost(manager.getId(), "EUR");
			deviationOfFlightCostEUR = this.repository.findDeviationFlightCost(manager.getId(), "EUR");
			minimumFlightCostEUR = this.repository.findMinimumFlightCost(manager.getId(), "EUR");
			maximumFlightCostEUR = this.repository.findMaximumFlightCost(manager.getId(), "EUR");
		}

		dashboard = new ManagerDashboard();
		dashboard.setRanking(ranking);
		dashboard.setRetire(retire);
		dashboard.setRatio(ratio);
		dashboard.setMostPopularAirport(mostPopularAirport);
		dashboard.setLessPopularAirport(lessPopularAirport);
		dashboard.setTotalOfOnTimeLegs(totalOfOnTimeLegs);
		dashboard.setTotalOfDelayedLegs(totalOfDelayedLegs);
		dashboard.setTotalOfCancelledLegs(totalOfCancelledLegs);
		dashboard.setTotalOfLandedLegs(totalOfLandedLegs);
		dashboard.setAverageFlightCostEUR(averageFlightCostEUR);
		dashboard.setDeviationOfFlightCostEUR(deviationOfFlightCostEUR);
		dashboard.setMinimumFlightCostEUR(minimumFlightCostEUR);
		dashboard.setMaximumFlightCostEUR(maximumFlightCostEUR);

		super.getBuffer().addData(dashboard);
	}

	@Override
	public void unbind(final ManagerDashboard dashboard) {
		Dataset dataset;

		dataset = super.unbindObject(dashboard, //
			"ranking", "retire", "ratio", "mostPopularAirport", "lessPopularAirport", "totalOfCancelledLegs", "totalOfDelayedLegs", "totalOfLandedLegs", "totalOfOnTimeLegs", //
			"averageFlightCostEUR", "deviationOfFlightCostEUR", "minimumFlightCostEUR", "maximumFlightCostEUR");

		super.getResponse().addData(dataset);
	}
}
