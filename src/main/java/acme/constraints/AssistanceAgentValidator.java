
package acme.constraints;

import java.util.List;

import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.validation.AbstractValidator;
import acme.client.components.validation.Validator;
import acme.realms.assistanceAgent.AssistanceAgent;
import acme.realms.assistanceAgent.AssistanceAgentRepository;

@Validator
public class AssistanceAgentValidator extends AbstractValidator<ValidAssistanceAgent, AssistanceAgent> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AssistanceAgentRepository repository;

	// ConstraintValidator interface ------------------------------------------


	@Override
	protected void initialise(final ValidAssistanceAgent annotation) {
		assert annotation != null;
	}

	@Override
	public boolean isValid(final AssistanceAgent assistanceAgent, final ConstraintValidatorContext context) {
		assert context != null;

		boolean result;

		if (assistanceAgent == null)
			super.state(context, false, "*", "acme.validation.NotNull.message");
		else {

			boolean isAgentCodeUnique = true;

			List<AssistanceAgent> assistanceAgents = this.repository.findAllAssistanceAgent();
			assistanceAgents.removeIf(agent -> agent.getId() == assistanceAgent.getId());
			String agentCode1 = assistanceAgent.getCode();
			for (AssistanceAgent agent : assistanceAgents)
				if (agent.getCode().equals(agentCode1))
					isAgentCodeUnique = false;

			super.state(context, isAgentCodeUnique, "code", "acme.validation.assistanceAgent.code.message");

		}

		result = !super.hasErrors(context);

		return result;
	}

}
