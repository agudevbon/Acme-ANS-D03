
package acme.features.member.assignment;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightAssignments.DutyType;
import acme.entities.flightAssignments.FlightAssignment;
import acme.entities.flights.Leg;
import acme.realms.Member;

@GuiService
@Service
public class MemberAssignmentShowService extends AbstractGuiService<Member, FlightAssignment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberAssignmentRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		int assignmentId = super.getRequest().getData("id", int.class);
		FlightAssignment assignment = this.repository.findAssignmentById(assignmentId);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		boolean isAuthorised = assignment != null && assignment.getMember().getId() == memberId;
		super.getResponse().setAuthorised(isAuthorised);
	}

	@Override
	public void load() {
		int assignmentId = super.getRequest().getData("id", int.class);
		FlightAssignment assignment = this.repository.findAssignmentById(assignmentId);
		super.getBuffer().addData(assignment);
	}

	@Override
	public void unbind(final FlightAssignment assignment) {
		Dataset dataset;

		// Select choices
		SelectChoices dutyChoices = SelectChoices.from(DutyType.class, assignment.getDuty());
		List<Leg> legs = this.repository.findAllLegs();
		SelectChoices legChoices = SelectChoices.from(legs, "flightNumber", assignment.getLeg());
		List<Member> members = this.repository.findAllAvailableMembers();
		SelectChoices memberChoices = SelectChoices.from(members, "employeeCode", assignment.getMember());

		dataset = super.unbindObject(assignment, "duty", "status", "remarks", "lastUpdateMoment", "leg", "member");

		dataset.put("dutyChoices", dutyChoices);
		dataset.put("legChoices", legChoices);
		dataset.put("memberChoices", memberChoices);
		dataset.put("duty", dutyChoices.getSelected().getKey());
		dataset.put("leg", legChoices.getSelected().getKey());
		dataset.put("member", memberChoices.getSelected().getKey());

		dataset.put("scheduledDeparture", assignment.getLeg().getScheduledDeparture());
		dataset.put("scheduledArrival", assignment.getLeg().getScheduledArrival());
		dataset.put("departureAirport", assignment.getLeg().getDeparture().getName());
		dataset.put("arrivalAirport", assignment.getLeg().getArrival().getName());

		List<FlightAssignment> crew = this.repository.findCrewByLegId(assignment.getLeg().getId());
		dataset.put("crew", crew);

		super.getResponse().addData(dataset);
	}
}
