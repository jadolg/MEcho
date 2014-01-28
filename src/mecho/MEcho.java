/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mecho;

/**
 *
 * @author Akiel
 */
public class MEcho {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Data.uname = "jorge";
        new Sniffer().start();
        new Interrogator().start();

    }
}
