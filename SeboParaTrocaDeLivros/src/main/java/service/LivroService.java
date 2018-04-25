package service;

import excecoes.RollbackException;
import model.Cliente;
import model.Livro;
import persistencia.DAOLivro;

public class LivroService {
	
	private DAOLivro livroDAO = new DAOLivro();
	
	public void salvarLivro(Livro livro) {
		try {
			validarIsbn(livro.getIsbn());
			livroDAO.save(livro);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void modificarLivro(Livro livro) {
		
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
			// TODO Auto-generated catch block
			e.printStackTrace();
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
			throw new RollbackException("Livro já existe");
		}
		
	}

}
