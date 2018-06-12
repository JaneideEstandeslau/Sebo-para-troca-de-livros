package Beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import model.Usuario;
import service.UsuarioService;

@SessionScoped
@Named
public class CadastrarAdminBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioService service;
	private Usuario usuario;
	private String senha;

	public void visualizarPerfil() {
		try {
			usuario = service.recuperarAdmin("705.448.694-35");
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	public void init() {

		usuario = new Usuario();

	}

	public String saveUsuario() {
		try {

			if (isEdicaoDeClinte()) {
				service.modificarUsuario(usuario);
				reportarMensagemDeSucesso("Administrador '" + usuario.getNome() + "' salvo");

				return "paginaDoUsuario.xhtml?faces-redirect=true";
			} else {

				if (usuario.getSenha().equals(senha)) {
					service.salvarAdmin(usuario);
					reportarMensagemDeSucesso("Administrador '" + usuario.getNome() + "' salvo");
					return "paginaDoUsuario.xhtml?faces-redirect=true";
				} else {
					reportarMensagemDeErro("Senhas não conferem");
					return null;
				}
			}
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}

	}

	public boolean isEdicaoDeClinte() {
		return usuario.getId() != null;
	}

	public UsuarioService getService() {
		return service;
	}

	public void setService(UsuarioService service) {
		this.service = service;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
