package service;

import model.Cliente;
import model.ProblemaTroca;
import model.Troca;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOProblemaTroca;
import persistencia.DAOSolicitacao;
import persistencia.DAOTroca;

public class ProblemaTrocaService {
	
	private DAOCliente clienteDAO = new DAOCliente();
	private DAOTroca trocaDAO = new DAOTroca();
	private DAOProblemaTroca problemaDAO = new DAOProblemaTroca();
	
	public void registrarProblemaTroca(Long idTroca, Long idCliente, ProblemaTroca problema) {
		
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
			e.printStackTrace();
		}
		
	}
	
	public void problemaResolvido(Long idProblemaTroca) {
		try {
			
			ProblemaTroca problema = (ProblemaTroca) problemaDAO.getByID(new ProblemaTroca(), idProblemaTroca);
			problema.setResolvido(true);
			problemaDAO.update(problema);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
