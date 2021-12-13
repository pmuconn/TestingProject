package com.boot.actuator.logview;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class LogViewEndpointAutoconfig {

    @Bean
    public LogViewEndpoint logViewEndpoint(Environment environment) {
        return new LogViewEndpoint("/srv/testproject/logs");
    }

    @Bean
    public LogViewEndpoint sampleAdditionalLogViewEndpoint(Environment environment) {
        return new SampleAdditionalLogViewEndpoint("/some/path/to/logs");
    }

}
