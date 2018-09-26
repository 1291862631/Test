package serviceSocket;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import java.io.InputStreamReader;

import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class service {
	public static String body;
	public static File file;
	public static Date date;
	public static FileWriter fileWriter;
	public static PrintWriter pint;
	public static Base64.Decoder decoder = Base64.getDecoder();
	public static ServerSocket serverSocket = null;
	public static final int PORT = 8080;

	public static void main(String[] args) throws IOException {

		date = new Date();
		SimpleDateFormat init = new SimpleDateFormat("MM月dd日HH时mm分s秒s");
		file = new File("E:/SocketTes/" + init.format(date).toString() + ".txt");

		BufferedReader in;
		fileWriter = new FileWriter(file, true);
		pint = new PrintWriter(fileWriter);
		pint.println(date);
		init();
	}

	public static void init() {
		try {
			serverSocket = new ServerSocket(PORT);
			while (true) {
				Socket socket = serverSocket.accept();
				new TheadServer(socket);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static class TheadServer implements Runnable {
		private Socket socket;

		public TheadServer(Socket socket) {
			System.out.println("执行一次");
			this.socket = socket;
			new Thread(this).start();
		}

		@Override
		public void run() {
			try {

				BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

				body = in.readLine();
				byte[] text = decoder.decode(body.substring(7, body.length()));
				pint.println(new String(text));
				// DataOutputStream out = new
				// DataOutputStream(socket.getOutputStream());
				// System.out.println("请输入：\t");
				// String outStr = new BufferedReader(new
				// InputStreamReader(System.in)).readLine();
				// out.writeUTF(outStr);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
// in.close();
// pw.flush();
// pw.close();
// socket.shutdownInput();// 关闭输入流
// socket.close();
// serverSocket.close();
