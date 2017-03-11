package cliente;
import java.awt.event.*;
import java.util.Map;

import javax.swing.*;

public class accionComboIp implements ActionListener{
	jframe jf;
	public JScrollPane barra;
	public JTextPane area;
	public accionComboIp(jframe jf){
		this.jf=jf;
	}
	
	public void actionPerformed(ActionEvent e){
		if(jf.jpc.ip.getSelectedItem()!=null){
			
		
		
		String ip=(String)jf.jpc.ip.getSelectedItem();
		
		if(!ip.equals("Chat Grupal")){
			for(Map.Entry<String, String> z: jf.IpsMenu.entrySet()){
				if(z.getValue().equals(ip)){
					ip=z.getKey();
				}
			}
		}
		
		
		for(Map.Entry<String, JScrollPane> z: jf.jpc.hbarra.entrySet()){
			if(ip.equals(z.getKey())){
				barra=z.getValue();
				barra.setVisible(true);
			}else{
				barra=z.getValue();
				barra.setVisible(false);
			}
		}
		
		for(Map.Entry<String, JTextPane> z: jf.jpc.harea.entrySet()){
			if(ip.equals(z.getKey())){
				area=z.getValue();
				jf.jpc.styleDoc=area.getStyledDocument();
				
			}					
		}
	}
		jf.jpc.caja.requestFocus();
		
	}

}
