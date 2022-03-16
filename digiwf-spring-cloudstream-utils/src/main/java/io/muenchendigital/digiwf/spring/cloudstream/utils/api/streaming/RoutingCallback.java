package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.function.context.MessageRoutingCallback;
import org.springframework.messaging.Message;

import java.util.Map;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.error.ErrorMessageDefaultListener.FUNCTION_ROUTING_ERROR;
import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.error.ErrorMessageDefaultListener.MISSING_TYPE_HEADER_ERROR;

@RequiredArgsConstructor
public class RoutingCallback implements MessageRoutingCallback {

    private final Map<String, String> typeMappings;

    @Override
    public FunctionRoutingResult routingResult(final Message<?> message) {
        final String functionDefinition;

        if (message.getHeaders().containsKey(StreamingHeaders.TYPE)) {
            final String header = (String) message.getHeaders().get(StreamingHeaders.TYPE);
            functionDefinition = this.typeMappings.getOrDefault(header, FUNCTION_ROUTING_ERROR);
        } else {
            functionDefinition = MISSING_TYPE_HEADER_ERROR;
        }

        return new FunctionRoutingResult(functionDefinition);
    }

}
