
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
public class MemberLogPublishService extends AbstractGuiService<Member, ActivityLog> {

	@Autowired
	private MemberLogRepository repository;


	@Override
	public void authorise() {
		int id = super.getRequest().getData("id", int.class);
		ActivityLog log = this.repository.findOneById(id);
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();

		boolean isAuthorised = log != null && log.getFlightAssignment().getMember().getId() == memberId && log.getFlightAssignment().getStatus().toString().equals("CONFIRMED");

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
		super.bindObject(log, "incidentType", "description", "severityLevel");
	}

	@Override
	public void validate(final ActivityLog log) {
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		boolean isOwner = log.getFlightAssignment().getMember().getId() == memberId;

		super.state(isOwner, "flightAssignment", "No puedes publicar logs de asignaciones ajenas.");
	}

	@Override
	public void perform(final ActivityLog log) {
		log.setRegistrationMoment(new Date());
		this.repository.save(log);
	}

	@Override
	public void unbind(final ActivityLog log) {
		Dataset dataset = super.unbindObject(log, "incidentType", "description", "severityLevel");
		dataset.put("registrationMoment", log.getRegistrationMoment());
		dataset.put("flightAssignment", log.getFlightAssignment());

		super.getResponse().addData(dataset);
	}
}
