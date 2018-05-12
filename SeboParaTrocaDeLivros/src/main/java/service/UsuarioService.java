package service;

import excecoes.RollbackException;
import model.Cliente;
import model.Usuario;
import persistencia.DAOUsuario;

public class UsuarioService {
	
	private DAOUsuario usuarioDAO = new DAOUsuario();
	
	
	/**
	 * Esse metodo adiciona um usuário do tipo administrado ao sistema.
	 * @param admin
	 */
	public void salvarAdmin(Usuario admin) {
		try {
			validarCPF(admin.getCpf());
			usuarioDAO.save(admin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Esse metodo remove um usuário administrador do sistema.
	 * @param idAdmin
	 */
	public void removerAdmin(Long idAdmin) {
		
		try {
			Usuario admin = (Usuario) usuarioDAO.getByID(new Usuario(), idAdmin);
			admin.setAtivo(false);
			usuarioDAO.update(admin);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void validarCPF(String cpf) throws RollbackException {

		Usuario u = usuarioDAO.validarCPF(cpf);

		if (u != null) {
			throw new RollbackException("Já existe um Administrador com esse CPF");
		}
	}

}
