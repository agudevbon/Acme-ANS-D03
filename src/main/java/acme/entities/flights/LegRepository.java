
package acme.entities.flights;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface LegRepository extends AbstractRepository {

	@Query("select l from Leg l where l.flightNumber = :number")
	Leg findLegByFlightNumber(String number);

	@Query("select l from Leg l where l.aircraft.registrationNumber = :number")
	List<Leg> findLegsByAircraft(String number);
}
