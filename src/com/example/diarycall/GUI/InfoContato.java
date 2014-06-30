package com.example.diarycall.GUI;

import com.example.diarycall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

public class InfoContato extends Activity{
	
	private String nome;
	private String fone;
	private EditText nomeEdt;
	private EditText foneEdt;
	
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
		setInfo();
		super.onStart();
	}
	
	private void setInfo(){
		nomeEdt.setText(nome);
		foneEdt.setText(fone);
	}

}
