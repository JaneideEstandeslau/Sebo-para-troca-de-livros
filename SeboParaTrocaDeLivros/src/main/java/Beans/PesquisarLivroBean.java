package Beans;


import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Livro;
import service.LivroService;

@SessionScoped
@Named
public class PesquisarLivroBean extends AbstractBean{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LivroService service;
	private List<Livro> livros;
	private String nomeDoLivro;
	
	public String pesquisa(){
		return  "pesquisarLivro.xhtml?faces-redirect=true";
	}
	
	public String Exibir() {
		return "Nome do livro "+nomeDoLivro;
	}

	public String getNomeDoLivro() {
		return nomeDoLivro;
	}

	public void setNomeDoLivro(String nomeDoLivro) {
		this.nomeDoLivro = nomeDoLivro;
	}
	
	public List<Livro> getResultado(){
		try{
			 livros = service.getAllLivrosTitulo(nomeDoLivro);
			 return livros;
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public List<Livro> getLivros() {
		return livros;
	}

	public void setLivros(List<Livro> livros) {
		this.livros = livros;
	}
	
	
}
