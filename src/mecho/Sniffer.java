/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mecho;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Akiel
 */
public class Sniffer extends Thread {

    @Override
    public synchronized void run() {
        try {
            super.run();

            InetAddress addr = InetAddress.getByName(Data.mcastaddr);
            MulticastSocket ms = new MulticastSocket(Data.mcastport);
            ms.joinGroup(addr);
            byte[] buf = new byte[1024 * 3];
            while (true) {
                try {
                    DatagramPacket dp = new DatagramPacket(buf, buf.length);
                    ms.receive(dp);
                    String s = new String(dp.getData(), "8859_1");
                    if (s.startsWith("ipk")) {
                        //interrogation packet arrived
                        Transm.sendUser(Data.uname);
                    } else if (s.startsWith("upk|")) {
                        //new user arrives
                        String nusr = s.substring(s.indexOf("|") + 1, s.indexOf("\n"));
                        if (!Data.ulist.contains(nusr)) {
                            Data.ulist.add(nusr);
                        }
                    } else if (s.startsWith("tpk|")) {
                        String astr = s.substring(s.indexOf("|") + 1, s.indexOf("\n"));
                        Data.textBuf.add(astr);
                    } else if (s.startsWith("ppk|")) {
                        String user = s.substring(s.indexOf("|") + 1, s.indexOf(":"));
                        if (user.equals(Data.uname)) {
                            String texto = s.substring(s.indexOf(":") + 1, s.indexOf("\n"));
                            Data.textBuf.add(texto);
                        }
                    }
                } catch (IOException ex) {
                    Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        } catch (IOException ex) {
            Logger.getLogger(Sniffer.class.getName()).log(Level.SEVERE, null, ex);
        }


    }
}
