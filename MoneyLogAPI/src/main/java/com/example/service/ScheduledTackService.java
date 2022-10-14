package com.example.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.domain.User;
import com.example.mapper.UserMapper;

@Service
public class ScheduledTackService {

	@Autowired
	private UserMapper userMapper;

	public void scheduleDeleteParam(User user) throws ParseException {
		LocalDateTime today = LocalDateTime.now().plusHours(1);

		ZoneId zone = ZoneId.systemDefault();
		ZonedDateTime zonedDateTime = ZonedDateTime.of(today, zone);
		Instant instant = zonedDateTime.toInstant();
		Date runDate = Date.from(instant);

		Timer timer = new Timer(false);
		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				userMapper.deletePasswordParam(user);
				timer.cancel();
			}
		};
		timer.schedule(task, runDate);

	}

}
