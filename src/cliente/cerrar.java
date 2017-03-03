package cliente;
import java.awt.event.*;
import java.io.*;
import java.net.*;

import javax.swing.JOptionPane;

public class cerrar extends WindowAdapter{
	
	public void windowClosing(WindowEvent e){
		try{
			Socket socketCerrar=new Socket("192.168.1.1",9999);
			paqueteEnvio datos=new paqueteEnvio();
			
			datos.setTipoMensaje("offline");
			
			ObjectOutputStream paqueteDatos=new ObjectOutputStream(socketCerrar.getOutputStream());
			
			paqueteDatos.writeObject(datos);
			
			socketCerrar.close();
			
		}catch(Exception el){
			JOptionPane.showMessageDialog(null, "error ventana");
		}
	}
}
