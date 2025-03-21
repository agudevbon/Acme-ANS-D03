
package acme.features.manager.leg;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flights.Leg;
import acme.entities.flights.LegStatus;
import acme.realms.Manager;

@GuiService
public class ManagerLegShowService extends AbstractGuiService<Manager, Leg> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerLegRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int legId;
		Leg leg;

		legId = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(legId);
		status = super.getRequest().getPrincipal().hasRealm(leg.getManager());

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int legId;
		Leg leg;

		legId = super.getRequest().getData("id", int.class);
		leg = this.repository.findLegById(legId);

		super.getBuffer().addData(leg);
	}

	@Override
	public void unbind(final Leg leg) {
		Dataset dataset;
		String departure = leg.getDeparture().getIataCode();
		String arrival = leg.getArrival().getIataCode();
		String aircraft = leg.getAircraft().getRegistrationNumber();

		SelectChoices statusChoices;
		statusChoices = SelectChoices.from(LegStatus.class, leg.getStatus());

		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "duration", "status");

		dataset.put("departure", departure);
		dataset.put("arrival", arrival);
		dataset.put("aircraft", aircraft);
		dataset.put("statusChoices", statusChoices);
		super.getResponse().addData(dataset);
	}

}
