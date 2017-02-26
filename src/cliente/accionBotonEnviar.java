package cliente;
import java.awt.event.*;
import java.net.*;
import java.io.*;

public class accionBotonEnviar implements ActionListener{
	private jframe chat;
	public accionBotonEnviar(jframe chat){
		this.chat=chat;
	}
	
	//----------------- Event to send data ---------------------------------------------	
	public void actionPerformed(ActionEvent e){
		chat.area.append(chat.nick.getText() + ": " + chat.caja.getText() + "\n");
		
		try{
			Socket socketServer=new Socket("192.168.1.1",9999); //server ip
			
			paqueteEnvio datos=new paqueteEnvio();
			
			datos.setNick(chat.nick.getText());
			datos.setIp(chat.ip.getSelectedItem().toString());
			datos.setMensaje(chat.caja.getText());
			
			ObjectOutputStream datosEnviar=new ObjectOutputStream(socketServer.getOutputStream());
			datosEnviar.writeObject(datos);
			
			socketServer.close();
		}catch(UnknownHostException e1){
			e1.printStackTrace();
		}catch(IOException e2){
			e2.printStackTrace();
		}
	}
}
