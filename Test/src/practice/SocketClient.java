package practice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

import util.config;
import util.userLogin;

public class SocketClient {
	// 搭建客户端
	public static void main(String[] args) throws IOException {
		try {

			Socket socket = new Socket("192.168.31.88", 8080);
			// Socket socket = new Socket("app.evecca.com", 5899);
			System.out.println("客户端启动成功");

			OutputStream write = socket.getOutputStream();

			BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			String readline;
			userLogin ben = new userLogin("13405969308");
			while (true) {

				write.write(ben.getUserLoginBen());
				write.flush();
				// System.out.println(socket.isOutputShutdown());
				socket.shutdownOutput();
				System.out.println("Server:" + in.readLine());

			}

		} catch (Exception e) {
			System.out.println("can not listen to:" + e);// 出错，打印出错信息
		}
	}

}