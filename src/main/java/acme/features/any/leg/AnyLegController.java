
package acme.features.any.leg;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.principals.Any;
import acme.client.controllers.AbstractGuiController;
import acme.client.controllers.GuiController;
import acme.entities.flights.Leg;

@GuiController
public class AnyLegController extends AbstractGuiController<Any, Leg> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AnyLegShowService			showService;

	@Autowired
	private AnyLegListByFlightService	listByService;

	// Constructors -----------------------------------------------------------


	@PostConstruct
	protected void initialise() {
		;
		super.addBasicCommand("show", this.showService);
		super.addCustomCommand("list-by", "list", this.listByService);
	}
}
