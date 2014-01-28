/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package aSysTray;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

/**
 *
 * @author jaorozco
 */
public class TraySystem {

    private static TrayIcon trayIcon;
    private static SystemTray tray = null;
    private static JFrame owner;
    public static boolean ontry = false;

    public static void message(String head, String message) {
        if (ontry) {
            trayIcon.displayMessage(head, message, TrayIcon.MessageType.INFO);
        }
    }

    private static TrayIcon getTrayIcon(Image i, String t, PopupMenu m) {
        if (trayIcon == null) {
            trayIcon = new TrayIcon(i, t, m);
        }
        return trayIcon;
    }

    public TraySystem(JFrame owner) {
        TraySystem.owner = owner;
    }

    public void active(Image a) {
        if (SystemTray.isSupported()) {
            tray = SystemTray.getSystemTray();
            Image image = a;
            ActionListener exitListener = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            };

            ActionListener restoreListener = new ActionListener() {

                public void actionPerformed(ActionEvent e) {
                    ontry = false;
                    tray.remove(trayIcon);
                    owner.setVisible(true);
                    owner.setExtendedState(JFrame.NORMAL);
                }
            };


            PopupMenu popup = new PopupMenu();
            MenuItem defaultItem1 = new MenuItem("Mostrar chat");
            MenuItem defaultItem = new MenuItem("Salir");
            defaultItem.addActionListener(exitListener);
            defaultItem1.addActionListener(restoreListener);
            popup.add(defaultItem1);
            popup.add(defaultItem);
            try {
                getTrayIcon(image, "Mecho v1.0", popup);
                trayIcon.setImageAutoSize(true);
                trayIcon.addActionListener(restoreListener);
                tray.add(trayIcon);
                owner.dispose();
                ontry = true;
//                trayIcon.displayMessage("AT v1.2",
//                        "Agenda 1.2 by $h@",
//                        TrayIcon.MessageType.INFO);

            } catch (AWTException e1) {
                // e1.printStackTrace();
            }
        }
    }
}
