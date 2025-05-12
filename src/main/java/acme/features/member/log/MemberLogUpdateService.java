
package acme.features.member.log;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.activityLogs.ActivityLog;
import acme.entities.flightAssignments.FlightAssignment;
import acme.realms.Member;

@GuiService
@Service
public class MemberLogUpdateService extends AbstractGuiService<Member, ActivityLog> {

	@Autowired
	private MemberLogRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ActivityLog log = this.repository.findOneById(id);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		boolean isAuthorised = log != null && log.getFlightAssignment().getMember().getId() == memberId;

		super.getResponse().setAuthorised(isAuthorised);
	}

	@Override
	public void load() {
		int id = super.getRequest().getData("id", int.class);
		ActivityLog log = this.repository.findOneById(id);
		super.getBuffer().addData(log);
	}

	@Override
	public void bind(final ActivityLog log) {
		super.bindObject(log, "incidentType", "description", "severityLevel", "flightAssignment");
	}

	@Override
	public void validate(final ActivityLog log) {
		if (log.getFlightAssignment() == null)
			return;

		int currentMemberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		boolean isOwner = log.getFlightAssignment().getMember().getId() == currentMemberId;

		super.state(isOwner, "flightAssignment", "Solo puedes modificar logs de tus asignaciones.");
	}

	@Override
	public void perform(final ActivityLog log) {
		log.setRegistrationMoment(new Date());
		this.repository.save(log);
	}

	@Override
	public void unbind(final ActivityLog log) {
		Dataset dataset = super.unbindObject(log, "incidentType", "description", "severityLevel");

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		List<FlightAssignment> assignments = this.repository.findConfirmedAssignmentsByMember(memberId);
		SelectChoices choices = SelectChoices.from(assignments, "id", log.getFlightAssignment());

		dataset.put("flightAssignments", choices);
		dataset.put("registrationMoment", log.getRegistrationMoment());

		super.getResponse().addData(dataset);
	}
}
