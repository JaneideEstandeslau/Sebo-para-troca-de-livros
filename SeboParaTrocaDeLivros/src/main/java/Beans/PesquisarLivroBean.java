package Beans;


import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import model.Livro;
import persistencia.DAOLivro;
import service.LivroService;

@SessionScoped
@ManagedBean
public class PesquisarLivroBean extends AbstractBean{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private LivroService service = new LivroService();
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
