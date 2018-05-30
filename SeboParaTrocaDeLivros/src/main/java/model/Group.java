package model;


public enum Group {
	
	ADMIN("Admin"), VISITANTE("Visitante");
	
	private String nome;
	
	private Group(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
