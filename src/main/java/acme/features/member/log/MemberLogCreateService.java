
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
public class MemberLogCreateService extends AbstractGuiService<Member, ActivityLog> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberLogRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		ActivityLog log = new ActivityLog();
		log.setRegistrationMoment(new Date());
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

		super.state(isOwner, "flightAssignment", "Solo puedes registrar actividades para asignaciones tuyas.");
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
