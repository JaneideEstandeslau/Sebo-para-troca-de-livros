package Beans;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import model.Solicitacao;
import service.SolicitacaoService;

@SessionScoped
@Named
public class SoliRecebidasBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private SolicitacaoService service;
	private Solicitacao soli;
	
	public List<Solicitacao> getSolicitacoes(){
		try {
			return service.getSoliRecebidas(getUsuarioLogado());
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public List<Solicitacao> getSoliEnviadas(){
		try {
			return service.getSoliEnviadas(getUsuarioLogado());
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String aceitarSoli() {
		try {
			service.aceitarSolicitacao(soli.getId());
			reportarMensagemDeSucesso("Aceitar solicitação.");
			return "trocasEnviadas.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String recusarSoli() {
		try {
			service.cancelarSolicitacaoReecebida(soli, soli.getClienteSolicitou(), soli.getLivroSolicitado());
			reportarMensagemDeSucesso("Solicitação recusada com sucesso.");
			return "solicitacoesRecebidas.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public String cancelarSoli() {
		try {
			service.cancelarSolicitacaoEnviada(soli, soli.getClienteSolicitou(), soli.getLivroSolicitado());
			reportarMensagemDeSucesso("Solicitação concelada com sucesso.");
			return "soliEnviadas.xhtml?faces-redirect=true";
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
