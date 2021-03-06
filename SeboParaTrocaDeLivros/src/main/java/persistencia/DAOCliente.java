package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import model.Cliente;
import model.Usuario;

public class DAOCliente extends DAOGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void updateCliente(Cliente c) throws Exception {
		this.update(c);
	}

	/**
	 * Recupera um cliente com seus respectivos problemas na troca
	 * 
	 * @param idCliente
	 * @return
	 */
	public Cliente recuprarClienteComProblemaTroca(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c LEFT JOIN FETCH c.problematroca ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}

	/**
	 * Esse método recupera um cliente do sistema
	 * 
	 * @param idCliente
	 */
	public Cliente recuperarCliente(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}
	
	public Cliente recuperarClienteLogin(String login) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c WHERE c.login = :login");
			query.setParameter("login", login);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}
	
	public Usuario recuperarUsuarioLogin(String login) {
		EntityManager em = getEntityManager();
		Usuario resultado = null;
		try {
			TypedQuery<Usuario> query = (TypedQuery<Usuario>) em
					.createQuery("SELECT c FROM Usuario c WHERE c.login = :login");
			query.setParameter("login", login);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}
	
	public List<Cliente> getClientes() {
		EntityManager em = getEntityManager();
		List<Cliente> resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c");
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			return null;
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
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c LEFT JOIN FETCH c.solicitacoes ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}

	public Cliente recuperarClienteComSolicitacoesRecebidas(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em.createQuery(
					"SELECT c FROM Cliente c LEFT JOIN FETCH c.solicitacoesRecebidas ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
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
		}
		return resultado;
	}

	/**
	 * Esse método recupera um cliente junto com os livros que o mesmo deseja.
	 * 
	 * @param idCliente
	 * @return
	 */
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
		}
		return resultado;
	}

	/**
	 * Esse método recupera o cliente com as trocas que o mesmo envio.
	 * 
	 * @param idCliente
	 * @return
	 */
	public Cliente recuperarClienteComTrocasEnviadas(Long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c LEFT JOIN FETCH c.trocasEnviadas ls WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}

	/**
	 * Esse método recupera o cliente com as trocas que ele recebeu.
	 * 
	 * @param idCliente
	 * @return
	 */
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
		}
		return resultado;
	}

	/**
	 * Esse metodo faz um busca no banco para saber se não existe um usuario com o
	 * login que querem cadastrar.
	 * 
	 * @param login
	 * @return
	 */
	public List<Cliente> validarCPF(String cpf) {

		EntityManager em = getEntityManager();
		List<Cliente> resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf");
			query.setParameter("cpf", cpf);
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;

	}
	
	public Cliente validarCPFCadastro(String cpf) {

		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c WHERE c.cpf = :cpf");
			query.setParameter("cpf", cpf);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;

	}

	/**
	 * Esse método verifica se o login que o usuario deseja cadastrar é valido, ou
	 * seja, se não existe um usuário com esse login.
	 * 
	 * @param login
	 * @return
	 */
	public List<Cliente> validarLogin(String login) {

		EntityManager em = getEntityManager();
		List<Cliente> resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT u FROM Usuario u WHERE u.login = :login");
			query.setParameter("login", login);
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;

	}
	
	public Cliente validarLoginCadastro(String login) {

		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT u FROM Usuario u WHERE u.login = :login");
			query.setParameter("login", login);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;

	}

	/**
	 * Esse método recupear um cliente pelo CPF junto com os seu endereço.
	 * 
	 * @param cpf
	 * @return
	 */
	public Cliente recuperarClienteComEndereco(String cpf) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c LEFT JOIN FETCH c.endereco ls WHERE c.cpf = :cpf");
			query.setParameter("cpf", cpf);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}

	public Cliente recuperarClienteParaLogar(String login, String senha) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
					.createQuery("SELECT c FROM Cliente c WHERE c.login = :login AND c.senha = :senha");
			query.setParameter("login", login);
			query.setParameter("senha", senha);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}
	
	public Cliente getAvaliacoes(long idCliente) {
		EntityManager em = getEntityManager();
		Cliente resultado = null;
		try {
			TypedQuery<Cliente> query = (TypedQuery<Cliente>) em
			.createQuery("SELECT c FROM Cliente c LEFT JOIN FETCH c.avaliacoes WHERE c.id = :idCliente");
			query.setParameter("idCliente", idCliente);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}
}
