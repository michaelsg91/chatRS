package servidor;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import cliente.*;

public class jframe extends JFrame implements Runnable{
	private JTextArea area;
	private JScrollPane barra;
	private Thread hilo;
	public jframe(){
		//--- Frame properties --------------------
		setTitle("Servidor Chat");
		setPreferredSize(new Dimension(400,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		//----------------------------------------
				
		//--- Initialization of variables -----------------------
		
		area=new JTextArea(390,590);
		barra=new JScrollPane(area);
		area.setLineWrap(true);
		area.setEditable(false);
		
		hilo=new Thread(this);
		//------------------------------------------------------
		
		barra.setBounds(5,5,390,590);
		
		add(barra);
		hilo.start();
}
	
	public void run(){
		//--- Recivig packets --------------------------------------------------------------------
		try{
		ServerSocket socketRecibir=new ServerSocket(9999);
		String nick, ip, mensaje;
		HashMap<String,String> listaIp=new HashMap<String,String>();
		
		paqueteEnvio paqueteRecibido;
		while(true){
		Socket serverRecibe=socketRecibir.accept();
		
				
		ObjectInputStream paqueteDatos= new ObjectInputStream(serverRecibe.getInputStream());
		
		paqueteRecibido=(paqueteEnvio)paqueteDatos.readObject();
		
		nick=paqueteRecibido.getNick();
		ip=paqueteRecibido.getIp();
		mensaje=paqueteRecibido.getMensaje();
		
		//-------  Send messages  ------------------------------------
		if(!mensaje.equals("9im0nline9")){
			
			for(Map.Entry<String, String> z: listaIp.entrySet()){
				if(z.getValue().equals(ip)){
					ip=z.getKey();
				}
			}
			
			
			InetAddress localizacion=serverRecibe.getInetAddress();
			
			String IpRemota=localizacion.getHostAddress();
			
		
		area.append("De " + IpRemota +": "+ mensaje + ". Para: " + ip + "\n");
		
		Socket socketEnvia=new Socket(ip, 9090);
		
		ObjectOutputStream paqueteReenvio=new ObjectOutputStream(socketEnvia.getOutputStream());
		
		paqueteReenvio.writeObject(paqueteRecibido);
		
		socketEnvia.close();
			
		serverRecibe.close();
		//------------------------------------------------------------
		
		}else{
			boolean b=true;
			//------- Detect Online -------------------
			
			InetAddress localizacion=serverRecibe.getInetAddress();
			
			String IpRemota=localizacion.getHostAddress();
			
			for(Map.Entry<String, String> z: listaIp.entrySet()){
				
				if(nick.equals(z.getValue())){
					b=false;
				}
			}
			
			if(b){
				area.append("Online: " + IpRemota + "\n");
				
				listaIp.put(IpRemota,nick);
				
				paqueteRecibido.setIps(listaIp);
				
				
				
					//--- Send Ips ------------------------------------------------------------
					for(Map.Entry<String, String> z: listaIp.entrySet()){
					
						Socket socketEnvia=new Socket(z.getKey(), 9090);
					
						ObjectOutputStream paqueteReenvio=new ObjectOutputStream(socketEnvia.getOutputStream());
					
						paqueteReenvio.writeObject(paqueteRecibido);
					
						socketEnvia.close();
						
						serverRecibe.close();
					}
				//------------------------------------------------------------------------

			}else{
				Socket socketEnvia=new Socket(IpRemota, 9090);
				
				ObjectOutputStream paqueteAutorization=new ObjectOutputStream(socketEnvia.getOutputStream());
				
				paqueteRecibido.setMensaje("9unaut0rized9");
			
				paqueteAutorization.writeObject(paqueteRecibido);
			
				socketEnvia.close();
				
				serverRecibe.close();
		
			}
						
			}
			}
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
	//-----------------------------------------------------------------------------------------------------------
}
