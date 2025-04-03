
package acme.features.Customer.booking;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.basis.AbstractRealm;
import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.entities.booking.Booking;
import acme.entities.booking.TravelClass;
import acme.entities.flights.Flight;
import acme.entities.flights.FlightRepository;
import acme.realms.Customer;

public class CustomerBookingCreateService extends AbstractGuiService<Customer, Booking> {

	@Autowired
	private CustomerBookingRepository	repository;

	@Autowired
	private FlightRepository			flightRepository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {

		AbstractRealm principal = super.getRequest().getPrincipal().getActiveRealm();
		int customerId = principal.getId();
		Customer customer = this.repository.findCustomerById(customerId);

		Booking booking = new Booking();
		booking.setPurchaseMoment(MomentHelper.getCurrentMoment());
		booking.setIsPublished(true);
		booking.setCustomer(customer);

		super.getBuffer().addData(booking);

	}

	@Override

	public void bind(final Booking booking) {

		int flightId;
		Flight flight = new Flight();
		flightId = super.getRequest().getData("flight", int.class);
		super.bindObject(booking, "locatorCode", "purchaseMoment", "lastCreditCardNibble", "price", "travelClass", "isPublished");
		booking.setFlight(flight);

	}

	@Override
	public void validate(final Booking booking) {
		Booking boking = this.repository.findBookingByLocatorCode(booking.getLocatorCode());
		if (boking != null)
			super.state(false, "locatorCode", "acme.validation.confirmation.message.booking.locator-code");

	}
	@Override
	public void perform(final Booking object) {
		this.repository.save(object);
	}

	@Override
	public void unbind(final Booking booking) {
		Dataset dataset;
		SelectChoices choices;
		SelectChoices flightChoices;

		Collection<Flight> flights = this.repository.findAllFlights();
		flightChoices = SelectChoices.from(flights, "description", booking.getFlight());
		choices = SelectChoices.from(TravelClass.class, booking.getTravelClass());

		dataset = super.unbindObject(booking, "locatorCode", "purchaseMoment", "lastCreditCardNibble", "price", "isPublished");
		dataset.put("travelClass", choices);
		dataset.put("flight", flightChoices.getSelected().getKey());
		dataset.put("flights", flightChoices);

		super.getResponse().addData(dataset);

	}

}
