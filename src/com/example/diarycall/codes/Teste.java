package com.example.diarycall.codes;

import java.io.File;
import java.util.List;

import org.junit.Test;

import com.example.diarycall.data.DataOffline;

public class Teste {
	
	@Test
	public void test() throws Exception{
		DataOffline data = new DataOffline();
		File file = new File("src/com/example/diarycall/codes/contacts.dat");
		System.err.println(file.canWrite());
		List<Contato> ls = data.loadContacts(file);
		data.setContact(new Contato("Teste","0000000"));
		data.saveData(file);
	}

}
