package com.example.common.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:/message/ErrorMessage.properties")
@PropertySource("classpath:/message/SuccessMessage.properties")
@PropertySource("classpath:/message/ValidatingMessage.properties")
public class Message {
	@Autowired
	private Environment env;

	public String get(String key) {
		return env.getProperty(key);
	}
}
