
package acme.realms.assistanceAgent;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import acme.client.repositories.AbstractRepository;

public interface AssistanceAgentRepository extends AbstractRepository {

	@Query("SELECT a FROM AssistanceAgent a")
	List<AssistanceAgent> findAllAssistanceAgent();

}
