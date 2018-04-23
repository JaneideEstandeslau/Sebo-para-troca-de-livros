package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
public class Cliente implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private String login;
	private String senha;
	private int ponto = 0;
//	private boolean ativo;
	
//	@OneToMany(mappedBy= "cliente", cascade = CascadeType.ALL)
//	private Collection<ProblemaTroca> problematroca = new ArrayList<ProblemaTroca>();

	@OneToMany(mappedBy = "usuarioPossue", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH }, fetch = FetchType.EAGER)
	private Collection<Livro> livrosPossuem = new ArrayList<Livro>();

	@ManyToMany(mappedBy = "clientesDesejam", cascade = CascadeType.ALL)
	private Collection<Livro> livrosDesejados = new ArrayList<Livro>();

	@OneToOne(mappedBy = "cliente")
	@JoinColumn(name = "fk_cliente")
	private Endereco endereco;

	@OneToMany(mappedBy = "clienteSolicitou", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH })
	private Collection<Solicitacao> solicitacoes = new ArrayList<Solicitacao>();
	
	@OneToMany(mappedBy= "clienteEnviando", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH })
	private Collection<Troca> trocasEnviadas = new ArrayList<Troca>();
	
	@OneToMany(mappedBy= "clienteRecebendo", cascade = {CascadeType.PERSIST,CascadeType.MERGE, CascadeType.REFRESH })
	private Collection<Troca> trocasRecebidas = new ArrayList<Troca>();
	
	
	public Collection<Troca> getTrocasEnviadas() {
		return trocasEnviadas;
	}

	public void setTrocasEnviadas(Collection<Troca> trocasEnviadas) {
		this.trocasEnviadas = trocasEnviadas;
	}

	public Collection<Troca> getTrocasRecebidas() {
		return trocasRecebidas;
	}

	public void setTrocasRecebidas(Collection<Troca> trocasRecebidas) {
		this.trocasRecebidas = trocasRecebidas;
	}

	public Collection<Solicitacao> getSolicitacoes() {
		return solicitacoes;
	}

	public void setSolicitacoes(Collection<Solicitacao> solicitacoes) {
		this.solicitacoes = solicitacoes;
	}

	public Collection<Livro> getLivrosDesejados() {
		return livrosDesejados;
	}

	public void setLivrosDesejados(Collection<Livro> livrosDesejados) {
		this.livrosDesejados = livrosDesejados;
	}

	public Collection<Livro> getLivrosPossuem() {
		return livrosPossuem;
	}

	public void setLivrosPossuem(Collection<Livro> livrosPossuem) {
		this.livrosPossuem = livrosPossuem;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public int getPonto() {
		return ponto;
	}

	public void setPonto(int ponto) {
		this.ponto = ponto;
	}
	
//	public boolean isAtivo() {
//		return ativo;
//	}
//
//	public void setAtivo(boolean ativo) {
//		this.ativo = ativo;
//	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((login == null) ? 0 : login.hashCode());
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
		Cliente other = (Cliente) obj;
		if (login == null) {
			if (other.login != null)
				return false;
		} else if (!login.equals(other.login))
			return false;
		return true;
	}

}
