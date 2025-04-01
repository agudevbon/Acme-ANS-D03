
package acme.features.member.log;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.activityLogs.ActivityLog;

@Repository
public interface MemberLogRepository extends AbstractRepository {

	@Query("SELECT al FROM ActivityLog al WHERE al.flightAssignment.member.id = :memberId")
	Collection<ActivityLog> findAllByMemberId(int memberId);

	@Query("SELECT al FROM ActivityLog al WHERE al.id = :id")
	ActivityLog findOneById(int id);

	@Query("SELECT al.flightAssignment.status FROM ActivityLog al WHERE al.id = :id")
	String findAssignmentStatusByLogId(int id);
}
