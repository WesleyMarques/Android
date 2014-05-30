/**
 *
 */
package com.example.diarycall.data;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.List;

import com.example.diarycall.codes.Contato;

/**
 * @author Wesley
 *
 */
public class DataOffline {

    private static List<Contato> contacts;
    
    

    /**
     * Load users from file
     *
     * @return Returns a users list.
     * @throws Exception 
     *
     *
     *
     */
    public static List<Contato> loadContacts() throws Exception {
        List<Object> listConts = new ArrayList<Object>();
        contacts = new ArrayList<Contato>();
        listConts = readData("contacts.dat");
        //contacts = new ArrayList<Contato>();
        // Downcast da lista de usuários do tipo Object para User
        if (listConts != null) {
            for (Object object : listConts) {
                contacts.add((Contato) object);
            }
        }
        saveData("contacts.dat");
        return contacts;
    }

    /**
     * Read a file.
     *
     * @param file Name of the file.
     * @return An object list, with the information about the file.
     * @throws Exception 
     */
    private static List<Object> readData(String file) throws Exception{
        ObjectInputStream in = null;
        List<Object> dataObject = null;

        	try {
				in = new ObjectInputStream(new FileInputStream("src/com/example/diarycall/data/"+file));
			} catch (StreamCorruptedException e) {
				throw new Exception("Erro no stream: "+ e.getMessage());
			} catch (FileNotFoundException e) {
				throw new Exception("Erro no arquivo: "+ e.getMessage());
			} catch (IOException e) {
				throw new Exception("Erro no Carregamento: "+ e.getMessage());
			}
            dataObject = (ArrayList<Object>) in.readObject();
            in.close();
        return dataObject;
    }

    public static boolean saveData(List<Contato> cont, String file) throws Exception{
    	contacts = cont;
    	return saveData(file);
    	
    }
    /**
     * Save data in archive
     *
     * @param file
     * @return
     */
    public static boolean saveData(String file) throws Exception{
        ObjectOutputStream out = null;
        try {
            try {            	
                out = new ObjectOutputStream(new FileOutputStream("src/com/example/diarycall/data/"+file));
            } catch (FileNotFoundException e) {
            	System.err.println(e.getMessage());
                return false;
            }
            out.writeObject(contacts);
            out.close();
        } catch (Exception e) {
            throw new Exception("Erro ao salvar os dados");
        }
        return true;
    }
}
