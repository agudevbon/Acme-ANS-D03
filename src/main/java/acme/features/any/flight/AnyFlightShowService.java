
package acme.features.any.flight;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Any;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flights.Flight;

@GuiService
public class AnyFlightShowService extends AbstractGuiService<Any, Flight> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyFlightRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		int flightId;
		Flight flight;

		flightId = super.getRequest().getData("id", int.class);
		flight = this.repository.findFlightById(flightId);

		super.getBuffer().addData(flight);
	}

	@Override
	public void unbind(final Flight flight) {
		Dataset dataset;

		dataset = super.unbindObject(flight, "tag", "indication", "cost", "description", "draftMode");
		dataset.put("scheduledDeparture", flight.getScheduledDeparture());
		dataset.put("scheduledArrival", flight.getScheduledArrival());
		dataset.put("departureCity", flight.getDepartureCity());
		dataset.put("arrivalCity", flight.getArrivalCity());
		dataset.put("layovers", flight.getLayovers());

		super.getResponse().addData(dataset);
	}

}
