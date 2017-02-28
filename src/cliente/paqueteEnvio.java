package cliente;
import java.io.*;
import java.util.*;

public class paqueteEnvio implements Serializable{
	private String	nick,ip,mensaje;
	private HashMap<String,String> Ips;

	public HashMap<String, String> getIps() {
		return Ips;
	}

	public void setIps(HashMap<String, String> ips) {
		Ips = ips;
	}
	
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
	

}
