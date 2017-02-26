package cliente;
import java.awt.event.*;
import java.net.*;
import java.io.*;



public class online extends WindowAdapter{
	public void windowOpened(WindowEvent e){
		//------- Notify to server that it's online ----------------------
		try{
			Socket socketChat=new Socket("192.168.1.1",9999);
			
			paqueteEnvio dato=new paqueteEnvio();
			dato.setMensaje("9imonline9");
			
			ObjectOutputStream datoEnvio=new ObjectOutputStream(socketChat.getOutputStream());
			
			datoEnvio.writeObject(dato);
			
			socketChat.close();
			
			
		}catch(Exception e1){
			e1.printStackTrace();
		}
	}
}
