
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
public class MemberAssignmentUpdateService extends AbstractGuiService<Member, FlightAssignment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberAssignmentRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		int assignmentId = super.getRequest().getData("id", int.class);
		FlightAssignment assignment = this.repository.findAssignmentById(assignmentId);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		boolean isAuthorised = assignment != null && assignment.getMember().getId() == memberId && assignment.getStatus() != AssignmentStatus.CONFIRMED;

		super.getResponse().setAuthorised(isAuthorised);
	}

	@Override
	public void load() {
		int assignmentId = super.getRequest().getData("id", int.class);
		FlightAssignment assignment = this.repository.findAssignmentById(assignmentId);
		super.getBuffer().addData(assignment);
	}

	@Override
	public void bind(final FlightAssignment assignment) {
		super.bindObject(assignment, "duty", "leg", "member", "remarks");
	}

	@Override
	public void validate(final FlightAssignment assignment) {
		if (assignment.getMember() == null || assignment.getLeg() == null)
			return;

		int currentMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		boolean isSelfAssignment = assignment.getMember().getId() == currentMemberId;

		super.state(isSelfAssignment, "member", "Solo puedes modificar asignaciones tuyas.");

		boolean isAvailable = "AVAILABLE".equals(assignment.getMember().getAvailabilityStatus());
		super.state(isAvailable, "member", "El miembro debe estar disponible.");

		boolean notAlreadyAssigned = !this.repository.existsAssignmentForLeg(assignment.getMember().getId(), assignment.getLeg().getId());
		super.state(notAlreadyAssigned, "leg", "Ya estás asignado a este tramo.");

		if (assignment.getDuty().name().equals("PILOT")) {
			boolean pilotAlreadyAssigned = this.repository.isPilotAssigned(assignment.getLeg().getId());
			super.state(!pilotAlreadyAssigned, "duty", "Este tramo ya tiene un piloto asignado.");
		}

		if (assignment.getDuty().name().equals("COPILOT")) {
			boolean copilotAlreadyAssigned = this.repository.isCopilotAssigned(assignment.getLeg().getId());
			super.state(!copilotAlreadyAssigned, "duty", "Este tramo ya tiene un copiloto asignado.");
		}
	}

	@Override
	public void perform(final FlightAssignment assignment) {
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
