package util;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtil {

	@Produces //define como instanciar um objeto
	@ApplicationScoped //o objeto criado vai existir durante toda a aplicação
	public EntityManagerFactory criarEMF() {
		EntityManagerFactory emf = null;
		try {
			emf = Persistence.createEntityManagerFactory("trocalivros");
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
		return emf;
	}

	@Produces
	@RequestScoped //ao terminar o uso do objeto o mesmo é descartado
	public EntityManager criarEM(EntityManagerFactory factory) {
		return factory.createEntityManager();
	}

	public void fecharEM(@Disposes EntityManager em) {
		em.close();
	}

	public void fecharEMF(@Disposes EntityManagerFactory emf) {
		emf.close();
	}

}
