
package acme.features.technician.involves;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.maintenanceRecords.Involves;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.entities.task.Task;
import acme.realms.Technician;

@GuiService
public class TechnicianInvolvesCreateService extends AbstractGuiService<Technician, Involves> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianInvolvesRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int maintenanceRecordId;
		MaintenanceRecord maintenanceRecord;
		Technician technician;

		if (!super.getRequest().hasData("maintenanceRecordId"))
			status = false;
		else {
			String method = super.getRequest().getMethod();

			maintenanceRecordId = super.getRequest().getData("maintenanceRecordId", int.class);
			maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRecordId);

			technician = maintenanceRecord == null ? null : maintenanceRecord.getTechnician();
			status = maintenanceRecord != null && maintenanceRecord.isDraftMode() && super.getRequest().getPrincipal().hasRealm(technician);

			if (method.equals("POST")) {
				int taskId = super.getRequest().getData("task", int.class);
				Task task = this.repository.findTaskById(taskId);
				Collection<Task> available = this.repository.findValidTasksToLink(maintenanceRecord);

				if (task == null && taskId != 0 || task != null && !available.contains(task))
					status = false;
			}
		}

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Involves involves;
		Integer maintenanceRecordId;
		MaintenanceRecord maintenanceRecord;

		maintenanceRecordId = super.getRequest().getData("maintenanceRecordId", int.class);
		maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRecordId);

		involves = new Involves();
		involves.setMaintenanceRecord(maintenanceRecord);
		super.getBuffer().addData(involves);

	}

	@Override
	public void bind(final Involves involves) {

		int taskId;
		Task task;

		taskId = super.getRequest().getData("task", int.class);

		task = this.repository.findTaskById(taskId);

		involves.setTask(task);
		super.bindObject(involves);

	}

	@Override
	public void validate(final Involves involves) {
		;
	}

	@Override
	public void perform(final Involves involves) {

		this.repository.save(involves);

	}

	@Override
	public void unbind(final Involves involves) {
		Collection<Task> tasks;
		Dataset dataset;
		SelectChoices typeChoices;
		int maintenanceRecordId;
		MaintenanceRecord maintenanceRecord;

		maintenanceRecordId = super.getRequest().getData("maintenanceRecordId", int.class);
		maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRecordId);

		tasks = this.repository.findValidTasksToLink(maintenanceRecord);
		typeChoices = SelectChoices.from(tasks, "description", involves.getTask());

		dataset = super.unbindObject(involves);
		dataset.put("maintenanceRecordId", involves.getMaintenanceRecord().getId());
		dataset.put("maintenanceRecord", involves.getMaintenanceRecord());
		dataset.put("task", typeChoices.getSelected().getKey());
		dataset.put("tasks", typeChoices);

		dataset.put("maintenanceRecordId", super.getRequest().getData("maintenanceRecordId", int.class));

		super.getResponse().addData(dataset);

	}
}
