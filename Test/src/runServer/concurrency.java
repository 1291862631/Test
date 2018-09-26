package runServer;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.BindException;
import java.net.ConnectException;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import util.SocketUtil;
import util.userReg;

public class concurrency {
	private static Date time = new Date();
	public static Socket socket;
	public static DataOutputStream out;

	/**
	 * 并发同时进行2000个访问
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		int count = 20;
		CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
		ExecutorService executorService = Executors.newFixedThreadPool(count);
		long now = System.currentTimeMillis();
		for (int i = 0; i < count; i++)
			executorService.execute(new concurrency().new Task(cyclicBarrier, time));
		executorService.shutdown();
		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		long end = System.currentTimeMillis();
		System.out.println("运行结束： " + (end - now) + "(Fms)");
	}

	public class Task implements Runnable {
		private CyclicBarrier cyclicBarrier;
		SimpleDateFormat init;
		File file;
		FileWriter filewire = null;
		PrintWriter write;
		Date date;

		public Task(CyclicBarrier cyclicBarrier, Date date) {
			this.cyclicBarrier = cyclicBarrier;
			this.date = date;

		}

		@Override
		public void run() {
			userReg ben = new userReg();
			init = new SimpleDateFormat("MM月dd日HH时mm分s秒s");
			file = new File("E:/ServerClient/" + init.format(date).toString() + ".txt");

			try {
				filewire = new FileWriter(file, true);
				write = new PrintWriter(filewire);
				// 等待所有任务准备就绪
				cyclicBarrier.await();
				// socket = new Socket("192.168.31.88", 8080);
				// socket = new Socket("192.168.5.70", 5899);
				try {
					socket = new Socket("app.evecca.com", 5899);
				} catch (BindException e) {
					write.println("占用");
				}

				if (socket.isConnected()) {
					write.println("成功");
				}
				SocketUtil.Send(new String(ben.getUserRegBen()), socket);
				String respstr = SocketUtil.AcceptBase64(socket);
				System.out.println(respstr);
				// out = new DataOutputStream(socket.getOutputStream());
				// out.write(data);
				// out.flush();
				// out.close();
				// socket.close();
			} catch (ConnectException e) {
				write.println("超时");
			} catch (SocketException e) {
				write.println("被踢");
				// TODO: handle exception
			} catch (InterruptedException e) {
				System.out.println("InterruptedException");
			} catch (BrokenBarrierException e) {
				System.out.println("BrokenBarrierException");
			} catch (UnknownHostException e) {
				System.out.println("UnknownHostException");
			} catch (IOException e) {
				System.out.println("IOException");
			} catch (Exception e) {
				System.out.println("Exception");
			}
			try {
				filewire.flush();
				filewire.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			write.flush();
			write.close();
		}
	}
}