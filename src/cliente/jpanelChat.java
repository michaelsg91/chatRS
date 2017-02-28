package cliente;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class jpanelChat extends JPanel implements Runnable{
	private JLabel nnick, online;
	public JLabel nick;
	public JTextArea area;
	public JButton enviar;	
	public JTextField caja;
	public JComboBox ip;
	private String usuario;
	private JScrollPane barra;
	private Thread hilo;
	public jpanelChat(){
		
		//--- Panel properties --------------------
		setPreferredSize(new Dimension(400,600));
		setLayout(null);
		//-----------------------------------------
			
		//--- Variables initialization ---------------
		usuario=JOptionPane.showInputDialog("Nick: ");
		
		ip=new JComboBox();		
		nick=new JLabel(usuario);
		caja=new JTextField();
		
		nnick=new JLabel("Nick: ");
		online=new JLabel("Online: ");
		enviar=new JButton("Enviar");
		
		area=new JTextArea(400,600);
		barra=new JScrollPane(area);
		area.setLineWrap(true);
		area.setEditable(false);
		
		hilo=new Thread(this);
		//---------------------------------------------
		
		//--- Location elements -------------
		nnick.setBounds(5, 5, 40, 20);
		nick.setBounds(45,5,150,20);
		online.setBounds(190,5,60,20);
		ip.setBounds(250,5,140,20);
		
		barra.setBounds(5,35,390,520);
		
		caja.setBounds(5,570,300,20);
		enviar.setBounds(310,570,80,20);
		//-----------------------------------
		
		enviar.addActionListener(new accionBotonEnviar(this));// Event to button
		
		caja.addKeyListener(new teclaEnviar(this));
		
		add(caja);add(enviar);add(nick);add(nnick);add(online);add(ip);add(barra);
		
		hilo.start();
	}
	
	public void run(){
		try{
			ServerSocket socketRecibir=new ServerSocket(9090);
			Socket chatRecibe;
			
			
			
			paqueteEnvio paqueteRecibido;
			
			while(true){
				chatRecibe=socketRecibir.accept();
				
				InetAddress localizacion=chatRecibe.getLocalAddress();
				
				
				String IpRemota=localizacion.getHostAddress();				
				
				
				ObjectInputStream datosEntrada=new ObjectInputStream(chatRecibe.getInputStream());
				
				paqueteRecibido=(paqueteEnvio)datosEntrada.readObject();
				
				if(!paqueteRecibido.getMensaje().equals("9im0nline9")){
					area.append(paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje() + "\n");
					
				}else{
					ArrayList <String> IpsMenu=new ArrayList<String>();
					
					IpsMenu=paqueteRecibido.getIps();
					
					ip.removeAllItems();
					
					for(String z: IpsMenu){
						if(!IpRemota.equals(z)){
							ip.addItem(z);
						}
					}
				}
				
				
			}
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
	}
}
