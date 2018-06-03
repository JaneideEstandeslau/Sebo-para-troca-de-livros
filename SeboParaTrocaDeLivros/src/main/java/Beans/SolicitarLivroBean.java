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
	
	public void solicitar() {
		try {
			service.solicitarLivro((long) 1, livro.getId());
			reportarMensagemDeSucesso("Você solicitou o livro " + livro.getTitulo());
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
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
