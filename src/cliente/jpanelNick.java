package cliente;
import java.awt.*;
import java.io.ObjectInputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

public class jpanelNick extends JPanel{
	public JLabel ingNick,menError;
	public JTextField cajaNick;
	public JButton ok;
	public jpanelNick(){
		//--- Panel properties --------------------
		setPreferredSize(new Dimension(400,600));
		setLayout(null);
		//-----------------------------------------
		
		//--- Variables Initialization -------------
		ingNick=new JLabel("Ingresa tu nombre de usuario:");
		menError=new JLabel("");
		cajaNick=new JTextField();
		ok=new JButton("OK");
		//------------------------------------------
		
		//--- Location elements -------------
		ingNick.setBounds(50,100,300,30);
		cajaNick.setBounds(150,150,100,30);
		ok.setBounds(170,220,60,30);
		menError.setBounds(50,300,300,30);		
		//-----------------------------------
		
		//--- Properties elements ----------------------
		ingNick.setHorizontalAlignment(JLabel.CENTER);
		menError.setHorizontalAlignment(JLabel.CENTER);
		//----------------------------------------------
		
		cajaNick.addKeyListener(new teclaOk(this));
		add(ingNick);add(cajaNick);add(ok);add(menError);
		
	}
}
