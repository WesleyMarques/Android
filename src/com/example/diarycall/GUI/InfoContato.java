package com.example.diarycall.GUI;

import com.example.diarycall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InfoContato extends Activity{
	
	private String nome;
	private String fone;
	private EditText nomeEdt;
	private EditText foneEdt;
	private Button salvarBt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_contact);
		Intent intent = getIntent();
		nome = intent.getStringExtra("nome");
		fone = intent.getStringExtra("fone");
	}
	
	@Override
	protected void onStart() {
		nomeEdt = (EditText) findViewById(R.id.edtName);
		foneEdt = (EditText) findViewById(R.id.edtPhone);
		salvarBt = (Button) findViewById(R.id.buttonSalvar);
		setInfo();
		super.onStart();
	}
	
	private void setInfo(){
		nomeEdt.setText(nome);
		foneEdt.setText(fone);
		setEnableCampos(false);
	}
	
	private void setVisiInSaveButton(int visibily){
		salvarBt.setVisibility(visibily);		
	}
	
	private void setEnableCampos(boolean valor){
		nomeEdt.setEnabled(valor);
		foneEdt.setEnabled(valor);
		
	}
	
//	Functions of buttons
	/**
	 * Action of button edit 
	 */
	public void editarInfo(View view){
		setVisiInSaveButton(View.VISIBLE);
		setEnableCampos(true);
	}
	
	/**
	 * Action of button save  
	 */
	public void salvarInfo(View view){
		setInfo();
		setVisiInSaveButton(View.INVISIBLE);
	}

	public void enviarMsg(View view){
		try {
			Thread threadInfoContact = new Thread() {
				@Override
				public void run() {
					Intent sengMsg = new Intent(InfoContato.this, SendMessage.class);
					sengMsg.putExtra("nome", nome);
					sengMsg.putExtra("fone", fone);
					startActivity(sengMsg);// chama a tela
				}
			};
			threadInfoContact.start();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Erro : " + e.getMessage(), Toast.LENGTH_SHORT).show();
		}	
		
	}
}
