
package acme.entities.Booking;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.entities.Passenger.Passenger;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "booking")
public class Booking extends AbstractEntity {

	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1L;

	//RELACIONARLO CON CUSTOMER

	// Código localizador único con el patrón especificado
	@Column(unique = true)
	@Pattern(regexp = "^[A-Z0-9]{6,8}$", message = "{booking.locator.pattern}")
	@Mandatory
	@Automapped
	private String				locatorCode;

	// Momento de compra (en el pasado)
	@Past
	@Mandatory
	@Automapped
	private Date				purchaseMoment;

	// Clase de viaje (Economy o Business) Enumerado
	@Mandatory
	@Enumerated(EnumType.STRING)
	@Automapped
	private TravelClass			travelClass;

	// Precio (debe ser positivo)

	@Mandatory
	@ValidMoney
	@Automapped
	private Money				price;

	// Últimos 4 dígitos de la tarjeta de crédito (opcional)
	@Optional
	@Size(min = 4, max = 4)
	@Pattern(regexp = "^\\d{4}$", message = "{booking.lastCardNibble.pattern}")
	@Automapped
	private String				lastCardNibble;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@Automapped
	private Passenger			passenger;  // 
}
