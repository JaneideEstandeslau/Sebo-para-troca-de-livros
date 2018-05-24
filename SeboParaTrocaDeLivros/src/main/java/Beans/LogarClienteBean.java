package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

@ViewScoped
@ManagedBean
public class LogarClienteBean extends AbstractBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String login;
	private String senha;
	private boolean logar;
	private boolean editarSenha;
	
	
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
	
	public void cancelarRedeficaoSenha() {
		logar = true;
		editarSenha = false;
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
