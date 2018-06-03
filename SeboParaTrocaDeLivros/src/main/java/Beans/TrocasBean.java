package Beans;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import model.Troca;
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
	private Troca troca;
	private String codRastreio;
	
	public List<Troca> getTrocasRebidas(){
		try {
			return trocaService.getTrocasRebidas((long) 1);
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public List<Troca> getTrocasEnviadas(){
		try {
			return trocaService.getTrocasEnviadas((long) 2);
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String recebi() {
		try {
			troca.setRecebida(true);
			trocaService.upedate(troca);
			return "trocasRecebidas.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String informeCodRastreio() {
		try {
			troca.setCodRastreio(codRastreio);
			trocaService.upedate(troca);
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
}
