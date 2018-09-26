package runServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import util.userLogin;

public class Client {
	public static final String IP_ADDR = "192.168.1.121";// 服务器地址
	public static final int PORT = 8080;// 服务器端口号

	public static void main(String[] args) {
		System.out.println("客户端启动...");
		Socket socket = null;
		try {
			socket = new Socket(IP_ADDR, PORT);
			userLogin user = new userLogin("");
			DataInputStream in = new DataInputStream(socket.getInputStream());
			DataOutputStream out = new DataOutputStream(socket.getOutputStream());
			out.write(user.getUserLoginBen());

			out.flush();
			out.close();
			// in.close();
			// System.out.println(in.readUTF());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
