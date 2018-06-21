package Beans;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Usuario;
import service.UsuarioService;

@ViewScoped
@Named
public class ModSenhaAdmin extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String senhaAtual;
	private String novaSenha;
	private String confNovaSenha;
	private Usuario c;
	@Inject
	private UsuarioService usuarioService;
	
	public String modSenha() {
		try {
			c = (Usuario) usuarioService.getByID(getUsuarioLogado());
			if(senhaAtualConfere()) {
				if(novaSenha.equals(confNovaSenha)) {
					c.setSenha(novaSenha);
					usuarioService.modificarSenha(c);
					reportarMensagemDeSucesso("Senha atualizada com sucesso.");
					return "paginaDoUsuario.xhtml?faces-redirect=true";
				}
				else {
					reportarMensagemDeErro("Senhas informadas não confere.");
					return null;
				}
				
			}
			else {
				reportarMensagemDeErro("Senha informada, como atual, não confere.");
				return null;
			}
		} catch (ServiceDacException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	private boolean senhaAtualConfere() throws ServiceDacException {
		
		return usuarioService.senhaAtualConfere(c.getSenha(), senhaAtual);
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getConfNovaSenha() {
		return confNovaSenha;
	}

	public void setConfNovaSenha(String confNovaSenha) {
		this.confNovaSenha = confNovaSenha;
	}
}
