
package acme.features.member.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.activityLogs.ActivityLog;
import acme.realms.Member;

@GuiService
@Service
public class MemberLogDeleteService extends AbstractGuiService<Member, ActivityLog> {

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
		// No binding in delete
	}

	@Override
	public void validate(final ActivityLog log) {
		// No additional validation needed
	}

	@Override
	public void perform(final ActivityLog log) {
		this.repository.delete(log);
	}

	@Override
	public void unbind(final ActivityLog log) {
		Dataset dataset = super.unbindObject(log, "incidentType", "description", "severityLevel");
		dataset.put("registrationMoment", log.getRegistrationMoment());
		dataset.put("flightAssignment", log.getFlightAssignment());

		super.getResponse().addData(dataset);
	}
}
