
package acme.features.technician.maintenanceRecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.entities.maintenanceRecords.MaintenanceStatus;
import acme.entities.maintenanceRecords.MaintenanceTask;
import acme.realms.Technician;

@GuiService
public class TechnicianMaintenanceShowService extends AbstractGuiService<Technician, MaintenanceRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianMaintenanceRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int maintenanceRId;
		MaintenanceRecord maintenanceRecord;
		List<MaintenanceTask> maintenanceTask;
		Technician technician;

		maintenanceRId = super.getRequest().getData("id", int.class);
		maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRId);
		maintenanceTask = this.repository.findMaintenanceTaskByMaintenanceId(maintenanceRId);

		// || maintenanceRecord.getId() != maintenanceTask.getMaintanceRecord().getId()
		technician = maintenanceRecord == null ? null : maintenanceTask.get(0).getTask().getTechnician();
		status = maintenanceRecord != null && super.getRequest().getPrincipal().hasRealm(technician);

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int maintenanceRId;
		MaintenanceRecord maintenanceRecord;

		maintenanceRId = super.getRequest().getData("id", int.class);
		maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRId);

		super.getBuffer().addData(maintenanceRecord);
	}

	@Override
	public void unbind(final MaintenanceRecord maintenanceRecord) {
		Dataset dataset;

		SelectChoices aircraftChoices;
		List<Aircraft> aircrafts = this.repository.findAircrafts();
		aircraftChoices = SelectChoices.from(aircrafts, "registrationNumber", maintenanceRecord.getAircraft());

		SelectChoices statusChoices;
		statusChoices = SelectChoices.from(MaintenanceStatus.class, maintenanceRecord.getStatus());

		dataset = super.unbindObject(maintenanceRecord, "moment", "status", "inspectionDueDate", "estimatedCost", "notes", "draftMode");

		dataset.put("statuss", statusChoices);
		dataset.put("aircrafts", aircraftChoices);
		dataset.put("aircraft", aircraftChoices.getSelected().getKey());

		super.getResponse().addData(dataset);
	}

}
