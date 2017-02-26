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
	private String nick,ip, mensaje;
	private ArrayList<String> ipsOnline;
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
		//------------------------------------------------------
		
		barra.setBounds(5,5,390,590);
		
		add(barra);
	}
	
	public void run(){
		try{
			ServerSocket serverServer=new ServerSocket(9999);
			ipsOnline=new ArrayList<String>();
			
			paqueteEnvio datosRecibidos;
			Socket server,socketServer;
			String ipEntrante;
			
			while(true){
				//------ Receive-Send data -------------------------------------------------- 
				server=serverServer.accept();
				ObjectInputStream datosEntrada=new ObjectInputStream(server.getInputStream());
				
				datosRecibidos=(paqueteEnvio)datosEntrada.readObject();
				
				nick=datosRecibidos.getNick();
				ip=datosRecibidos.getIp();
				mensaje=datosRecibidos.getMensaje();
				
				if(!mensaje.equals("9im0nline9")){
					area.append(nick + ": " + mensaje + ". Para: "+ ip + "\n");
					
					socketServer=new Socket(ip, 9009);
					
					ObjectOutputStream datosEnviar=new ObjectOutputStream(socketServer.getOutputStream());
					
					datosEnviar.writeObject(datosRecibidos);
					
					socketServer.close();
					server.close();
				//---------------------------------------------------------------------------	
				}else{
					//------ Detect online ----------------------------
					InetAddress localizacion=server.getInetAddress();
					
					ipEntrante=localizacion.getHostAddress();
					
					area.append("Online: " + ipEntrante);
					
					ipsOnline.add(ipEntrante);
					
					datosRecibidos.setIps(ipsOnline);
					//-------------------------------------------------
					
					//------ Send ips ---------------------------------
					for(String z: ipsOnline){
						socketServer=new Socket(z,9090);
						
						ObjectOutputStream datosEnviar=new ObjectOutputStream(socketServer.getOutputStream());
						
						datosEnviar.writeObject(datosRecibidos);
						
						socketServer.close();
												
					}
					//-------------------------------------------------
				}
			}
			
			
		}catch(IOException | ClassNotFoundException e){
			e.printStackTrace();
		}
	}
}
