package Beans;


import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import model.Usuario;
import service.UsuarioService;

@SessionScoped
@Named
public class CadastrarAdminBean extends AbstractBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private UsuarioService service;
	private Usuario usuario;
	
	public void visualizarPerfil() {
		try {
			usuario = service.recuperarAdmin("70544869436");
		}catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}
	
	public void init() {
		if(usuario == null) {
			usuario = new Usuario();
		}
	}
	
	public String saveUsuario() {
		try {
			
			if(isEdicaoDeClinte()) {
				service.modificarUsuario(usuario);
			}
			else {
				service.salvarAdmin(usuario);
			}
		}catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Administrador '" + usuario.getNome() + "' salvo");

		return "paginaDoAdmin.xhtml?faces-redirect=true";
		
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

}
