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
import javax.persistence.PrimaryKeyJoinColumn;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;


@Entity
@PrimaryKeyJoinColumn(referencedColumnName = "id")
public class Cliente extends Usuario {

	
	private int ponto = 0;
	
	
	@OneToMany(mappedBy= "cliente", cascade = CascadeType.ALL)
	private Collection<ProblemaTroca> problematroca = new ArrayList<ProblemaTroca>();

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
	
	
	public Collection<ProblemaTroca> getProblematroca() {
		return problematroca;
	}

	public void setProblematroca(Collection<ProblemaTroca> problematroca) {
		this.problematroca = problematroca;
	}

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

	public int getPonto() {
		return ponto;
	}

	public void setPonto(int ponto) {
		this.ponto = ponto;
	}
	
}
