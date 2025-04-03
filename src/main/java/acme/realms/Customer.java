
package acme.realms;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import acme.client.components.basis.AbstractRole;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidNumber;
import acme.client.components.validation.ValidString;
import acme.constraints.ValidIdentifier;
import acme.constraints.ValidPhoneNumber;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer extends AbstractRole {

	/**
	 * 
	 */
	//-------------------------------------------------------------------------------------
	private static final long	serialVersionUID	= 1L;
	//-------------------------------------------------------------------------------------

	@Mandatory
	@ValidIdentifier
	@Automapped
	private String				identifier;

	// Número de teléfono con el patrón especificado
	@ValidPhoneNumber
	@Mandatory
	@Automapped
	private String				phoneNumber;

	// Dirección física, ciudad y país
	@Size(max = 255)
	@ValidString
	@Mandatory
	private String				address;

	@Size(max = 50)
	@ValidString
	@Mandatory
	private String				city;

	@ValidString
	@Size(max = 50)
	@Mandatory
	private String				country;

	// Puntos opcionales con un máximo de 500,000
	@Optional
	@ValidNumber(min = 0, max = 500000)
	private Integer				points;

	/*
	 * @ManyToOne
	 * 
	 * @JoinColumn(name = "customer_id")
	 * 
	 * @Automapped
	 * private Booking booking; //
	 */
}
