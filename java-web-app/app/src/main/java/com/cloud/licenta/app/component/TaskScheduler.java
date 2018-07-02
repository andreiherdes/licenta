package com.cloud.licenta.app.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.cloud.licenta.app.dao.UserPlanDao;
import com.cloud.licenta.app.service.UserService;

@Component
public class TaskScheduler {

	@Autowired
	private UserPlanDao userPlanDao;

	@Autowired
	private UserService userService;

	@Autowired
	private EmailServiceImpl emailService;

	// Daily task for usage updates
	@Scheduled(cron = "0 5 0 * * *")
	public void refreshRequestsRemaining() {
		try {
			userPlanDao.performBatchUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Hourly task for usage check
	@Scheduled(cron = "0 0 * * * *")
	public void checkUsageCountdownAndAlertUsers() {
		try {
			List<Long> usersWithLowUsage = userPlanDao.getUserPlansWithLowRequestsRemaining();
			List<String> emails = userService.getEmailsFilteredByUserId(usersWithLowUsage);

			for (String email : emails) {
				emailService.sendSimpleMessage(email, "Running low on requests",
						"Hello! This mail has been sent automatically, as you are running low on requests! Your API usage will be suspended, contact us for more details.");
			}
		} catch (Exception e) {

		}
	}
}
