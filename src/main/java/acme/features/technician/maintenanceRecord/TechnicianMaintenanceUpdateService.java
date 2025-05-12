
package acme.features.technician.maintenanceRecord;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.components.views.SelectChoices;
import acme.client.helpers.MomentHelper;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.aircraft.Aircraft;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.entities.maintenanceRecords.MaintenanceStatus;
import acme.realms.Technician;

@GuiService
public class TechnicianMaintenanceUpdateService extends AbstractGuiService<Technician, MaintenanceRecord> {

	// Internal state ---------------------------------------------------------

	@Autowired
	private TechnicianMaintenanceRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		boolean status;
		int maintenanceRecordId;
		MaintenanceRecord maintenanceRecord;
		Technician technician;

		maintenanceRecordId = super.getRequest().getData("id", int.class);
		maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRecordId);
		technician = maintenanceRecord == null ? null : maintenanceRecord.getTechnician();
		status = maintenanceRecord != null && super.getRequest().getPrincipal().hasRealm(technician) && maintenanceRecord.getDraftMode();

		super.getResponse().setAuthorised(status);
	}

	@Override
	public void load() {
		int maintenanceRecordId;
		MaintenanceRecord maintenanceRecord;

		maintenanceRecordId = super.getRequest().getData("id", int.class);
		maintenanceRecord = this.repository.findMaintenanceRecordById(maintenanceRecordId);

		super.getBuffer().addData(maintenanceRecord);
	}

	@Override
	public void bind(final MaintenanceRecord maintenanceRecord) {

		Date moment = MomentHelper.getCurrentMoment();

		int aircraftId = super.getRequest().getData("aircraft", int.class);
		Aircraft aircraft = this.repository.findAircraftById(aircraftId);

		super.bindObject(maintenanceRecord, "status", "inspectionDueDate", "estimatedCost", "notes");

		maintenanceRecord.setAircraft(aircraft);
		maintenanceRecord.setMoment(moment);
	}

	@Override
	public void validate(final MaintenanceRecord maintenanceRecord) {
		;
	}

	@Override
	public void perform(final MaintenanceRecord maintenanceRecord) {
		this.repository.save(maintenanceRecord);
	}

	@Override
	public void unbind(final MaintenanceRecord maintenanceRecord) {
		Dataset dataset;

		SelectChoices aircraftChoices;
		List<Aircraft> aircrafts = this.repository.findAircrafts();
		aircraftChoices = SelectChoices.from(aircrafts, "registrationNumber", maintenanceRecord.getAircraft());

		SelectChoices statusChoices;
		statusChoices = SelectChoices.from(MaintenanceStatus.class, maintenanceRecord.getStatus());

		dataset = super.unbindObject(maintenanceRecord, "status", "inspectionDueDate", "estimatedCost", "notes", "draftMode");

		dataset.put("statuss", statusChoices);
		dataset.put("aircrafts", aircraftChoices);
		dataset.put("aircraft", aircraftChoices.getSelected().getKey());

		super.getResponse().addData(dataset);

	}

}
