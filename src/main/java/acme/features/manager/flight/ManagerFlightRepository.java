
package acme.features.manager.flight;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flights.Flight;
import acme.entities.flights.Leg;

@Repository
public interface ManagerFlightRepository extends AbstractRepository {

	@Query("select f from Flight f where f.id = :id")
	Flight findFlightById(Integer id);

	@Query("select f from Flight f where f.manager.id = :id")
	List<Flight> findFlightByManagerId(Integer id);

	@Query("select l from Leg l where l.flight.id = :id")
	List<Leg> findLegsByFlight(Integer id);

}
