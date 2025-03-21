
package acme.features.manager.leg;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flights.Leg;

@Repository
public interface ManagerLegRepository extends AbstractRepository {

	@Query("select l from Leg l where l.id = :id")
	Leg findLegById(Integer id);

	@Query("select l from Leg l where l.manager.id = :id")
	List<Leg> findLegByManagerId(Integer id);
}
