package cliente;
import java.awt.event.*;
import java.net.*;
import javax.swing.JOptionPane;


import java.io.*;

class online extends WindowAdapter{
	jframe jf;
	public online(jframe jf){
		this.jf=jf;
	}
	
	public void windowOpened(WindowEvent e){
		try{
			Socket miSocket=new Socket("192.168.1.1",9999);
			paqueteEnvio datos=new paqueteEnvio();
			
			datos.setMensaje("9im0nline9");
			datos.setNick(jf.jp.nick.getText());
			
			ObjectOutputStream paqueteDatos=new ObjectOutputStream(miSocket.getOutputStream());
			
			paqueteDatos.writeObject(datos);
			
			miSocket.close();
			
		}catch(Exception el){
			el.printStackTrace();
		}
	}
}
