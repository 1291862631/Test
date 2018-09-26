package runServer;

import java.util.concurrent.CountDownLatch;

public class CountRunnable implements Runnable {

	private CountDownLatch countDownLatch;

	public CountRunnable(CountDownLatch countDownLatch) {

		this.countDownLatch = countDownLatch;

	}

	public void run() {

		try {
			synchronized (countDownLatch) {
				countDownLatch.countDown();

				System.out.println("thread counts = " + (countDownLatch.getCount()));
			}
			countDownLatch.await();
			Thread.sleep(5000);
			System.out.println("5秒后---开始并发");

		} catch (InterruptedException e) {

			e.printStackTrace();

		}

	}
}