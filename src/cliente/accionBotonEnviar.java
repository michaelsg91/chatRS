package cliente;
import java.awt.event.*;
import java.net.*;
import java.util.Map;

import javax.swing.JOptionPane;

import java.io.*;

public class accionBotonEnviar implements ActionListener{
	jframe jp;
	public accionBotonEnviar(jframe jp){
		this.jp=jp;
	}
	public void actionPerformed(ActionEvent e){
		jp.jpc.area.append("Tú: " + jp.jpc.caja.getText() + "\n");
		
		try{
			
		Socket socketEnviar=new Socket("192.168.1.1",9999);
		
		paqueteEnvio datos=new paqueteEnvio();
		
		datos.setNick(jp.jpc.nick.getText());
		datos.setIp(jp.jpc.ip.getSelectedItem().toString());
		datos.setMensaje(jp.jpc.caja.getText());
		
		
		ObjectOutputStream paqueteDatos= new ObjectOutputStream(socketEnviar.getOutputStream());
		paqueteDatos.writeObject(datos);
		
		socketEnviar.close();
		
		}catch(UnknownHostException e2){
			e2.printStackTrace();
		}catch(IOException e3){
			e3.printStackTrace();
		}
		jp.jpc.caja.setText("");
		
	}
}
