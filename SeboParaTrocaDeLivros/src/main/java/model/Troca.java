package model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Troca {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	private String dataTroca;
	
	@OneToOne
	private ProblemaTroca problema;
	
	@ManyToOne
	private Livro livro;
	
	@ManyToOne
	private Cliente clienteEnviando;
	
	@ManyToOne
	private Cliente clienteRecebendo;
	
	public String getDataTroca() {
		return dataTroca;
	}
	public void setDataTroca(String dataTroca) {
		this.dataTroca = dataTroca;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public ProblemaTroca getProblema() {
		return problema;
	}
	public void setProblema(ProblemaTroca problema) {
		this.problema = problema;
	}
	public Cliente getClienteEnviando() {
		return clienteEnviando;
	}
	public void setClienteEnviando(Cliente clienteEnviando) {
		this.clienteEnviando = clienteEnviando;
	}
	public Cliente getClienteRecebendo() {
		return clienteRecebendo;
	}
	public void setClienteRecebendo(Cliente clienteRecebendo) {
		this.clienteRecebendo = clienteRecebendo;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		Troca other = (Troca) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
