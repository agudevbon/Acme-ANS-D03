
package acme.features.manager.flight;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flights.Flight;

@Repository
public interface ManagerFlightRepository extends AbstractRepository {

	@Query("select l.flight from Leg l where l.flight.id = :id")
	Flight findFlightById(Integer id);

	@Query("select f from Flight f where f.manager.id = :id")
	List<Flight> findFlightByManagerId(Integer id);

}
