package main;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@Slf4j
public class SecondaryServiceApplication {
	public static void main(String[] args) {
		System.setProperty("javax.net.ssl.trustStore", "./second.truststore");
		System.setProperty("javax.net.ssl.trustStorePassword", "secret");
		System.setProperty("jsse.enableSNIExtension", "false");
		SpringApplication.run(SecondaryServiceApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedOrigins("*");
			}
		};
	}
}