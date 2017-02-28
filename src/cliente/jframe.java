package cliente;
import java.awt.BorderLayout;

import javax.swing.*;

public class jframe extends JFrame{
	jpanelChat jp;
	public jframe(){
		jp=new jpanelChat();
		
		setTitle("ChatRS");
		setResizable(false);		
		add(jp);
		pack();
		setLocationRelativeTo(null);
		
		addWindowListener(new online());
		
		jp.caja.requestFocus();
		
	}
	
}
