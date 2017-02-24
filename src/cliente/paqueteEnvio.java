package cliente;
import java.io.*;
import java.util.*;

public class paqueteEnvio implements Serializable{
	private String nick,ip,mensaje;
	private ArrayList<String> ips;
	
	//--- Geters and Seters ---------------------
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getMensaje() {
		return mensaje;
	}
	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}
	public ArrayList<String> getIps() {
		return ips;
	}
	public void setIps(ArrayList<String> ips) {
		this.ips = ips;
	}
	
	

}
