package model;

public enum StatusProblema {
	
	RESOLVENDO("Resolvendo"), RESOLVIDO("Resolvido"), PENDENTE("Pendente");
	
	private String nome;
	
	private StatusProblema(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
