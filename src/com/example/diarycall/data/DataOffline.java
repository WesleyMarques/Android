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
import java.util.Map;

import android.R.bool;

import com.example.diarycall.codes.Contato;
import com.example.diarycall.codes.Menssagem;

/**
 * @author Wesley
 *
 */
public class DataOffline {

    private List<Contato> contacts;
    private List<Menssagem> messages;
    
    public DataOffline() {
		contacts = new ArrayList<Contato>();
		messages = new ArrayList<Menssagem>();
	}
    
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
     * Load users from file
     *
     * @return Returns a users list.
     * @throws Exception 
     *
     *
     *
     */
    public List<Menssagem> loadMessage(File file) throws Exception {
        List<Object> listMsgs = new ArrayList<Object>();
        messages = new ArrayList<Menssagem>();
        listMsgs = readData(file);
        // Downcast da lista de usuários do tipo Object para User
        if (listMsgs != null) {
            for (Object object : listMsgs) {
                messages.add((Menssagem) object);
            }
        }
        return messages;
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
			}
        	if (in != null) {
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
    public boolean saveData(File file, Object obj) throws Exception{
    	FileOutputStream out = null;
        ObjectOutputStream outObj = null;
        try {
            try { 
            	out = new FileOutputStream(file);
            	outObj = new ObjectOutputStream(out);
            } catch (FileNotFoundException e) {
            	System.err.println(e.getMessage());
                return false;
            }
            outObj.writeObject(obj);
        } catch (Exception e) {
            throw new Exception("Erro ao salvar os dados");
        }finally{
        	out.close();
            outObj.close();
        }
        return true;
    }
    
    public List<Contato> getContacts(){
    	return contacts;
    }
    
    public List<Menssagem> getMessages(){
    	return messages;
    }
    
    public void setContact(Contato cont){
    	contacts.add(cont);    	
    }
    
    public void setMessage(Menssagem msg){
    	messages.add(msg);    	
    }
    public boolean removeByNumber(String number){
    	int i = 0;
    	for (Contato contato : contacts) {
    		if (contato.getPhoneNumber().equals(number)) {
    			contacts.remove(i);
    			return true;
			}
    		i++;
		}
    	return false;
    }
    
    public boolean editByOldNumber(File file,String num, Contato newCont) throws Exception{
    	for (Contato contato : contacts) {
    		if (contato.getPhoneNumber().equals(num)) {
    			contato.setNome(newCont.getNome());
    			contato.setPhoneNumber(newCont.getPhoneNumber());
    			saveData(file, contacts);
    			return true;
			}			
		}    	
    	return false;
    }
}
