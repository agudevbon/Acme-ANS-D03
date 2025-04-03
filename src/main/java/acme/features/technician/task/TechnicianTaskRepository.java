
package acme.features.technician.task;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.task.Task;

@Repository
public interface TechnicianTaskRepository extends AbstractRepository {

	@Query("select t from Task t where t.id = :id")
	Task findTaskById(Integer id);

	@Query("select t from Task t where t.technician.id =:technicianId ")
	List<Task> findTaskByTechnicianId(Integer technicianId);

}
