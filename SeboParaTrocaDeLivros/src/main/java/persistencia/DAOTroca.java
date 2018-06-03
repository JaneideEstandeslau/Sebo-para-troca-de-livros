package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import model.Troca;

public class DAOTroca extends DAOGenerico{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

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
		}
		return resultado;
	}
	
	public Troca recTroca(Long idTroca) {
		EntityManager em = getEntityManager();
		Troca resultado = null;
		try {
			TypedQuery<Troca> itemQuery = em.createQuery(
					"SELECT t FROM Troca t LEFT JOIN FETCH t.clienteEnviando c1 "+""
	+ "LEFT JOIN FETCH t.clienteRecebendo c2 WHERE 1 = 1 AND t.recebida = false AND t.id = :idTroca",
					Troca.class);
			itemQuery.setParameter("idTroca", idTroca);
			resultado = itemQuery.getSingleResult();
			return resultado;
		} catch (PersistenceException pe) {
			return null;
		}
	}
	
	public Troca recuperarTroca(Long idTroca) {
		EntityManager em = getEntityManager();
		Troca resultado = null;
		try {
			TypedQuery<Troca> query = (TypedQuery<Troca>) em.createQuery(
					"SELECT t FROM Troca t LEFT JOIN FETCH t.livro ls WHERE t.id = :idTroca");
			query.setParameter("idTroca", idTroca);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}
	
	public List<Troca> recuperarTrocasRecebias(Long idCliente) {
		EntityManager em = getEntityManager();
		List<Troca> resultado = null;
		try {
			TypedQuery<Troca> itemQuery = em.createQuery(
					"SELECT t FROM Troca t LEFT JOIN FETCH t.clienteEnviando c1 "+""
	+ "LEFT JOIN FETCH t.clienteRecebendo c2 WHERE 1 = 1 AND t.recebida = false AND c2.id = :idCliente",
					Troca.class);
			itemQuery.setParameter("idCliente", idCliente);
			resultado = itemQuery.getResultList();
			return resultado;
		} catch (PersistenceException pe) {
			return null;
		}
	}
	
	public List<Troca> recuperarTrocasEnviadas(Long idCliente) {
		EntityManager em = getEntityManager();
		List<Troca> resultado = null;
		try {
			TypedQuery<Troca> itemQuery = em.createQuery(
					"SELECT t FROM Troca t LEFT JOIN FETCH t.clienteEnviando c1 "+""
	+ "LEFT JOIN FETCH t.clienteRecebendo c2 WHERE 1 = 1 AND t.recebida = false AND c1.id = :idCliente",
					Troca.class);
			itemQuery.setParameter("idCliente", idCliente);
			resultado = itemQuery.getResultList();
			return resultado;
		} catch (PersistenceException pe) {
			return null;
		}
	}

}
