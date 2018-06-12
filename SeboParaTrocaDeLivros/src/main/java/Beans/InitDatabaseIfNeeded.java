package Beans;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.omnifaces.cdi.Eager;
import excecoes.DacRuntimeException;
import excecoes.RollbackException;
import service.CadastrarAdmin;

@Named
@Eager // Thanks, Omnifaces!!!
@ApplicationScoped
public class InitDatabaseIfNeeded {

	@Inject
	private CadastrarAdmin cadastrar;

	@PostConstruct
	public void postConstruct() {
		try {
			cadastrar.generateData();
		} catch(RollbackException e) {
			throw new DacRuntimeException("Ocorreu algum erro ao tentar inicializar a base de dados.", e);
		}
	}

}
