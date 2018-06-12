package Beans;


import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import model.Livro;
import service.SolicitacaoService;

@SessionScoped
@Named
public class SolicitarLivroBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private SolicitacaoService service;
	private Livro livro;
	
	public String solicitar() {
		try {
			service.solicitarLivro(getUsuarioLogado(), livro.getId());
			reportarMensagemDeSucesso("Você solicitou o livro " + livro.getTitulo());
			return "pesquisarLivroLogado.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	
	public SolicitacaoService getService() {
		return service;
	}
	public void setService(SolicitacaoService service) {
		this.service = service;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
}
