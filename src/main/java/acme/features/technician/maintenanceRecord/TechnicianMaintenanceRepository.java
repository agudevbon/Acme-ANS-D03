
package acme.features.technician.maintenanceRecord;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.entities.maintenanceRecords.MaintenanceTask;

@Repository
public interface TechnicianMaintenanceRepository extends AbstractRepository {

	@Query("select m from MaintenanceRecord m where m.id =:id")
	MaintenanceRecord findMaintenanceRecordById(Integer id);

	@Query("select m from MaintenanceRecord m where m.technician.id =:technicianId ")
	List<MaintenanceRecord> findMaintenanceRecordByTechnicianId(Integer technicianId);

	@Query("select mt from MaintenanceTask mt where mt.maintanceRecord.id =:maintenanceRid")
	List<MaintenanceTask> findMaintenanceTaskByMaintenanceId(Integer maintenanceRid);

	@Query("select a from Aircraft a")
	List<Aircraft> findAircrafts();

	@Query("select a from Aircraft a where a.id = :id")
	Aircraft findAircraftById(Integer id);
}
