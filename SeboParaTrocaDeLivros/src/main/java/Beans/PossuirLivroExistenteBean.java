package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Cliente;
import model.Endereco;
import model.Livro;
import service.ClienteService;
import service.LivroService;

@SessionScoped
@ManagedBean
public class PossuirLivroExistenteBean extends AbstractBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4912670054671337785L;
	private LivroService service = new LivroService();
	private ClienteService clienteService = new ClienteService();
	private Livro livro;
	private String conservacao;

	public String init() {
		try {
			Livro l = service.recuperarLivroClientePossui(livro.getId());
			if(l.getUsuarioPossue() == null) {
				return "informarDescrocao.xhtml?faces-redirect=true";
			}
			else {
				reportarMensagemDeErro("Esse livro já possui dono");
				return null;
			}
		} catch (ServiceDacException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String possuirLivro() {
		try {
			livro.setConservacao(conservacao);
			service.modificarLivro(livro);
//			clienteService.adicionaLivroPossuintes(, idLivro);
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
		}
		reportarMensagemDeSucesso("Você possui o livro " + livro.getTitulo());
		return "pesquisarLivroLogado.xhtml?faces-redirect=true";
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
