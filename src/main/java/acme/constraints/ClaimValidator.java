
package acme.constraints;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.claims.AcceptedStatus;
import acme.entities.claims.Claim;
import acme.entities.claims.ClaimRepository;
import acme.entities.flights.Leg;
import acme.entities.trackingLogs.TrackingLog;

@Validator
public class ClaimValidator extends AbstractValidator<ValidClaim, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ClaimRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidClaim annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Claim claim, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (claim == null)
			super.state(context, false, "*", "acme.validation.NotNull.message");
		else {

			// Checking logic between claim and its trackingLogs
			{
				boolean isLogicValid;

				List<TrackingLog> trackingLogs = this.repository.findAllByClaimId(claim.getId());
				trackingLogs = trackingLogs.stream().filter(x -> x.getResolutionPercentage() == 100.00).toList();

				if (trackingLogs.isEmpty())
					isLogicValid = claim.getIsAccepted().equals(AcceptedStatus.PENDING);

				else if (trackingLogs.size() == 1)
					isLogicValid = trackingLogs.get(0).getIsAccepted().equals(claim.getIsAccepted());

				else {
					Integer moment = MomentHelper.compare(trackingLogs.get(0).getLastUpdate(), trackingLogs.get(1).getLastUpdate());
					TrackingLog lastTrackingLogUpdated = moment < 0 ? trackingLogs.get(0) : trackingLogs.get(1);
					isLogicValid = lastTrackingLogUpdated.getIsAccepted().equals(claim.getIsAccepted());
				}

				super.state(context, isLogicValid, "accepted", "acme.validation.claim.isAccepted.message");
			}

			// Checking the leg associated is in the past
			{
				boolean correctLeg;

				Leg leg = claim.getFlightLeg();
				correctLeg = MomentHelper.compare(MomentHelper.getCurrentMoment(), leg.getScheduledArrival()) >= 0 ? true : false;

				super.state(context, correctLeg, "leg", "acme.validation.claim.legInPast.message");
			}
		}

		result = !super.hasErrors(context);

		return result;
	}

}
