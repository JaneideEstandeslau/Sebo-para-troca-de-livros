package Beans;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import model.Cliente;
import service.ClienteService;

@ViewScoped
@Named
public class VisualizarClientesBean extends AbstractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ClienteService clienteService;
	private Cliente cli;
	private List<Cliente> clientes;

	public void teste() {
		System.out.println("Fui chamado!!!");
	}
	
	@PostConstruct
	public void postConstruct() {
		try {
			this.clientes = clienteService.getClientes();
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}
	
	public List<Cliente> getClientes() {
		return this.clientes;
	}

	public String desativarCliente() {
		try {
			if (cli.isAtivo() == false) {
				reportarMensagemDeErro("Cliente já esta desativado");
				return null;
			} else {
				cli.setAtivo(false);
				clienteService.update(cli);
				reportarMensagemDeSucesso("Cliente Desativado");
				return "adminVisualizarClientes.xhtml?faces-redirect=true";
			}
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}

	public String ativarCliente() {
		try {
			if (cli.isAtivo() == true) {
				reportarMensagemDeErro("Cliente já esta ativo");
				return null;
			} else {
				cli.setAtivo(true);
				clienteService.update(cli);
				reportarMensagemDeSucesso("Cliente Ativado");
				return "adminVisualizarClientes.xhtml?faces-redirect=true";
			}
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
	}

	public String adicionarPontoNegativo() {
		try {
			if (cli.getNumTrocasProblema() == 5) {
				reportarMensagemDeErro("Cliente já possui o numero de problemas limite, Desativieo");
			} else {
				cli.setNumTrocasProblema(cli.getNumTrocasProblema() + 1);
				clienteService.update(cli);
				reportarMensagemDeSucesso("Cliente Ativado");
			}
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
		}
		return "adminVisualizarClientes.xhtml?faces-redirect=true";
	}

	public Cliente getCli() {
		return cli;
	}

	public void setCli(Cliente cli) {
		this.cli = cli;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}
}
