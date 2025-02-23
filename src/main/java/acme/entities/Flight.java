
package acme.entities;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Flight extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@Length(max = 50)
	private String				tag;

	@Mandatory
	private boolean				indication;

	@Mandatory
	private Money				cost;

	@Length(max = 255)
	private String				description;

}
