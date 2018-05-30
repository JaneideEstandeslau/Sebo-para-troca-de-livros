package Beans;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Cliente;
import model.Endereco;
import service.ClienteService;
import service.EnderecoService;

@SessionScoped
@ManagedBean
public class CadastrarClienteBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ClienteService clienteService = new ClienteService();
	private EnderecoService enderecoService = new EnderecoService();
	private Cliente cliente;
	private Endereco endereco;
	
	public void visualizarPerfil() {
		try {
			cliente = clienteService.recuperarCliente((long) 1);
			endereco = enderecoService.recuperarEndereco(cliente.getEndereco().getId());
		} catch (ServiceDacException e) {
			reportarMensagemDeErro(e.getMessage());
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
		}
	}
	
	public void init() {
		if(cliente == null) {
			cliente = new Cliente();
			endereco = new Endereco();
		}
	}
	
	public String saveCliente() {
		try {
			
			if(isEdicaoDeClinte()) {
				clienteService.modificarUsuario(cliente);
				enderecoService.editarEndereco(endereco);
			}
			else {
				clienteService.salvarUsuario(cliente);
				enderecoService.cadastrarEndereco(endereco, cliente.getCpf());
			}
		}catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		reportarMensagemDeSucesso("Usuario '" + cliente.getNome() + "' salvo");

		return "paginaDoUsuario.xhtml?faces-redirect=true";
		
	}
	
	public boolean isEdicaoDeClinte() {
		return cliente.getId() != null;
	}

	public ClienteService getClienteService() {
		return clienteService;
	}

	public void setClienteService(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	public EnderecoService getEnderecoService() {
		return enderecoService;
	}

	public void setEnderecoService(EnderecoService enderecoService) {
		this.enderecoService = enderecoService;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}
}
