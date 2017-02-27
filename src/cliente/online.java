package cliente;
import java.awt.event.*;
import java.net.*;
import javax.swing.JOptionPane;


import java.io.*;

class online extends WindowAdapter{
	public void windowOpened(WindowEvent e){
		try{
			Socket miSocket=new Socket("192.168.1.1",9999);
			paqueteEnvio datos=new paqueteEnvio();
			
			datos.setMensaje("9im0nline9");
			
			ObjectOutputStream paqueteDatos=new ObjectOutputStream(miSocket.getOutputStream());
			
			paqueteDatos.writeObject(datos);
			
			miSocket.close();
			
		}catch(Exception el){
			
		}
	}
}
