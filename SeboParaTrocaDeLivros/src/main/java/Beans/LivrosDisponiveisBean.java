package Beans;

import java.util.ArrayList;
import java.util.Collection;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import excecoes.RollbackException;
import model.Livro;
import service.ClienteService;

@ViewScoped
@ManagedBean
public class LivrosDisponiveisBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClienteService clienteService = new ClienteService();
	private Livro livro;
	
	public Collection<Livro> getLivros(){
		try {
			return clienteService.recuperarClienteComLivrosPossue((long) 1);
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String remover() {
		try {
			clienteService.removerLivroPossuintes((long) 1, livro.getId());
			reportarMensagemDeSucesso("Livro '" + livro.getTitulo() + "' removido");
			return "livrosDisponiveis.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public ClienteService getClienteService() {
		return clienteService;
	}
	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	
	
	

}
