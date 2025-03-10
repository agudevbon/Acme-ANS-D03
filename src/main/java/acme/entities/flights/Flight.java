
package acme.entities.flights;

import java.beans.Transient;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidString;
import acme.client.helpers.SpringHelper;
import acme.realms.Manager;
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
	@ValidString(max = 50)
	@Automapped
	private String				tag;

	@Mandatory
	@Automapped
	private boolean				indication;

	@Mandatory
	@ValidMoney(min = 0.00, max = 1000000.00)
	@Automapped
	private Money				cost;

	@Optional
	@ValidString(max = 255)
	@Automapped
	private String				description;

	// Derived attributes -----------------------------------------------------


	@Transient
	public Date scheduledDeparture() {
		Date result;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);
		List<Leg> legs = repository.findLegsByFlight(this.getId());

		List<Date> departures = legs.stream().map(Leg::getScheduledDeparture).toList();

		Date scheduledDeparture = departures.stream().min(Date::compareTo).orElse(null);

		result = scheduledDeparture;

		return result;
	}

	@Transient
	public Date scheduledArrival() {
		Date result;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);

		List<Leg> legs = repository.findLegsByFlight(this.getId());

		List<Date> arrivals = legs.stream().map(Leg::getScheduledArrival).toList();

		Date scheduledArrival = arrivals.stream().max(Date::compareTo).orElse(null);

		result = scheduledArrival;

		return result;
	}

	@Transient
	public String departureCity() {
		String result;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);

		List<Leg> legs = repository.findLegsByFlight(this.getId());

		Leg leg = legs.stream().min(Comparator.comparing(Leg::getScheduledDeparture)).orElse(null);

		result = leg == null ? null : leg.getDeparture().getCity();
		return result;

	}

	@Transient
	public String arrivalCity() {
		String result;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);

		List<Leg> legs = repository.findLegsByFlight(this.getId());

		Leg leg = legs.stream().max(Comparator.comparing(Leg::getScheduledArrival)).orElse(null);

		result = leg == null ? null : leg.getDeparture().getCity();
		return result;

	}

	@Transient
	public Integer layovers() {
		Integer result;
		FlightRepository repository;

		repository = SpringHelper.getBean(FlightRepository.class);

		List<Leg> legs = repository.findLegsByFlight(this.getId());

		result = legs.size();

		return result;
	}

	// Relationships ----------------------------------------------------------


	@Mandatory
	@Valid
	@ManyToOne(optional = false)
	private Manager manager;

}
