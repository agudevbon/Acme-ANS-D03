
package acme.features.member.assignment;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.flightAssignments.FlightAssignment;
import acme.realms.Member;

@GuiService
@Service
public class MemberAssignmentListCompletedService extends AbstractGuiService<Member, FlightAssignment> {

	@Autowired
	private MemberAssignmentRepository repository;


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<FlightAssignment> assignments;
		int memberId = super.getRequest().getPrincipal().getActiveRealm().getId();
		Date now = new Date();

		assignments = this.repository.findCompletedAssignmentsByMember(now, memberId);
		super.getBuffer().addData(assignments);
	}

	@Override
	public void unbind(final FlightAssignment assignment) {
		Dataset dataset = super.unbindObject(assignment, "duty", "leg", "status");
		dataset.put("type", "completed");
		super.getResponse().addData(dataset);
	}
}
