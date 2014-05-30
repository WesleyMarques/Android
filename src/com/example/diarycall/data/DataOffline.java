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
import java.util.ArrayList;
import java.util.List;

import android.R.bool;

import com.example.diarycall.codes.Contato;

/**
 * @author Wesley
 *
 */
public class DataOffline {

    private static List<Contato> contacts;
    
    public static void addContact(Contato cont){
    	contacts.add(cont);
    	
    }

    /**
     * Load users from file
     *
     * @return Returns a users list.
     *
     *
     *
     */
    public static List<Contato> loadContacts() {
        List<Object> listConts;
        listConts = readData("contacts.dat");
        contacts = new ArrayList<Contato>();
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
     */
    private static List<Object> readData(String file) {
        ObjectInputStream in = null;
        List<Object> dataObject = null;
        try {
            try {
                in = new ObjectInputStream(new FileInputStream(file));
                dataObject = (ArrayList<Object>) in.readObject();
            } catch (FileNotFoundException e) {
                System.err.println(e.getMessage());
            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            }
            in.close();
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return dataObject;
    }

    public static boolean saveData(List<Contato> cont, String file){
    	contacts = cont;
    	return saveData(file);
    	
    }
    /**
     * Save data in archive
     *
     * @param file
     * @return
     */
    public static boolean saveData(String file) {
        ObjectOutputStream out = null;
        try {
            try {
                out = new ObjectOutputStream(new FileOutputStream(file));
            } catch (FileNotFoundException e) {
                return false;
            }
            out.writeObject(contacts);
            out.close();
        } catch (Exception e) {
            System.err.println(e);
            System.exit(1);
        }
        return true;
    }
}
