package com.example.diarycall.GUI;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.example.diarycall.R;
import com.example.diarycall.codes.Contato;
import com.example.diarycall.data.DataOffline;

import android.R.bool;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContatoGUI extends Activity {

	private EditText txtNome;
	private EditText txtTelefone;
	private String[] dados = new String[2];
	private Contato contactActual;
	private DataOffline data;

	public ContatoGUI() {
		contactActual = new Contato();
		data = new DataOffline();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro);
		
		try {
			txtNome = (EditText) findViewById(R.id.edtName);
			txtTelefone = (EditText) findViewById(R.id.edtPhone);
		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}
	}

	public void btnConfirmar_click(View view) {

		try {
			Intent data = new Intent();
			contactActual.setNome(txtNome.getText().toString());
			contactActual.setPhoneNumber(txtTelefone.getText().toString());
			File file = getFileStreamPath("contacts.dat");
			if (file.exists()) {
				if (!contactExist(this.data.loadContacts(file)))
					throw new Exception("Esse nome já foi registrado");						
			}else{
				toast("Arquivo não encontrado!");
			}
			this.data.setContact(contactActual);
			this.data.saveData(file, this.data.getContacts());
			setResult(Activity.RESULT_OK, data);
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

	public void btnThread_click(View view) {
		try {
			Thread threadCall = new Thread(){
				@Override
				public void run()
	              {
					Intent it = new Intent(ContatoGUI.this, CallReceive.class);
					startActivityForResult(it,0);// chama a tela 
	              }				
			};
			threadCall.start();
		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}					
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
