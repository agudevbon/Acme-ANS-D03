
package acme.constraints;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.flights.Leg;
import acme.entities.flights.LegRepository;

@Validator
public class LegValidator extends AbstractValidator<ValidLeg, Leg> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private LegRepository repository;

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
			{
				boolean uniqueLeg;
				Leg existingLeg;

				existingLeg = this.repository.findLegByFlightNumber(leg.getFlightNumber());
				uniqueLeg = existingLeg == null || existingLeg.equals(leg);

				super.state(context, uniqueLeg, "flightNumber", "acme.validation.leg.duplicated-flight-number.message");
			}
			{

				boolean rightOrder;

				rightOrder = leg.getScheduledArrival().after(leg.getScheduledDeparture());
				super.state(context, rightOrder, "*", "acme.validation.leg.wrong-date-order.message");
			}
			{
				boolean rightFlightNumber;

				String iataCode = leg.getAircraft().getAirline().getIataCode();
				rightFlightNumber = leg.getFlightNumber().substring(0, 3).equals(iataCode);
				super.state(context, rightFlightNumber, "flightNumber", "acme.validation.leg.wrong-iata.message");
			}
			{
				boolean rightManager;

				String manager = leg.getFlight().getManager().getIdentifier();
				rightManager = leg.getManager().getIdentifier().equals(manager);
				super.state(context, rightManager, "manager", "acme.validation.leg.diferent-manager.message");
			}
			{
				boolean rightDuration;

				long longDuration = leg.getScheduledArrival().getTime() - leg.getScheduledDeparture().getTime();
				long diferenciaEnMinutos = longDuration / (1000 * 60);
				rightDuration = (int) diferenciaEnMinutos == leg.getDuration();
				super.state(context, rightDuration, "duration", "acme.validation.leg.wrong-duration.message");
			}
			{
				boolean overlapedAircraft = false;

				List<Leg> legsWSameAircraft = this.repository.findLegsByAircraft(leg.getAircraft().getRegistrationNumber());

				for (Leg objetoExistente : legsWSameAircraft)
					if (leg.getScheduledDeparture().before(objetoExistente.getScheduledArrival()) && leg.getScheduledArrival().after(objetoExistente.getScheduledDeparture())) {
						overlapedAircraft = true;
						break;
					}
				super.state(context, overlapedAircraft, "aircraft", "acme.validation.leg.overlaped-aircraft.message");
			}

			{
				boolean sameAirport;

				sameAirport = leg.getDeparture() != leg.getArrival();

				super.state(context, sameAirport, "*", "acme.validation.leg.same-airport.message");
			}
		}
		result = !super.hasErrors(context);

		return result;
	}

}
