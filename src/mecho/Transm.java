/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mecho;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.net.MulticastSocket;

/**
 *
 * @author Akiel
 */
public class Transm {

    public static void sendText(String text) {
        broadcast("tpk|<" + Data.uname + ">: " + text + "\n");
    }

    public static void interrogate() {
        broadcast("ipk");
    }

    public static void sendUser(String user) {
        broadcast("upk|" + user + "\n");
    }

    public static void sendPrivate(String text, String user) {
        broadcast("ppk|" + user + ":*" + Data.uname + ": " + text + "\n");
    }

    public static void broadcast(String s) {
        try {
            InetAddress addr = InetAddress.getByName(Data.mcastaddr);
            MulticastSocket ms = new MulticastSocket(Data.mcastport);
            byte[] buf = s.getBytes();
            DatagramPacket dp = new DatagramPacket(buf, buf.length, addr, 5000);
            ms.setTimeToLive(Data.mcastttl);
            ms.send(dp);
        } catch (UnknownHostException ex) {
            Logger.getLogger(Interrogator.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Interrogator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
