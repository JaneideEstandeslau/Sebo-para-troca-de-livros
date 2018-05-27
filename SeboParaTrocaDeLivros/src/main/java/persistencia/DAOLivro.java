package persistencia;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import model.Livro;

public class DAOLivro extends DAOGenerico {

	/**
	 * Esse método faz com que um cliente solicite um livro caso ele ainda não tenha
	 * solicitado ou não o possua.
	 * 
	 * @param idCliente
	 * @throws Exception
	 */

	public void adicionarLivroListaDesejos(Livro livro) throws Exception {

		this.update(livro);

	}

	/**
	 * Esse metodo recupera um livro junto com as suas c=solicitações.
	 * 
	 * @param idLivro
	 * @return retrona um objeto do tipo livro
	 */
	public Livro recuperarLivroComSolicitacoes(Long idLivro) {
		EntityManager em = getEntityManager();
		Livro resultado = null;
		try {
			TypedQuery<Livro> query = (TypedQuery<Livro>) em
					.createQuery("SELECT l FROM Livro l LEFT JOIN FETCH l.solicitacoes s WHERE l.id = :idLivro");
			query.setParameter("idLivro", idLivro);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	public Livro recuperarLivroComClienteDesejam(Long idLivro) {
		EntityManager em = getEntityManager();
		Livro resultado = null;
		try {
			TypedQuery<Livro> query = (TypedQuery<Livro>) em
					.createQuery("SELECT l FROM Livro l LEFT JOIN FETCH l.clientesDesejam s WHERE l.id = :idLivro");
			query.setParameter("idLivro", idLivro);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	public Livro recuperarLivroComTrocas(Long idLivro) {
		EntityManager em = getEntityManager();
		Livro resultado = null;
		try {
			TypedQuery<Livro> query = (TypedQuery<Livro>) em
					.createQuery("SELECT l FROM Livro l LEFT JOIN FETCH l.trocas s WHERE l.id = :idLivro");
			query.setParameter("idLivro", idLivro);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	public Livro recuperarLivroComPossuinte(Long idLivro) {
		EntityManager em = getEntityManager();
		Livro resultado = null;
		try {
			TypedQuery<Livro> query = (TypedQuery<Livro>) em
					.createQuery("SELECT l FROM Livro l LEFT JOIN FETCH l.usuarioPossue s WHERE l.id = :idLivro");
			query.setParameter("idLivro", idLivro);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	public Livro validarIsbn(String isbn) {
		EntityManager em = getEntityManager();
		Livro resultado = null;
		try {
			TypedQuery<Livro> query = (TypedQuery<Livro>) em.createQuery("SELECT l FROM Livro l WHERE l.isbn = :isbn");
			query.setParameter("isbn", isbn);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	public List<Livro> getAllLivrosTitulo(String titulo) {

		EntityManager em = getEntityManager(); 
		List<Livro> resultado = new ArrayList<>();
		try {
			TypedQuery<Livro> query = (TypedQuery<Livro>) em.createQuery("SELECT l FROM Livro l WHERE l.titulo = :titulo");
			query.setParameter("titulo", titulo);
			resultado = query.getResultList();
			return resultado;
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
	}
	
	public List<Livro> searchItems(String searchTerms) {
		EntityManager em = getEntityManager();
		List<Livro> itens = null;
		
		try {
			TypedQuery<Livro> itemQuery = em.createQuery("SELECT l FROM Livro l WHERE 1 = 1 AND l.titulo LIKE :titulo",Livro.class);
			itemQuery.setParameter("titulo", "%" + searchTerms + "%");
			itens = itemQuery.getResultList();
			return itens;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
}
