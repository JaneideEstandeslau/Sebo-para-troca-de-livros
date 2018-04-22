package service;

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
	
	public void validarIsbn(String isbn) {
		
		Livro livro = livroDAO.validarIsbn(isbn);
		if(livro != null) {
			System.out.println("Livro já existe");
		}
		
	}

}
