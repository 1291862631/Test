package serviceSocket;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import util.userLogin;

public class ClientMainTe {
	public static final String IP_ADDR = "120.79.137.160";// 服务器地址
	public static final int PORT = 5898;// 服务器端口号

	public ClientMainTe() {
		try {
			// 构造与服务器通讯的Socket对象，参数为服务器IP地址（String）和端口号（int），端口号需要和服务器端开放的端口号对应
			Socket s = new Socket(IP_ADDR, PORT);
			// 启动一个线程与服务器通讯，并把链接服务器的Socket对象传递过去
			new TheadLong(s).start();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new ClientMainTe();
	}

}

/**
 * 与服务器通讯的线程
 */
class TheadLong extends Thread {
	private Socket s = null;
	// 输出流
	private PrintStream out = null;
	// 缓冲输入流
	private BufferedReader in = null;
	// 录入文字的Scanner对象
	private Scanner scanner = null;

	public TheadLong(Socket s) {
		// 将Socket对象实例保存在全局变量中，因为run方法中我们还要用它断开链接
		this.s = s;
		try {
			// 从Socket中获取输入流和输出流，由于我们只做一个简单的字符串通讯，所以采用BufferedRead和PrintStream来封装输入、输出流
			out = new PrintStream(s.getOutputStream());
			in = new BufferedReader(new InputStreamReader(s.getInputStream()));
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void run() {
		userLogin user = new userLogin("13405969308");

		try {
			while (true) {
				out.println(user.getUserLoginBen().toString());
				out.flush();
				String str = in.readLine();
				if (str != null) {
					System.out.println("服务器返回：" + str);
				} else {
					System.out.println("本次会话结束！");
					return;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {

			try {

				if (!s.isClosed()) {

					s.close();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

}