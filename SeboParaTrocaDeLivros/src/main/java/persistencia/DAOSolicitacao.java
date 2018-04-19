package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Livro;
import model.Solicitacao;

public class DAOSolicitacao extends DAOGenerico{
	
	
	public Solicitacao recuperarSolicitacaoComCliente(Long idSolicitacao) {
		EntityManager em = getEntityManager();
		Solicitacao resultado = null;
		try {
			TypedQuery<Solicitacao> query = (TypedQuery<Solicitacao>) em
					.createQuery("SELECT s FROM Solicitacao s LEFT JOIN FETCH s.clienteSolicitou c WHERE s.id = :idSolicitacao");
			query.setParameter("idSolicitacao", idSolicitacao);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}
	
	public Solicitacao recuperarSolicitacaoComLivro(Long idSolicitacao) {
		EntityManager em = getEntityManager();
		Solicitacao resultado = null;
		try {
			TypedQuery<Solicitacao> query = (TypedQuery<Solicitacao>) em
					.createQuery("SELECT s FROM Solicitacao s LEFT JOIN FETCH s.livroSolicitado c WHERE s.id = :idSolicitacao");
			query.setParameter("idSolicitacao", idSolicitacao);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}


}
