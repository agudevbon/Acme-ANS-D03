
package acme.entities.customer;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.entities.booking.Booking;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "customer")
public class Customer extends AbstractEntity {

	/**
	 * 
	 */
	//-------------------------------------------------------------------------------------
	private static final long	serialVersionUID	= 1L;
	//-------------------------------------------------------------------------------------
	// Identificador único con el patrón especificado
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z]{2,3}\\d{6}$", message = "{customer.identifier.pattern}")

	private String				identifier;

	// Número de teléfono con el patrón especificado
	@Pattern(regexp = "^\\+?\\d{6,15}$", message = "{customer.phone.pattern}")
	@Mandatory
	private String				phoneNumber;

	// Dirección física, ciudad y país
	@Size(max = 255)
	@Mandatory
	private String				address;

	@Size(max = 50)
	@Mandatory
	private String				city;

	@Size(max = 50)
	@Mandatory
	private String				country;

	// Puntos opcionales con un máximo de 500,000
	@Min(0)
	@Max(500000)
	private Integer				points;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@Automapped
	private Booking				booking;  // 
}
