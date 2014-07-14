package com.example.diarycall.GUI;

import java.io.File;

import com.example.diarycall.R;
import com.example.diarycall.codes.Contato;
import com.example.diarycall.data.DataOffline;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InfoContato extends Activity {

	private String nome;
	private String fone;
	private EditText nomeEdt;
	private EditText foneEdt;
	private Button salvarBt;
	private DataOffline data;
	private AlertDialog alerta;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.info_contact);
		Intent intent = getIntent();
		data = new DataOffline();
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

	private void setVisiInSaveButton(int visibily) {
		salvarBt.setVisibility(visibily);
	}

	private void setEnableCampos(boolean valor) {
		nomeEdt.setEnabled(valor);
		foneEdt.setEnabled(valor);

	}

	// Functions of buttons
	/**
	 * Action of button edit
	 */
	public void editarInfo(View view) {
		
		setVisiInSaveButton(View.VISIBLE);
		setEnableCampos(true);
	}

	/**
	 * Action of button save
	 * @throws Exception 
	 */
	public void salvarInfo(View view) throws Exception {
		data.loadContacts(getFileStreamPath("contacts.dat"));
		Log.i("Dados", nomeEdt.getText().toString()+"-"+foneEdt.getText().toString());
		if (data.editByOldNumber(getFileStreamPath("contacts.dat"),fone, new Contato(nomeEdt.getText().toString(),foneEdt.getText().toString()))) {
			nome = nomeEdt.getText().toString();
			fone = foneEdt.getText().toString();
			setInfo();
			setResult(DiaryCall.EDIT_CONTACT);
			Toast.makeText(getApplicationContext(), "Alteração ralizada com sucesso", Toast.LENGTH_SHORT).show();
		}else{
			Toast.makeText(getApplicationContext(), "Usuário não encontrado", Toast.LENGTH_SHORT).show();
		}
		
		setVisiInSaveButton(View.INVISIBLE);
		
	}

	public void enviarMsg(View view) {
		try {
			Thread threadInfoContact = new Thread() {
				@Override
				public void run() {
					Intent sengMsg = new Intent(InfoContato.this,
							SendMessage.class);
					sengMsg.putExtra("nome", nome);
					sengMsg.putExtra("fone", fone);
					startActivityForResult(sengMsg,0);// chama a tela
				}
			};
			threadInfoContact.start();
		} catch (Exception e) {
			Toast.makeText(getApplicationContext(), "Erro : " + e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

	}

	public void deleteContato(View view) {
		// Cria o gerador do AlertDialog
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// define o titulo
		builder.setTitle("Importante");
		// define a mensagem
		builder.setMessage("Deseja realmente remover o contato?");
		// define um botão como positivo
		builder.setPositiveButton("SIM", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						try {
							deleteContatoAux();
							setResult(DiaryCall.DELETE_CONTACT);
							finish();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
		// define um botão como negativo.
		builder.setNegativeButton("NÃO",new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface arg0, int arg1) {
						//não pode
					}
				}); 
		// cria o AlertDialog 
		alerta = builder.create();
		alerta.show(); 
	}

	private void deleteContatoAux() throws Exception {
		File file = getFileStreamPath("contacts.dat");
		if (file.exists()) {
			this.data.loadContacts(file);
		}
		this.data.removeByNumber(fone);
		this.data.saveData(file, data.getContacts());

	}
}
