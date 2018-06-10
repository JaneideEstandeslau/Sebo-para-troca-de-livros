package service;

import java.io.Serializable;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import excecoes.RollbackException;
import model.Cliente;
import model.Livro;
import model.Solicitacao;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOSolicitacao;
import util.TransacionalCdi;

@ApplicationScoped
public class SolicitacaoService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DAOCliente clienteDAO;
	@Inject
	private DAOSolicitacao soliDAO;
	@Inject
	private DAOLivro livroDAO;
	@Inject
	private TrocaService trocaServe;

	/**
	 * Esse metodo solicito, faz com que um cliente solicite um livro já existente.
	 * 
	 * @param idCliente
	 * @param idLivro
	 * @throws RollbackException
	 */
	@TransacionalCdi
	public void solicitarLivro(Long idCliente, Long idLivro) throws RollbackException {

		Livro usuarioPossue = livroDAO.recuperarLivroComPossuinte(idLivro);

		if (usuarioPossue.getUsuarioPossue() != null) {

			if (usuarioPossue.getUsuarioPossue().isAtivo() == true) {

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
							cliente.setPonto(cliente.getPonto() - 1);
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
				throw new RollbackException("O usuario possui esse livro não esta ativo.");
			}
		} else {
			throw new RollbackException("Nem um usuario possui esse livro, por tanto você não pode solicitalo");
		}

	}

	/**
	 * Esse metodo faz com que o cliente que enviou a solicitação de troca cancele a
	 * mesma.
	 * 
	 * @param idSolicitacao
	 * @throws RollbackException
	 */
	@TransacionalCdi
	public void cancelarSolicitacaoEnviada(Solicitacao soli, Cliente cliente, Livro livro) throws RollbackException {

		try {
			cliente.setPonto(cliente.getPonto() + 1);
			cliente.getSolicitacoes().remove(livro);
			soli.setAtiva(false);
			soliDAO.update(soli);
			clienteDAO.update(cliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	/**
	 * Esse método faz com que o cliente que recebeu a solicitação cancele a mesma.
	 * 
	 * @param idSolicitacao
	 * @throws RollbackException
	 */
	@TransacionalCdi
	public void cancelarSolicitacaoReecebida(Solicitacao soli, Cliente cliente, Livro livro) throws RollbackException {

		try {
			cliente.setPonto(cliente.getPonto() + 1);
			cliente.getSolicitacoes().remove(livro);
			soli.setAtiva(false);
			soliDAO.update(soli);
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
	@TransacionalCdi
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
	 * 
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
