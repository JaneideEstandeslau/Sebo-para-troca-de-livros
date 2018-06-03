package service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Usuario;
import persistencia.DAOUsuario;

@ApplicationScoped
public class UsuarioService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DAOUsuario usuarioDAO;

	/**
	 * Esse metodo adiciona um usuário do tipo administrado ao sistema.
	 * 
	 * @param admin
	 * @throws RollbackException
	 */
	public void salvarAdmin(Usuario admin) throws RollbackException {
		try {
			validarCPF(admin.getCpf());
			usuarioDAO.save(admin);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	/**
	 * Esse metodo remove um usuário administrador do sistema.
	 * 
	 * @param idAdmin
	 * @throws RollbackException
	 */
	public void removerAdmin(Long idAdmin) throws RollbackException {

		try {
			Usuario admin = (Usuario) usuarioDAO.getByID(new Usuario(), idAdmin);
			admin.setAtivo(false);
			usuarioDAO.update(admin);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	public void validarCPF(String cpf) throws RollbackException {

		Usuario u = usuarioDAO.validarCPF(cpf);

		if (u != null) {
			throw new RollbackException("Já existe um Administrador com esse CPF");
		}
	}

	public void modificarUsuario(Usuario usuario) throws RollbackException {
		try {
			validarCPF(usuario.getCpf());
			Usuario c = (Usuario) usuarioDAO.getByID(new Usuario(), usuario.getId());
			c.setLogin(usuario.getLogin());
			c.setNome(usuario.getNome());
			usuarioDAO.update(c);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	public Usuario recuperarAdmin(String cpf) throws RollbackException {
		try {
			return usuarioDAO.validarCPF(cpf);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}
	
	public Object getByID(Long id) throws ServiceDacException {
		try {
			return (Usuario) usuarioDAO.getByID(new Usuario(), id);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}

}
