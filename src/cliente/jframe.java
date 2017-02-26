package cliente;
import javax.swing.*;
import java.awt.*;

public class jframe extends JFrame{
	private JLabel nnick, online;
	public JLabel nick;
	public JTextArea area;
	private JButton enviar;
	public JTextField caja;
	public JComboBox ip;
	private String usuario;
	private JScrollPane barra;
	public jframe(){
		
		//--- Frame properties --------------------
		setTitle("ChatRS");
		setPreferredSize(new Dimension(400,600));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(null);
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
		//----------------------------------------
		
		//--- Initialization of variables -----------------------
		usuario=JOptionPane.showInputDialog("Ingresa tu nick");
		nnick=new JLabel("Nick: ");
		nick=new JLabel(usuario);		
		online=new JLabel("Online: ");
		ip=new JComboBox();
		caja=new JTextField();
		enviar=new JButton("Enviar");
		
		area=new JTextArea(390,520);
		barra=new JScrollPane(area);
		area.setLineWrap(true);
		area.setEditable(false);
		//------------------------------------------------------
		
		//--- Location elements --------------------------------
		nnick.setBounds(5, 5, 40, 20);
		nick.setBounds(45,5,150,20);
		online.setBounds(190,5,60,20);
		ip.setBounds(250,5,140,20);
		
		barra.setBounds(5,35,390,520);
		
		caja.setBounds(5,570,300,20);
		enviar.setBounds(310,570,80,20);
		//-----------------------------------------------------
		
		add(nnick);add(nick);add(online);add(barra);add(ip);add(caja);add(enviar);
		
	}
}
