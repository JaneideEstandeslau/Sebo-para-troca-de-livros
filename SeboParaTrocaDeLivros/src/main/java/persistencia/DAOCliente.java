package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Cliente;
import model.Livro;

public class DAOCliente extends DAOGenerico {

	
	public void updateCliente(Cliente c) throws Exception {
		this.update(c);
	}
	
	/**
	 * Recupera um cliente com seus respectivos problemas na troca
	 * @param idCliente
	 * @return
	 */
	public Cliente recuprarClienteComProblemaTroca(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c LEFT JOIN FETCH c.problematroca ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}
	
	/**
	 * Esse método recupera um cliente do sistema
	 * @param idCliente
	 */
	public Cliente recuperarCliente(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	/**
	 * Esse método recupera um cliente do banco junto com as solicitações que o
	 * mesmo já fez.
	 * 
	 * @param idCliente
	 * @return retorna um objeto do tipo cliente.
	 */
	public Cliente recuperarClienteComSolicitacoes(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c LEFT JOIN FETCH c.solicitacoes ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	/**
	 * Esse método recupera um cliente do banco junto com os livros que o mesmo
	 * possue.
	 * 
	 * @param idCliente
	 * @return um objeto do tipo cliente
	 */
	public Cliente recuperarClienteComLivrosPossue(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c LEFT JOIN FETCH c.livrosPossuem ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	
	public Cliente recuperarClienteComLivrosDesejados(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c LEFT JOIN FETCH c.livrosDesejados ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}
	
	public Cliente recuperarClienteComTrocasEnviadas(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c LEFT JOIN FETCH c.trocasEnviadas ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}
	
	public Cliente recuperarClienteComTrocasRecebidas(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c LEFT JOIN FETCH c.trocasRecebidas ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

	/**
	 * Esse metodo faz um busca no banco para saber se não existe um usuario com o login que
	 * querem cadastrar.
	 * @param login
	 * @return
	 */
	public Cliente validarLogin(String login) {
		
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c WHERE c.login = :login");
			query.setParameter("login", login);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
		
	}

	public Cliente recuperarClienteComEndereco(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c LEFT JOIN FETCH c.endereco ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} finally {
			em.close();
		}
		return resultado;
	}

}
