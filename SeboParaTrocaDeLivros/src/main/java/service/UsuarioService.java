package service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Cliente;
import model.Group;
import model.Usuario;
import persistencia.DAOUsuario;
import util.TransacionalCdi;

@ApplicationScoped
public class UsuarioService implements Serializable {

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
	@TransacionalCdi
	public void salvarAdmin(Usuario admin) throws RollbackException {
		try {
			validarCPF(admin.getCpf());
			validarLoginCadastro(admin.getLogin());
			admin.setAtivo(true);
			admin.setTipo(Group.ADMIN);
			calcularHashDaSenha(admin);
			usuarioDAO.save(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Esse metodo remove um usuário administrador do sistema.
	 * 
	 * @param idAdmin
	 * @throws RollbackException
	 */
	@TransacionalCdi
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
			throw new RollbackException("Já existe um administrador com esse CPF");
		}
	}

	public void validarCPFUpdate(String cpf) throws RollbackException {

		List<Usuario> u = usuarioDAO.validarCPFUpdate(cpf);

		if (u.size() > 1) {
			throw new RollbackException("Já existe um administrador com esse CPF");
		}
	}

	public void validarLoginUpdate(String login) throws RollbackException {

		List<Usuario> u = usuarioDAO.validarLoginUpdate(login);

		if (u.size() > 1) {
			throw new RollbackException("Já existe um administrador com esse login");
		}
	}

	@TransacionalCdi
	public void modificarUsuario(Usuario usuario) throws RollbackException {
		try {
			validarCPFUpdate(usuario.getCpf());
			validarLoginUpdate(usuario.getLogin());
			Usuario c = (Usuario) usuarioDAO.getByID(new Usuario(), usuario.getId());
			c.setLogin(usuario.getLogin());
			c.setNome(usuario.getNome());
			c.setSobrenome(usuario.getSobrenome());
			c.setCpf(usuario.getCpf());
			usuarioDAO.update(c);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	public Usuario recuperarAdmin(String cpf) throws RollbackException {
		try {
			return usuarioDAO.recuperarAdmin(cpf);
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

	private String hash(String password) throws ServiceDacException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			String output = Base64.getEncoder().encodeToString(digest);
			return output;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new ServiceDacException("Could not calculate hash!", e);
		}
	}

	public int validarLogin(String login) {
		Usuario c = usuarioDAO.validarLoginCadastro(login);

		if (c != null) {
			return 1;
		}
		return 2;
	}

	private String calcularHashDaSenha(Usuario user) throws ServiceDacException {
		user.setSenha(hash(user.getSenha()));
		return user.getSenha();
	}

	private void validarLoginCadastro(String login) throws RollbackException {
		Usuario c = usuarioDAO.validarLoginCadastro(login);

		if (c != null) {
			throw new RollbackException("Já existe um cliente com esse Login");
		}
	}

	@TransacionalCdi
	public void modificarSenha(Usuario cliente) throws RollbackException {
		try {
			calcularHashDaSenha(cliente);
			usuarioDAO.update(cliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	public boolean senhaAtualConfere(String passwordAtualHash, String confirmacaoPasswordAtual)
			throws ServiceDacException {

		if (passwordAtualHash == null && confirmacaoPasswordAtual == null) {
			return true;
		}

		if (passwordAtualHash == null || confirmacaoPasswordAtual == null) {
			return false;
		}

		String confirmacaoPasswordAtualHash = hash(confirmacaoPasswordAtual);
		return passwordAtualHash.equals(confirmacaoPasswordAtualHash);
	}
}
