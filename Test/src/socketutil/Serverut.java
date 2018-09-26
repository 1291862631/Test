package socketutil;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

import net.sf.json.JSONObject;
import util.SocketUtil;
import util.userLogin;

public class Serverut {
	private static final int PORT = 8080;// 监听的端口号

	public static void main(String[] args) {
		System.out.println("服务器启动...\n");
		Serverut server = new Serverut();
		server.init();

	}

	public void init() {
		try {
			ServerSocket serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket client = serverSocket.accept();
				new HandlerThread(client);
			}
		} catch (Exception e) {
			System.out.println("服务器异常: " + e.getMessage());
		}
	}

	class HandlerThread implements Runnable {
		Socket socket;
		String accept;
		boolean is = true;

		public HandlerThread(Socket client) {
			this.socket = client;
			new Thread(this).start();
		}

		public void run() {
			try {
				while (true) {
					accept = SocketUtil.Accept(socket);
					System.out.println(accept);
					SocketUtil.Send("Success", socket);
					if (accept != null) {
						break;
					}
					// is = isCode(accept);
					// if (true) {
					// System.out.println("suress");
					// SocketUtil.Send("Success", socket);
					// }
					// Thread.sleep(1000);
					// break;
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public boolean isCode(String accept) {
		JSONObject json = JSONObject.fromString(accept);
		if (json.get("Cmd").equals("UserLogin"))
			return false;
		else
			return true;

	}
}