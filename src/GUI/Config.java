/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import mecho.Data;

/**
 *
 * @author Akiel
 */
public class Config {

    public static Properties props = new Properties();

    public static void cargar() throws FileNotFoundException, IOException {
        props.load(new FileInputStream("configuracion.conf"));
        Data.uname = props.getProperty("Nombre");
        Data.mcastaddr = props.getProperty("Direccion Multicast");
        Data.mcastport = Integer.valueOf(props.getProperty("Puerto Multicast"));
        Data.mcastttl = Integer.valueOf(props.getProperty("TTL Multicast"));
        Data.sounds = Boolean.valueOf(props.getProperty("Sounds"));
    }

    public static void defaultConf() throws FileNotFoundException, IOException {
        props.setProperty("Nombre", "");
        props.setProperty("Direccion Multicast", "226.10.10.1");
        props.setProperty("Puerto Multicast", "5000");
        props.setProperty("TTL Multicast", "1");
        props.setProperty("Sounds", "true");
        props.store(new FileOutputStream("configuracion.conf"), "");
    }

    public static void setName(String name) throws FileNotFoundException, IOException {
        props.setProperty("Nombre", name);
        Data.uname = name;
        props.store(new FileOutputStream("configuracion.conf"), "");
    }
    
    public static void setSounds(boolean sounds) throws FileNotFoundException, IOException {
        props.setProperty("Sounds",String.valueOf(sounds));
        Data.sounds = sounds;
        props.store(new FileOutputStream("configuracion.conf"), "");
    }
}
