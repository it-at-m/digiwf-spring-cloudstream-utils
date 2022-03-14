package io.muenchendigital.digiwf.spring.cloudstream.utils.configuration;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.Message;
import org.springframework.util.StringUtils;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.error.ErrorMessageDefaultListener.FUNCTION_ROUTING_ERROR;
import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.error.ErrorMessageDefaultListener.MISSING_TYPE_HEADER_ERROR;

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
                final String functionDefinition;

                if (message.getHeaders().containsKey(StreamingHeaders.TYPE)) {
                    final String header = (String) message.getHeaders().get(StreamingHeaders.TYPE);
                    functionDefinition = StreamingConfiguration.this.streamingProperties.getTypeMappings().getOrDefault(header, FUNCTION_ROUTING_ERROR);
                } else {
                    functionDefinition = MISSING_TYPE_HEADER_ERROR;
                }

                return new FunctionRoutingResult(functionDefinition);
            }
        };
    }
}