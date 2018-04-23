package service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import model.Cliente;
import model.Livro;
import model.Troca;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOTroca;

public class TrocaService {

	private DAOCliente clienteDAO = new DAOCliente();
	private DAOLivro livroDAO = new DAOLivro();
	private DAOTroca trocaDAO = new DAOTroca();

	/**
	 * Esse método deixa registrado as trocas que o cliente fez.
	 * 
	 * @param idClienteEnviando
	 * @param idLivro
	 * @param idClienteRecebendo
	 */
	public void realizarTroca(Long idClienteEnviando, Long idLivro, Long idClienteRecebendo) {

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
			clienteRecebendo.setPonto(clienteRecebendo.getPonto() - 1);
			clienteEnviando.getLivrosPossuem().remove(livro);
			livro.setUsuarioPossue(null);
			
			livroDAO.update(livro);
			clienteDAO.updateCliente(clienteEnviando);
			clienteDAO.updateCliente(clienteRecebendo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	

	}

	public void cancelarTroca(Long idTroca) {
		
		try {
//			Troca troca = (Troca) trocaDAO.getByID(new Troca(), idTroca);
			trocaDAO.delete(new Troca(), idTroca);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Troca troca = DAOTroca.retrieve(idTroca);
		//
		// Cliente clienteRecebeu = troca.getClienteRecebendo();
		// clienteRecebeu.setPonto(clienteRecebeu.getPonto()+1);
		//
		// Cliente clienteEnviou = troca.getClienteEnviando();
		// clienteEnviou.setPonto(clienteEnviou.getPonto()-1);
		//
		// DAOTroca.retrieve(idTroca);
		// DAOCliente.update(clienteEnviou);
		// DAOCliente.update(clienteRecebeu);

	}

}
