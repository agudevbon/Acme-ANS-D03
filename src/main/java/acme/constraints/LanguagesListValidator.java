
package acme.constraints;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import acme.client.components.validation.AbstractValidator;

public class LanguagesListValidator extends AbstractValidator<ValidLanguagesList, List<String>> {

	// Internal state

	//	@Autowired
	//	private LanguagesListRepository repository;

	// ConstraintValidator interface

	@Override
	protected void initialise(final ValidLanguagesList list) {
		assert list != null;
	}

	@Override
	public boolean isValid(final List<String> list, final ConstraintValidatorContext context) {
		assert context != null;
		boolean result = true;
		int sum = 0;

		if (list == null)
			super.state(context, false, "*", "javax.validation.constraints.NotNull.message");
		else {
			for (String elem : list)
				sum += elem.length();

			if (sum > 255)
				super.state(context, false, "languages", "acme.validation.languages.message");
		}

		result = !super.hasErrors(context);

		return result;
	}

}
