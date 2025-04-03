
package acme.features.member.assignment;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightAssignments.AssignmentStatus;
import acme.entities.flightAssignments.DutyType;
import acme.entities.flightAssignments.FlightAssignment;
import acme.entities.flights.Leg;
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
		assignment.setLastUpdateMoment(new Date());
		assignment.setStatus(AssignmentStatus.PENDING);
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
		boolean isDutyLeadAttendant = assignment.getDuty().equals(DutyType.LEAD_ATTENDANT);

		super.state(isSelfAssignment, "member", "Solo puedes crear asignaciones para ti mismo.");
		super.state(isDutyLeadAttendant, "duty", "Solo puedes crear asignaciones como LEAD_ATTENDANT.");

		boolean isAvailable = assignment.getMember().getAvailabilityStatus().toString().equals("AVAILABLE");
		boolean notAlreadyAssigned = !this.repository.existsAssignmentForLeg(assignment.getMember().getId(), assignment.getLeg().getId());

		super.state(isAvailable, "member", "El miembro debe estar disponible.");
		super.state(notAlreadyAssigned, "leg", "El miembro ya está asignado a este tramo.");

		if (assignment.getDuty().equals(DutyType.PILOT)) {
			boolean pilotAlreadyAssigned = this.repository.isPilotAssigned(assignment.getLeg().getId());
			super.state(!pilotAlreadyAssigned, "duty", "Este tramo ya tiene un piloto asignado.");
		}

		if (assignment.getDuty().equals(DutyType.COPILOT)) {
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
		Dataset dataset;

		SelectChoices dutyChoices = SelectChoices.from(DutyType.class, assignment.getDuty());
		Collection<Leg> legs = this.repository.findAllLegs();
		SelectChoices legChoices = SelectChoices.from(legs, "flightNumber", assignment.getLeg());

		Collection<Member> members = this.repository.findAllAvailableMembers();
		SelectChoices memberChoices = SelectChoices.from(members, "employeeCode", assignment.getMember());

		dataset = super.unbindObject(assignment, "remarks");

		dataset.put("dutyChoices", dutyChoices);
		dataset.put("legChoices", legChoices);
		dataset.put("memberChoices", memberChoices);

		dataset.put("duty", dutyChoices.getSelected().getKey());
		dataset.put("leg", legChoices.getSelected().getKey());
		dataset.put("member", memberChoices.getSelected().getKey());

		super.getResponse().addData(dataset);
	}
}
