package com.example.diarycall.GUI;

import java.util.List;

import com.example.diarycall.R;
import com.example.diarycall.codes.Contato;
import com.example.diarycall.data.DataOffline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ContatoGUI extends Activity {

	private EditText txtNome;
	private EditText txtTelefone;
	private Contato contact = new Contato();
	private List<Contato> contacts;

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
			contact.setNome(txtNome.getText().toString());
			contact.setPhoneNumber(txtTelefone.getText().toString());
			System.err.println("erro");
			contacts = DataOffline.loadContacts();
			contacts.add(contact);
			DataOffline.saveData("contacts.dat");
			
			data.putExtra("agenda", contact);
			setResult(Activity.RESULT_OK, data);
			finish();
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

}
