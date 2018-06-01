package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Cliente;
import model.Livro;
import model.Solicitacao;

public class DAOSolicitacao extends DAOGenerico {

	public Solicitacao recuperarSolicitacaoComCliente(Long idSolicitacao) {
		EntityManager em = getEntityManager();
		Solicitacao resultado = null;
		try {
			TypedQuery<Solicitacao> query = (TypedQuery<Solicitacao>) em.createQuery(
					"SELECT s FROM Solicitacao s LEFT JOIN FETCH s.clienteSolicitou c WHERE s.id = :idSolicitacao");
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
			TypedQuery<Solicitacao> query = (TypedQuery<Solicitacao>) em.createQuery(
					"SELECT s FROM Solicitacao s LEFT JOIN FETCH s.livroSolicitado c WHERE s.id = :idSolicitacao");
			query.setParameter("idSolicitacao", idSolicitacao);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	public List<Solicitacao> soliRecebidas(Long idCliente) {
		EntityManager em = getEntityManager();
		List<Solicitacao> itens = null;

		try {
			TypedQuery<Solicitacao> itemQuery = em.createQuery(
					"SELECT s FROM Solicitacao s LEFT JOIN FETCH s.clienteSolicitou c1 LEFT JOIN FETCH s.clienteRecebeuSolicitacao c2 WHERE 1 = 1 AND s.aceita = false AND s.ativa = true AND c2.id = :idCliente",
					Solicitacao.class);
			itemQuery.setParameter("idCliente", idCliente);
			itens = itemQuery.getResultList();
			return itens;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
