
package acme.features.member.assignment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightAssignments.AssignmentStatus;
import acme.entities.flightAssignments.FlightAssignment;
import acme.realms.Member;

@GuiService
@Service
public class MemberAssignmentPublishService extends AbstractGuiService<Member, FlightAssignment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberAssignmentRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		int assignmentId = super.getRequest().getData("id", int.class);
		FlightAssignment assignment = this.repository.findAssignmentById(assignmentId);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		boolean isAuthorised = assignment != null && assignment.getMember().getId() == memberId && assignment.getDuty().name().equals("LEAD_ATTENDANT") && assignment.getStatus() != AssignmentStatus.CONFIRMED;

		super.getResponse().setAuthorised(isAuthorised);
	}

	@Override
	public void load() {
		int assignmentId = super.getRequest().getData("id", int.class);
		FlightAssignment assignment = this.repository.findAssignmentById(assignmentId);
		super.getBuffer().addData(assignment);
	}

	@Override
	public void validate(final FlightAssignment assignment) {
		Date now = new Date();
		Date departure = assignment.getLeg().getScheduledDeparture();

		super.state(departure.after(now), "leg", "No puedes publicar una asignación cuyo vuelo ya haya comenzado.");
	}

	@Override
	public void perform(final FlightAssignment assignment) {
		assignment.setStatus(AssignmentStatus.CONFIRMED);
		assignment.setLastUpdateMoment(new Date());
		this.repository.save(assignment);
	}

	@Override
	public void unbind(final FlightAssignment assignment) {
		Dataset dataset = super.unbindObject(assignment, "duty", "leg", "member", "remarks");
		dataset.put("status", assignment.getStatus().toString());
		dataset.put("lastUpdateMoment", assignment.getLastUpdateMoment());

		super.getResponse().addData(dataset);
	}
}
