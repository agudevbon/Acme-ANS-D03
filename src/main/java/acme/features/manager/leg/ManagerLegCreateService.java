
package acme.features.manager.leg;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.airports.Airport;
import acme.entities.flights.Flight;
import acme.entities.flights.Leg;
import acme.entities.flights.LegStatus;
import acme.realms.Manager;

@GuiService
public class ManagerLegCreateService extends AbstractGuiService<Manager, Leg> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerLegRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int legId;

		if (super.getRequest().getMethod().equals("GET"))
			status = true;
		else {
			int managerId, departureId, arrivalId, aircraftId, flightId;
			Airport departure;
			Airport arrival;
			Aircraft aircraft;
			Flight flight;

			managerId = super.getRequest().getPrincipal().getActiveRealm().getId();
			departureId = super.getRequest().getData("departure", int.class);
			arrivalId = super.getRequest().getData("arrival", int.class);
			aircraftId = super.getRequest().getData("aircraft", int.class);
			flightId = super.getRequest().getData("flight", int.class);

			arrival = this.repository.findAirportById(arrivalId);
			departure = this.repository.findAirportById(departureId);
			flight = this.repository.findFlightByIdAndManager(flightId, managerId);
			aircraft = this.repository.findActiveAircraftById(aircraftId);
			legId = super.getRequest().getData("id", int.class);

			boolean departureStatus = departureId == 0 || departure != null;
			boolean arrivalStatus = arrivalId == 0 || arrival != null;
			boolean aircraftStatus = aircraftId == 0 || aircraft != null;
			boolean flightStatus = flightId == 0 || flight != null;
			status = legId == 0 && departureStatus && arrivalStatus && aircraftStatus && flightStatus;
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Leg leg;
		Manager manager;

		manager = (Manager) super.getRequest().getPrincipal().getActiveRealm();

		leg = new Leg();
		leg.setDraftMode(true);
		leg.setManager(manager);

		super.getBuffer().addData(leg);

	}

	@Override
	public void bind(final Leg leg) {

		int flightId = super.getRequest().getData("flight", int.class);
		Flight flight = this.repository.findFlightById(flightId);

		int aircraftId = super.getRequest().getData("aircraft", int.class);
		Aircraft aircraft = this.repository.findAircraftById(aircraftId);

		int departureId = super.getRequest().getData("departure", int.class);
		Airport departure = this.repository.findAirportById(departureId);

		int arrivalId = super.getRequest().getData("arrival", int.class);
		Airport arrival = this.repository.findAirportById(arrivalId);

		super.bindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "status");

		leg.setFlight(flight);
		leg.setAircraft(aircraft);
		leg.setDeparture(departure);
		leg.setArrival(arrival);
	}

	@Override
	public void validate(final Leg leg) {
		{
			boolean futureDeparture = true;

			Date present = MomentHelper.getBaseMoment();

			if (leg.getScheduledDeparture() != null)
				futureDeparture = leg.getScheduledDeparture().after(present);

			super.state(futureDeparture, "scheduledDeparture", "acme.validation.leg.past-date.message");
		}
		{
			boolean futureArrival = true;

			Date present = MomentHelper.getBaseMoment();

			if (leg.getScheduledArrival() != null)
				futureArrival = leg.getScheduledArrival().after(present);

			super.state(futureArrival, "scheduledArrival", "acme.validation.leg.past-date.message");
		}
	}

	@Override
	public void perform(final Leg leg) {
		this.repository.save(leg);
	}

	@Override
	public void unbind(final Leg leg) {
		Dataset dataset;
		SelectChoices flightChoices;
		List<Flight> managerFlights = this.repository.findDraftingFlightByManagerId(leg.getManager().getId());
		flightChoices = SelectChoices.from(managerFlights, "tag", leg.getFlight());

		SelectChoices aircraftChoices;
		List<Aircraft> aircrafts = this.repository.findAircrafts();
		aircraftChoices = SelectChoices.from(aircrafts, "registrationNumber", leg.getAircraft());

		SelectChoices departureChoices;
		SelectChoices arrivalChoices;
		List<Airport> airports = this.repository.findAirports();
		departureChoices = SelectChoices.from(airports, "iataCode", leg.getDeparture());
		arrivalChoices = SelectChoices.from(airports, "iataCode", leg.getArrival());

		SelectChoices statusChoices;
		statusChoices = SelectChoices.from(LegStatus.class, leg.getStatus());

		dataset = super.unbindObject(leg, "flightNumber", "scheduledDeparture", "scheduledArrival", "status");

		dataset.put("statuss", statusChoices);
		dataset.put("flights", flightChoices);
		dataset.put("flight", flightChoices.getSelected().getKey());
		dataset.put("aircrafts", aircraftChoices);
		dataset.put("aircraft", aircraftChoices.getSelected().getKey());
		dataset.put("departures", departureChoices);
		dataset.put("departure", departureChoices.getSelected().getKey());
		dataset.put("arrivals", arrivalChoices);
		dataset.put("arrival", arrivalChoices.getSelected().getKey());

		super.getResponse().addData(dataset);

	}

}
