package cliente;

import java.awt.*;
import java.awt.event.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.io.*;

public class accionBotonEnviar implements ActionListener{
	jframe jp;
	public JTextPane area;
	public JScrollPane barra;
	public accionBotonEnviar(jframe jp){
		this.jp=jp;
	}
	public void actionPerformed(ActionEvent e){
		if(!jp.jpc.caja.getText().isEmpty() && jp.IpsMenu.size()>1){
			try{
				String ip=(String)jp.jpc.ip.getSelectedItem();
				
				//--- Get ip -----------------------------------------------	
				if(!ip.equals("Chat Grupal")){
					for(Map.Entry<String, String> z: jp.IpsMenu.entrySet()){
						if(z.getValue().equals(ip)){
							ip=z.getKey();
						}
					}
				}
				
				
				for(Map.Entry<String, JTextPane> z: jp.jpc.harea.entrySet()){
					if(ip.equals(z.getKey())){
						jp.jpc.styleDoc=z.getValue().getStyledDocument();
						area=z.getValue();
					}					
				}
				for(Map.Entry<String, JScrollPane> z: jp.jpc.hbarra.entrySet()){
					if(ip.equals(z.getKey())){
						barra=z.getValue();
					}					
				}
								
				
				jp.jpc.styleDoc.setParagraphAttributes(jp.jpc.styleDoc.getLength(), jp.jpc.caja.getText().length(), jp.jpc.estilo2, false);
					
				jp.jpc.styleDoc.insertString(jp.jpc.styleDoc.getLength(), jp.jpc.caja.getText() + "\n", jp.jpc.estilo2);// Your message		
				
				//--- Automatic scrolling down ----------------
				Dimension tamTextPane=area.getSize();
				Point p=new Point(0,tamTextPane.height);
				barra.getViewport().setViewPosition(p);
				//---------------------------------------------
				
				Socket socketEnviar=new Socket("192.168.1.1",9999);
				
				paqueteEnvio datos=new paqueteEnvio();
				
				datos.setNick(jp.jpc.nick.getText());
				datos.setIp(ip);
				datos.setMensaje(jp.jpc.caja.getText());
				datos.setTipoMensaje("mensaje");
				
				
				ObjectOutputStream paqueteDatos= new ObjectOutputStream(socketEnviar.getOutputStream());
				paqueteDatos.writeObject(datos);
				
				socketEnviar.close();
				
				}catch(UnknownHostException e2){
					e2.printStackTrace();
				}catch(IOException e3){
					e3.printStackTrace();
				}catch(Exception e4){
					
				}
				jp.jpc.caja.setText("");
				
			}
		}
		
}
