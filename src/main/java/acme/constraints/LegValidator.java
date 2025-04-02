
package acme.constraints;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.entities.aircraft.AircraftStatus;
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

				rightOrder = leg.getScheduledArrival() == null || leg.getScheduledDeparture() == null || leg.getScheduledArrival().after(leg.getScheduledDeparture());
				super.state(context, rightOrder, "scheduledArrival", "acme.validation.leg.wrong-date-order.message");
			}
			{
				boolean rightFlightNumber = true;

				if (leg.getFlightNumber() != null && leg.getFlightNumber().length() > 3 && leg.getAircraft() != null)
					rightFlightNumber = leg.getFlightNumber().substring(0, 3).equals(leg.getAircraft().getAirline().getIataCode());
				super.state(context, rightFlightNumber, "flightNumber", "acme.validation.leg.wrong-iata.message");
			}
			{
				boolean rightManager;

				rightManager = leg.getManager() != null ? true : leg.getManager().getIdentifier().equals(leg.getFlight().getManager().getIdentifier());
				super.state(context, rightManager, "manager", "acme.validation.leg.diferent-manager.message");
			}
			{
				boolean rightDuration = true;
				if (leg.getScheduledArrival() != null && leg.getScheduledDeparture() != null) {
					long longDuration = leg.getScheduledArrival().getTime() - leg.getScheduledDeparture().getTime();
					long diferenciaEnMinutos = longDuration / (1000 * 60);
					rightDuration = (int) diferenciaEnMinutos >= 1 && (int) diferenciaEnMinutos <= 1000;
				}
				super.state(context, rightDuration, "scheduledArrival", "acme.validation.leg.wrong-duration.message");
			}
			{
				boolean overlapedAircraft = true;

				if (leg.getAircraft() != null && leg.getScheduledArrival() != null && leg.getScheduledDeparture() != null) {
					List<Leg> legsWSameAircraft = this.repository.findLegsByAircraft(leg.getAircraft().getRegistrationNumber());
					legsWSameAircraft = legsWSameAircraft.stream().filter(legs -> !Objects.equals(legs.getFlightNumber(), leg.getFlightNumber())).collect(Collectors.toList());
					overlapedAircraft = !legsWSameAircraft.stream().anyMatch(existingObject ->
					// Case 1: Start of new object is within an existing interval
					existingObject.getScheduledDeparture().compareTo(leg.getScheduledDeparture()) <= 0 && existingObject.getScheduledArrival().compareTo(leg.getScheduledDeparture()) >= 0 ||

					// Case 2: End of new object is within an existing interval
						existingObject.getScheduledDeparture().compareTo(leg.getScheduledArrival()) <= 0 && existingObject.getScheduledArrival().compareTo(leg.getScheduledArrival()) >= 0 ||

						// Case 3: New object completely contains an existing interval
						existingObject.getScheduledDeparture().compareTo(leg.getScheduledDeparture()) >= 0 && existingObject.getScheduledArrival().compareTo(leg.getScheduledArrival()) <= 0);

				}
				super.state(context, overlapedAircraft, "aircraft", "acme.validation.leg.overlaped-aircraft.message");
			}
			{
				boolean sameAirport;

				sameAirport = leg.getDeparture() == null && leg.getArrival() == null ? true : leg.getDeparture() != leg.getArrival();

				super.state(context, sameAirport, "departure", "acme.validation.leg.same-airport.message");
			}
			{
				boolean correctAircraft = true;

				correctAircraft = leg.getAircraft() == null ? true : leg.getAircraft().getStatus() == AircraftStatus.ACTIVE_SERVICE;
				super.state(context, correctAircraft, "departure", "acme.validation.leg.maintenance-aircraft.message");
			}

		}
		result = !super.hasErrors(context);

		return result;
	}

}
