
package acme.features.technician.task;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.task.Task;
import acme.entities.task.TaskType;
import acme.realms.Technician;

@GuiService
public class TechnicianTaskPublishService extends AbstractGuiService<Technician, Task> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianTaskRepository repository;

	// AbstractGuiService interface --------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int taskId;
		Task task;
		Technician technician;

		taskId = super.getRequest().getData("id", int.class);
		task = this.repository.findTaskById(taskId);
		technician = task == null ? null : task.getTechnician();
		status = task != null && super.getRequest().getPrincipal().hasRealm(technician) && task.isDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int taskId;
		Task task;

		taskId = super.getRequest().getData("id", int.class);
		task = this.repository.findTaskById(taskId);

		super.getBuffer().addData(task);
	}

	@Override
	public void bind(final Task task) {

		super.bindObject(task, "type", "description", "priority", "estimatedDuration");

	}

	@Override
	public void validate(final Task task) {
		;
	}

	@Override
	public void perform(final Task task) {
		task.setDraftMode(false);
		this.repository.save(task);
	}

	@Override
	public void unbind(final Task task) {
		Dataset dataset;

		SelectChoices typeChoices;
		typeChoices = SelectChoices.from(TaskType.class, task.getType());

		dataset = super.unbindObject(task, "technician.licenseNumber", "type", "estimatedDuration", "description", "priority", "estimatedDuration", "draftMode");

		dataset.put("types", typeChoices);

		super.getResponse().addData(dataset);

	}
}
