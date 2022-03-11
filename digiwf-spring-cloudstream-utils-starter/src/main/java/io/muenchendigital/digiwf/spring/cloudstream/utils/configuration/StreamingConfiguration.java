package io.muenchendigital.digiwf.spring.cloudstream.utils.configuration;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;

@Configuration
@RequiredArgsConstructor
@ComponentScan(basePackages = "io.muenchendigital.digiwf.spring.cloudstream.utils")
@EnableConfigurationProperties(StreamingProperties.class)
public class StreamingConfiguration {

    private final StreamingProperties streamingProperties;

    // 1. Reads the message from the specified EventBus topic (see spring.cloud.stream.bindings.functionRouter-in-0.destination)
    // 2. Routes messages to the corresponding method (based on 'type'-header)

    @Bean
    public MessageRoutingCallback customRouter() {
        return new MessageRoutingCallback() {

            @Override
            public FunctionRoutingResult routingResult(final Message<?> message) {
                return new FunctionRoutingResult(StreamingConfiguration.this.streamingProperties.getTypeMappings().get((String) message.getHeaders().get(StreamingHeaders.TYPE)));
            }
        };
    }

}