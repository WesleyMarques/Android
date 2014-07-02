package com.example.diarycall.codes;

import java.io.Serializable;

public class Menssagem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String foneDestino;
	private String messagem;
	
	public Menssagem(String foneDestino, String mensagem) {
		
		this.foneDestino = foneDestino;
		this.messagem = mensagem;
		
	}
	
	public Menssagem() {
	}

	public String getFoneDestino() {
		return foneDestino;
	}

	public void setFoneDestino(String foneDestino) {
		this.foneDestino = foneDestino;
	}

	public String getMessagem() {
		return messagem;
	}

	public void setMessagem(String messagem) {
		this.messagem = messagem;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Menssagem other = (Menssagem) obj;
		if (foneDestino == null) {
			if (other.foneDestino != null)
				return false;
		} else if (!foneDestino.equals(other.foneDestino))
			return false;
		if (messagem == null) {
			if (other.messagem != null)
				return false;
		} else if (!messagem.equals(other.messagem))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		int len = messagem.length();
		if(len > 50)
			len = 50;
		return "["+foneDestino+"]\n\t"+messagem.substring(0, len);
	}
	
}
