package io.muenchendigital.digiwf.spring.cloudstream.utils.configuration;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.RoutingCallback;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.spring.cloudstream.utils")
@EnableConfigurationProperties(StreamingProperties.class)
public class StreamingConfiguration {

    private final StreamingProperties streamingProperties;

    @Bean
    @ConditionalOnMissingBean
    public MessageRoutingCallback customRouter() {
        return new RoutingCallback(this.streamingProperties.getTypeMappings());
    }
}