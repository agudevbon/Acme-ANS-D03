
package acme.features.member.assignment;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.flightAssignments.FlightAssignment;
import acme.entities.flights.Leg;
import acme.realms.Member;

@Repository
public interface MemberAssignmentRepository extends AbstractRepository {

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.member.id = :memberId AND fa.leg.scheduledDeparture>:now")
	List<FlightAssignment> findUncompletedAssignmentsByMember(Date now, int memberId);

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.member.id = :memberId AND fa.leg.scheduledArrival<:now")
	List<FlightAssignment> findCompletedAssignmentsByMember(Date now, int memberId);

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.id = :id")
	FlightAssignment findAssignmentById(int id);

	@Query("SELECT COUNT(fa) > 0 FROM FlightAssignment fa WHERE fa.member.id = :memberId AND fa.leg.id = :legId")
	boolean existsAssignmentForLeg(int memberId, int legId);

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.member.id = :memberId")
	List<FlightAssignment> findAllByMemberId(int memberId);

	@Query("SELECT m FROM Member m WHERE m.availabilityStatus = 'AVAILABLE'")
	List<Member> findAllAvailableMembers();

	@Query("SELECT COUNT(fa) > 0 FROM FlightAssignment fa WHERE fa.leg.id = :legId AND fa.duty = 'PILOT'")
	boolean isPilotAssigned(int legId);

	@Query("SELECT COUNT(fa) > 0 FROM FlightAssignment fa WHERE fa.leg.id = :legId AND fa.duty = 'COPILOT'")
	boolean isCopilotAssigned(int legId);

	@Query("SELECT l FROM Leg l")
	List<Leg> findAllLegs();

	@Query("SELECT fa FROM FlightAssignment fa WHERE fa.leg.id = :legId")
	List<FlightAssignment> findCrewByLegId(int legId);

}
