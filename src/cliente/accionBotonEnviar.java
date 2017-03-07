package cliente;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.*;
import java.net.*;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

import java.io.*;

public class accionBotonEnviar implements ActionListener{
	jframe jp;
	public accionBotonEnviar(jframe jp){
		this.jp=jp;
	}
	public void actionPerformed(ActionEvent e){
		
		try{
		
		jp.jpc.styleDoc.setParagraphAttributes(jp.jpc.styleDoc.getLength(), jp.jpc.caja.getText().length(), jp.jpc.estilo2, false);
			
		jp.jpc.styleDoc.insertString(jp.jpc.styleDoc.getLength(), jp.jpc.caja.getText() + "\n", jp.jpc.estilo2);// Your message		
		
		//--- Automatic scrolling down ----------------
		Dimension tamTextPane=jp.jpc.area.getSize();
		Point p=new Point(0,tamTextPane.height);
		jp.jpc.barra.getViewport().setViewPosition(p);
		//---------------------------------------------
		
		Socket socketEnviar=new Socket("192.168.1.1",9999);
		
		paqueteEnvio datos=new paqueteEnvio();
		
		datos.setNick(jp.jpc.nick.getText());
		datos.setIp(jp.jpc.ip.getSelectedItem().toString());
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
