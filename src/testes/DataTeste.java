/**
 * 
 */
package testes;

import static org.junit.Assert.*;

import org.junit.Test;

import com.example.diarycall.codes.Contato;
import com.example.diarycall.data.DataOffline;

/**
 * @author Wesley
 * 
 */
public class DataTeste {

	@Test
	public void DeveCarregarAquivOffline() {
		try {
			//assertNull(DataOffline.loadContacts());
			System.out.println(DataOffline.loadContacts().get(0).getNome());
			
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	
}
