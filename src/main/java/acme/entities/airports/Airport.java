
package acme.entities.airports;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidEmail;
import acme.client.components.validation.ValidUrl;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Airport extends AbstractEntity {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	@Mandatory
	@Length(max = 50)
	@Automapped
	private String				name;

	@Mandatory
	@Column(unique = true)
	private String				iataCode;

	@Mandatory
	@Automapped
	private OperationalScope	scope;

	@Mandatory
	@Length(max = 50)
	@Automapped
	private String				city;

	@Mandatory
	@Length(max = 50)
	@Automapped
	private String				country;

	@Optional
	@ValidUrl
	@Automapped
	private String				website;

	@Optional
	@ValidEmail
	@Automapped
	private String				email;

	@Optional
	@Automapped
	private String				addres;

	@Optional
	@Pattern(regexp = "^\\+?\\d{6,15}$")
	@Automapped
	private String				phoneNumber;
}
