
package acme.features.technician.task;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.entities.task.Task;
import acme.realms.Technician;

@GuiService
public class TechnicianTaskListByMaintenanceService extends AbstractGuiService<Technician, Task> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianTaskRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		Boolean status;
		int masterId;
		MaintenanceRecord maintenanceRecord;
		Technician technician;

		masterId = super.getRequest().getData("masterId", int.class);
		maintenanceRecord = this.repository.findMaintenanceRecordById(masterId);
		technician = maintenanceRecord == null ? null : maintenanceRecord.getTechnician();
		status = maintenanceRecord != null && super.getRequest().getPrincipal().hasRealm(technician);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		Collection<Task> tasks;
		int masterId;

		masterId = super.getRequest().getData("masterId", int.class);
		tasks = this.repository.findTasksByMaintenanceId(masterId);

		super.getBuffer().addData(tasks);
	}

	@Override
	public void unbind(final Task task) {
		Integer maintenanceRecordId;
		MaintenanceRecord maintenanceRecord;

		Dataset dataset;
		boolean showCreate = true;

		maintenanceRecordId = super.getRequest().hasData("maintenanceRecordId") ?//
			super.getRequest().getData("maintenanceRecordId", int.class) : null;

		dataset = super.unbindObject(task, "id", "type", "estimatedDuration", "description", "priority");
		if (task.isDraftMode())
			dataset.put("draftMode", "✔");
		else
			dataset.put("draftMode", "✖");

		// super.addPayload(dataset, task, "technician.licenseNumber");
		super.getResponse().addGlobal("showCreate", showCreate);
		super.getResponse().addData(dataset);
	}
}
