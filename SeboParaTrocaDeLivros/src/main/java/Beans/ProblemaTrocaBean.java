package Beans;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import excecoes.RollbackException;
import model.ProblemaTroca;
import model.Troca;
import service.ProblemaTrocaService;
import service.TrocaService;

@SessionScoped
@ManagedBean
public class ProblemaTrocaBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ProblemaTroca problema;
	private Troca troca;
	private ProblemaTrocaService service = new ProblemaTrocaService();
	private TrocaService trocaService = new TrocaService();
	
	public void init() {
		problema = new ProblemaTroca();
	}

	public String registrar() {
		try {
			service.registrarProblemaTroca(troca.getId(), (long) 1, problema);
			reportarMensagemDeSucesso("Problema registrado com sucesso");
			return "paginaDoUsuario.xhtml?faces-redirect=true";
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}
	
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

}
