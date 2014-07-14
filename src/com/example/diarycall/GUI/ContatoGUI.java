package com.example.diarycall.GUI;

import java.io.File;
import java.util.List;

import com.example.diarycall.R;
import com.example.diarycall.codes.Contato;
import com.example.diarycall.controller.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContatoGUI extends Activity {

	private EditText txtNome;
	private EditText txtTelefone;
	private String[] dados = new String[2];
	private Contato contactActual;
	private Controller controller = null;

	public ContatoGUI() throws Exception {
		contactActual = new Contato();
		controller = new Controller();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro);
		controller.setFileContacts(getFileStreamPath("contacts.dat"));
		controller.carregaContatos();
		try {
			txtNome = (EditText) findViewById(R.id.edtName);
			txtTelefone = (EditText) findViewById(R.id.edtPhone);
		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}
	}

	/**
	 * Cadastrar Contato
	 * @param view
	 */
	public void btnConfirmar_click(View view) {

		try {
			Intent data = new Intent();
			contactActual.setNome(txtNome.getText().toString());
			contactActual.setPhoneNumber(txtTelefone.getText().toString());
			File file = getFileStreamPath("contacts.dat");
			if (file.exists()) {
				if (!contactExist(controller.getData().loadContacts(file)))
					throw new Exception("Esse nome já foi registrado");						
			}else{
				toast("Arquivo não encontrado!");
			}
			controller.getData().setContact(contactActual);
			controller.getData().saveData(file, controller.getData().getContacts());
			setResult(DiaryCall.INSERT_CONTACT, data);
			finish();
		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}
	}
	
	private boolean contactExist(List<Contato> contacts){
		for (Contato cont : contacts) {
			if (cont.getNome().equals(contactActual.getNome())) {
				return false;				
			}			
		}		
		return true;
	}

	public void btnCancelar_click(View view) {
		try {
			setResult(Activity.RESULT_CANCELED);
			finish();
		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}
	}

	public void toast(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}

	private void trace(String msg) {
		toast(msg);
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		dados[0] = txtNome.getText().toString();
		dados[1] = txtTelefone.getText().toString();		
	}
	
	@Override
	protected void onRestart() {
		super.onRestart();
		txtNome.setText(dados[0]);
		txtTelefone.setText(dados[1]);
	}

}
