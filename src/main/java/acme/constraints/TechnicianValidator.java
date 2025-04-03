
package acme.constraints;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.realms.Technician;
import acme.realms.TechnicianRepository;

public class TechnicianValidator extends AbstractValidator<ValidTechnician, Technician> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidTechnician annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final Technician technician, final ConstraintValidatorContext context) {

		assert context != null;

		boolean result;

		if (technician == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			{
				boolean uniqueTechnician;
				Technician existingTechnician;

				existingTechnician = this.repository.findTechnicianByLicenseNumber(technician.getLicenseNumber());
				uniqueTechnician = existingTechnician == null || existingTechnician.equals(technician);

				super.state(context, uniqueTechnician, "licenseNumber", "acme.validation.technician.duplicated-identifier.message");
			}
			{
				boolean correctLicenseNumber = true;

				if (technician.getLicenseNumber() != null) {
					char firstLetterName = technician.getIdentity().getName().charAt(0);
					char firstLetterSurname = technician.getIdentity().getSurname().charAt(0);
					correctLicenseNumber = technician.getLicenseNumber().charAt(0) == firstLetterName && technician.getLicenseNumber().charAt(1) == firstLetterSurname;

				}

				super.state(context, correctLicenseNumber, "licenseNumber", "acme.validators.technician.correct-pattern");
			}
		}

		result = !super.hasErrors(context);

		return result;

	}
}
