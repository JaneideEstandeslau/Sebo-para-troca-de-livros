package service;

import model.Cliente;
import model.Endereco;
import persistencia.DAOCliente;
import persistencia.DAOEndereco;

public class EnderecoService {
	
	private DAOEndereco enderecoDAO = new  DAOEndereco();
	private DAOCliente clienteDAO = new DAOCliente();
	
	public void cadastrarEndereco(Endereco endereco, Long idCliente) {
		
		Cliente cliente = clienteDAO.recuperarClienteComEndereco(idCliente);
		try {
			cliente.setEndereco(endereco);
			endereco.setCliente(cliente);
			enderecoDAO.save(endereco);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void editarEndereco(Endereco endereco) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
