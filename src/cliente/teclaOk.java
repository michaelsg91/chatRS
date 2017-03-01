package cliente;
import java.awt.event.*;

public class teclaOk extends KeyAdapter{
	private jpanelNick jp;
	public teclaOk(jpanelNick jp){
		this.jp=jp;
	}
	
	public void keyPressed(KeyEvent e){
		if(e.getKeyCode()==KeyEvent.VK_ENTER && jp.isFocusable()){
			jp.ok.doClick();
		}
	}
	
}