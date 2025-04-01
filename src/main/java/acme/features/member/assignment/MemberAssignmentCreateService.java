
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
public class MemberAssignmentCreateService extends AbstractGuiService<Member, FlightAssignment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberAssignmentRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		FlightAssignment assignment = new FlightAssignment();
		assignment.setStatus(AssignmentStatus.PENDING);
		assignment.setLastUpdateMoment(new Date());

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
		boolean isDutyLeadAttendant = assignment.getDuty().name().equals("LEAD_ATTENDANT");

		super.state(isSelfAssignment, "member", "Solo puedes crear asignaciones para ti mismo.");
		super.state(isDutyLeadAttendant, "duty", "Solo puedes crear asignaciones como LEAD_ATTENDANT.");

		boolean isAvailable = "AVAILABLE".equals(assignment.getMember().getAvailabilityStatus());
		boolean notAlreadyAssigned = !this.repository.existsAssignmentForLeg(assignment.getMember().getId(), assignment.getLeg().getId());

		super.state(isAvailable, "member", "El miembro debe estar disponible.");
		super.state(notAlreadyAssigned, "leg", "El miembro ya está asignado a este tramo.");

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
		assignment.setStatus(AssignmentStatus.PENDING);
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
