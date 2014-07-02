package com.example.diarycall.codes;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.example.diarycall.data.DataOffline;

public class Teste {
	
	@Test
	public void test() throws Exception{
		DataOffline data = new DataOffline();
		File file = new File("src/com/example/diarycall/codes/messages.dat");
		System.err.println(file.canWrite());
		data.saveData(file,new ArrayList<Menssagem>());
	}

}
