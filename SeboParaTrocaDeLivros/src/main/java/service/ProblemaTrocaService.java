package service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Cliente;
import model.ProblemaTroca;
import model.StatusProblema;
import model.Troca;
import persistencia.DAOCliente;
import persistencia.DAOProblemaTroca;
import persistencia.DAOTroca;
import util.TransacionalCdi;

@ApplicationScoped
public class ProblemaTrocaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DAOCliente clienteDAO;
	@Inject
	private DAOTroca trocaDAO;
	@Inject
	private DAOProblemaTroca problemaDAO;

	@TransacionalCdi
	public void registrarProblemaTroca(Long idTroca, Long idCliente, ProblemaTroca problema) throws RollbackException {

		Cliente cliente = clienteDAO.recuprarClienteComProblemaTroca(idCliente);
		Troca troca = trocaDAO.recuperarTrocaComProblemas(idTroca);

		problema.setCliente(cliente);
		problema.setTroca(troca);
		problema.setResolvido(StatusProblema.PENDENTE);

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

	@TransacionalCdi
	public void modificarProblema(ProblemaTroca problema) throws RollbackException {
		try {
//			ProblemaTroca p = (ProblemaTroca) problemaDAO.getByID(new ProblemaTroca(), problema.getId());
//			p.setResolvido(problema.getResolvido());
			problemaDAO.update(problema);
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

	public List<ProblemaTroca> getProblemasPendentes() throws ServiceDacException {
		try {
			return problemaDAO.recuperarProbTroca(StatusProblema.PENDENTE);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}
	
	public List<ProblemaTroca> getProblemasResolvendo() throws ServiceDacException {
		try {
			return problemaDAO.recuperarProbTroca(StatusProblema.RESOLVENDO);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}

}
