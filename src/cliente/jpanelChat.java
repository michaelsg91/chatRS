package cliente;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.StyledDocument;

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
	public boolean b;
	public StyledDocument styleDoc;
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
		
		area=new JTextPane();		
		barra=new JScrollPane(area);		
		area.setEditable(false);
		styleDoc=area.getStyledDocument();				
		barra.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		barra.setHorizontalScrollBar(null);
		
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
		
		
		
		caja.addKeyListener(new teclaEnviar(this));
		
		add(caja);add(enviar);add(nick);add(nnick);add(online);add(ip);add(barra);
		
	}
}
