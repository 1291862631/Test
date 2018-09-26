package socketutil;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import util.SocketUtil;
import util.userLogin;

public class Clientut {
	public static final String IP_ADDR = "192.168.31.88";// 服务器地址
	public static final int PORT = 8080;// 服务器端口号

	public static void main(String[] args) {
		System.out.println("客户端启动...");

		userLogin user = new userLogin("Test");
		try {
			Socket socket = new Socket(IP_ADDR, PORT);

			SocketUtil.Send(new String(user.getUserLoginBen()), socket);
			while (true) {
				String respstr = SocketUtil.Accept(socket);
				if (respstr.equals("Success"))
					socket.close();
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
