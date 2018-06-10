package service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Cliente;
import model.Livro;
import model.ProblemaTroca;
import model.StatusProblema;
import model.Troca;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOProblemaTroca;
import persistencia.DAOTroca;
import util.TransacionalCdi;

@ApplicationScoped
public class TrocaService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DAOCliente clienteDAO;
	@Inject
	private DAOLivro livroDAO;
	@Inject
	private DAOTroca trocaDAO;
	@Inject
	private DAOProblemaTroca probDAO;

	/**
	 * Esse método deixa registrado as trocas que o cliente fez.
	 * 
	 * @param idClienteEnviando
	 * @param idLivro
	 * @param idClienteRecebendo
	 * @throws RollbackException
	 */
	public void realizarTroca(Long idClienteEnviando, Long idLivro, Long idClienteRecebendo) throws RollbackException {

		LocalDate data = LocalDate.now();
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		Troca troca = new Troca();

		Cliente clienteRecebendo = clienteDAO.recuperarClienteComTrocasRecebidas(idClienteRecebendo);
		Cliente clienteEnviando = clienteDAO.recuperarClienteComTrocasEnviadas(idClienteEnviando);

		Livro livro = livroDAO.recuperarLivroComTrocas(idLivro);
		livro.getTrocas().add(troca);

		clienteEnviando.getTrocasEnviadas().add(troca);
		clienteRecebendo.getTrocasRecebidas().add(troca);

		troca.setDataTroca(fmt.format(data));
		troca.setClienteEnviando(clienteEnviando);
		troca.setClienteRecebendo(clienteRecebendo);
		troca.setLivro(livro);

		try {
			trocaDAO.save(troca);
			clienteEnviando.setPonto(clienteEnviando.getPonto() + 1);
			clienteEnviando.getLivrosPossuem().remove(livro);
			livro.setUsuarioPossue(null);

			livroDAO.update(livro);
			clienteDAO.updateCliente(clienteEnviando);
			clienteDAO.updateCliente(clienteRecebendo);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	@TransacionalCdi
	public void upedate(Troca troca) throws RollbackException {
		try {
			Troca t = trocaDAO.recuperarTrocaComProblemas(troca.getId());
			ProblemaTroca prob = t.getProblema();
			if(prob != null) {
				prob.setResolvido(StatusProblema.RESOLVIDO);
				probDAO.update(prob);
			}
			trocaDAO.update(troca);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	@TransacionalCdi
	public void cancelarTroca(Troca troca, Livro livro, Cliente clienteEnviando, Cliente clienteRecebendo)
			throws RollbackException {

		try {

			clienteRecebendo.setPonto(clienteRecebendo.getPonto() + 1);
			clienteRecebendo.getSolicitacoes().remove(livro);
			clienteEnviando.setPonto(clienteEnviando.getPonto() - 1);

			clienteDAO.update(clienteEnviando);
			clienteDAO.update(clienteRecebendo);

			trocaDAO.delete(troca, troca.getId());

		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	public List<Troca> getTrocasRebidas(Long idCliente) throws RollbackException {
		try {

			return trocaDAO.recuperarTrocasRecebias(idCliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	public List<Troca> getTrocasEnviadas(Long idCliente) throws RollbackException {
		try {

			return trocaDAO.recuperarTrocasEnviadas(idCliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	public Object getByID(Long id) throws ServiceDacException {
		try {
			return trocaDAO.getByID(new Troca(), id);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}

	public Troca getTroca(Long idTroca) throws ServiceDacException {
		try {
			return trocaDAO.recTroca(idTroca);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}
}
