/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mecho;


import javax.swing.JOptionPane;

/**
 *
 * @author Akiel
 */
public class Interrogator extends Thread {

    @Override
    public void run() {
        super.run();
        while (true) {
            Data.ulist.clear();
            Transm.interrogate();
            try {
                sleep(2000);
            } catch (InterruptedException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        }
    }
}
