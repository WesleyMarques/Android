package com.example.diarycall.codes;

import java.io.Serializable;

public class Contato implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String nome;
	private String phoneNumber;

	public Contato() {

	}

	public Contato(String nome, String phoneNumber) {
		this.setNome(nome);
		this.setPhoneNumber(phoneNumber);

	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
