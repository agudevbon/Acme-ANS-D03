
package acme.constraints;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.entities.claims.AcceptedStatus;
import acme.entities.trackingLogs.TrackingLog;

public class TrackingLogValidator extends AbstractValidator<ValidTrackingLog, TrackingLog> {

//	@Autowired
//	private ClaimRepository claimRepository;
	
	// ConstraintValidator interface
	
	@Override
	protected void initialise(final ValidTrackingLog annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final TrackingLog trackingLog, final ConstraintValidatorContext context) {
			assert context != null;

			boolean result;

			if (trackingLog == null)
				super.state(context, false, "*", "acme.validation.NotNull.message");
			else {

				// Checking resolution attribute when resolution percentage is 100
				{
					boolean shouldExistResolution;
					
					shouldExistResolution = trackingLog.getResolutionPercentage() != 100.00 || trackingLog.isResolutionValid();
					super.state(context, shouldExistResolution, "resolution", "acme.validation.trackinLog.shouldExistResolution.message");
				}

				// Checking accepted status and percentage matches
				{
					boolean isLogAcceptedLogic;
					boolean isPending = trackingLog.getIsAccepted().equals(AcceptedStatus.PENDING);
					boolean isComplete = trackingLog.getResolutionPercentage() == 100.0;

					isLogAcceptedLogic = !isComplete && isPending || isComplete && !isPending;
					super.state(context, isLogAcceptedLogic, "isAccepted", "acme.validation.trackingLog.isLogAcceptedLogic.message");
				}

				// Checking there only exists 1 completed tracking log
				{
					boolean checkExistsOnlyOneCompletedTrackingLog;
					
					List<TrackingLog> trackingLogs = this.claimRepository.findAllByClaimId(trackingLog.getClaim().getId());
					trackingLogs = trackingLogs.stream().filter(log -> log.getResolutionPercentage() == 100.00).toList();
					checkExistsOnlyOneCompletedTrackingLog = trackingLogs.size() <= 2 ? true : false;

					super.state(context, checkExistsOnlyOneCompletedTrackingLog, "checkExistsOnlyOneCompletedTrackingLog", "acme.validation.trackingLog.checkExistsOnlyOneCompletedTrackingLog.message");
				}

				// Checking if draftMode attribute matches his claim logic
				{
					boolean isDraftModeLogic;

					isDraftModeLogic = !trackingLog.isDraftMode() || trackingLog.getClaim().isDraftMode();
					super.state(context, isDraftModeLogic, "draftMode", "acme.validation.trackingLog.isDraftModeLogic.message");

				}
			}

			result = !super.hasErrors(context);

			return result;
		}

	}

}
