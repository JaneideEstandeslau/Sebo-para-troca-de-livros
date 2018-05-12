package service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

import excecoes.RollbackException;
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
			validarCPF(cliente.getCpf());
			cliente.setAtivo(true);
			clienteDAO.save(cliente);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void removerUsuario(Long idCliente) {
		try {
			Cliente cliente = (Cliente) clienteDAO.getByID(new Cliente(), idCliente);
			cliente.setAtivo(false);
			clienteDAO.update(cliente);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Esse metodo modifica as informações pessoais do usuário.
	 * @param cliente
	 */
	public void modificarUsuario(Cliente cliente) {
		try {
			validarCPF(cliente.getCpf());
			Cliente c = (Cliente) clienteDAO.getByID(new Cliente(), cliente.getId());
			c.setLogin(cliente.getLogin());
			c.setNome(cliente.getNome());
			c.setPonto(cliente.getPonto());
			clienteDAO.update(c);
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
				throw new RollbackException("Você possui esse livro, por tanto não pode Desejalo");
			}

		} else {
			throw new RollbackException("Você já adicionou esse livro a sua lista de desejos");
		}
	}
	
	/**
	 * Esse método remove um livro da lista de desejos do usuário.
	 * @param idCliente
	 * @param idLivro
	 */
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
	 * Esse método verifica se o login informado não esta associado a nem um usuário
	 * já existente no sistema.
	 * 
	 * @param cpf
	 * @throws RollbackException 
	 */
	public void validarCPF(String cpf) throws RollbackException {

		Cliente c = clienteDAO.validarCPF(cpf);

		if (c != null) {
			throw new RollbackException("Já existe um cliente com esse CPF");
		}
	}
	
	/**
	 * Esse método verifica se o livro já possue um dono.
	 * @param idLivro
	 * @throws RollbackException 
	 */
	public void verificarLivroPossuiDono(Livro usuarioPossue) throws RollbackException {

		if (usuarioPossue.getUsuarioPossue() != null) {
			throw new RollbackException("Você já possue esse livro");
		}
		
	}

}
