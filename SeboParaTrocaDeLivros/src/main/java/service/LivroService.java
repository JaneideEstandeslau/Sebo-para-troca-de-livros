package service;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import excecoes.RollbackException;
import excecoes.ServiceDacException;
import model.Cliente;
import model.Livro;
import persistencia.DAOLivro;

public class LivroService implements Serializable{
	
	private DAOLivro livroDAO = new DAOLivro();
	
	public void salvarLivro(Livro livro) throws RollbackException {
		try {
			validarIsbn(livro.getIsbn());
			livroDAO.save(livro);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
	}
	
	public void modificarLivro(Livro livro) throws RollbackException {
		
		try {
			validarIsbn(livro.getIsbn());
			Livro l = (Livro) livroDAO.getByID(new Livro(), livro.getId());
			l.setConservacao(livro.getConservacao());
			l.setTitulo(livro.getTitulo());
			l.setEditora(livro.getEditora());
			l.setPublicacao(livro.getPublicacao());
			l.setIsbn(livro.getIsbn());
			l.setSinopse(livro.getSinopse());
			livroDAO.update(l);
		} catch (Exception e) {
			throw new RollbackException(e.getMessage());
		}
		
	}
	/**
	 * Esse método verifica se já existe um livro com o isbn que o cliente esta tentando cadastrar.
	 * @param isbn
	 * @throws RollbackException
	 */
	public void validarIsbn(String isbn) throws RollbackException {
		
		Livro livro = livroDAO.validarIsbn(isbn);
		if(livro != null) {
			throw new RollbackException("Livro já existe procureo e o possua");
		}
		
	}
	
	public Livro getByID(Long itemId) throws ServiceDacException {
		try {
			return (Livro) livroDAO.getByID(new Livro(), itemId);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}
	
	public List<Livro> getAllLivrosTitulo(String titulo) throws ServiceDacException{
		try {
			return livroDAO.searchItems(titulo);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}
	
	public Livro recuperarLivroPeloISBN(String isbn) throws ServiceDacException{
		try {
			return livroDAO.recuperarLivroPeloISBN(isbn);
		} catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}
	
	public Livro recuperarLivroClientePossui(Long id) throws ServiceDacException{
		try {
			return livroDAO.recuperarLivroComPossuinte(id);
		}catch (Exception e) {
			throw new ServiceDacException(e.getMessage(), e);
		}
	}

}
