package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming;

import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.messaging.Message;

import java.util.Map;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.error.ErrorMessageDefaultListener.FUNCTION_ROUTING_ERROR;
import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.error.ErrorMessageDefaultListener.MISSING_TYPE_HEADER_ERROR;

public class FunctionRouter {

    private final Map<String, String> typeMappings;

    // 1. Reads the message from the specified EventBus topic (see spring.cloud.stream.bindings.functionRouter-in-0.destination)
    // 2. Routes messages to the corresponding method (based on 'type'-header)

    public FunctionRouter(final Map<String, String> typeMappings) {
        this.typeMappings = typeMappings;
    }

    public MessageRoutingCallback customRouter() {
        return new MessageRoutingCallback() {

            @Override
            public FunctionRoutingResult routingResult(final Message<?> message) {
                final String functionDefinition;

                if (message.getHeaders().containsKey(StreamingHeaders.TYPE)) {
                    final String header = (String) message.getHeaders().get(StreamingHeaders.TYPE);
                    functionDefinition = typeMappings.getOrDefault(header, FUNCTION_ROUTING_ERROR);
                } else {
                    functionDefinition = MISSING_TYPE_HEADER_ERROR;
                }

                return new FunctionRoutingResult(functionDefinition);
            }
        };
    }
}
