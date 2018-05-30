package service;

import java.io.Serializable;

import excecoes.RollbackException;
import model.Cliente;
import model.Endereco;
import persistencia.DAOCliente;
import persistencia.DAOEndereco;

public class EnderecoService implements Serializable{
	
	private DAOEndereco enderecoDAO = new  DAOEndereco();
	private DAOCliente clienteDAO = new DAOCliente();
	
	public void cadastrarEndereco(Endereco endereco, String cpf) throws RollbackException {
		
		Cliente cliente = clienteDAO.recuperarClienteComEndereco(cpf);
		try {
			cliente.setEndereco(endereco);
			endereco.setCliente(cliente);
			enderecoDAO.save(endereco);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}
	
	public void editarEndereco(Endereco endereco) throws RollbackException {
		try {
			Endereco end = (Endereco) enderecoDAO.getByID(new Endereco(), endereco.getId());
			end.setRua(endereco.getRua());
			end.setNumero(endereco.getNumero());
			end.setBairro(endereco.getBairro());
			end.setCidade(endereco.getCidade());
			end.setEstado(endereco.getEstado());
			end.setCep(endereco.getCep());
			enderecoDAO.update(end);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}
	
	public Endereco recuperarEndereco(Long idEndereco) throws RollbackException {
		try {
		return enderecoDAO.recuperarEndereco(idEndereco);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

}
