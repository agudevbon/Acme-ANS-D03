
package acme.entities.trackingLogs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidScore;
import acme.client.components.validation.ValidString;
import acme.entities.claims.AcceptedStatus;
import acme.entities.claims.Claim;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TrackingLog extends AbstractEntity {

	// Serialisation version

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				lastUpdate;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				step;

	@Mandatory
	@ValidScore
	@Automapped
	private Double				resolutionPercentage;

	@Mandatory
	@Valid
	@Automapped
	private AcceptedStatus		isAccepted;

	@Mandatory
	@Automapped
	private boolean				draftMode;

	@Optional
	@ValidString(min = 1, max = 255)
	@Automapped
	private String				resolution;

	// Derived attributes


	@Transient
	public boolean isResolutionValid() {
		return this.resolution != null && !this.resolution.trim().isEmpty();
	}

	// Relationships


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Claim claim;

}
