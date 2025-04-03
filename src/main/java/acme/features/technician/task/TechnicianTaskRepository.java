
package acme.features.technician.task;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.entities.task.Task;

@Repository
public interface TechnicianTaskRepository extends AbstractRepository {

	@Query("select t from Task t where t.id = :id")
	Task findTaskById(Integer id);

	@Query("select t from Task t where t.technician.id =:technicianId ")
	List<Task> findTaskByTechnicianId(Integer technicianId);

	@Query("select m from MaintenanceRecord m where m.id =:id")
	MaintenanceRecord findMaintenanceRecordById(Integer id);

	@Query("select m from MaintenanceRecord m where m.technician.id =:technicianId ")
	List<MaintenanceRecord> findMaintenanceRecordByTechnicianId(Integer technicianId);

}
