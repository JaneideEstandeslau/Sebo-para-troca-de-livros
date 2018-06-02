package Beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import excecoes.RollbackException;
import model.Troca;
import service.TrocaService;

@ViewScoped
@ManagedBean
public class TrocasBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TrocaService trocaService = new TrocaService();
	private Troca troca;
	private String codRastreio;
	
	public List<Troca> getTrocasRebidas(){
		try {
			return trocaService.getTrocasRebidas((long) 2);
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public List<Troca> getTrocasEnviadas(){
		try {
			return trocaService.getTrocasEnviadas((long) 1);
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
			trocaService.cancelarTroca(troca.getId(), troca.getClienteEnviando().getId(), troca.getClienteRecebendo().getId());
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
}
