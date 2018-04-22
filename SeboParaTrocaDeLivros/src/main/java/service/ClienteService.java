package service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import model.Cliente;
import model.Livro;
import model.Solicitacao;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import persistencia.DAOSolicitacao;

public class ClienteService {

	private DAOCliente clienteDAO = new DAOCliente();
	private DAOSolicitacao soliDAO = new DAOSolicitacao();
	private DAOLivro livroDAO = new DAOLivro();
	private TrocaService trocaServe = new TrocaService();

	/**
	 * Esse método cadastra um cliente
	 * @param cliente
	 */
	public void salvarUsuario(Cliente cliente) {
		try {
			validarLogin(cliente.getLogin());
			clienteDAO.save(cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removerUsuario(Long idCliente) {
		try {
			Cliente cliente = (Cliente) clienteDAO.getByID(new Cliente(), idCliente);
//			cliente.setAtivo(false);
			clienteDAO.update(cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Esse método adiciona um livro ao usuário que o possue, caso ele não pertença a outro usuário.
	 * @param idCliente
	 * @param idLivro
	 */
	public void adicionaLivroPossuintes(Long idCliente, Long idLivro) {
		
		Livro livro = livroDAO.recuperarLivroComPossuinte(idLivro);
		try {
			verificarLivroPossuiDono(livro);
			Cliente cliente = clienteDAO.recuperarCliente(idCliente);
			cliente.getLivrosPossuem().add(livro);
			livro.setUsuarioPossue(cliente);
			livroDAO.update(livro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Esse método remove um livro da lista dos livros que um usuário possue.
	 * @param idCliente
	 * @param idLivro
	 */
	public void removerLivroPossuintes(Long idCliente, Long idLivro) {
		Livro livro = livroDAO.recuperarLivroComPossuinte(idLivro);
		try {
			
			Cliente cliente = clienteDAO.recuperarCliente(idCliente);
			cliente.getLivrosPossuem().remove(livro);
			livro.setUsuarioPossue(null);
			livroDAO.update(livro);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	/**
	 * Esse método faz com que um cliente solicite um livro caso ele ainda não tenha
	 * solicitado ou não o possua.
	 * 
	 * @param idCliente
	 * @param idLivro
	 * @throws Exception
	 */

	public void adicionarLivroListaDesejos(Long idCliente, Long idLivro) throws Exception {

		Livro livro = livroDAO.recuperarLivroComClienteDesejam(idLivro);
		boolean deseja = false;
		for (Cliente c : livro.getClientesDesejam()) {
			if (c.getId() == idCliente) {
				deseja = true;
				break;
			}
		}

		if (deseja == false) {

			boolean possui = false;
			Cliente c = clienteDAO.recuperarClienteComLivrosPossue(idCliente);
			for (Livro l : c.getLivrosPossuem()) {
				if (l.getIsbn().equals(livro.getIsbn())) {
					possui = true;
					break;
				}
			}
			if (possui == false) {

				Cliente cliente = clienteDAO.recuperarClienteComLivrosDesejados(idCliente);
				livro.getClientesDesejam().add(cliente);
				cliente.getLivrosDesejados().add(livro);
				livroDAO.adicionarLivroListaDesejos(livro);
			} else {
				System.out.println("Você possui esse livro, por tanto não pode Desejalo");
			}

		} else {
			System.out.println("Você já adicionou esse livro a sua lista de desejos");
		}
	}
	
	public void removerLivroListaDesejos(Long idCliente, Long idLivro) {
		
		Livro livro = livroDAO.recuperarLivroComClienteDesejam(idLivro);
		Cliente cliente = clienteDAO.recuperarClienteComLivrosDesejados(idCliente);
		cliente.getLivrosDesejados().remove(livro);
		livro.getClientesDesejam().remove(cliente);
		try {
			livroDAO.update(livro);
			clienteDAO.update(cliente);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	

	/**
	 * Esse metodo solicito, faz com que um cliente solicite um livro já existente.
	 * 
	 * @param idCliente
	 * @param idLivro
	 */
	public void solicitarLivro(Long idCliente, Long idLivro) {

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
						System.out.println("Você possui esse livro, por tanto não pode solicitalo");
					}
				} else {
					System.out.println("Você já solicitou esse livro");
				}
			} else {
				System.out.println("Para solicitar um livro você precisa enviar outro primeiro");
			}
		} else {
			System.out.println("Nem um usuario possui esse livro");
		}

	}
	
	public void cancelarSolicitacao(Long idSolicitacao) {
		
		try {
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

	/**
	 * Esse método verifica se o login informado não esta associado a nem um usuário
	 * já existente no sistema.
	 * 
	 * @param login
	 */
	public void validarLogin(String login) {

		Cliente c = clienteDAO.validarLogin(login);

		if (c != null) {
			System.out.println("Login invalido");
		}
	}
	
	/**
	 * Esse método verifica se o livro já possue um dono.
	 * @param idLivro
	 */
	public void verificarLivroPossuiDono(Livro usuarioPossue) {

		if (usuarioPossue.getUsuarioPossue() != null) {
			System.out.println("Usuario já possue esse livro");
		}
		
	}

}
