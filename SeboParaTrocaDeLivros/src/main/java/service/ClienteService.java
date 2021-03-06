package service;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collection;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Avaliacao;
import model.Cliente;
import model.Group;
import model.Livro;
import persistencia.DAOCliente;
import persistencia.DAOLivro;
import util.TransacionalCdi;

import java.util.List;

@ApplicationScoped
public class ClienteService implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private DAOCliente clienteDAO;
	@Inject
	private DAOLivro livroDAO;

	/**
	 * Esse método cadastra um cliente
	 * 
	 * @param cliente
	 * @throws RollbackException
	 */
	@TransacionalCdi
	public void salvarUsuario(Cliente cliente) throws RollbackException {
		try {
			validarLogin(cliente.getLogin());
			validarCPF(cliente.getCpf());
			cliente.setAtivo(true);
			cliente.setTipo(Group.CLIENTE);
			Cliente c = clienteDAO.recuperarCliente((long) 2);
			if (c == null) {
				cliente.setPonto(1);
			}
			calcularHashDaSenha(cliente);
			clienteDAO.save(cliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	private void validarCPF(String cpf) throws RollbackException {
		Cliente c = clienteDAO.validarCPFCadastro(cpf);

		if (c != null) {
			throw new RollbackException("Já existe um cliente com esse CPF");
		}
	}

	private void validarLogin(String login) throws RollbackException {
		Cliente c = clienteDAO.validarLoginCadastro(login);

		if (c != null) {
			throw new RollbackException("Já existe um cliente com esse Login");
		}
	}

	@TransacionalCdi
	public void removerUsuario(Long idCliente) throws RollbackException {
		try {
			Cliente cliente = (Cliente) clienteDAO.getByID(new Cliente(), idCliente);
			cliente.setAtivo(false);
			clienteDAO.update(cliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	/**
	 * Esse metodo modifica as informações pessoais do usuário.
	 * 
	 * @param cliente
	 * @throws RollbackException
	 */
	@TransacionalCdi
	public void modificarUsuario(Cliente cliente) throws RollbackException {
		try {
			validarCPFCadastrado(cliente.getCpf());
			validarLoginCadastrado(cliente.getLogin());
			Cliente c = (Cliente) clienteDAO.getByID(new Cliente(), cliente.getId());
			c.setLogin(cliente.getLogin());
			c.setNome(cliente.getNome());
			c.setPonto(cliente.getPonto());
			c.setEmail(cliente.getEmail());
			clienteDAO.update(c);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}

	}

	/**
	 * Esse método adiciona um livro ao usuário que o possue, caso ele não pertença
	 * a outro usuário.
	 * 
	 * @param idCliente
	 * @param idLivro
	 * @throws RollbackException
	 */
	@TransacionalCdi
	public void adicionaLivroPossuintes(Long idCliente, Long idLivro) throws RollbackException {

		Livro livro = livroDAO.recuperarLivroComPossuinte(idLivro);
		try {
			// verificarLivroPossuiDono(livro);
			Cliente cliente = clienteDAO.recuperarCliente(idCliente);
			cliente.getLivrosPossuem().add(livro);
			livro.setUsuarioPossue(cliente);
			livroDAO.update(livro);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	/**
	 * Esse método remove um livro da lista dos livros que um usuário possue.
	 * 
	 * @param idCliente
	 * @param idLivro
	 * @throws RollbackException
	 */
	@TransacionalCdi
	public void removerLivroPossuintes(Long idCliente, Long idLivro) throws RollbackException {
		Livro livro = livroDAO.recuperarLivroComPossuinte(idLivro);
		try {

			Cliente cliente = clienteDAO.recuperarCliente(idCliente);
			cliente.getLivrosPossuem().remove(livro);
			livro.setUsuarioPossue(null);
			livroDAO.update(livro);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
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
	@TransacionalCdi
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
	 * 
	 * @param idCliente
	 * @param idLivro
	 */
	@TransacionalCdi
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
	public void validarCPFCadastrado(String cpf) throws RollbackException {

		List<Cliente> c = clienteDAO.validarCPF(cpf);

		if (c.size() > 1) {
			throw new RollbackException("Já existe um cliente com esse CPF");
		}
	}

	public void validarLoginCadastrado(String login) throws RollbackException {

		List<Cliente> c = clienteDAO.validarLogin(login);

		if (c.size() > 1) {
			throw new RollbackException("Já existe um cliente com esse Login");
		}
	}

	/**
	 * Esse método verifica se o livro já possue um dono.
	 * 
	 * @param idLivro
	 * @throws RollbackException
	 */
	public void verificarLivroPossuiDono(Livro usuarioPossue) throws RollbackException {

		if (usuarioPossue.getUsuarioPossue() != null) {
			throw new RollbackException("Esse livro já possue um Dono");
		}

	}

	public Object getByID(Long id) throws ServiceDacException {
		try {
			return (Cliente) clienteDAO.getByID(new Cliente(), id);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}

	public Cliente recuperarCliente(Long idCliente) throws ServiceDacException {
		try {
			return clienteDAO.recuperarCliente(idCliente);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}

	public Collection<Livro> recuperarClienteComLivrosPossue(Long idCliente) throws RollbackException {
		try {
			Cliente cliente = clienteDAO.recuperarClienteComLivrosPossue(idCliente);
			Collection<Livro> livros = cliente.getLivrosPossuem();
			return livros;
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}

	public List<Cliente> getClientes() throws RollbackException {
		try {
			return clienteDAO.getClientes();
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}
	
	@TransacionalCdi
	public void update(Cliente cliente) throws RollbackException {
		try {
			clienteDAO.update(cliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}
	
	/**
	 * Método que calcula o hash de uma dada senha usando o algoritmo SHA-256.
	 * 
	 * @param password
	 *            senha a ser calculada o hash
	 * @return hash da senha
	 * @throws ServiceDacException
	 *             lançada caso ocorra algum erro durante o processo
	 */
	private String hash(String password) throws ServiceDacException {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("SHA-256");
			md.update(password.getBytes("UTF-8"));
			byte[] digest = md.digest();
			String output = Base64.getEncoder().encodeToString(digest);
			return output;
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			throw new ServiceDacException("Could not calculate hash!", e);
		}
	}
	
	private String calcularHashDaSenha(Cliente user) throws ServiceDacException {
		user.setSenha(hash(user.getSenha()));
		return user.getSenha();
	}
	
	public boolean senhaAtualConfere(String passwordAtualHash, String confirmacaoPasswordAtual) throws ServiceDacException {
		
		if (passwordAtualHash == null && confirmacaoPasswordAtual == null) {
			return true;
		}

		if (passwordAtualHash == null || confirmacaoPasswordAtual == null) {
			return false;
		}
		
		String confirmacaoPasswordAtualHash = hash(confirmacaoPasswordAtual);
		return passwordAtualHash.equals(confirmacaoPasswordAtualHash);
	}
	
	@TransacionalCdi
	public void modificarSenha(Cliente cliente) throws RollbackException {
		try {
			calcularHashDaSenha(cliente);
			clienteDAO.update(cliente);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}
	
	public int avaliacoes(long idCliente) {
		Cliente c = clienteDAO.getAvaliacoes(idCliente);
		int cont = 0;
		for(Avaliacao avali: c.getAvaliacoes()) {
			cont += avali.getPonto();
		}
		
		if(cont > 0) {
			cont = cont/c.getAvaliacoes().size();
			if(cont >= 5)
				return 5;
			else 
				return cont;
		}
		else {
			return 0;
		}
	}
}
