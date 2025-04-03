
package acme.features.any.flight;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flights.Flight;
import acme.entities.flights.Leg;

@Repository
public interface AnyFlightRepository extends AbstractRepository {

	@Query("select f from Flight f where f.id = :id")
	Flight findFlightById(Integer id);

	@Query("select f from Flight f where f.draftMode = false")
	List<Flight> findPublishedFlightsd();

	@Query("select l from Leg l where l.flight.id = :id")
	List<Leg> findLegsByFlight(Integer id);

}
