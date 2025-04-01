
package acme.features.member.log;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.activityLogs.ActivityLog;
import acme.realms.Member;

@GuiService
@Service
public class MemberLogUpdateService extends AbstractGuiService<Member, ActivityLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberLogRepository repository;

	// AbstractGuiService interface -------------------------------------------


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
		String assignmentStatus = this.repository.findAssignmentStatusByLogId(log.getId());

		boolean isNotPublished = !assignmentStatus.equals("CONFIRMED");
		super.state(isNotPublished, "*", "No puedes modificar un registro ya publicado.");
	}

	@Override
	public void perform(final ActivityLog log) {
		log.setRegistrationMoment(new Date());
		this.repository.save(log);
	}

	@Override
	public void unbind(final ActivityLog log) {
		Dataset dataset = super.unbindObject(log, "incidentType", "description", "severityLevel", "flightAssignment");
		dataset.put("registrationMoment", log.getRegistrationMoment());

		super.getResponse().addData(dataset);
	}
}
