package practice;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketTimeoutException;
import java.util.Base64;
import java.util.Date;
import java.util.Timer;
import util.config;
import util.login;
import util.userLogin;

/**
 * 用户登录接口测试
 * 
 * @author yangh·
 *
 */
public class ouput implements Runnable {
	userLogin ben;
	PrintWriter loginWrite;
	String phone;
	Date date;
	Thread thread;// 循环发送心跳包的线程
	Base64.Decoder decoder = Base64.getDecoder();
	Timer timer = new Timer();

	OutputStream outputStream;
	Socket socket;
	login log;

	public void run() {
		try {
			ben = new userLogin("13405969308");
			Socket socket = new Socket();
			SocketAddress endpoint = new InetSocketAddress(config.IP_ADDR, config.PORT);
			socket.connect(endpoint);
			socket.setKeepAlive(true);
			Thread.sleep(5000);
			OutputStream os = socket.getOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(os);
			oos.writeObject(ben.getUserLoginBen());
			oos.flush();
		} catch (SocketTimeoutException | NullPointerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
