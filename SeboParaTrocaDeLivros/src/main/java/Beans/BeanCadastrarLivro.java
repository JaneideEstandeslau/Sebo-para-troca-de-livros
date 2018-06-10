package Beans;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import model.Livro;
import service.ClienteService;
import service.LivroService;

@ViewScoped
@Named
public class BeanCadastrarLivro extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2207143158846475463L;
	@Inject
	private LivroService service;
	@Inject
	private ClienteService clienteService;
	private Livro livro;

	public void init() {
		if (livro == null) {
			livro = new Livro();
		}
	}
	
	public String visualizar() {
		this.init();
		return  "visualizarLivro.xhtml?faces-redirect=true";
	}

	public String saveLivro() {

		try {
			if (isEdicaoDeLivro()) {
				service.modificarLivro(livro);
			} else {

				service.salvarLivro(livro);
				clienteService.adicionaLivroPossuintes(getUsuarioLogado(), livro.getId());
			}
		} catch (Exception e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Livro '" + livro.getTitulo() + "' salvo");

		return "paginaDoUsuario.xhtml?faces-redirect=true";
	}
	
	public boolean isEdicaoDeLivro() {
		return livro.getId() != null;
	}
	
	public boolean naoEdicaoDeLivro() {
		return livro.getId() == null;
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

}
