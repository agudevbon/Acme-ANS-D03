
package acme.features.manager.leg;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.aircraft.Aircraft;
import acme.entities.airports.Airport;
import acme.entities.flights.Flight;
import acme.entities.flights.Leg;

@Repository
public interface ManagerLegRepository extends AbstractRepository {

	@Query("select l from Leg l where l.id = :id")
	Leg findLegById(Integer id);

	@Query("select f from Flight f where f.id = :id")
	Flight findFlightById(Integer id);

	@Query("select l from Leg l where l.manager.id = :id")
	List<Leg> findLegByManagerId(Integer id);

	@Query("select f from Flight f where f.manager.id = :id")
	List<Flight> findDraftingFlightByManagerId(Integer id);

	@Query("select a from Aircraft a")
	List<Aircraft> findAircrafts();

	@Query("select a from Aircraft a where a.id = :id")
	Aircraft findAircraftById(Integer id);

	@Query("select a from Airport a")
	List<Airport> findAirports();

	@Query("select a from Airport a where a.id = :id")
	Airport findAirportById(Integer id);
}
