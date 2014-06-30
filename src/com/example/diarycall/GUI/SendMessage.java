package com.example.diarycall.GUI;

import com.example.diarycall.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class SendMessage extends Activity{
	
	public EditText msgCampo;
	public EditText toMsg;
	public Intent intent;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.sendmessage);
		intent = getIntent();
	}
	
	@Override
	protected void onStart() {
		msgCampo = (EditText) findViewById(R.id.editTextMsg);
		toMsg = (EditText) findViewById(R.id.editTextTo);
		toMsg.setText(intent.getStringExtra("nome")+"["+intent.getStringExtra("fone")+"]");
		super.onStart();
	}
	
	public void apagarClick(View view){
		msgCampo.setText("");
		
	}

}
