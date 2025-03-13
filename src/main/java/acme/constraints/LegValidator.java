
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.flights.Leg;

@Validator
public class LegValidator extends AbstractValidator<ValidLeg, Leg> {

	// ConstraintValidator interface ------------------------------------------

	@Override
	protected void initialise(final ValidLeg annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Leg leg, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (leg == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			boolean rightOrder;

			rightOrder = leg.getScheduledArrival().after(leg.getScheduledDeparture());
			super.state(context, rightOrder, "*", "acme.validation.leg.wrong-date-order.message");
		}
		{
			boolean rightFlightNumber;

			String iataCode = leg.getAircraft().getAirline().getIataCode();
			rightFlightNumber = leg.getFlightNumber().substring(0, 3).equals(iataCode);
			super.state(context, rightFlightNumber, "flightNmuber", "acme.validation.leg.wrong-iata.message");
		}
		{
			boolean rightManager;

			String manager = leg.getFlight().getManager().getIdentifier();
			rightManager = leg.getManager().getIdentifier().equals(manager);
			super.state(context, rightManager, "manager", "acme.validation.leg.diferent-manager.message");
		}

		result = !super.hasErrors(context);

		return result;
	}

}
