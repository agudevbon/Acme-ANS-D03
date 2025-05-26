/*
 * TechnicianTaskDeleteService.java
 *
 * Copyright (C) 2012-2025 Rafael Corchuelo.
 *
 * In keeping with the traditional purpose of furthering education and research, it is
 * the policy of the copyright owner to permit non-commercial use and redistribution of
 * this software. It has been tested carefully, but it is not guaranteed for any particular
 * purposes. The copyright owner does not offer any warranties or representations, nor do
 * they accept any liabilities with respect to them.
 */

package acme.features.technician.task;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.maintenanceRecords.Involves;
import acme.entities.task.Task;
import acme.entities.task.TaskType;
import acme.realms.Technician;

@GuiService
public class TechnicianTaskDeleteService extends AbstractGuiService<Technician, Task> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianTaskRepository repository;

	// AbstractGuiService interface -------------------------------------------


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
				if (task != null) {
					technician = task.getTechnician();
					status = task.isDraftMode() && super.getRequest().getPrincipal().hasRealm(technician);
				}
			}
		}

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
		Technician technician = (Technician) super.getRequest().getPrincipal().getActiveRealm();
		super.bindObject(task, "type", "description", "priority", "estimatedDuration");
		task.setTechnician(technician);
	}

	@Override
	public void validate(final Task task) {
		/*
		 * boolean status;
		 * Collection<Involves> involves;
		 * 
		 * involves = this.repository.findInvolvesByTaskId(task.getId());
		 * 
		 * status = involves.isEmpty();
		 * super.state(status, "*", "acme.validation.task.maintenance-record-conectado.message", task);
		 */;
	}

	@Override
	public void perform(final Task task) {
		boolean status;
		Collection<Involves> involves;

		involves = this.repository.findInvolvesByTaskId(task.getId());

		status = involves.isEmpty();
		super.state(status, "*", "acme.validation.task.maintenance-record-linked.message", task);

	}
	@Override
	public void unbind(final Task task) {
		SelectChoices typeChoices;
		Dataset dataset;

		typeChoices = SelectChoices.from(TaskType.class, task.getType());

		dataset = super.unbindObject(task, "type", "description", "priority", "estimatedDuration", "draftMode");
		dataset.put("technician", task.getTechnician().getIdentity().getFullName());
		dataset.put("type", typeChoices.getSelected().getKey());
		dataset.put("types", typeChoices);

		super.getResponse().addData(dataset);
	}

}
