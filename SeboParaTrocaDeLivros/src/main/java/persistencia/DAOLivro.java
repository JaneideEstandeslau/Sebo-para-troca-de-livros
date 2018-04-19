package persistencia;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Livro;

public class DAOLivro extends DAOGenerico{
	
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
			TypedQuery<Livro> query = (TypedQuery<Livro>) em
					.createQuery("SELECT l FROM Livro l WHERE l.isbn = :isbn");
			query.setParameter("isbn", isbn);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

}
