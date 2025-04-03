
package acme.features.assistanceAgent.claim;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.Claim;
import acme.realms.assistanceAgent.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimListUndergoingService extends AbstractGuiService<AssistanceAgent, Claim> {

	@Autowired
	private AssistanceAgentClaimRepository repository;


	@Override
	public void authorise() {
		boolean authorised = super.getRequest().getPrincipal().hasRealmOfType(AssistanceAgent.class);

		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		Collection<Claim> undergoingClaims;
		int agentId;

		agentId = super.getRequest().getPrincipal().getActiveRealm().getId();
		undergoingClaims = this.repository.findUndergoingClaims(agentId);
		super.getBuffer().addData(undergoingClaims);
	}

	@Override
	public void unbind(final Claim claim) {
		Dataset dataset;

		dataset = super.unbindObject(claim, "registrationDate", "passengerEmail", "type", "isAccepted");
		super.getResponse().addData(dataset);
	}

}
