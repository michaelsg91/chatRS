package cliente;
import java.awt.BorderLayout;

import javax.swing.*;

public class jframe extends JFrame{
	jpanelChat jpc;
	jpanelNick jpn;
	public jframe(){
		jpc=new jpanelChat();
		jpn=new jpanelNick();
		
		setTitle("ChatRS");
		setResizable(false);		
		add(jpc);
		pack();
		add(jpn);
		pack();
		setLocationRelativeTo(null);
				
		jpc.caja.requestFocus();
		jpn.ok.addActionListener(new accionBotonOk(this));
		
		jpc.setVisible(false);
		jpn.setVisible(true);
		
	}
	
}
