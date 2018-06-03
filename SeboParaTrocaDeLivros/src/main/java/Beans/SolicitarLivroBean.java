package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import excecoes.RollbackException;
import model.Livro;
import service.SolicitacaoService;

@SessionScoped
@ManagedBean
public class SolicitarLivroBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SolicitacaoService service = new SolicitacaoService();
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
