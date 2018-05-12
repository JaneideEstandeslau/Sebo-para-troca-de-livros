package model;


public enum Group {
	
	Livro("Lirvo"), 
	LIVRODEBOLSO("Livro de bolso"),
	CAPADURA("Capadura"), 
	EBOOK("Ebook"), 
	REVISTA("Revista"),
	PERIODICO("Periodico");
	
	private String nome;
	
	private Group(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
}
