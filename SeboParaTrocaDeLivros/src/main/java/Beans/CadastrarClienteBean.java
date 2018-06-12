package Beans;

import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Cliente;
import model.Endereco;
import service.ClienteService;
import service.EnderecoService;

@ViewScoped
@Named
public class CadastrarClienteBean extends AbstractBean{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private ClienteService clienteService;
	@Inject
	private EnderecoService enderecoService;
	private String senha;
	private Cliente cliente;
	private Endereco endereco;
	
	
	public void visualizarPerfil() {
		try {
			cliente = clienteService.recuperarCliente(getUsuarioLogado());
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
				reportarMensagemDeSucesso("Usuario '" + cliente.getNome() + "' salvo");
				
				return "paginaDoUsuario.xhtml?faces-redirect=true";
			}
			else {
			
				if(cliente.getSenha().equals(senha)) {
					clienteService.salvarUsuario(cliente);
					enderecoService.cadastrarEndereco(endereco, cliente.getCpf());
					reportarMensagemDeSucesso("Usuario '" + cliente.getNome() + "' salvo");
					
					return "paginaDoUsuario.xhtml?faces-redirect=true";
				}
				else {
					reportarMensagemDeErro("Senhas não conferem");
					return null;
				}
			}
		}catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		
	}
	
	public String removerConta() {
		try {
			clienteService.removerUsuario(getUsuarioLogado());
		} catch (RollbackException e) {
			reportarMensagemDeErro(e.getMessage());
			return null;
		}
		return "deslogarUsuario.xhtml?faces-redirect=true";
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

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
}
