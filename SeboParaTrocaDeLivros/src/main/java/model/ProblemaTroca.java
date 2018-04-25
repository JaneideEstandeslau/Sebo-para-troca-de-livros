package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class ProblemaTroca{
	
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Lob
	private String descricaoProblema;
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToOne(mappedBy="problema",fetch=FetchType.EAGER)
	private Troca troca;
	
	private boolean resolvido;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescricaoProblema() {
		return descricaoProblema;
	}

	public void setDescricaoProblema(String descricaoProblema) {
		this.descricaoProblema = descricaoProblema;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Troca getTroca() {
		return troca;
	}

	public void setTroca(Troca troca) {
		this.troca = troca;
	}

	public boolean isResolvido() {
		return resolvido;
	}

	public void setResolvido(boolean resolvido) {
		this.resolvido = resolvido;
	}
	
	
}
