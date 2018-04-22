package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
public class Livro {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	@Lob
	private String conservacao;
	private String editora;
	private int publicacao;
	private String isbn;
	@Lob
	private String sinopse;

	@ManyToMany
	private Collection<Cliente> clientesDesejam = new ArrayList<Cliente>();

	@ManyToOne
	private Cliente usuarioPossue = null;
	
	@OneToMany(mappedBy = "clienteSolicitou", cascade = CascadeType.ALL)
	private Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
	
	@OneToMany(mappedBy= "livro", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH })
	private Collection<Troca> trocas = new ArrayList<Troca>();

	public Livro() {
		// TODO Auto-generated constructor stub
	}

	
	public Collection<Troca> getTrocas() {
		return trocas;
	}

	public void setTrocas(Collection<Troca> trocas) {
		this.trocas = trocas;
	}

	public Collection<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Collection<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Cliente> getClientesDesejam() {
		return clientesDesejam;
	}

	public void setClientesDesejam(Collection<Cliente> clientesDesejam) {
		this.clientesDesejam = clientesDesejam;
	}

	public String getConservacao() {
		return conservacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getEditora() {
		return editora;
	}

	public void setEditora(String editora) {
		this.editora = editora;
	}

	public int getPublicacao() {
		return publicacao;
	}

	public void setPublicacao(int publicacao) {
		this.publicacao = publicacao;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public Cliente getUsuarioPossue() {
		return usuarioPossue;
	}

	public void setUsuarioPossue(Cliente usuarioPossue) {
		this.usuarioPossue = usuarioPossue;
	}

	public void setConservacao(String conservacao) {
		this.conservacao = conservacao;
	}

	public String getSinopse() {
		return sinopse;
	}

	public void setSinopse(String sinopse) {
		this.sinopse = sinopse;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((isbn == null) ? 0 : isbn.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Livro other = (Livro) obj;
		if (isbn == null) {
			if (other.isbn != null)
				return false;
		} else if (!isbn.equals(other.isbn))
			return false;
		return true;
	}

}
