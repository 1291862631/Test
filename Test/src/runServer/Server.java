package runServer;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

public class Server {
	public static final int PORT = 8080;// 监听的端口号

	public static void main(String[] args) {
		Server server = new Server();
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

	public class HandlerThread implements Runnable {
		private Socket socket;
		Base64.Decoder decoder = Base64.getDecoder();
		String stringText;

		public HandlerThread(Socket client) {
			socket = client;
			new Thread(this).start();
		}

		public void run() {
			try {
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				BufferedReader buff = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				String body = buff.readLine();
				byte[] text = decoder.decode(body.substring(7, body.length()));
				stringText = new String(text);
				buff.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}
}