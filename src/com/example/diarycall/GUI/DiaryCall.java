package com.example.diarycall.GUI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.example.diarycall.R;
import com.example.diarycall.codes.Contato;
import com.example.diarycall.data.DataOffline;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class DiaryCall extends Activity {

	private List<Contato> contacts = null;
	private ArrayAdapter<Contato> adaptador = null;
	private DataOffline data = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_diary_call);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	protected void onRestart() {
		super.onRestart();
	}

	public void onClickCadastro(View view) {
		switch (view.getId()) {
		case R.id.cadastrarButton:
			InserirContato();
			break;
		}
	}

	private void InserirContato() {
		try {
			Thread threadContact = new Thread() {
				@Override
				public void run() {
					Intent it = new Intent(DiaryCall.this, ContatoGUI.class);
					startActivityForResult(it, 0);// chama a tela
				}
			};
			threadContact.start();
		} catch (Exception e) {
			trace("Erro : " + e.getMessage());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			toast("Contato cadastrado com sucesso!");
		} else {
			toast("Contato não cadastrado!");
		}
	}

	/*
	 * @Override public boolean onCreateOptionsMenu(Menu menu) {
	 * 
	 * // Inflate the menu; this adds items to the action bar if it is present.
	 * getMenuInflater().inflate(R.menu.diary_call, menu); return true; }
	 * 
	 * @Override public boolean onOptionsItemSelected(MenuItem item) { // Handle
	 * action bar item clicks here. The action bar will // automatically handle
	 * clicks on the Home/Up button, so long // as you specify a parent activity
	 * in AndroidManifest.xml. int id = item.getItemId(); if (id ==
	 * R.id.action_settings) { return true; } return
	 * super.onOptionsItemSelected(item); }
	 */

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_diary_call,
					container, false);
			return rootView;
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
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		
		// List View with all Contacts

		ListView listView = (ListView) findViewById(R.id.listContacts);
		File file = getFileStreamPath("contacts.dat");
		data = new DataOffline();
		try {
			contacts = data.loadContacts(file);
		} catch (Exception e) {
			toast(e.getMessage());
		}
		if (contacts == null) {
			contacts = new ArrayList<Contato>();
			contacts.add(new Contato("Nenhum Contato", ""));
			
		}
		adaptador = new ArrayAdapter<Contato>(this,
				android.R.layout.simple_list_item_1, contacts);
		listView.setAdapter(adaptador);
		super.onResume();
		// End List View
	}

}
