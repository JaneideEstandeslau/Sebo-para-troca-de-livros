package Beans;

import java.io.Serializable;
import java.security.Principal;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.inject.Inject;

import model.Cliente;
import model.Group;
import model.Usuario;
import persistencia.DAOCliente;


public class AbstractBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7887186144461468149L;
	@Inject
	private DAOCliente clienteDAO;
	
	protected void reportarMensagemDeErro(String detalhe) {
		reportarMensagem(true, detalhe);

	}

	protected void reportarMensagemDeSucesso(String detalhe) {
		reportarMensagem(false, detalhe);
	}

	private void reportarMensagem(boolean isErro, String detalhe) {
		String tipo = "Success!";
		Severity severity = FacesMessage.SEVERITY_INFO;
		if (isErro) {
			tipo = "Error!";
			severity = FacesMessage.SEVERITY_ERROR;
			FacesContext.getCurrentInstance().validationFailed();
		}

		FacesMessage msg = new FacesMessage(severity, tipo, detalhe);

		Flash flash = FacesContext.getCurrentInstance().getExternalContext().getFlash();
		flash.setKeepMessages(true);
		flash.setRedirect(true);
		FacesContext.getCurrentInstance().addMessage(null, msg);

	}
	
	public Group[] getTipos() {
		return Group.values();
	}
	
	public boolean isUserInRole(String role) {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		return externalContext.isUserInRole(role);
	}
	
	public String getUserLogin() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		Principal userPrincipal = externalContext.getUserPrincipal();
		if (userPrincipal == null) {
			return "";
		}
		
		return userPrincipal.getName();
	}
	
	public Long getUsuarioLogado() {
		
		String login = getUserLogin();
		Usuario users = null;
		try {
			users = clienteDAO.recuperarUsuarioLogin(login);
		} catch (Exception e) {
			e.printStackTrace();
			reportarMensagemDeErro("Erro ao recuperar o usuário logado!");
		}

		if (users != null) {
			return users.getId();
		}
		return null;
	}
	
	public Group tipoUser() {
		String login = getUserLogin();
		Usuario users = null;
		try {
			users = clienteDAO.recuperarUsuarioLogin(login);
		} catch (Exception e) {
			e.printStackTrace();
			reportarMensagemDeErro("Erro ao recuperar o usuário logado!");
		}

		if (users != null) {
			return users.getTipo();
		}
		return null;
	}
	
	public Usuario getUsuario() {
		
		String login = getUserLogin();
		Usuario users = null;
		try {
			users = clienteDAO.recuperarUsuarioLogin(login);
		} catch (Exception e) {
			e.printStackTrace();
			reportarMensagemDeErro("Erro ao recuperar o usuário logado!");
		}

		if (users != null) {
			return users;
		}
		return null;
	}
	
	public String getCpfLogado() {
		
		String login = getUserLogin();
		Usuario users = null;
		try {
			users = clienteDAO.recuperarUsuarioLogin(login);
		} catch (Exception e) {
			e.printStackTrace();
			reportarMensagemDeErro("Erro ao recuperar o usuário logado!");
		}

		if (users != null) {
			return users.getCpf();
		}
		return null;
	}
	
	public Cliente getClientee() {
		
		String login = getUserLogin();
		Cliente users = null;
		try {
			users = clienteDAO.recuperarClienteLogin(login);
		} catch (Exception e) {
			e.printStackTrace();
			reportarMensagemDeErro("Erro ao recuperar o usuário logado!");
		}

		if (users != null) {
			return users;
		}
		return null;
	}
}
