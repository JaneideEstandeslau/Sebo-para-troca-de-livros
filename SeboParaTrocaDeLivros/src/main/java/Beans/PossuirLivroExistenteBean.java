package Beans;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Livro;
import service.ClienteService;
import service.LivroService;

@SessionScoped
@Named
public class PossuirLivroExistenteBean extends AbstractBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4912670054671337785L;
	@Inject
	private LivroService service;
	@Inject
	private ClienteService clienteService;
	private Livro livro;
	private String conservacao;

	public String init() {
		try {
			clienteService.verificarLivroPossuiDono(livro);
			Livro l = service.recuperarLivroClientePossui(livro.getId());
			if(l.getUsuarioPossue() == null) {
				return "informarDescrocao.xhtml?faces-redirect=true";
			}
			else {
				reportarMensagemDeErro("Esse livro já possui um dono");
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
	
	public String possuirLivro() {
		try {
			livro.setConservacao(conservacao);
			service.update(livro);
			clienteService.adicionaLivroPossuintes((long) 2, livro.getId());
			reportarMensagemDeSucesso("Você possui o livro " + livro.getTitulo());
			conservacao = null;
			return "pesquisarLivroLogado.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public LivroService getService() {
		return service;
	}

	public void setService(LivroService service) {
		this.service = service;
	}

	public Livro getLivro() {
		return livro;
	}

	public void setLivro(Livro livro) {
		this.livro = livro;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public String getConservacao() {
		return conservacao;
	}

	public void setConservacao(String conservacao) {
		this.conservacao = conservacao;
	}
}
