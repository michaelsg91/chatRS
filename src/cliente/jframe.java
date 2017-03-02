package cliente;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;


public class jframe extends JFrame implements Runnable{
	public jpanelChat jpc=new jpanelChat();;
	public jpanelNick jpn=new jpanelNick();;
	private Thread hilo;
	public jframe(){
		hilo=new Thread(this);
				
		setTitle("ChatRS");
		setResizable(false);		
		add(jpc);
		pack();
		add(jpn);
		pack();
		setLocationRelativeTo(null);
				
		
		jpn.ok.addActionListener(new accionBotonOk(this));
		jpc.enviar.addActionListener(new accionBotonEnviar(this));// Event to button
				
		jpc.setVisible(false);	
		
		hilo.start();
	}
	
	public void run(){
		//--- Receiving packets --------------------------------------------------------------------
				try{
					ServerSocket socketRecibir=new ServerSocket(9090);
					Socket chatRecibe;
					InetAddress localizacion;					
					String IpRemota;
						
					paqueteEnvio paqueteRecibido;
					
					while(true){
						chatRecibe=socketRecibir.accept();
						
						localizacion=chatRecibe.getLocalAddress();						
						IpRemota=localizacion.getHostAddress();				
						
						
						ObjectInputStream datosEntrada=new ObjectInputStream(chatRecibe.getInputStream());
						
						paqueteRecibido=(paqueteEnvio)datosEntrada.readObject();
						
						if(paqueteRecibido.getTipoMensaje().equals("authorized")){
							
							this.jpc.setVisible(true);
							this.jpn.setVisible(false);
							//--- Receiving IPs --------------------------------------------
							HashMap<String,String> IpsMenu=new HashMap<String,String>();
							
							IpsMenu=paqueteRecibido.getIps();
							
							jpc.ip.removeAllItems();					
							
							for(Map.Entry<String, String> z: IpsMenu.entrySet()){
								if(!IpRemota.equals(z.getKey())){
									jpc.ip.addItem(z.getValue());
								}
							}
							this.jpc.caja.requestFocus();
							//--------------------------------------------------------------
						}else if(paqueteRecibido.getTipoMensaje().equals("unauthorized")){ 
							this.jpc.setVisible(false);
							this.jpn.menError.setText("El usario ya está en uso. Intenta con otro.");
							this.jpn.setVisible(true);
						}else if(paqueteRecibido.getTipoMensaje().equals("mensaje")){
							this.jpc.setVisible(true);
							this.jpn.setVisible(false);
							jpc.area.append(paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje() + "\n"); //Message
						}					
						
					}
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
	}
	
}
