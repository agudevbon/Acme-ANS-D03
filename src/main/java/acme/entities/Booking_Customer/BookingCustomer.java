
package acme.entities.Booking_Customer;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.mappings.Automapped;
import acme.entities.Booking.Booking;
import acme.entities.Customer.Customer;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Data
@Getter
@Setter
@Table(name = "BookingCustomer")

public class BookingCustomer extends AbstractEntity {

	//-------------------------------------------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	//-------------------------------------------------------------------------------------

	@ManyToOne
	@JoinColumn(name = "booking_id", nullable = false)
	@Automapped
	private Booking				booking;

	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	@Automapped
	private Customer			customer;

}
