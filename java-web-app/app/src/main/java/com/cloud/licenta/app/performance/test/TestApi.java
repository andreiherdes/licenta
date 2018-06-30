package com.cloud.licenta.app.performance.test;

import org.apache.log4j.Logger;

public class TestApi {

	private final static Logger LOGGER = Logger.getLogger(TestApi.class);

	public static void main(String[] args) throws InterruptedException {

		for (int i = 1; i <= 5000; i++) {
			TestThread T1 = new TestThread("First loop: Thread-" + i);
			if (i % 3 == 0 || i % 5 == 0)
				Thread.sleep(1500);
			T1.start();
		}

		for (int i = 1; i <= 3; i++) {
			TestThread T1 = new TestThread("Second loop: Thread-" + i);
			T1.start();
		}

		for (int i = 1; i <= 10; i++) {
			TestThread T1 = new TestThread("Third loop: Thread-" + i);
			T1.start();
		}

		for (int i = 1; i <= 1000; i++) {
			TestThread T1 = new TestThread("Fourth loop: Thread-" + i);
			if (i % 3 == 0 || i % 5 == 0)
				Thread.sleep(1500);
			T1.start();
		}

		for (int i = 1; i <= 50; i++) {
			TestThread T1 = new TestThread("Fifth loop: Thread-" + i);
			if (i % 3 == 0)
				Thread.sleep(3000);
			T1.start();
		}

		LOGGER.debug("Active threads count: " + Thread.activeCount());

		for (int i = 1; i <= 200; i++) {
			if (i == 100)
				LOGGER.debug("Active threads count: " + Thread.activeCount());
			TestThread T1 = new TestThread("Sixth loop: Thread-" + i);
			T1.start();
		}

		for (int i = 1; i <= 5000; i++) {
			TestThread T1 = new TestThread("Seventh" + " loop: Thread-" + i);
			if (i % 3 == 0 || i % 5 == 0)
				Thread.sleep(1500);
			T1.start();
		}

		LOGGER.debug("Active threads count: " + Thread.activeCount());

	}
}
