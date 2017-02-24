package servidor;
import java.awt.Dimension;

import javax.swing.*;

public class jframe extends JFrame{
	private JTextArea area;
	private JScrollPane barra;
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
		
		area=new JTextArea(390,590);
		barra=new JScrollPane(area);
		area.setLineWrap(true);
		area.setEditable(false);
		//------------------------------------------------------
		
		barra.setBounds(5,5,390,590);
		
		add(barra);
	}
}
