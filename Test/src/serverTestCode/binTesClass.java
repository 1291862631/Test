package serverTestCode;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

import util.userReg;

public class binTesClass {
	public static Socket socket;
	public static DataOutputStream out;
	public static userReg ben = new userReg();

	private static int thread_num = 500; // 200个线程可以同时访问
	private static int client_num = 2000; // 模拟2000个客户端访问

	public static void main(String[] args) {

		ExecutorService exec = Executors.newCachedThreadPool();
		final Semaphore semp = new Semaphore(thread_num);
		for (int index = 0; index < client_num; index++) {
			Runnable run = new Runnable() {
				public void run() {
					File file = new File("E:/ServerStressTest/TestConfigClass.txt");
					FileWriter filewire = null;
					try {
						filewire = new FileWriter(file, true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					PrintWriter write = new PrintWriter(filewire);

					try {
						semp.acquire();
						socket = new Socket("192.168.1.121", 8080);
						if (socket.isConnected()) {
							write.println("1");
						}
						out = new DataOutputStream(socket.getOutputStream());
						out.write(ben.getUserRegBen());
						semp.release();
					} catch (Exception e) {
						write.println("0");
					}
					write.flush();
					write.close();
				}
			};
			exec.execute(run);
		}
		System.out.println("结束：");
		exec.shutdown();

	}
}