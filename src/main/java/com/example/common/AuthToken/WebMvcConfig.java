package com.example.common.AuthToken;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
	@Bean
	AuthTokenInterceptor authTokenInterceptor() {
		return new AuthTokenInterceptor();
	}

	public void addInterceptors(InterceptorRegistry registry) {
		// トークン認証
		registry.addInterceptor(authTokenInterceptor()).excludePathPatterns("/user/googleSignIn")
				.excludePathPatterns("/category/getCategoryList").excludePathPatterns("/user/login")
				// TODO リリース時、以下削除
				.excludePathPatterns("/**");
	}
}
