
package acme.features.member.log;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.activityLogs.ActivityLog;
import acme.entities.flightAssignments.FlightAssignment;

@Repository
public interface MemberLogRepository extends AbstractRepository {

	@Query("SELECT al FROM ActivityLog al WHERE al.flightAssignment.member.id = :memberId")
	Collection<ActivityLog> findAllByMemberId(int memberId);

	@Query("SELECT al FROM ActivityLog al WHERE al.id = :id")
	ActivityLog findOneById(int id);

	@Query("SELECT al.flightAssignment.status FROM ActivityLog al WHERE al.id = :id")
	String findAssignmentStatusByLogId(int id);

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.member.id = :memberId AND fa.status != acme.entities.flightAssignments.AssignmentStatus.CONFIRMED")
	Collection<FlightAssignment> findAvailableAssignmentsByMember(int memberId);
}
