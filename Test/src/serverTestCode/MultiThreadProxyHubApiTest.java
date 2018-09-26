package serverTestCode;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.NumberFormat;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.userReg;

public class MultiThreadProxyHubApiTest {
	static int count = 0;
	// 并发量是thread_num
	int thread_num = 200;
	// 总访问量是client_num
	int client_num = 2000;

	// 线程数:
	// 执行时间:
	// 总执行时间:
	// 吞吐量:
	float avg_exec_time = 0;
	float sum_exec_time = 0;
	long first_exec_time = Long.MAX_VALUE;
	long last_done_time = Long.MIN_VALUE;
	float total_exec_time = 0;

	private static Socket socket;

	userReg ben = new userReg();

	public void run() {

		final MultiThreadProxyHubApiTest currentObj = this;

		final ConcurrentHashMap<Integer, ThreadRecord> records = new ConcurrentHashMap<Integer, ThreadRecord>();

		// 建立ExecutorService线程池
		ExecutorService exec = Executors.newFixedThreadPool(thread_num);
		// thread_num个线程可以同时访问
		// 模拟client_num个客户端访问

		final CountDownLatch doneSignal = new CountDownLatch(client_num);

		for (int i = 0; i < client_num; i++) {

			Runnable run = new Runnable() {

				public void run() {
					File file = new File("E:/ServerStressTest/TestConfig.txt");
					// File file = new File("E:\\Test5.txt");
					FileWriter filewire = null;
					try {
						filewire = new FileWriter(file, true);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					PrintWriter write = new PrintWriter(filewire);
					int index = getIndex();
					long st = System.currentTimeMillis();
					try {
						socket = new Socket("192.168.1.121", 8080);
						// BufferedReader in = new BufferedReader(new
						// InputStreamReader(socket.getInputStream()));
						if (socket.isConnected()) {
							write.println("1");
						}

						DataOutputStream out = new DataOutputStream(socket.getOutputStream());
						out.write(ben.getUserRegBen());
						out.flush();
						out.close();
						socket.close();
					} catch (Exception e) {
						write.println("0");
					}
					try {
						filewire.flush();
						filewire.close();
					} catch (IOException e) {
						e.printStackTrace();
					}

					write.flush();
					write.close();

					records.put(index, new ThreadRecord(st, System.currentTimeMillis()));
					doneSignal.countDown();// 每调用一次countDown()方法，计数器减1
				}
			};
			exec.execute(run);
		}

		try {
			// 计数器大于0 时，await()方法会阻塞程序继续执行
			doneSignal.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		/**
		 * 获取每个线程的开始时间和结束时间
		 */
		for (int i : records.keySet()) {
			ThreadRecord r = records.get(i);
			sum_exec_time += ((double) (r.et - r.st)) / 1000;

			if (r.st < first_exec_time) {
				first_exec_time = r.st;
			}
			if (r.et > last_done_time) {
				this.last_done_time = r.et;
			}
		}

		this.avg_exec_time = this.sum_exec_time / records.size();
		this.total_exec_time = ((float) (this.last_done_time - this.first_exec_time)) / 1000;
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(4);

		System.out.println("======================================================");
		System.out.println("线程数: " + thread_num + "-- 客户计数: " + client_num + ".");
		System.out.println("执行时间:   " + nf.format(this.avg_exec_time) + " s");
		System.out.println("总执行时间: " + nf.format(this.total_exec_time) + " s");
		System.out.println("吞吐量:      " + nf.format(this.client_num / this.total_exec_time) + " /s");
	}

	public static void main(String[] args) {

		new MultiThreadProxyHubApiTest().run();

		System.out.println("finished!");
	}

	public static int getIndex() {
		return ++count;
	}

	class ThreadRecord {
		long st;
		long et;

		public ThreadRecord(long st, long et) {
			this.st = st;
			this.et = et;
		}
	}
}
