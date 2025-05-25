
package acme.constraints;

import java.time.temporal.ChronoUnit;
import java.util.Date;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.client.helpers.MomentHelper;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.entities.maintenanceRecords.MaintenanceRecordRepository;

@Validator
public class MaintenanceRecordValidator extends AbstractValidator<ValidMaintenanceRecord, MaintenanceRecord> {
	// Internal state ---------------------------------------------------------

	@Autowired
	private MaintenanceRecordRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidMaintenanceRecord annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final MaintenanceRecord maintenanceRecord, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (maintenanceRecord == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {

			{

				boolean rightInspectionDueDate;
				Date minMoment;

				if (maintenanceRecord.getDraftMode() && maintenanceRecord.getMoment() != null && maintenanceRecord.getInspectionDueDate() != null) {
					// based on JobValidator
					minMoment = MomentHelper.deltaFromMoment(maintenanceRecord.getMoment(), 1, ChronoUnit.MINUTES);
					rightInspectionDueDate = MomentHelper.isAfterOrEqual(maintenanceRecord.getInspectionDueDate(), minMoment);
					super.state(context, rightInspectionDueDate, "inspectionDueDate", "acme.validation.maintenance-record.inspection-due-date-incorrect.message");
				}

			}
			{
				boolean inspenctionInTheFuture;
				Date currentMoment;

				if (maintenanceRecord.getDraftMode() && maintenanceRecord.getInspectionDueDate() != null) {
					currentMoment = MomentHelper.deltaFromMoment(MomentHelper.getCurrentMoment(), 1, ChronoUnit.MINUTES);
					inspenctionInTheFuture = MomentHelper.isAfterOrEqual(maintenanceRecord.getInspectionDueDate(), currentMoment);
					super.state(context, inspenctionInTheFuture, "inspectionDueDate", "acme.validation.maintenance-record.inspection-in-the-future.message");
				}

			}

		}
		result = !super.hasErrors(context);

		return result;
	}

}
