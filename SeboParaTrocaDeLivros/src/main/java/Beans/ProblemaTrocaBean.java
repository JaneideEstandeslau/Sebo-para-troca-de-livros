package Beans;

import java.util.List;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.ProblemaTroca;
import model.StatusProblema;
import model.Troca;
import service.ProblemaTrocaService;
import service.TrocaService;

@SessionScoped
@Named
public class ProblemaTrocaBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private StatusProblema status;
	private ProblemaTroca problema;
	private Troca troca;
	@Inject
	private ProblemaTrocaService service;
	@Inject
	private TrocaService trocaService;
	
	public void init() {
		problema = new ProblemaTroca();
	}
	
	public String visuzaliarProb() {
		return "visualizarTroca.xhtml?faces-redirect=true";
	}
	
	public void recTroca() {
		try {
			troca = trocaService.getTroca(troca.getId());
		} catch (ServiceDacException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	public String registrar() {
		try {
			service.registrarProblemaTroca(troca.getId(), getUsuarioLogado(), problema);
			reportarMensagemDeSucesso("Problema registrado com sucesso");
			return "paginaDoUsuario.xhtml?faces-redirect=true";
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
	
	public List<ProblemaTroca> getProblemas(){
		try {
			return service.getProblemasPendentes();
		} catch (ServiceDacException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public List<ProblemaTroca> getProblemasResol(){
		try {
			return service.getProblemasResolvendo();
		} catch (ServiceDacException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
	public StatusProblema[] recStatus() {
		return StatusProblema.values();
	}
	
	public void modProblema() {
		try {
			problema.setResolvido(status);
			service.modificarProblema(problema);
			reportarMensagemDeSucesso("Status do Problema Modificado");
			problema = null;
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}

	public ProblemaTroca getProblema() {
		return problema;
	}

	public void setProblema(ProblemaTroca problema) {
		this.problema = problema;
	}

	public ProblemaTrocaService getService() {
		return service;
	}

	public void setService(ProblemaTrocaService service) {
		this.service = service;
	}

	public Troca getTroca() {
		return troca;
	}

	public void setTroca(Troca troca) {
		this.troca = troca;
	}

	public TrocaService getTrocaService() {
		return trocaService;
	}

	public void setTrocaService(TrocaService trocaService) {
		this.trocaService = trocaService;
	}

	public StatusProblema getStatus() {
		return status;
	}

	public void setStatus(StatusProblema status) {
		this.status = status;
	}

}
