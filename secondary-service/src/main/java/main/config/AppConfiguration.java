package main.config;

import lombok.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@Getter
@Setter
public class AppConfiguration {
    @Value("${base.endpoint}")
    public String baseEndpoint;
}