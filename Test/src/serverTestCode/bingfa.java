package serverTestCode;

import java.io.DataOutputStream;
import java.net.Socket;
import java.util.concurrent.CountDownLatch;

import util.userReg;

public class bingfa {

	private static Socket socket;
	public static userReg ben = new userReg();

	// 模拟的并发量
	private static final int BINGFA = 2000;

	private static CountDownLatch cdl = new CountDownLatch(BINGFA);

	public static void main(String[] args) {
		for (int i = 0; i < BINGFA; i++) {
			new Thread(new UserRequest()).start();
			System.out.println(i);
			cdl.countDown();
		}
		System.out.println("fale");
	}

	public static class UserRequest implements Runnable {
		@Override
		public void run() {
			try {
				cdl.await();
				socket = new Socket("192.168.5.70", 5899);
				// BufferedReader in = new BufferedReader(new
				// InputStreamReader(socket.getInputStream()));
				DataOutputStream out = new DataOutputStream(socket.getOutputStream());
				out.write(getByte());
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	// 发送的请求参数
	public static byte[] getByte() {
		return ben.getUserRegBen();
	}
}
