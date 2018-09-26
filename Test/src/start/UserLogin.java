package start;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import connectModel.userLoginStable;
import connectModel.userRegConcurrent;
import connectModel.userLoginStable;
import util.userLogin;

/**
 * 数量: 5w 操作场景： 用户登录每秒一个用户
 * 
 * @author yangh
 *
 */
public class UserLogin {
	private static Date time = new Date();
	private static File file = new File("E:/Client/Phone/TestLogin.txt");

	public static void main(String[] args) throws InterruptedException, IOException {
		long sta = System.currentTimeMillis();
		BufferedReader br = new BufferedReader(new FileReader(file));// 构造一个BufferedReader类来读取文件
		// userLoginConcurrent(br);
		userLoginStable(br);
		long now = System.currentTimeMillis();
		System.out.println("耗时--" + (now - sta));
	}

	/**
	 * 用户登陆并发场景
	 * 
	 * @throws IOException
	 */
	public static void userLoginConcurrent(BufferedReader br) throws IOException {
		int i = 0;
		String sphone = null;
		while ((sphone = br.readLine()) != null) {
			i++;
		}
		System.out.println(sphone);
		CyclicBarrier cyclicBarrier = new CyclicBarrier(i);
		ExecutorService executorService = Executors.newFixedThreadPool(i);
		for (int a = 0; a < i; a++)
			executorService.execute(new userLoginStable(sphone, time));

		executorService.shutdown();

		br.close();
	}

	/**
	 * 用户登录稳定性测试场景
	 * 
	 * @throws InterruptedException
	 * @throws IOException
	 */
	public static void userLoginStable(BufferedReader br) throws IOException, InterruptedException {
		String sphone = "15588186404";
		ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
		// while ((sphone = br.readLine()) != null) {
		cachedThreadPool.execute(new userLoginStable(sphone, time));
		// Thread.sleep(1000);
		// }
		br.close();
	}
}
