
package acme.features.manager.dashboard;

import java.util.Comparator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
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
		Integer ratio;
		String mostPopularAirport;
		String lessPopularAirport;

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
		try {
			ratio = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.ON_TIME).size() / this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.DELAYED).size();
		} catch (Exception e) {
			ratio = -1;
		}

		totalOfOnTimeLegs = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.ON_TIME).size();
		totalOfDelayedLegs = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.DELAYED).size();
		totalOfCancelledLegs = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.CANCELLED).size();
		totalOfLandedLegs = this.repository.getManagerLegsByStatus(manager.getId(), LegStatus.LANDED).size();

		try {
			averageFlightCostEUR = this.repository.findAverageFlightCost(manager.getId(), "EUR");
			deviationOfFlightCostEUR = this.repository.findDeviationFlightCost(manager.getId(), "EUR");
			minimumFlightCostEUR = this.repository.findMinimumFlightCost(manager.getId(), "EUR");
			maximumFlightCostEUR = this.repository.findMaximumFlightCost(manager.getId(), "EUR");
		} catch (Exception e) {
			averageFlightCostEUR = -1;
			deviationOfFlightCostEUR = -1;
			minimumFlightCostEUR = -1;
			maximumFlightCostEUR = -1;
		}
		dashboard = new ManagerDashboard();
		dashboard.setRanking(ranking);
		dashboard.setRetire(retire);
		dashboard.setRatio(ratio);
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
			"ranking", "retire", "ratio", "totalOfCancelledLegs", "totalOfDelayedLegs", "totalOfLandedLegs", "totalOfOnTimeLegs", //
			"averageFlightCostEUR", "deviationOfFlightCostEUR", "minimumFlightCostEUR", "maximumFlightCostEUR");

		super.getResponse().addData(dataset);
	}
}
