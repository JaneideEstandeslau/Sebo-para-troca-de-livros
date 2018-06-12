package service;

import java.io.Serializable;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import excecoes.RollbackException;
import model.Usuario;

@ApplicationScoped
public class CadastrarAdmin implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3371124651147613246L;

	@Inject
	private UsuarioService clienteService;

	public void generateData() throws RollbackException {

		Usuario user = new Usuario();

		user.setCpf("705.448.694-35");
		user.setLogin("janeide_estandeslau");
		user.setNome("Janeide");
		user.setSobrenome("Estandeslau");
		user.setSenha("50jeipb08d");

		int existe = clienteService.validarLogin(user.getLogin());
		if (existe == 2) {
			clienteService.salvarAdmin(user);
		}

	}
}
