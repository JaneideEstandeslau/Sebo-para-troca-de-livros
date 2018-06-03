package service;

import java.io.Serializable;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Cliente;
import model.ProblemaTroca;
import model.StatusProblema;
import model.Troca;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOProblemaTroca;
import persistencia.DAOSolicitacao;
import persistencia.DAOTroca;

public class ProblemaTrocaService implements Serializable {

	private DAOCliente clienteDAO = new DAOCliente();
	private DAOTroca trocaDAO = new DAOTroca();
	private DAOProblemaTroca problemaDAO = new DAOProblemaTroca();

	public void registrarProblemaTroca(Long idTroca, Long idCliente, ProblemaTroca problema) throws RollbackException {

		Cliente cliente = clienteDAO.recuprarClienteComProblemaTroca(idCliente);
		Troca troca = trocaDAO.recuperarTrocaComProblemas(idTroca);

		problema.setCliente(cliente);
		problema.setTroca(troca);

		cliente.getProblematroca().add(problema);
		troca.setProblema(problema);

		try {
			problemaDAO.save(problema);
			clienteDAO.update(cliente);
			trocaDAO.update(troca);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	public void modificarProblema(ProblemaTroca problema) throws RollbackException {
		try {
			ProblemaTroca p = (ProblemaTroca) problemaDAO.getByID(new ProblemaTroca(), problema.getId());
			p.setResolvido(problema.getResolvido());
			problemaDAO.update(p);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	/**
	 * visualizar a troca e oferecer a opção de problema resolviso.
	 * 
	 * @throws RollbackException
	 * 
	 */
	public void problemaResolvido(Long idProblemaTroca) throws RollbackException {
		try {

			ProblemaTroca problema = (ProblemaTroca) problemaDAO.getByID(new ProblemaTroca(), idProblemaTroca);
			// problema.setResolvido(StatusProblema.);
			problemaDAO.update(problema);

		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	public Object getByID(Long id) throws ServiceDacException {
		try {
			return problemaDAO.getByID(new ProblemaTroca(), id);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}

}
