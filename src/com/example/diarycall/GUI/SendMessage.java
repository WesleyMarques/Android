package com.example.diarycall.GUI;

import java.util.List;

import com.example.diarycall.R;
import com.example.diarycall.codes.Contato;
import com.example.diarycall.codes.Menssagem;
import com.example.diarycall.controller.Controller;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class SendMessage extends Activity{
	
	public EditText msgCampo;
	public EditText toMsg;
	public EditText searchMsg;
	public Intent intent;
	private Controller control;
	private ArrayAdapter<Menssagem> adaptador = null;
	private ListView listSearch;
	private List<Menssagem> listMsg = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendmessage);
		intent = getIntent();
		control = new Controller();
		control.setFileContacts(getFileStreamPath("contacts.dat"));
		control.setFileMessage(getFileStreamPath("messages.dat"));
	}
	
	@Override
	protected void onStart() {
		msgCampo = (EditText) findViewById(R.id.editTextMsg);
		toMsg = (EditText) findViewById(R.id.editTextTo);
		listSearch = (ListView) findViewById(R.id.listMensagens);
		searchMsg = (EditText) findViewById(R.id.searchMsg);
		toMsg.setText(intent.getStringExtra("nome")+"["+intent.getStringExtra("fone")+"]");
		
		//Evento para o campo de texto quando modificado
				searchMsg.addTextChangedListener(new TextWatcher() {
					@Override
					public void onTextChanged(CharSequence s, int start, int before,
							int count) {
						control.carregaContatos();
						listMsg = control.filtraMsg(searchMsg.getText().toString());
						setMsgInListView();
					}

					@Override
					public void beforeTextChanged(CharSequence s, int start, int count,
							int after) {
						// TODO Auto-generated method stub

					}

					@Override
					public void afterTextChanged(Editable s) {
						// TODO Auto-generated method stub

					}
				});
				
				listSearch.setOnItemClickListener(new OnItemClickListener() {
					
					@SuppressWarnings("rawtypes")
					@Override
					public void onItemClick(AdapterView arg0, View view, int position, long index){
						final int auxPosition = position;
						
						
														
					}
				});

		super.onStart();
	}
	
	public void apagarClick(View view){
		msgCampo.setText("");
		
	}
	
	public void sendMsg(View view) throws Exception{
		if (control.enviaMsg(intent.getStringExtra("fone"), msgCampo.getText().toString())) {
			msgCampo.setText("");
			toMsg.setText("");
		}
	}
	
	@Override
	protected void onResume() {
		control.carregaMsg();
		listMsg = control.filtraMsg("");
		setMsgInListView();
		super.onResume();
	}
	
	private void setMsgInListView() {
		adaptador = new ArrayAdapter<Menssagem>(this,
				android.R.layout.simple_list_item_1, listMsg);
		listSearch.setAdapter(adaptador);
	}
}
