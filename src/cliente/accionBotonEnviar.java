package cliente;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class accionBotonEnviar implements ActionListener{
	jpanelChat jp;
	public accionBotonEnviar(jpanelChat jp){
		this.jp=jp;
	}
	public void actionPerformed(ActionEvent e){
		jp.area.append("\n" + jp.nick.getText() + ": " + jp.caja.getText());
		
		try{
			
		Socket socketEnviar=new Socket("192.168.1.1",9999);
		
		paqueteEnvio datos=new paqueteEnvio();
		datos.setNick(jp.nick.getText());
		datos.setIp(jp.ip.getSelectedItem().toString());
		datos.setMensaje(jp.caja.getText());
		
		
		ObjectOutputStream paqueteDatos= new ObjectOutputStream(socketEnviar.getOutputStream());
		paqueteDatos.writeObject(datos);
		
		socketEnviar.close();
		
		}catch(UnknownHostException e2){
			e2.printStackTrace();
		}catch(IOException e3){
			e3.printStackTrace();
		}
		
	}
}
