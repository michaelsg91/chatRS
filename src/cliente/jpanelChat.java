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
	public SimpleAttributeSet estilo,estilo2;
	public HashMap<String,JScrollPane> hbarra;
	public HashMap<String,JTextPane> harea;
	public jpanelChat(){
		
		//--- Panel properties --------------------
		setPreferredSize(new Dimension(400,600));
		setLayout(null);
		//-----------------------------------------
			
		//--- Variables initialization ---------------
		ip=new JComboBox();		
		nick=new JLabel();
		caja=new JTextField();
		
		nnick=new JLabel("Nick: ");
		online=new JLabel("Online: ");
		enviar=new JButton("Enviar");
		
		hbarra=new HashMap<String,JScrollPane>();
		harea=new HashMap<String,JTextPane>();
		
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
		
		ip.addItem("Chat Grupal");

		caja.addKeyListener(new teclaEnviar(this));
		
		creaArea("Chat Grupal");
		
		add(caja);add(enviar);add(nick);add(nnick);add(online);add(ip);
		
	}
	
	public void creaArea(String ip){
		
		
		area=new JTextPane();		
		barra=new JScrollPane(area);		
		area.setEditable(false);
		
		barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barra.setHorizontalScrollBar(null);	
		
		
		barra.setBounds(5,35,390,520);
		
		hbarra.put(ip, barra);
		harea.put(ip, area);
		
		barra.setVisible(false);
		
		this.add(barra);
		
	}
}
