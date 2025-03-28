
package acme.features.maintenanceRecord;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.maintenanceRecords.MaintenanceRecord;

@Repository
public interface TechnicianMaintanceRepository extends AbstractRepository {

	@Query("select m  from MaintenanceRecord m where m.id =:id")
	MaintenanceRecord findMaintenanceRecordById(Integer id);

	@Query("select m from MaintenanceTask m where m.task.technician.id= :id ")
	List<MaintenanceRecord> findMaintenanceRecordByTechnicianId(Integer id);
}
