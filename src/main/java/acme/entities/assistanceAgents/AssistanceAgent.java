
package acme.entities.assistanceAgents;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;

import acme.client.components.basis.AbstractEntity;
import acme.client.components.datatypes.Money;
import acme.client.components.mappings.Automapped;
import acme.client.components.validation.Mandatory;
import acme.client.components.validation.Optional;
import acme.client.components.validation.ValidMoment;
import acme.client.components.validation.ValidMoney;
import acme.client.components.validation.ValidString;
import acme.client.components.validation.ValidUrl;

public class AssistanceAgent extends AbstractEntity {

	// Serialisation version 

	private static final long	serialVersionUID	= 1L;

	// Attributes

	@Mandatory
	//	@Pattern(regexp = "^[A-Z]{2-3}\d{6}$")
	@Column(unique = true)
	private String				code;

	@Mandatory
	@Valid
	@Automapped
	private List<String>		languages;

	@Mandatory
	@Valid
	@ManyToOne
	private Airline				airline;

	@Mandatory
	@ValidMoment(past = true)
	@Temporal(TemporalType.TIMESTAMP)
	private Date				moment;

	@Optional
	@ValidString(max = 255)
	@Automapped
	private String				bio;

	@Optional
	@ValidMoney
	@Automapped
	private Money				salary;

	@Optional
	@ValidUrl
	@Automapped
	private String				photoLink;

}
