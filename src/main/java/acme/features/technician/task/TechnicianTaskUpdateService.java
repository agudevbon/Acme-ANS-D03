
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
public class TechnicianTaskUpdateService extends AbstractGuiService<Technician, Task> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianTaskRepository repository;

	// AbstractGuiService interface --------------------------------------------


	@Override
	public void authorise() {
		boolean status = false;
		Integer taskId;
		Task task;
		Technician technician;

		if (super.getRequest().hasData("id", Integer.class)) {
			taskId = super.getRequest().getData("id", Integer.class);
			if (taskId != null) {
				task = this.repository.findTaskById(taskId);
				technician = task == null ? null : task.getTechnician();
				status = task != null && task.isDraftMode() && super.getRequest().getPrincipal().hasRealm(technician);
			}
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Task task;
		Technician technician;

		technician = (Technician) super.getRequest().getPrincipal().getActiveRealm();

		task = new Task();
		task.setDraftMode(true);
		task.setTechnician(technician);

		super.getBuffer().addData(task);
	}

	@Override
	public void bind(final Task task) {

		Technician technician = (Technician) super.getRequest().getPrincipal().getActiveRealm();

		super.bindObject(task, "type", "description", "priority", "estimatedDuration");
		task.setTechnician(technician);

	}

	@Override
	public void validate(final Task task) {
		;
	}

	@Override
	public void perform(final Task task) {
		this.repository.save(task);
	}

	@Override
	public void unbind(final Task task) {
		Dataset dataset;

		SelectChoices typeChoices;
		typeChoices = SelectChoices.from(TaskType.class, task.getType());

		dataset = super.unbindObject(task, "type", "estimatedDuration", "description", "priority", "estimatedDuration", "draftMode");

		dataset.put("technician", task.getTechnician().getIdentity().getFullName());
		dataset.put("types", typeChoices);
		dataset.put("type", typeChoices.getSelected().getKey());

		super.getResponse().addData(dataset);

	}
}
