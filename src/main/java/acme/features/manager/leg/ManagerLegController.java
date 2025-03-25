
package acme.features.manager.leg;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.flights.Leg;
import acme.realms.Manager;

@GuiController
public class ManagerLegController extends AbstractGuiController<Manager, Leg> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private ManagerLegShowService		showService;

	@Autowired
	private ManagerLegListService		listService;

	@Autowired
	private ManagerLegCreateService		createService;

	@Autowired
	private ManagerLegDeleteService		deleteService;

	@Autowired
	private ManagerLegUpdateService		updateService;

	@Autowired
	private ManagerLegPublishService	publishService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		super.addBasicCommand("list", this.listService);
		super.addBasicCommand("show", this.showService);
		super.addBasicCommand("create", this.createService);
		super.addBasicCommand("delete", this.deleteService);
		super.addBasicCommand("update", this.updateService);

		super.addCustomCommand("publish", "update", this.publishService);
	}
}
