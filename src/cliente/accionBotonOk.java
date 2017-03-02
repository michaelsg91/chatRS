package cliente;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class accionBotonOk implements ActionListener{
	jframe jf;
	public accionBotonOk(jframe jf){
		this.jf=jf;
	}
	
	public void actionPerformed(ActionEvent e){
				
		jf.jpc.nick.setText(jf.jpn.cajaNick.getText());		
		
		
		try{
			Socket miSocket=new Socket("192.168.1.1",9999);
			paqueteEnvio datos=new paqueteEnvio();
			
			datos.setNick(jf.jpc.nick.getText());
			datos.setTipoMensaje("nick");
			
			ObjectOutputStream paqueteDatos=new ObjectOutputStream(miSocket.getOutputStream());
			
			paqueteDatos.writeObject(datos);
			
			miSocket.close();
			
		}catch(Exception el){
			el.printStackTrace();
		}
					
	}
}
