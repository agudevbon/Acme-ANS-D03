
package acme.forms;

import java.util.List;

import acme.client.components.basis.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDashboard extends AbstractForm {

	private static final long	serialVersionUID	= 1L;

	// Last 5 destination
	private List<String>		lastFiveDestinations;

	// Legs number that have an activity log record with an incident severity ranging 
	private int					lowSeverityLogs;     // 0–3
	private int					mediumSeverityLogs;  // 4–7
	private int					highSeverityLogs;    // 8–10

	// Last leg Crew
	private List<String>		lastLegCompanions; // employeeCodes

	// Flight assignments grouped by their statuses
	private int					pendingAssignments;
	private int					confirmedAssignments;
	private int					canceledAssignments;

	// Last month flight assignments statistics
	private Double				averageAssignmentsLastMonth;
	private Integer				minAssignmentsLastMonth;
	private Integer				maxAssignmentsLastMonth;
	private Double				deviationAssignmentsLastMonth;
}
