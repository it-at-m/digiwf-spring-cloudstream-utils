package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayloadSender {

    private final Sinks.Many<Message<Object>> messageSink;

    public void sendPayload(final Object payload, final String type) {
        Map<String, Object> headers = new HashMap<>();
        headers.put(StreamingHeaders.TYPE, type);
        MessageHeaders messageHeaders = new MessageHeaders(headers);
        sendPayload(payload, messageHeaders);

    }

    public void sendPayload(final Object payload, final MessageHeaders messageHeaders) {

        final Message<Object> message = MessageBuilder.createMessage(payload, messageHeaders);

        final Sinks.EmitResult emitResult = this.messageSink.tryEmitNext(message);

        if (emitResult.isSuccess()) {
            log.info("The message {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The message {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }
        log.debug("Message: {}", message);
    }

}
