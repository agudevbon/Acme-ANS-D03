
package acme.features.manager.dashboard;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flights.Leg;
import acme.entities.flights.LegStatus;
import acme.realms.Manager;

@Repository
public interface ManagerDashboardRepository extends AbstractRepository {

	@Query("select m from Manager m where m.userAccount.id = :userAccountId")
	Manager findManagerByAccountId(int userAccountId);

	@Query("select m from Manager m")
	List<Manager> getAllManagers();

	@Query("select l from Leg l where l.manager.id = :id and l.status = :status")
	List<Leg> getManagerLegsByStatus(int id, LegStatus status);

	@Query("select avg(f.cost.amount) FROM Flight f WHERE f.manager.id = :managerId and f.draftMode = false and f.cost.currency = :currency")
	double findAverageFlightCost(int managerId, String currency);

	@Query("select stddev(f.cost.amount) FROM Flight f WHERE f.manager.id = :managerId and f.draftMode = false and f.cost.currency = :currency")
	double findDeviationFlightCost(int managerId, String currency);

	@Query("select max(f.cost.amount) FROM Flight f WHERE f.manager.id = :managerId and f.draftMode = false and f.cost.currency = :currency")
	double findMaximumFlightCost(int managerId, String currency);

	@Query("select min(f.cost.amount) FROM Flight f WHERE f.manager.id = :managerId and f.draftMode = false and f.cost.currency = :currency")
	double findMinimumFlightCost(int managerId, String currency);

}
