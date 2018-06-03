package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Cliente;

public class DAOUsuario extends DAOGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Cliente validarCPF(String cpf) {

		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf");
			query.setParameter("cpf", cpf);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} 
		return resultado;

	}

}
