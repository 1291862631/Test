package start;

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

import connectModel.userRegConcurrent;
import connectModel.userRegStable;
import connectModel.userLoginStable;
import util.SocketUtil;
import util.userReg;

public class UserRegistrat {
	private static Date time = new Date();
	private static DataOutputStream out;
	private static CyclicBarrier cyclicBarrier;
	private static ExecutorService executorService;

	public static void main(String[] args) throws IOException, InterruptedException {
		// 测试数量
		int number = 20000;
		// 间隔时间（秒）
		int time = 1;
		long sta = System.currentTimeMillis();
		UserRegConcurrentTwoW(1);
		long now = System.currentTimeMillis();
		System.out.println("耗时--" + (now - sta));
		// 耗时显示在右边就表示测试完了
	}

	/**
	 * 用户1.8W 1分终内 并发结束 1s并发300用户
	 * 
	 * interval：间隔
	 * 
	 * @throws InterruptedException
	 */
	public static void UserRegConcurrentInterval() throws InterruptedException {
		int number = 18000;
		executorService = Executors.newFixedThreadPool(number);
		cyclicBarrier = new CyclicBarrier(300);
		Date time = new Date();
		for (int i = 300; i < 18000; i += 300) {
			for (int o = 0; o < 300; o++) {
				executorService.execute(new userRegConcurrent(cyclicBarrier, time));
			}
			System.out.println("睡眠三秒");
			Thread.sleep(3000);
		}
		executorService.shutdown();
	}

	/**
	 * 用户2W个用户并发压力测试
	 */
	public static void UserRegConcurrentTwoW(int number) {
		cyclicBarrier = new CyclicBarrier(number);
		executorService = Executors.newFixedThreadPool(number);
		Date time = new Date();
		for (int i = 0; i < number; i++)
			executorService.execute(new userRegConcurrent(cyclicBarrier, time));

		executorService.shutdown();
		while (!executorService.isTerminated()) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 用户5W个用户注册模块（稳定性）间隔1秒
	 * 
	 * @throws InterruptedException
	 */
	public static void UserRegStablilityFiveW() throws InterruptedException {
		executorService = Executors.newCachedThreadPool();
		for (int i = 0; i < 50000; i++) {
			executorService.execute(new userRegStable(time));
			Thread.sleep(10);
		}

	}
}