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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (aceita ? 1231 : 1237);
		result = prime * result + (ativa ? 1231 : 1237);
		result = prime * result + ((clienteRecebeuSolicitacao == null) ? 0 : clienteRecebeuSolicitacao.hashCode());
		result = prime * result + ((clienteSolicitou == null) ? 0 : clienteSolicitou.hashCode());
		result = prime * result + ((dalaSolicitacao == null) ? 0 : dalaSolicitacao.hashCode());
		result = prime * result + ((livroSolicitado == null) ? 0 : livroSolicitado.hashCode());
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
		if (aceita != other.aceita)
			return false;
		if (ativa != other.ativa)
			return false;
		if (clienteRecebeuSolicitacao == null) {
			if (other.clienteRecebeuSolicitacao != null)
				return false;
		} else if (!clienteRecebeuSolicitacao.equals(other.clienteRecebeuSolicitacao))
			return false;
		if (clienteSolicitou == null) {
			if (other.clienteSolicitou != null)
				return false;
		} else if (!clienteSolicitou.equals(other.clienteSolicitou))
			return false;
		if (dalaSolicitacao == null) {
			if (other.dalaSolicitacao != null)
				return false;
		} else if (!dalaSolicitacao.equals(other.dalaSolicitacao))
			return false;
		if (livroSolicitado == null) {
			if (other.livroSolicitado != null)
				return false;
		} else if (!livroSolicitado.equals(other.livroSolicitado))
			return false;
		return true;
	}
}
