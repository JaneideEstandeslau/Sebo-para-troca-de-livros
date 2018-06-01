package model;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Solicitacao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String dalaSolicitacao;
	private boolean aceita;
	private boolean ativa;

	@ManyToOne
	private Cliente clienteSolicitou;
	
	@ManyToOne
	private Cliente clienteRecebeuSolicitacao;

	@ManyToOne(fetch = FetchType.EAGER)
	private Livro livroSolicitado;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDalaSolicitacao() {
		return dalaSolicitacao;
	}

	public void setDalaSolicitacao(String dalaSolicitacao) {
		this.dalaSolicitacao = dalaSolicitacao;
	}
	public boolean isAceita() {
		return aceita;
	}

	public void setAceita(boolean aceita) {
		this.aceita = aceita;
	}

	public Cliente getClienteSolicitou() {
		return clienteSolicitou;
	}

	public void setClienteSolicitou(Cliente clienteSolicitou) {
		this.clienteSolicitou = clienteSolicitou;
	}

	public Livro getLivroSolicitado() {
		return livroSolicitado;
	}

	public void setLivroSolicitado(Livro livroSolicitado) {
		this.livroSolicitado = livroSolicitado;
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
		Solicitacao other = (Solicitacao) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean isAtiva() {
		return ativa;
	}

	public void setAtiva(boolean ativa) {
		this.ativa = ativa;
	}

	public Cliente getClienteRecebeuSolicitacao() {
		return clienteRecebeuSolicitacao;
	}

	public void setClienteRecebeuSolicitacao(Cliente clienteRecebeuSolicitacao) {
		this.clienteRecebeuSolicitacao = clienteRecebeuSolicitacao;
	}
}
