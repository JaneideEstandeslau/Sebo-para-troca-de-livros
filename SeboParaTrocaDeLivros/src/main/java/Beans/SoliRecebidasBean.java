package Beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import excecoes.RollbackException;
import model.Solicitacao;
import service.SolicitacaoService;

@SessionScoped
@ManagedBean
public class SoliRecebidasBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private SolicitacaoService service = new SolicitacaoService();
	private Solicitacao soli;
	
	public List<Solicitacao> getSolicitacoes(){
		try {
			return service.getSoliRecebidas((long) 2);
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public List<Solicitacao> getSoliEnviadas(){
		try {
			return service.getSoliEnviadas((long) 1);
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String aceitarSoli() {
		try {
			service.aceitarSolicitacao(soli.getId());
			return "trocasEnviadas.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String recusarSoli() {
		try {
			service.cancelarSolicitacaoReecebida(soli.getId());
			return "solicitacoesRecebidas.xhtml?faces-redirect=true";
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

	public Solicitacao getSoli() {
		return soli;
	}

	public void setSoli(Solicitacao soli) {
		this.soli = soli;
	}
}
