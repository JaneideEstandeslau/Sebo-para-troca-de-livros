package Beans;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import model.Avaliacao;
import service.AvaliacaoService;

@ViewScoped
@Named
public class AvaliarTrocaBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private AvaliacaoService service;
	private Avaliacao avali;
	private long idCliente;

	public void init() {
		avali = new Avaliacao();
	}

	public String salvarAvaliacao() {
		try {
			service.salverAvaliacao(avali, idCliente);
			reportarMensagemDeSucesso("Avaliação salva com sucesso!");
			return "trocasRecebidas.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}

	public Avaliacao getAvali() {
		return avali;
	}

	public void setAvali(Avaliacao avali) {
		this.avali = avali;
	}

	public long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(long idCliente) {
		this.idCliente = idCliente;
	}

}
