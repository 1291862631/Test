package runServer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Parallellimit {

	public static void main(String[] args) {

		ExecutorService pool = Executors.newCachedThreadPool();

		CountDownLatch cdl = new CountDownLatch(20000);

		for (int i = 0; i < 20000; i++) {

			pool.execute(new CountRunnable(cdl));

		}

	}

}
