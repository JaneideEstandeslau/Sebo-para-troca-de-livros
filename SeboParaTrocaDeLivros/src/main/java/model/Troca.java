package model;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class Troca implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5011633873933172284L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	private boolean recebida;

	private String codRastreio;

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

	public boolean isRecebida() {
		return recebida;
	}

	public void setRecebida(boolean recebida) {
		this.recebida = recebida;
	}

	public String getCodRastreio() {
		return codRastreio;
	}

	public void setCodRastreio(String codRastreio) {
		this.codRastreio = codRastreio;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((clienteEnviando == null) ? 0 : clienteEnviando.hashCode());
		result = prime * result + ((clienteRecebendo == null) ? 0 : clienteRecebendo.hashCode());
		result = prime * result + ((dataTroca == null) ? 0 : dataTroca.hashCode());
		result = prime * result + ((livro == null) ? 0 : livro.hashCode());
		result = prime * result + ((problema == null) ? 0 : problema.hashCode());
		result = prime * result + (recebida ? 1231 : 1237);
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
		if (clienteEnviando == null) {
			if (other.clienteEnviando != null)
				return false;
		} else if (!clienteEnviando.equals(other.clienteEnviando))
			return false;
		if (clienteRecebendo == null) {
			if (other.clienteRecebendo != null)
				return false;
		} else if (!clienteRecebendo.equals(other.clienteRecebendo))
			return false;
		if (dataTroca == null) {
			if (other.dataTroca != null)
				return false;
		} else if (!dataTroca.equals(other.dataTroca))
			return false;
		if (livro == null) {
			if (other.livro != null)
				return false;
		} else if (!livro.equals(other.livro))
			return false;
		if (problema == null) {
			if (other.problema != null)
				return false;
		} else if (!problema.equals(other.problema))
			return false;
		if (recebida != other.recebida)
			return false;
		return true;
	}
	
}
