package io.muenchendigital.digiwf.spring.cloudstream.utils.configuration;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.FunctionRouter;
import lombok.RequiredArgsConstructor;
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
    public MessageRoutingCallback customRouter() {
        final FunctionRouter functionRouter = new FunctionRouter(streamingProperties.getTypeMappings());
        return functionRouter.customRouter();
    }
}