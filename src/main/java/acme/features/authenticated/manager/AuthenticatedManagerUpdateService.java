
package acme.features.authenticated.manager;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.principals.Authenticated;
import acme.client.helpers.PrincipalHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.realms.Manager;

@GuiService
public class AuthenticatedManagerUpdateService extends AbstractGuiService<Authenticated, Manager> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private AuthenticatedManagerRepository repository;

	// AbstractService interface ----------------------------------------------ç


	@Override
	public void authorise() {
		boolean status;

		if (super.getRequest().getMethod().equals("GET"))
			status = super.getRequest().getPrincipal().hasRealmOfType(Manager.class);
		else {
			int managerId;
			Manager manager;
			int id = super.getRequest().getPrincipal().getAccountId();
			managerId = super.getRequest().getData("id", int.class);
			manager = this.repository.findManagerById(managerId);
			status = id == manager.getUserAccount().getId();
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Manager manager;
		int userAccountId;

		userAccountId = super.getRequest().getPrincipal().getAccountId();
		manager = this.repository.findManagerByUserAccountId(userAccountId);

		super.getBuffer().addData(manager);
	}

	@Override
	public void bind(final Manager manager) {
		assert manager != null;

		super.bindObject(manager, "identifier", "experience", "birthDate", "picture");
	}

	@Override
	public void validate(final Manager manager) {
		assert manager != null;
	}

	@Override
	public void perform(final Manager manager) {
		assert manager != null;

		this.repository.save(manager);
	}

	@Override
	public void unbind(final Manager manager) {
		assert manager != null;

		Dataset dataset;

		dataset = super.unbindObject(manager, "identifier", "experience", "birthDate", "picture");
		super.getResponse().addData(dataset);
	}

	@Override
	public void onSuccess() {
		if (super.getRequest().getMethod().equals("POST"))
			PrincipalHelper.handleUpdate();
	}
}
