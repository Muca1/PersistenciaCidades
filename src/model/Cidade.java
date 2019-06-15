package model;

public class Cidade {

	private String nome;
	private int cep;
	private String uf;

	public Cidade(String nome, int cep, String uf) {
		this.nome = nome;
		this.cep = cep;
		this.uf = uf;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public int getCep() {
		return cep;
	}

	public void setCep(int cep) {
		this.cep = cep;
	}

	public String getUf() {
		return uf;
	}

	public void setUf(String uf) {
		this.uf = uf;
	}
}
