
package acme.entities.activityLogs;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.checkerframework.common.value.qual.IntRange;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidString;
import acme.entities.flightAssignments.FlightAssignment;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ActivityLog extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	@Automapped
	private Date				registrationMoment;

	@Mandatory
	@ValidString(min = 1, max = 50)
	@Automapped
	private String				incidentType;

	@Mandatory
	@ValidString(min = 1, max = 255)
	@Automapped
	private String				description;

	@Mandatory
	@IntRange(from = 0, to = 10)
	@Automapped
	private Integer				severityLevel;

	// Relationships ----------------------------------------------------------

	@Mandatory
	@ManyToOne
	private FlightAssignment	flightAssignment;
}
