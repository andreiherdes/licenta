package com.cloud.licenta.app.component;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TaskScheduler {

	@Scheduled(cron = "* 5 0 1 * *")
	public void refreshRequestsRemaining() {
		System.out.println("Sunt un cron job");
	}
}
