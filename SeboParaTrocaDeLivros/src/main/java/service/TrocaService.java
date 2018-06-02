package service;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.List;

import excecoes.RollbackException;
import model.Cliente;
import model.Livro;
import model.Troca;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOTroca;

public class TrocaService implements Serializable {

	private DAOCliente clienteDAO = new DAOCliente();
	private DAOLivro livroDAO = new DAOLivro();
	private DAOTroca trocaDAO = new DAOTroca();
	private ClienteService service = new ClienteService();

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
	
	public void upedate(Troca troca) throws RollbackException {
		try {
			trocaDAO.update(troca);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	public void cancelarTroca(Long idTroca, Long idEnviando, Long idRecendo) throws RollbackException {

		try {
			Troca troca = trocaDAO.recuperarTroca(idTroca);
			Cliente clienteRecebendo = clienteDAO.recuperarClienteComTrocasRecebidas(idRecendo);
			Cliente clienteEnviando = clienteDAO.recuperarClienteComTrocasEnviadas(idEnviando);
			
			clienteRecebendo.setPonto(clienteRecebendo.getPonto() + 1);
			clienteEnviando.setPonto(clienteEnviando.getPonto() - 1);
			
			clienteDAO.update(clienteEnviando);
			clienteDAO.update(clienteRecebendo);
			
			service.adicionaLivroPossuintes(idEnviando, troca.getLivro().getId());
			trocaDAO.delete(new Troca(), idTroca);
			
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
}
