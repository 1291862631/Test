package serverTestCode;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

import util.userReg;

public class connectSocket {

	public static Socket sc;
	public static Socket socket;
	public static PrintWriter out;
	public static BufferedReader in;
	public static userReg ben = new userReg();

	public static void main(String[] arg) throws Exception {
		connectA();
	}

	public static void connectA() throws Exception {
		socket = new Socket("192.168.1.121", 8080);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		// out = new PrintWriter(socket.getOutputStream(), true);
		DataOutputStream out = new DataOutputStream(socket.getOutputStream());
		// ��������˷�������
		// out.print(ben.codeData());
		out.write(ben.getUserRegBen());

		// out.writeBytes("����");
		String response = in.readLine();

		in.close();
		out.close();

	}

	public static void connectB() throws Exception {
		Socket client = new Socket();
		client.connect(new InetSocketAddress("localhost", 9999));

		OutputStream ou = client.getOutputStream();
		ou.write("���ǿͻ��˷�������Ϣ".getBytes());
		// flush() ��Ҫ����������������������������շ�
		ou.flush();
		Thread inThread = new Thread() {
			@Override
			public void run() {
				int itemp;
				byte[] rbyte = new byte[1024];
				while (true) {
					try {
						InputStream in = client.getInputStream();
						if (in != null) {
							itemp = in.read(rbyte);
							System.out.println("��ȡ�������Ϣ��С: " + itemp);
							if (itemp > 0) {

								System.out.println("����˴���������Ϣ: " + new String(rbyte));
								// ����һ�η������Ϣ���˳�

								if (client != null) {
									if (in != null) {
										in.close();
									}
									if (ou != null) {
										ou.close();
									}
									client.close();
									return;
								}
							}
						}
					} catch (IOException e) {
						e.printStackTrace();
					}

				}
			}

		};
		inThread.start();
		// ������˷�����Ϣ
	}

	// connect socket service
	public static void connectAC() {
		try {
			// ����socket����
			sc = new Socket("192.168.31.31", 8080);

			DataOutputStream out = new DataOutputStream(sc.getOutputStream());

			DataInputStream in = new DataInputStream(sc.getInputStream());
			out.writeUTF("Socket");
			System.out.println("hserver:" + in.readUTF());
			System.out.println(in.readUTF());
			in.close();
			out.close();
			sc.close();
		} catch (Exception e) {
			System.out.println("success");
		}
	}

	public static void connectD() throws Exception {
		socket = new Socket("192.168.5.70", 5899);
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		for (int i = 0; i < 100; i++) {
			out.println(ben.getUserRegBen());
		}
		in.readLine();
		System.out.println("success");

	}

}
