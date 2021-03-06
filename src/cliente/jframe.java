package cliente;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledEditorKit;

public class jframe extends JFrame implements Runnable{
	public jpanelChat jpc=new jpanelChat();;
	public jpanelNick jpn=new jpanelNick();;
	private Thread hilo;
	public JTextPane area;
	public JScrollPane barra;
	public  SimpleAttributeSet estilo3,estilo4;
	public String ipRemota,IpLocal;
	HashMap<String,String> IpsMenu;
	public jframe(){
		hilo=new Thread(this);
				
		setTitle("ChatRS");
		setResizable(false);		
		add(jpc);
		pack();
		add(jpn);
		pack();
		setLocationRelativeTo(null);
				
		jpc.ip.addActionListener(new accionComboIp(this));
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
					
					
					paqueteEnvio paqueteRecibido;
					
					while(true){   // Infinite function
						chatRecibe=socketRecibir.accept();
						
						localizacion=chatRecibe.getLocalAddress();						
						IpLocal=localizacion.getHostAddress();				
						
						
						ObjectInputStream datosEntrada=new ObjectInputStream(chatRecibe.getInputStream());
						
						paqueteRecibido=(paqueteEnvio)datosEntrada.readObject();
						ipRemota=paqueteRecibido.getIp();
						
											
						if(paqueteRecibido.getTipoMensaje().equals("authorized") || paqueteRecibido.getTipoMensaje().equals("offline")){
							
							jpn.setVisible(false);				
							jpc.setVisible(true);
							
							//--- Receiving IPs --------------------------------------------
							IpsMenu=new HashMap<String,String>();
							
							IpsMenu=paqueteRecibido.getIps();
							
							jpc.ip.removeAllItems();
							
							jpc.ip.addItem("Chat Grupal");
							
							//--- Add and remove Ips ---------------------------------------
							for(Map.Entry<String, String> z: IpsMenu.entrySet()){
								if(!IpLocal.equals(z.getKey())){
									jpc.ip.addItem(z.getValue());
									jpc.creaArea(z.getKey());
								}
							}
							//---------------------------------------------------
							
													
							jpc.caja.requestFocus();
							//--------------------------------------------------------------
												
							
						}else if(paqueteRecibido.getTipoMensaje().equals("unauthorized")){ 
							this.jpc.setVisible(false);
							this.jpn.menError.setText("El usario ya está en uso. Intenta con otro.");
							this.jpn.setVisible(true);
						}else if(paqueteRecibido.getTipoMensaje().equals("mensaje")){
													
													
							int tam=paqueteRecibido.getNick().length()+paqueteRecibido.getMensaje().length()+2;
							
														
							for(Map.Entry<String, JTextPane> z: jpc.harea.entrySet()){
								if(ipRemota.equals(z.getKey())){
									area=z.getValue();
									jpc.styleDoc=area.getStyledDocument();									
								}					
							}
							
							for(Map.Entry<String, JScrollPane> z: jpc.hbarra.entrySet()){
								if(ipRemota.equals(z.getKey())){
									barra=z.getValue();
								}					
							}
							
							if(ipRemota.equals("Chat Grupal")){
								for(Map.Entry<String, String> z: IpsMenu.entrySet()){
									if(z.getValue().equals(paqueteRecibido.getNick())){
										ipRemota=z.getKey();
									}
								}
								for(Map.Entry<String, SimpleAttributeSet> z: jpc.estiloC.entrySet()){
									if(z.getKey().equals("Chat Grupal")){
										estilo4=z.getValue();
									}
									if(ipRemota.equals(z.getKey())){
										estilo3=z.getValue();
									}					
								}
								jpc.styleDoc.setParagraphAttributes(jpc.styleDoc.getLength(), paqueteRecibido.getNick().length()+2, estilo3, false);
								jpc.styleDoc.insertString(jpc.styleDoc.getLength(),paqueteRecibido.getNick() + ": ", estilo3);
								
								jpc.styleDoc.setParagraphAttributes(jpc.styleDoc.getLength(), paqueteRecibido.getMensaje().length(), estilo4, false);
								jpc.styleDoc.insertString(jpc.styleDoc.getLength(),paqueteRecibido.getMensaje() + "\n", estilo4);
								
							}else{
								jpc.styleDoc.setParagraphAttributes(jpc.styleDoc.getLength(), tam, jpc.estilo, false);								
								jpc.styleDoc.insertString(jpc.styleDoc.getLength(),paqueteRecibido.getMensaje() + "\n",jpc.estilo);
							}
												
							
							//--- Automatic scrolling down -------------
							Dimension tamTextPane=area.getSize();
							Point p=new Point(0,tamTextPane.height);
							barra.getViewport().setViewPosition(p);
							//------------------------------------------
							
						}					
						
					}// End infinite while
					
				}catch(Exception e){
					System.out.println(e.getMessage());
				}
				
	}
	
}
