
package acme.constraints;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Max;
import javax.validation.constraints.Positive;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation

@Positive
@Max(75)
public @interface ValidExperience {

	// Standard validation properties -----------------------------------------

	String message() default "{acme.validation.manager.experience.message}";

	Class<?>[] groups() default {};
	Class<? extends Payload>[] payload() default {};
}
