
package acme.features.member.log;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.activityLogs.ActivityLog;
import acme.realms.Member;

@GuiController
public class MemberLogController extends AbstractGuiController<Member, ActivityLog> {

	@Autowired
	private MemberLogListService	listService;

	@Autowired
	private MemberLogShowService	showService;

	@Autowired
	private MemberLogCreateService	createService;

	@Autowired
	private MemberLogUpdateService	updateService;

	@Autowired
	private MemberLogDeleteService	deleteService;

	@Autowired
	private MemberLogPublishService	publishService;


	@PostConstruct
	protected void initialise() {
		System.out.println(">> Inicializando MemberLogController");
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("update", this.updateService);
		super.addBasicCommand("delete", this.deleteService);

		super.addCustomCommand("publish", "update", this.publishService);
	}
}
