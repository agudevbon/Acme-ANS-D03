
package acme.forms;

import acme.client.components.basis.AbstractForm;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ManagerDashboard extends AbstractForm {

	// Serialisation version --------------------------------------------------

	private static final long	serialVersionUID	= 1L;

	// Attributes -------------------------------------------------------------

	String						ranking;
	int							retire;
	double						ratio;
	String						mostPopularAirport;
	String						lessPopularAirport;

	//Number of legs by Status
	int							totalOfOnTimeLegs;
	int							totalOfDelayedLegs;
	int							totalOfCancelledLegs;
	int							totalOfLandedLegs;

	//Flight Cost (USD)
	double						averageFlightCostUSD;

	double						deviationOfFlightCostUSD;

	double						minimumFlightCostUSD;

	double						maximumFlightCostUSD;

	//Flight Cost (EUR)
	double						averageFlightCostEUR;

	double						deviationOfFlightCostEUR;

	double						minimumFlightCostEUR;

	double						maximumFlightCostEUR;

	//Flight Cost (GBP)
	double						averageFlightCostGBP;

	double						deviationOfFlightCostGBP;

	double						minimumFlightCostGBP;

	double						maximumFlightCostGBP;
}
