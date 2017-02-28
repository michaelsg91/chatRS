package cliente;
import java.awt.event.*;

public class teclaEnviar extends KeyAdapter{
	private jpanelChat jp;
	public teclaEnviar(jpanelChat jp){
		this.jp=jp;
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER && jp.isFocusable()){
			jp.enviar.doClick();
		}
	}
	
}
