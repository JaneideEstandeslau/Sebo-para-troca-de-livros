package Beans;


import java.security.Principal;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import model.Usuario;

@ViewScoped
@Named
public class LogarClienteBean extends AbstractBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String senha;
	private boolean logar;
	private boolean editarSenha;
	
	
//	public Usuario getUsuarioLogado(){
//		dao = new UsuarioDAO();
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		ExternalContext externalContext = facesContext.getExternalContext();
//		Principal principal = externalContext.getUserPrincipal();
//		Usuario logado = null;
//		if (principal != null) {
//			try{
//				logado = dao.getByLogin(principal.getName());
//				return logado;
//			}catch(Exception e){
//				e.printStackTrace();
//			}
//		}
//		return logado;
//	}
	
	
	public void init() {
		logar = true;
		editarSenha = false;
	}
	
	public boolean logarUsuario(){
		return logar;
	}
	
	public boolean editarSenha() {
		return editarSenha;
	}
	public void redefinirSenha(){
		logar = false;
		editarSenha = true;
	}
	
	public String cancelarRedeficaoSenha() {
		return  "logarUsuario.xhtml?faces-redirect=true";
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public boolean isLogar() {
		return logar;
	}

	public void setLogar(boolean logar) {
		this.logar = logar;
	}

	public boolean isEditarSenha() {
		return editarSenha;
	}

	public void setEditarSenha(boolean editarSenha) {
		this.editarSenha = editarSenha;
	}
}
