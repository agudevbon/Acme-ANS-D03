
package acme.entities.Booking;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.validation.Mandatory;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Booking extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	// Código localizador único con el patrón especificado
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z0-9]{6,8}$", message = "{booking.locator.pattern}")
	@Mandatory
	private String				locatorCode;

	// Momento de compra (en el pasado)
	@Past
	@Mandatory
	private Date				purchaseMoment;

	// Clase de viaje (Economy o Business)
	@Mandatory
	private String				travelClass; // Se recomienda usar un Enum, pero en este caso lo dejamos como String

	// Precio (debe ser positivo)
	@Positive
	@Mandatory
	private Double				price;

	// Últimos 4 dígitos de la tarjeta de crédito (opcional)
	@Size(min = 4, max = 4)
	private String				lastCardNibble;
}
