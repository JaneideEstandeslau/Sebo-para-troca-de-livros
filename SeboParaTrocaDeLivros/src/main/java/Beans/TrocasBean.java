package Beans;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import model.Avaliacao;
import model.Troca;
import service.AvaliacaoService;
import service.ClienteService;
import service.TrocaService;

@SessionScoped
@Named
public class TrocasBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private TrocaService trocaService;
	@Inject
	private ClienteService clienteService;
	@Inject
	private AvaliacaoService service;
	private Troca troca;
	private String codRastreio;
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
	
	public List<Troca> getTrocasRebidas(){
		try {
			return trocaService.getTrocasRebidas(getUsuarioLogado());
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public List<Troca> getTrocasEnviadas(){
		try {
			return trocaService.getTrocasEnviadas(getUsuarioLogado());
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String recebi() {
		try {
			troca.setRecebida(true);
			trocaService.upedate(troca);
			reportarMensagemDeSucesso("Confirmado recebimento do livro");
			return "avaliarTroca.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String informeCodRastreio() {
		try {
			troca.setCodRastreio(codRastreio);
			trocaService.upedate(troca);
			reportarMensagemDeSucesso("Codigo de Rastreio Adicionado");
			return "trocasEnviadas.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String calcelarTroca() {
		try {
			trocaService.cancelarTroca(troca, troca.getLivro(),troca.getClienteEnviando(), troca.getClienteRecebendo());
			clienteService.adicionaLivroPossuintes(troca.getClienteEnviando().getId(), troca.getLivro().getId());
			reportarMensagemDeSucesso("Troca Cancelada");
			return "trocasEnviadas.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public TrocaService getTrocaService() {
		return trocaService;
	}

	public void setTrocaService(TrocaService trocaService) {
		this.trocaService = trocaService;
	}

	public Troca getTroca() {
		return troca;
	}

	public void setTroca(Troca troca) {
		this.troca = troca;
	}

	public String getCodRastreio() {
		return codRastreio;
	}

	public void setCodRastreio(String codRastreio) {
		this.codRastreio = codRastreio;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
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
