
package acme.entities.flights;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface FlightRepository extends AbstractRepository {

	@Query("select l from Leg l where l.flight.id = :id")
	List<Leg> findLegsByFlight(Integer id);

	@Query("SELECT l FROM Leg l WHERE l.flight.id = :id ORDER BY l.scheduledDeparture ASC")
	List<Leg> findLegsByFlightDeparture(Integer id);

	@Query("SELECT l FROM Leg l WHERE l.flight.id = :id ORDER BY l.scheduledArrival DESC")
	List<Leg> findLegsByFlightArrival(Integer id);

	@Query("select l from Leg l where l.flight.id = :id and l.draftMode = true")
	List<Leg> findDraftingLegsByFlight(Integer id);
}
