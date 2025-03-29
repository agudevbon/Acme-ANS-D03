
package acme.features.technician.maintenanceRecord;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.maintenanceRecords.MaintenanceRecord;

@Repository
public interface TechnicianMaintanceRepository extends AbstractRepository {

	@Query("select m.maintanceRecord from MaintenanceTask m where m.maintanceRecord.id =:id")
	MaintenanceRecord findMaintenanceRecordById(Integer id);

	@Query("select m.maintanceRecord  from MaintenanceTask m where m.task.technician.id= :technicianId ")
	List<MaintenanceRecord> findMaintenanceRecordByTechnicianId(Integer technicianId);
}
