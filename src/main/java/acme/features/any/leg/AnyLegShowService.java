
package acme.features.any.leg;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Any;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flights.Leg;

@GuiService
public class AnyLegShowService extends AbstractGuiService<Any, Leg> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyLegRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
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

		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "status", "draftMode");

		dataset.put("duration", leg.getDuration());
		dataset.put("flight", leg.getFlight().getTag());
		dataset.put("aircraft", leg.getAircraft().getRegistrationNumber());
		dataset.put("departure", leg.getDeparture().getIataCode());
		dataset.put("arrival", leg.getArrival().getIataCode());
		super.getResponse().addData(dataset);
	}

}
