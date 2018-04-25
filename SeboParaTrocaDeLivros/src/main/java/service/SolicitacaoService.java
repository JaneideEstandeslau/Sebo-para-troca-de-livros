package service;

import excecoes.RollbackException;
import model.Cliente;
import model.Livro;
import model.Solicitacao;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOSolicitacao;

public class SolicitacaoService {
	
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
						cliente.getSolicitacoes().add(solicitacao);
						livro.getSolicitacoes().add(solicitacao);
						solicitacao.setClienteSolicitou(cliente);
						solicitacao.setLivroSolicitado(livro);
						try {
							soliDAO.save(solicitacao);
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
	
	public void cancelarSolicitacao(Long idSolicitacao) {
		
		try {
			this.verificarSolicitacaoAceita(idSolicitacao);
			soliDAO.delete(new Solicitacao(), idSolicitacao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * Esse método aceita uma solicitação feita por um cliente e em seguida realiza
	 * a troca.
	 * 
	 * @param idSolicitacao
	 */
	public void aceitarSolicitacao(Long idSolicitacao) {

		Solicitacao soli = soliDAO.recuperarSolicitacaoComCliente(idSolicitacao);
		Long idClienteSolicitou = soli.getClienteSolicitou().getId();
		soli = soliDAO.recuperarSolicitacaoComLivro(idSolicitacao);
		Livro livro = livroDAO.recuperarLivroComPossuinte(soli.getLivroSolicitado().getId());
		soli.setAceita(true);

		try {
			soliDAO.update(soli);
			trocaServe.realizarTroca(livro.getUsuarioPossue().getId(), livro.getId(), idClienteSolicitou);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	public void verificarSolicitacaoAceita(Long idSolicitacao) throws Exception {
		Solicitacao soli = (Solicitacao) soliDAO.getByID(new Solicitacao(), idSolicitacao);
		if(soli.isAceita() == true) {
			throw new RollbackException("A solicitação foi aceita é o livro enviado, por tanto você não pode cancelala");
		}
	}

}
