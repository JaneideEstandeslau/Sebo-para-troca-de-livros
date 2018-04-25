package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Cliente;
import model.Troca;

public class DAOTroca extends DAOGenerico{
	
	/**
	 * Esse método recupera uma determinada troca do sistema com seus repectivos problemas.
	 * @param idTroca
	 * @return
	 */
	public Troca recuperarTrocaComProblemas(Long idTroca) {
		EntityManager em = getEntityManager();
		Troca resultado = null;
		try {
			TypedQuery<Troca> query = (TypedQuery<Troca>) em.createQuery(
					"SELECT t FROM Troca t LEFT JOIN FETCH t.problema ls WHERE t.id = :idTroca");
			query.setParameter("idTroca", idTroca);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}
	
	

}
