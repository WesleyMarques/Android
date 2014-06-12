/**
 *
 */
package com.example.diarycall.data;

import java.io.File;
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

    private List<Contato> contacts;
    


    /**
     * Load users from file
     *
     * @return Returns a users list.
     * @throws Exception 
     *
     *
     *
     */
    public List<Contato> loadContacts(File file) throws Exception {
        List<Object> listConts = new ArrayList<Object>();
        contacts = new ArrayList<Contato>();
        listConts = readData(file);
        // Downcast da lista de usuários do tipo Object para User
        if (listConts != null) {
            for (Object object : listConts) {
                contacts.add((Contato) object);
            }
        }
        return contacts;
    }

    /**
     * Read a file.
     *
     * @param file Name of the file.
     * @return An object list, with the information about the file.
     * @throws Exception 
     */
    private List<Object> readData(File file) throws Exception{
    	ObjectInputStream in = null;
        List<Object> dataObject = null;

        	try {
				in = new ObjectInputStream(new FileInputStream(file));
			} catch (StreamCorruptedException e) {
				throw new Exception("Erro no stream: "+ e.getMessage());
			} catch (FileNotFoundException e) {
				throw new Exception("Erro no arquivo: "+ e.getMessage());
			} catch (IOException e) {
				throw new Exception("Erro no Carregamento: "+ e.getMessage());
			}finally{
	            dataObject = (List<Object>) in.readObject();
				in.close();				
			}
            
        return dataObject;
    }
    /**
     * Save data in archive
     *
     * @param file
     * @return
     */
    public boolean saveData(File file) throws Exception{
    	FileOutputStream out = null;
        ObjectOutputStream outObj = null;
        try {
            try { 
            	out = new FileOutputStream(file);
            	outObj = new ObjectOutputStream(out);
                //out = openFileOutput("contacts.dat",MODE_APPEND);
            } catch (FileNotFoundException e) {
            	System.err.println(e.getMessage());
                return false;
            }
            //out.write(buffer);
            outObj.writeObject(contacts);
            out.close();
            outObj.close();
        } catch (Exception e) {
            throw new Exception("Erro ao salvar os dados");
        }
        return true;
    }
    
    public List<Contato> getContacts(){
    	return contacts;
    }
    
    public void setContact(Contato cont){
    	contacts.add(cont);
    	
    }
}
