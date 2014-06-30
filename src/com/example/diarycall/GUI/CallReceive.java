package com.example.diarycall.GUI;

import com.example.diarycall.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CallReceive extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.callreceive);

	}
	
	public void offClick(View view){
		finish();
		
	}

}
