
package acme.constraints;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.flights.Flight;
import acme.entities.flights.FlightRepository;
import acme.entities.flights.Leg;

@Validator
public class FlightValidator extends AbstractValidator<ValidFlight, Flight> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private FlightRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidFlight annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Flight flight, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (flight == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			{
				boolean hasLegs;

				List<Leg> flightLegs = this.repository.findLegsByFlight(flight.getId());

				hasLegs = flight.getDraftMode() ? true : !flightLegs.isEmpty();

				super.state(context, hasLegs, "tag", "acme.validation.flight.no-legs.message");
			}
			{
				boolean publishedLegs;

				List<Leg> flightLegs = this.repository.findDraftingLegsByFlight(flight.getId());

				publishedLegs = flight.getDraftMode() ? true : flightLegs.isEmpty();

				super.state(context, publishedLegs, "tag", "acme.validation.flight.unpublished-legs.message");
			}
			{
				boolean validCurrency;
				validCurrency = flight.getCost() == null || flight.getCost().getCurrency().equals("EUR");
				super.state(context, validCurrency, "cost", "acme.validation.flight.valid-currency.message");

			}
		}
		result = !super.hasErrors(context);

		return result;
	}

}
