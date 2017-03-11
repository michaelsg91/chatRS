package servidor;
import java.awt.*;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.util.*;
import cliente.*;

public class jframe extends JFrame implements Runnable{
	private JTextPane area;
	private JScrollPane barra;
	private StyledDocument styleDoc;
	private Thread hilo;
	public SimpleAttributeSet estilo,estilo2;
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
		
		area=new JTextPane();
		barra=new JScrollPane(area);
		styleDoc=area.getStyledDocument();
		area.setEditable(false);
		barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barra.setHorizontalScrollBar(null);
		
		estilo=new SimpleAttributeSet();
		estilo2=new SimpleAttributeSet();
		
		
		hilo=new Thread(this);
		//------------------------------------------------------
		
		//--- Style Message --------------------------------------------
		StyleConstants.setForeground(estilo, Color.decode("#990024"));
		
		StyleConstants.setForeground(estilo2, Color.decode("#06107A"));			
		
				
		
		barra.setBounds(5,5,390,590);
		
		add(barra);
		hilo.start();
}
	
	public void run(){
		//--- Recivig packets --------------------------------------------------------------------
		try{
		ServerSocket socketRecibir=new ServerSocket(9999);
		String nick, ip, mensaje,tipoMensaje;
		HashMap<String,String> listaIp=new HashMap<String,String>();
		InetAddress localizacion;
		String IpRemota;
		
		paqueteEnvio paqueteRecibido;
		while(true){
			
		Socket serverRecibe=socketRecibir.accept();
		
				
		ObjectInputStream paqueteDatos= new ObjectInputStream(serverRecibe.getInputStream());
		
		paqueteRecibido=(paqueteEnvio)paqueteDatos.readObject();
		
		//--- Read Host address entry --------------------------
		localizacion=serverRecibe.getInetAddress();		
		IpRemota=localizacion.getHostAddress();
		//-----------------------------------------------------
		
		nick=paqueteRecibido.getNick();
		ip=paqueteRecibido.getIp();
		mensaje=paqueteRecibido.getMensaje();
		tipoMensaje=paqueteRecibido.getTipoMensaje();
		
		//-------  Send messages  ------------------------------------
		if(tipoMensaje.equals("mensaje")){
			
			//--- Get ip ------------------------------------------
			for(Map.Entry<String, String> z: listaIp.entrySet()){
				if(z.getValue().equals(ip)){
					ip=z.getKey();
				}
			}
			//-----------------------------------------------------
		
		styleDoc.insertString(styleDoc.getLength(),"De " + IpRemota +": "+ mensaje + ". Para: todos \n",null);
		
		//--- Automatic scrolling down -------------
		Dimension tamTextPane=area.getSize();
		Point p=new Point(0,tamTextPane.height);
		barra.getViewport().setViewPosition(p);
		//------------------------------------------
		
		for(Map.Entry<String, String> z: listaIp.entrySet()){
			if(!IpRemota.equals(z.getKey())){
				Socket socketEnvia=new Socket(z.getKey(), 9090);			
				ObjectOutputStream paqueteReenvio=new ObjectOutputStream(socketEnvia.getOutputStream());			
				paqueteReenvio.writeObject(paqueteRecibido);		
				socketEnvia.close();				
				serverRecibe.close();
			}
			
		}
		
		//------------------------------------------------------------
		
		}else if(tipoMensaje.equals("nick")){
			
			//--- Detect if Nick is Repeat ---------------------------
			boolean b=true;
						
			for(Map.Entry<String, String> z: listaIp.entrySet()){				
				if(nick.equals(z.getValue())){
					b=false;
				}
			}
			//--------------------------------------------------------
			
			if(b){
				styleDoc.insertString(styleDoc.getLength(),"Online: " + IpRemota + "\n",estilo2);
				
				//--- Automatic scrolling down -------------
				Dimension tamTextPane=area.getSize();
				Point p=new Point(0,tamTextPane.height);
				barra.getViewport().setViewPosition(p);
				//------------------------------------------
				
				listaIp.put(IpRemota,nick);
				
				paqueteRecibido.setTipoMensaje("authorized");				
				paqueteRecibido.setIps(listaIp);		
				
				
					//--- Send Ips ------------------------------------------------------------
					for(Map.Entry<String, String> z: listaIp.entrySet()){
					
						Socket socketEnvia=new Socket(z.getKey(), 9090);					
						ObjectOutputStream paqueteReenvio=new ObjectOutputStream(socketEnvia.getOutputStream());					
						paqueteReenvio.writeObject(paqueteRecibido);					
						socketEnvia.close();						
						serverRecibe.close();
					}				
			}else{
				//--- Invalidating session ---------------------
				Socket socketEnvia=new Socket(IpRemota, 9090);				
				ObjectOutputStream paqueteAutorization=new ObjectOutputStream(socketEnvia.getOutputStream());				
				paqueteRecibido.setTipoMensaje("unauthorized");			
				paqueteAutorization.writeObject(paqueteRecibido);			
				socketEnvia.close();				
				serverRecibe.close();
				
			}// End if(b)
						
		}else if(tipoMensaje.equals("offline")){
			
			listaIp.remove(IpRemota); //Remove offline ip
			styleDoc.insertString(styleDoc.getLength(),"Offline: " + IpRemota + "\n", estilo);
			
			//--- Automatic scrolling down -------------
			Dimension tamTextPane=area.getSize();
			Point p=new Point(0,tamTextPane.height);
			barra.getViewport().setViewPosition(p);
			//------------------------------------------
			
			paqueteRecibido.setIps(listaIp);		
			paqueteRecibido.setTipoMensaje("offline");
			
			//--- Send updates Ips -------------------------------
			for(Map.Entry<String, String> z: listaIp.entrySet()){				
				Socket socketEnvia=new Socket(z.getKey(), 9090);			
				ObjectOutputStream paqueteReenvio=new ObjectOutputStream(socketEnvia.getOutputStream());				
				paqueteReenvio.writeObject(paqueteRecibido);			
				socketEnvia.close();				
				serverRecibe.close();
			}
			
		}// End if(offline)
		
		}// End infinite While
		
		}catch(IOException | ClassNotFoundException | BadLocationException e){
			e.printStackTrace();
		}
		
	}// End run()
	
}
