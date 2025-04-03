
package acme.features.assistanceAgent.claim;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.claims.AcceptedStatus;
import acme.entities.claims.Claim;
import acme.entities.claims.ClaimType;
import acme.entities.flights.Leg;
import acme.realms.assistanceAgent.AssistanceAgent;

@GuiService
public class AssistanceAgentClaimShowService extends AbstractGuiService<AssistanceAgent, Claim> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AssistanceAgentClaimRepository repository;

	// AbstractGuiService interfaced ------------------------------------------


	@Override
	public void authorise() {
		int claimId = super.getRequest().getData("id", int.class);
		Claim claim = this.repository.findClaimById(claimId);

		boolean authorised = super.getRequest().getPrincipal().hasRealmOfType(AssistanceAgent.class) && claim != null && super.getRequest().getPrincipal().getActiveRealm().getId() == claim.getId();
		super.getResponse().setAuthorised(authorised);
	}

	@Override
	public void load() {
		Claim claim;
		int id;

		id = super.getRequest().getData("id", int.class);
		Leg leg = this.repository.findLegByClaimId(id);
		claim = this.repository.findClaimById(id);
		claim.setFlightLeg(leg);

		super.getBuffer().addData(claim);
	}

	@Override
	public void unbind(final Claim claim) {
		Dataset dataset;
		SelectChoices claimTypeChoices = SelectChoices.from(ClaimType.class, claim.getType());
		SelectChoices indicatorChoices = SelectChoices.from(AcceptedStatus.class, claim.getIsAccepted());
		SelectChoices legChoices = SelectChoices.from(this.repository.findAvailableLegs(), "flightNumber", claim.getFlightLeg());

		dataset = super.unbindObject(claim, "registrationDate", "passengerEmail", "description", "draftMode");
		dataset.put("type", claimTypeChoices);
		dataset.put("isAccepted", indicatorChoices);
		dataset.put("fligtLeg", legChoices.getSelected().getKey());
		dataset.put("flightLegs", legChoices);

		super.getResponse().addData(dataset);
	}
}
