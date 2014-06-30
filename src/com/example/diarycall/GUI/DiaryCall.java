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
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import android.os.Build;

public class DiaryCall extends Activity {

	private List<Contato> contacts = null;
	private ArrayAdapter<Contato> adaptador = null;
	private DataOffline data = null;
	private EditText searchContact;
	private ListView listSearch;

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
	protected void onStart() {
		searchContact = (EditText) this.findViewById(R.id.searchContact);
		listSearch = (ListView) this.findViewById(R.id.listContacts);

		//Evento para o campo de texto quando modificado
		searchContact.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String filtFromET = searchContact.getText().toString();
				loadContacts(filtFromET);
				setContacts();
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
			
			@Override
			public void onItemClick(AdapterView arg0, View view, int position, long index){
				final int auxPosition = position;
				Log.d("menu", contacts.get(auxPosition).getNome());
				try {
					Thread threadInfoContact = new Thread() {
						@Override
						public void run() {
							Intent infoContato = new Intent(DiaryCall.this, InfoContato.class);
							infoContato.putExtra("nome", contacts.get(auxPosition).getNome());
							infoContato.putExtra("fone", contacts.get(auxPosition).getPhoneNumber());
							startActivityForResult(infoContato, 0);// chama a tela
						}
					};
					threadInfoContact.start();
				} catch (Exception e) {
					trace("Erro : " + e.getMessage());
				}								
			}
		});
		super.onStart();
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
					it.putExtra("Act", "Create contact");
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
			toast("Contato n�o cadastrado!");
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
		// List View with all Contacts
		loadContacts("");
		setContacts();
		super.onResume();
		// End List View
	}

	private void setContacts() {
		adaptador = new ArrayAdapter<Contato>(this,
				android.R.layout.simple_list_item_1, contacts);
		listSearch.setAdapter(adaptador);
	}

	private void loadContacts(String filter) {
		File file = getFileStreamPath("contacts.dat");
		List<Contato> listAux = null;
		data = new DataOffline();
		try {
			listAux = data.loadContacts(file);
		} catch (Exception e) {
			toast(e.getMessage());
		}
		if (listAux == null) {
			listAux.add(new Contato("Nenhum Contato", ""));
		}
		filterList(filter, listAux);

	}

	private void filterList(String filter, List<Contato> listAux) {
		contacts = new ArrayList<Contato>();
		for (Contato contato : listAux) {
			if (contato.getNome().contains(filter) || filter.equals("")) {
				this.contacts.add(contato);
			}
		}

	}

}
