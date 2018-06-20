package service;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import excecoes.RollbackException;
import model.Avaliacao;
import model.Cliente;
import persistencia.DAOAvaliacao;
import persistencia.DAOCliente;
import util.TransacionalCdi;

@ApplicationScoped
public class AvaliacaoService implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DAOAvaliacao avaliDAO;
	@Inject
	private DAOCliente clienteDAO;
	
	@TransacionalCdi
	public void salverAvaliacao(Avaliacao avali, long idCliente) throws RollbackException {
		Cliente c = clienteDAO.getAvaliacoes(idCliente);
		c.getAvaliacoes().add(avali);
		avali.setCliente(c);
		try {
			clienteDAO.update(c);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

}
