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
		// ��������
		int number = 20000;
		// ���ʱ�䣨�룩
		int time = 1;
		long sta = System.currentTimeMillis();
		UserRegConcurrentTwoW(1);
		long now = System.currentTimeMillis();
		System.out.println("��ʱ--" + (now - sta));
		// ��ʱ��ʾ���ұ߾ͱ�ʾ��������
	}

	/**
	 * �û�1.8W 1������ �������� 1s����300�û�
	 * 
	 * interval�����
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
			System.out.println("˯������");
			Thread.sleep(3000);
		}
		executorService.shutdown();
	}

	/**
	 * �û�2W���û�����ѹ������
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
	 * �û�5W���û�ע��ģ�飨�ȶ��ԣ����1��
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