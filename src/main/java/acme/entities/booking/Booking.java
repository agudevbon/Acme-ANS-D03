
package acme.entities.booking;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.Valid;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.helpers.SpringHelper;
import acme.entities.flights.Flight;
import acme.entities.flights.FlightRepository;
import acme.entities.passenger.Passenger;
import acme.realms.Customer;
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
	@Temporal(TemporalType.TIMESTAMP)
	private Date				purchaseMoment;

	// Clase de viaje (Economy o Business) Enumerado
	@Mandatory
	@Enumerated(EnumType.STRING)
	@Automapped
	private TravelClass			travelClass;

	// Precio (debe ser positivo)


	/*
	 * @Mandatory
	 * 
	 * @ValidMoney
	 * 
	 * @Automapped
	 */@Transient
	private Money getPrice() {
		Money res;
		FlightRepository flightRepository = SpringHelper.getBean(FlightRepository.class);
		BookingRespository bookingRespository = SpringHelper.getBean(BookingRespository.class);
		if (this.getFlight() == null) {
			Money noRes = new Money();
			noRes.setAmount(0.0);
			noRes.setCurrency("EUR");
			return noRes;
		} else {
			res = flightRepository.findCostByFlight(this.flight.getId());
			Collection<Passenger> passenger = bookingRespository.findPassengersByBooking(this.getId());
			Double amount = res.getAmount() * passenger.size();
			res.setAmount(amount);
			return res;
		}
	}


	// Últimos 4 dígitos de la tarjeta de crédito (opcional)
	@Optional
	@Size(min = 4, max = 4)
	@Pattern(regexp = "^\\d{4}$", message = "{booking.lastCardNibble.pattern}")
	@Automapped
	private String		lastCardNibble;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	@Automapped
	private Customer	customer;  // 

	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Flight		flight;

	@Mandatory
	@Valid
	@Automapped
	private Boolean		isPublished;

}
