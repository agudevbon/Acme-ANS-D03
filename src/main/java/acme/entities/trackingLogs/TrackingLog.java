
package acme.entities.trackingLogs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class TrackingLog extends AbstractEntity {

	// Serialisation Version

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@Mandatory
	@ValidMoment
	@Temporal(TemporalType.TIMESTAMP)
	private Date				lastUpdate;

	@Mandatory
	@ValidString(max = 50)
	@Automapped
	private String				step;

	@Mandatory
	@ValidNumber(min = 0, max = 100)
	@Automapped
	private Double				resolutionPercent;

	@Mandatory
	@Valid
	@Automapped
	private Boolean				isAccepted;

	@Mandatory
	@ValidString(max = 255)
	@Automapped
	private String				resolutionDesc;

}
