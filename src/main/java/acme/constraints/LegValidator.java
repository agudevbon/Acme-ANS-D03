
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.flights.Leg;

@Validator
public class LegValidator extends AbstractValidator<ValidManager, Leg> {

	// ConstraintValidator interface ------------------------------------------

	@Override
	protected void initialise(final ValidManager annotation) {
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
			super.state(context, rightOrder, "ticker", "acme.validation.manager.duplicated-identifier.message");
		}
		result = !super.hasErrors(context);

		return result;
	}

}
