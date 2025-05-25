
package acme.entities.maintenanceRecords;

import java.util.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;

@Repository
public interface MaintenanceRecordRepository extends AbstractRepository {

	@Query("select m from MaintenanceRecord m where m.inspectionDueDate =:inspectionDate")
	MaintenanceRecord findMaintenanceRecordByInspectionDueDate(Date inspectionDate);
}
