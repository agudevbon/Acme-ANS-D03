
package acme.features.technician.maintenanceRecord;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;

import acme.client.components.models.Dataset;
import acme.client.services.AbstractGuiService;
import acme.client.services.GuiService;
import acme.entities.maintenanceRecords.MaintenanceRecord;
import acme.realms.Technician;

@GuiService
public class TechnicianMaintenanceListService extends AbstractGuiService<Technician, MaintenanceRecord> {

	// Internal state ---------------------------------------------------------
	@Autowired
	private TechnicianMaintanceRepository repository;

	// AbstractGuiService interface -------------------------------------------


	@Override
	public void authorise() {
		super.getResponse().setAuthorised(true);
	}

	@Override
	public void load() {
		Collection<MaintenanceRecord> maintenanceRecords;
		int technicianId;

		technicianId = super.getRequest().getPrincipal().getActiveRealm().getId();
		maintenanceRecords = this.repository.findMaintenanceRecordByTechnicianId(technicianId);

		super.getBuffer().addData(maintenanceRecords);
	}

	@Override
	public void unbind(final MaintenanceRecord maintenanceRecord) {
		Dataset dataset;

		dataset = super.unbindObject(maintenanceRecord, "moment", "status", "inspectionDueDate", "estimatedCost", "notes", "draftMode");
		if (maintenanceRecord.getDraftMode())
			dataset.put("draftMode", "✔");
		else
			dataset.put("draftMode", "✖");
		super.getResponse().addData(dataset);
	}
}
