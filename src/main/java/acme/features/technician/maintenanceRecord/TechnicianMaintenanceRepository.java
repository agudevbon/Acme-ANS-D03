
package acme.features.technician.maintenanceRecord;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecords.Involves;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.entities.task.Task;

@Repository
public interface TechnicianMaintenanceRepository extends AbstractRepository {

	@Query("select m from MaintenanceRecord m where m.id =:id")
	MaintenanceRecord findMaintenanceRecordById(int id);

	@Query("select m from MaintenanceRecord m where m.technician.id =:technicianId ")
	List<MaintenanceRecord> findMaintenanceRecordByTechnicianId(int technicianId);

	@Query("select a from Aircraft a")
	List<Aircraft> findAircrafts();

	@Query("select a from Aircraft a where a.id = :id")
	Aircraft findAircraftById(int id);

	@Query("select i from Involves i where i.maintenanceRecord.id = :maintenanceRecordId")
	List<Involves> findInvolvesByMaintenanceRecordId(int maintenanceRecordId);

	@Query("select i.task from Involves i where i.maintenanceRecord.id =:maintenanceRecordId ")
	List<Task> findTasksByMaintenanceRecord(int maintenanceRecordId);

	@Query("select i.task from Involves i where i.maintenanceRecord.id =:maintenanceRecordId and i.maintenanceRecord.draftMode = :draft")
	List<Task> findDraftingTasksByMaintenance(Integer maintenanceRecordId, Boolean draft);
}
