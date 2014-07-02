package com.example.diarycall.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.diarycall.data.DataOffline;
import com.example.diarycall.codes.Contato;
import com.example.diarycall.codes.Menssagem;

public class Controller {
	private DataOffline dataBase;
	private File fileContacts;
	private File fileMessages;

	public Controller() {
		dataBase = new DataOffline();
	}
	
	public List<Contato> carregaContatos(){
		List<Contato> listAux = null;
		try {
			listAux = dataBase.loadContacts(fileContacts);
		} catch (Exception e) {
			//toast(e.getMessage());
		}
		if (listAux == null) {
			listAux = new ArrayList<Contato>();
			listAux.add(new Contato("Nenhum Contato", ""));
		}
		return listAux;
		
	}
	
	public List<Menssagem> carregaMsg(){
		List<Menssagem> listAux = null;
		try {
			listAux = dataBase.loadMessage(fileMessages);
		} catch (Exception e) {
			//toast(e.getMessage());
		}
		if (listAux == null) {
			listAux = new ArrayList<Menssagem>();
			listAux.add(new Menssagem("", "Nenhuma Menssagem"));
		}
		return listAux;
		
	}
	
	public List<Contato> filtraList(String filter){
		List<Contato> listAux = new ArrayList<Contato>();
		for (Contato contato : dataBase.getContacts()) {
			if (contato.getNome().contains(filter) || filter.equals("")) {
				listAux.add(contato);
			}
		}
		return listAux;
		
	}
	
	public List<Menssagem> filtraMsg(String filter){
		List<Menssagem> listAux = new ArrayList<Menssagem>();
		for (Menssagem menssagem : dataBase.getMessages()) {
			if (menssagem.getFoneDestino().contains(filter) || filter.equals("")) {
				listAux.add(menssagem);
			}
		}
		return listAux;
		
	}
	
	public void setFileContacts(File pathContacts) {
		fileContacts = pathContacts;
	}
	
	public boolean enviaMsg(String foneDestino, String mensagem) throws Exception{
		Menssagem msg = new Menssagem(foneDestino, mensagem);
		dataBase.loadMessage(fileMessages);
		dataBase.setMessage(msg);
		dataBase.saveData(fileMessages, dataBase.getMessages());
		return true;
	}

	public void setFileMessage(File fileStreamPath) {
		this.fileMessages = fileStreamPath;
		
	}
	
	
	

}
