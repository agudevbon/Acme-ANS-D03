
package acme.entities;

import javax.persistence.Entity;

import org.hibernate.validator.constraints.Length;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
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
	@Automapped
	private String				tag;

	@Mandatory
	@Automapped
	private boolean				indication;

	@Mandatory
	@Automapped
	private Money				cost;

	@Optional
	@Length(max = 255)
	@Automapped
	private String				description;

}
