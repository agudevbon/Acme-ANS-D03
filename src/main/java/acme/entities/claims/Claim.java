
package acme.entities.claims;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidEmail;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidClaim;
import acme.entities.assistanceAgents.AssistanceAgent;
import acme.entities.flights.Leg;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@ValidClaim
public class Claim extends AbstractEntity {

	// Serialisation version

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				registrationDate;

	@Mandatory
	@ValidEmail
	@Automapped
	private String				passengerEmail;

	@Mandatory
	@ValidString(min = 1, max = 255)
	@Automapped
	private String				description;

	@Mandatory
	@Valid
	@Automapped
	private ClaimType			type;

	@Mandatory
	@Valid
	@Automapped
	private AcceptedStatus		isAccepted;

	@Mandatory
	@Automapped
	private boolean				draftMode;

	// Relationships

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private AssistanceAgent		agent;

	@Optional
	@Valid
	@ManyToOne
	private Leg					flightLeg;

}
