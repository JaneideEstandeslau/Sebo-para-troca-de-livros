package persistencia;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Cliente;
import model.Usuario;

public class DAOUsuario extends DAOGenerico {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Usuario validarCPF(String cpf) {

		EntityManager em = getEntityManager();
		Usuario resultado = null;
		try {
			TypedQuery<Usuario> query = (TypedQuery<Usuario>) em
					.createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf");
			query.setParameter("cpf", cpf);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} 
		return resultado;

	}
	
	public List<Usuario> validarCPFUpdate(String cpf) {

		EntityManager em = getEntityManager();
		List<Usuario> resultado = null;
		try {
			TypedQuery<Usuario> query = (TypedQuery<Usuario>) em
					.createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf");
			query.setParameter("cpf", cpf);
			resultado = query.getResultList();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;

	}
	
	public Usuario validarLoginCadastro(String login) {

		EntityManager em = getEntityManager();
		Usuario resultado = null;
		try {
			TypedQuery<Usuario> query = (TypedQuery<Usuario>) em
					.createQuery("SELECT u FROM Usuario u WHERE u.login = :login");
			query.setParameter("login", login);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		}
		return resultado;
	}
	
	public Usuario recuperarAdmin(String cpf) {

		EntityManager em = getEntityManager();
		Usuario resultado = null;
		try {
			TypedQuery<Usuario> query = (TypedQuery<Usuario>) em
					.createQuery("SELECT u FROM Usuario u WHERE u.cpf = :cpf");
			query.setParameter("cpf", cpf);
			resultado = query.getSingleResult();
		} catch (PersistenceException pe) {
			return null;
		} 
		return resultado;

	}

}
