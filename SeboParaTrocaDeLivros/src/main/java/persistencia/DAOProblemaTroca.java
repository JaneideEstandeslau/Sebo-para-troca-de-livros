package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.ProblemaTroca;
import model.Solicitacao;
import model.StatusProblema;

public class DAOProblemaTroca extends DAOGenerico{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public List<ProblemaTroca> recuperarProbTroca(StatusProblema pendente) {
		EntityManager em = getEntityManager();
		List<ProblemaTroca> resultado = null;
		try {
			TypedQuery<ProblemaTroca> query = (TypedQuery<ProblemaTroca>) em.createQuery(
					"SELECT p FROM ProblemaTroca p LEFT JOIN FETCH p.cliente c WHERE p.resolvido = :idProb");
			query.setParameter("idProb", pendente);
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}

}
