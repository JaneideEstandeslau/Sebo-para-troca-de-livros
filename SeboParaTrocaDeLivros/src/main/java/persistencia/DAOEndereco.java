package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Endereco;

public class DAOEndereco extends DAOGenerico{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Endereco recuperarEndereco(Long idEndereco) {
		EntityManager em = getEntityManager();
		Endereco resultado = null;
		try {
			TypedQuery<Endereco> query = (TypedQuery<Endereco>) em
					.createQuery("SELECT e FROM Endereco e WHERE e.id = :idEndereco");
			query.setParameter("idEndereco", idEndereco);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}

}
