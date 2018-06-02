package service;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import excecoes.RollbackException;
import model.Cliente;
import model.Livro;
import model.Solicitacao;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOSolicitacao;

public class SolicitacaoService implements Serializable {

	private DAOCliente clienteDAO = new DAOCliente();
	private DAOSolicitacao soliDAO = new DAOSolicitacao();
	private DAOLivro livroDAO = new DAOLivro();
	private TrocaService trocaServe = new TrocaService();

	/**
	 * Esse metodo solicito, faz com que um cliente solicite um livro já existente.
	 * 
	 * @param idCliente
	 * @param idLivro
	 * @throws RollbackException
	 */
	public void solicitarLivro(Long idCliente, Long idLivro) throws RollbackException {

		Livro usuarioPossue = livroDAO.recuperarLivroComPossuinte(idLivro);

		if (usuarioPossue.getUsuarioPossue() != null) {

			Cliente cliente = clienteDAO.recuperarClienteComSolicitacoes(idCliente);
			if (cliente.getPonto() > 0) {

				boolean solicitou = false;
				for (Solicitacao s : cliente.getSolicitacoes()) {
					if (s.getLivroSolicitado().getId() == idLivro) {
						solicitou = true;
						break;
					}
				}

				if (solicitou == false) {

					Livro livro = livroDAO.recuperarLivroComSolicitacoes(idLivro);
					Cliente c = clienteDAO.recuperarClienteComLivrosPossue(idCliente);
					boolean possui = false;
					for (Livro l : c.getLivrosPossuem()) {
						if (l.getIsbn().equals(livro.getIsbn())) {
							possui = true;
							break;
						}
					}
					if (possui == false) {

						
						Solicitacao solicitacao = new Solicitacao();
						
						Cliente clienteRecebeu = clienteDAO
								.recuperarClienteComSolicitacoesRecebidas(usuarioPossue.getUsuarioPossue().getId());
						clienteRecebeu.getSolicitacoesRecebidas().add(solicitacao);

						cliente.getSolicitacoes().add(solicitacao);
						livro.getSolicitacoes().add(solicitacao);
						solicitacao.setClienteRecebeuSolicitacao(clienteRecebeu);
						solicitacao.setClienteSolicitou(cliente);
						solicitacao.setLivroSolicitado(livro);
						solicitacao.setAtiva(true);
						cliente.setPonto(cliente.getPonto()-1);
						try {
							soliDAO.save(solicitacao);
							clienteDAO.update(cliente);
						} catch (Exception e) {
							e.printStackTrace();
						}
					} else {
						throw new RollbackException("Você possui esse livro, por tanto não pode solicitalo");
					}
				} else {
					throw new RollbackException("Você já solicitou esse livro");
				}
			} else {
				throw new RollbackException("Para solicitar um livro você precisa enviar outro primeiro");
			}
		} else {
			throw new RollbackException("Nem um usuario possui esse livro, por tanto você não pode solicitalo");
		}

	}

	/**
	 * Esse metodo faz com que o cliente que enviou a solicitação de troca cancele a mesma.
	 * @param idSolicitacao
	 * @throws RollbackException
	 */
	
	public void cancelarSolicitacaoEnviada(Long idSolicitacao) throws RollbackException {

		try {
			Solicitacao soli = soliDAO.recuperarSolicitacaoComCliente(idSolicitacao);
			Cliente cliente = clienteDAO.recuperarClienteComSolicitacoes(soli.getClienteSolicitou().getId());
			cliente.setPonto(cliente.getPonto()+1);
			this.verificarSolicitacaoAceita(idSolicitacao);
			soliDAO.delete(new Solicitacao(), idSolicitacao);
			clienteDAO.update(cliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	/**
	 * Esse método faz com que o cliente que recebeu a solicitação cancele a mesma.
	 * @param idSolicitacao
	 * @throws RollbackException
	 */
	
	public void cancelarSolicitacaoReecebida(Long idSolicitacao) throws RollbackException {

		try {
			Solicitacao soli = soliDAO.recuperarSolicitacaoComCliente(idSolicitacao);
			Cliente cliente = clienteDAO.recuperarClienteComSolicitacoes(soli.getClienteSolicitou().getId());
			cliente.setPonto(cliente.getPonto()+1);
			soliDAO.delete(new Solicitacao(), idSolicitacao);
			clienteDAO.update(cliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	/**
	 * Esse método aceita uma solicitação feita por um cliente e em seguida realiza
	 * a troca.
	 * 
	 * @param idSolicitacao
	 * @throws RollbackException 
	 */
	public void aceitarSolicitacao(Long idSolicitacao) throws RollbackException {

		Solicitacao soli = soliDAO.recuperarSolicitacaoComCliente(idSolicitacao);
		Long idClienteSolicitou = soli.getClienteSolicitou().getId();
		soli = soliDAO.recuperarSolicitacaoComLivro(idSolicitacao);
		Livro livro = livroDAO.recuperarLivroComPossuinte(soli.getLivroSolicitado().getId());
		soli.setAceita(true);
		soli.setAtiva(false);

		try {
			soliDAO.update(soli);
			trocaServe.realizarTroca(livro.getUsuarioPossue().getId(), livro.getId(), idClienteSolicitou);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	/**
	 * Esse método verifica se uma solicitação feita já foi aceita
	 * @param idSolicitacao
	 * @throws Exception
	 */
	public void verificarSolicitacaoAceita(Long idSolicitacao) throws Exception {
		Solicitacao soli = (Solicitacao) soliDAO.getByID(new Solicitacao(), idSolicitacao);
		if (soli.isAceita() == true) {
			throw new RollbackException(
					"A solicitação foi aceita é o livro enviado, por tanto você não pode cancelala");
		}
	}
	
	public List<Solicitacao> getSoliRecebidas(Long idCliente) throws RollbackException {
		try {
			return soliDAO.soliRecebidas(idCliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}
	
	public List<Solicitacao> getSoliEnviadas(Long idCliente) throws RollbackException {
		try {
			return soliDAO.soliEnviadas(idCliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

}
