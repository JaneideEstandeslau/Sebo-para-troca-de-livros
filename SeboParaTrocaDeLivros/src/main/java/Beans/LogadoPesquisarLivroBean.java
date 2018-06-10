package Beans;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import model.Livro;
import service.LivroService;

@SessionScoped
@Named
public class LogadoPesquisarLivroBean extends AbstractBean{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private LivroService service;
	private List<Livro> livros;
	private String nomeDoLivro;
	
	/**
	 * Método verifica e retorna qual tipo (função) de usuário
	 * está online.
	 * 
	 * @return
	 */
	public Integer getTiporUserLog(){
		
		switch (tipoUser()) {
		case ADMIN:
			return 1;
		case CLIENTE:
			return 2;
		default:
			return 0;
		}
	}
	
	public String pesquisa(){
		return  "pesquisarLivroLogado.xhtml?faces-redirect=true";
	}
	
	public String verPerfil(Livro livro) {
		if(livro.getUsuarioPossue() != null) {
			return  "perfilDoCliente.xhtml?faces-redirect=true";
		}
		else {
			reportarMensagemDeErro("Nem um usuário possui esse livro");
			return null;
		}
	}
	
	public String teste() {
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
