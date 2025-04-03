
package acme.features.member.assignment;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.flightAssignments.FlightAssignment;
import acme.realms.Member;

@GuiController
public class MemberAssignmentController extends AbstractGuiController<Member, FlightAssignment> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private MemberAssignmentShowService				showService;

	@Autowired
	private MemberAssignmentCreateService			createService;

	@Autowired
	private MemberAssignmentUpdateService			updateService;

	@Autowired
	private MemberAssignmentListService				listService;

	@Autowired
	private MemberAssignmentListCompletedService	listCompletedService;

	@Autowired
	private MemberAssignmentListUncompletedService	listUncompletedService;

	@Autowired
	private MemberAssignmentPublishService			publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("list-completed", "list", this.listCompletedService);
		super.addCustomCommand("list-planned", "list", this.listUncompletedService);
		super.addCustomCommand("publish", "update", this.publishService);
	}
}
