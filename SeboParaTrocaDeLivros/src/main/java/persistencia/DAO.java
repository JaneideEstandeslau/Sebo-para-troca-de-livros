package persistencia;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DAO implements Serializable {

	static EntityManagerFactory emf;

	static {
		emf = Persistence.createEntityManagerFactory("trocalivros");
	}

	protected EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void close() {
		if (emf.isOpen()) {
			emf.close();
		}
	}

}
