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
	public static final String IP_ADDR = "120.79.137.160";// ��������ַ
	public static final int PORT = 5898;// �������˿ں�

	public ClientMainTe() {
		try {
			// �����������ͨѶ��Socket���󣬲���Ϊ������IP��ַ��String���Ͷ˿ںţ�int�����˿ں���Ҫ�ͷ������˿��ŵĶ˿ںŶ�Ӧ
			Socket s = new Socket(IP_ADDR, PORT);
			// ����һ���߳��������ͨѶ���������ӷ�������Socket���󴫵ݹ�ȥ
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
 * �������ͨѶ���߳�
 */
class TheadLong extends Thread {
	private Socket s = null;
	// �����
	private PrintStream out = null;
	// ����������
	private BufferedReader in = null;
	// ¼�����ֵ�Scanner����
	private Scanner scanner = null;

	public TheadLong(Socket s) {
		// ��Socket����ʵ��������ȫ�ֱ����У���Ϊrun���������ǻ�Ҫ�����Ͽ�����
		this.s = s;
		try {
			// ��Socket�л�ȡ�����������������������ֻ��һ���򵥵��ַ���ͨѶ�����Բ���BufferedRead��PrintStream����װ���롢�����
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
					System.out.println("���������أ�" + str);
				} else {
					System.out.println("���λỰ������");
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