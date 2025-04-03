
package acme.features.member.log;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.activityLogs.ActivityLog;
import acme.realms.Member;

@GuiService
@Service
public class MemberLogListService extends AbstractGuiService<Member, ActivityLog> {

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
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		Collection<ActivityLog> logs = this.repository.findAllByMemberId(memberId);
		super.getBuffer().addData(logs);
	}

	@Override
	public void unbind(final ActivityLog log) {
		Dataset dataset;

		dataset = super.unbindObject(log, "incidentType", "description", "severityLevel", "flightAssignment");
		dataset.put("registrationMoment", log.getRegistrationMoment());

		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		dataset.put("flightAssignments", this.repository.findAvailableAssignmentsByMember(memberId));

		super.getResponse().addData(dataset);
	}
}
