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
				
		
		jpn.ok.addActionListener(new accionBotonOk(this));// Action when you click the button
		jpc.enviar.addActionListener(new accionBotonEnviar(this));// Action when you click the button
				
		jpc.setVisible(false);	
		
		addWindowListener(new cerrar());
		
		hilo.start();
		
	}
	
	public void run(){
		//--- Receiving packets --------------------------------------------------------------------
				try{
					ServerSocket socketRecibir=new ServerSocket(9090);
					Socket chatRecibe;
					InetAddress localizacion;					
					String IpRemota;
					HashMap<String,String> IpsMenu;
					
					paqueteEnvio paqueteRecibido;
					
					while(true){   // Infinite function
						chatRecibe=socketRecibir.accept();
						
						localizacion=chatRecibe.getLocalAddress();						
						IpRemota=localizacion.getHostAddress();				
						
						
						ObjectInputStream datosEntrada=new ObjectInputStream(chatRecibe.getInputStream());
						
						paqueteRecibido=(paqueteEnvio)datosEntrada.readObject();
						
						if(paqueteRecibido.getTipoMensaje().equals("authorized") || paqueteRecibido.getTipoMensaje().equals("offline")){
							
							this.jpc.setVisible(true);
							this.jpn.setVisible(false);
							//--- Receiving IPs --------------------------------------------
							IpsMenu=new HashMap<String,String>();
							
							IpsMenu=paqueteRecibido.getIps();
							
							jpc.ip.removeAllItems();
							
							//--- Add and remove Ips ---------------------------------------
							for(Map.Entry<String, String> z: IpsMenu.entrySet()){
								if(!IpRemota.equals(z.getKey())){
									jpc.ip.addItem(z.getValue());
								}
							}
							//---------------------------------------------------
							
							if(jpc.ip.getSelectedItem()==null){
								jpc.enviar.setEnabled(false);
							}else{
								jpc.enviar.setEnabled(true);
							}
							
							this.jpc.caja.requestFocus();
							//--------------------------------------------------------------
							
						}else if(paqueteRecibido.getTipoMensaje().equals("unauthorized")){ 
							this.jpc.setVisible(false);
							this.jpn.menError.setText("El usario ya está en uso. Intenta con otro.");
							this.jpn.setVisible(true);
						}else if(paqueteRecibido.getTipoMensaje().equals("mensaje")){
																				
							jpc.styleDoc.insertString(jpc.styleDoc.getLength(),paqueteRecibido.getNick() + ": " + paqueteRecibido.getMensaje() + "\n",null); //Message
							
							//--- Automatic scrolling down -------------
							Dimension tamTextPane=jpc.area.getSize();
							Point p=new Point(0,tamTextPane.height);
							jpc.barra.getViewport().setViewPosition(p);
							//------------------------------------------
							
						}					
						
					}// End infinite while
					
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
	}
	
}
