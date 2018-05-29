package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Livro;
import service.LivroService;

@ViewScoped
@ManagedBean
public class BeanCadastrarLivro extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2207143158846475463L;
	private LivroService service = new LivroService();
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
			}
		} catch (Exception e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Livro '" + livro.getTitulo() + "' salvo");

		return "index.xhtml?faces-redirect=true";
	}
	
	public boolean isEdicaoDeLivro() {
		return livro.getId() != null;
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

}
