/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import aSysTray.TraySystem;
import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JList;
import javax.swing.JTextArea;
import mecho.Data;

/**
 *
 * @author Akiel
 */
public class updater extends Thread {

    private JTextArea texto;
    private JList usuarios;
    private JCheckBoxMenuItem sounds;
    public static String userindex = "";

    public updater(JTextArea texto, JList usuarios, JCheckBoxMenuItem sounds) {
        this.texto = texto;
        this.usuarios = usuarios;
        this.sounds = sounds;
    }

    public void playSound() {
        URL url = ClassLoader.getSystemResource("sound/alert.wav");
        AudioClip clip = Applet.newAudioClip(url);
        clip.play();
    }

    @Override
    public synchronized void run() {
        super.run();
        while (true) {
            DefaultListModel lm = (DefaultListModel) usuarios.getModel();

            LinkedList<String> vieja = new LinkedList<String>();
            for (int i = 1; i < lm.size(); i++) {
                vieja.add((String) lm.getElementAt(i));
            }

            String[] lista = (String[]) Data.ulist.toArray(new String[0]);
            Arrays.sort(lista);

            if (!Arrays.deepEquals(lista, vieja.toArray()) || vieja.isEmpty()) {
                if (usuarios.getSelectedIndex() >= 0) {
                    userindex = (String) usuarios.getModel().getElementAt(usuarios.getSelectedIndex());
                }
                lm.clear();
                int select = -1;
                lm.addElement("#Todos");
                for (int i = 0; i < lista.length; i++) {
                    lm.addElement(lista[i]);
                    if (!userindex.equals("") && lista[i].equals(userindex)) {
                        select = i;
                    }
                }
                usuarios.setSelectedIndex(select);
            }

            if (Data.textBuf.size() > 0) {
                for (int i = 0; i < Data.textBuf.size(); i++) {
                    texto.append(Data.textBuf.get(i) + "\n");
                    TraySystem.message("Mensaje recibido", Data.textBuf.get(i));
                }
                texto.setCaretPosition(texto.getText().trim().length());
                
                if (sounds.isSelected()) {
                    playSound();
                }
            }
            Data.textBuf.clear();
            try {
                sleep(200);
            } catch (InterruptedException ex) {
                Logger.getLogger(updater.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
