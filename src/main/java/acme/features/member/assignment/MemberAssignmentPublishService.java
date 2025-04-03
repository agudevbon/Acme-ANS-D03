
package acme.features.member.assignment;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightAssignments.AssignmentStatus;
import acme.entities.flightAssignments.DutyType;
import acme.entities.flightAssignments.FlightAssignment;
import acme.realms.Member;

@GuiService
@Service
public class MemberAssignmentPublishService extends AbstractGuiService<Member, FlightAssignment> {

	@Autowired
	private MemberAssignmentRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		FlightAssignment assignment = this.repository.findAssignmentById(id);
		int currentMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		boolean isAuthorised = assignment != null && assignment.getMember().getId() == currentMemberId && assignment.getStatus() != AssignmentStatus.CONFIRMED && assignment.getDuty().equals(DutyType.LEAD_ATTENDANT);

		super.getResponse().setAuthorised(isAuthorised);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		FlightAssignment assignment = this.repository.findAssignmentById(id);
		super.getBuffer().addData(assignment);
	}

	@Override
	public void bind(final FlightAssignment assignment) {
		super.bindObject(assignment, "remarks");
	}

	@Override
	public void validate(final FlightAssignment assignment) {
		Date now = new Date();
		boolean legIsUpcoming = assignment.getLeg().getScheduledDeparture().after(now);
		super.state(legIsUpcoming, "leg", "No se puede publicar una asignación de un tramo ya pasado.");
	}

	@Override
	public void perform(final FlightAssignment assignment) {
		assignment.setStatus(AssignmentStatus.CONFIRMED);
		assignment.setLastUpdateMoment(new Date());
		this.repository.save(assignment);
	}

	@Override
	public void unbind(final FlightAssignment assignment) {
		Dataset dataset = super.unbindObject(assignment, "remarks", "status", "lastUpdateMoment");
		super.getResponse().addData(dataset);
	}
}
