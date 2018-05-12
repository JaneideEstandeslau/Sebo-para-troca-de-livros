package service;

import excecoes.RollbackException;
import model.Cliente;
import model.Usuario;
import persistencia.DAOUsuario;

public class UsuarioService {
	
	private DAOUsuario usuarioDAO = new DAOUsuario();
	
	
	public void salvarAdmin(Usuario admin) {
		try {
			validarCPF(admin.getCpf());
			usuarioDAO.save(admin);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void validarCPF(String cpf) throws RollbackException {

		Usuario u = usuarioDAO.validarCPF(cpf);

		if (u != null) {
			throw new RollbackException("Já existe um cliente com esse CPF");
		}
	}

}
