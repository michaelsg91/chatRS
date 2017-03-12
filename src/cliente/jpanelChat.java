package cliente;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;

public class jpanelChat extends JPanel{
	private JLabel nnick, online;
	public JLabel nick;
	public JTextPane area;
	public JButton enviar;	
	public JTextField caja;
	public JComboBox ip;
	public JScrollPane barra;
	public StyledDocument styleDoc;
	public SimpleAttributeSet estilo,estilo2,estilo3;
	public HashMap<String,JScrollPane> hbarra;
	public HashMap<String,JTextPane> harea;
	public HashMap<String,SimpleAttributeSet> estiloC;
	public LinkedList<String> colores; 
	public int con;
	public jpanelChat(){
		
		//--- Panel properties --------------------
		setPreferredSize(new Dimension(400,600));
		setLayout(null);
		//-----------------------------------------
			
		//--- Variables initialization ---------------
		con=0;
		
		ip=new JComboBox();		
		nick=new JLabel();
		caja=new JTextField();
		
		nnick=new JLabel("Nick: ");
		online=new JLabel("Online: ");
		enviar=new JButton("Enviar");
		
		hbarra=new HashMap<String,JScrollPane>();
		harea=new HashMap<String,JTextPane>();
		estiloC=new HashMap<String, SimpleAttributeSet>();
		
		colores=new LinkedList<String>();
		
		estilo=new SimpleAttributeSet();
		estilo2=new SimpleAttributeSet();
		//---------------------------------------------
		
		//--- Location elements -------------
		nnick.setBounds(5, 5, 40, 20);
		nick.setBounds(45,5,150,20);
		online.setBounds(190,5,60,20);
		ip.setBounds(250,5,140,20);
		
		
		
		caja.setBounds(5,570,300,20);
		enviar.setBounds(310,570,80,20);
		
		//--- Style Message --------------------------------------------
		StyleConstants.setForeground(estilo, Color.decode("#990024"));
		StyleConstants.setFontSize(estilo, 14);
		StyleConstants.setBold(estilo, true);
		StyleConstants.setAlignment(estilo, 0);
		
		StyleConstants.setForeground(estilo2, Color.BLACK);			
		StyleConstants.setBold(estilo2, true);
		StyleConstants.setFontSize(estilo2, 14);		
		StyleConstants.setAlignment(estilo2, 2);
		
		
		//--- Save Colors -------------------------------
		colores.add("000000");
		colores.add("990024");
		colores.add("000A91");
		colores.add("00692D");
		colores.add("AFB50D");
		colores.add("666666");
		colores.add("996633");
		colores.add("666699");
		colores.add("660066");
		colores.add("00FF00");
		colores.add("663300");
		colores.add("00CCCC");
		colores.add("FF0099");
		colores.add("FF6600");
		colores.add("336666");
		colores.add("CCFFFF");
		colores.add("660000");
		colores.add("990066");
		colores.add("003333");
		colores.add("9999CC");
				
				
		ip.addItem("Chat Grupal");

		caja.addKeyListener(new teclaEnviar(this));
		
		creaArea("Chat Grupal");
		
		add(caja);add(enviar);add(nick);add(nnick);add(online);add(ip);
		
	}
	
	public void creaArea(String ip){
		
		
		area=new JTextPane();		
		barra=new JScrollPane(area);		
		area.setEditable(false);
		estilo3=new SimpleAttributeSet();
		
		barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barra.setHorizontalScrollBar(null);	
					
		StyleConstants.setBold(estilo3, true);
		StyleConstants.setFontSize(estilo3, 14);		
		StyleConstants.setAlignment(estilo3, 0);
		
		System.out.println(colores.get(con));
		StyleConstants.setForeground(estilo3, Color.decode("#"+ colores.get(con)));
		con++;
		
		barra.setBounds(5,35,390,520);
		
		hbarra.put(ip, barra);
		harea.put(ip, area);
		estiloC.put(ip, estilo3);
		
		barra.setVisible(false);
		
		if(con==20)con=0;
		
		this.add(barra);
		
	}
	
}
