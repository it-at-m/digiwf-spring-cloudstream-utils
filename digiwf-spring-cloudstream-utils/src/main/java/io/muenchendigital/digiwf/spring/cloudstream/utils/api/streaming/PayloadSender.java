package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

@Service
@RequiredArgsConstructor
@Slf4j
public class PayloadSender {

    private final Sinks.Many<Message<Object>> messageSink;

    public void sendPayload(final Object payload, final String type) {
        final Message<Object> message = MessageBuilder
                .withPayload(payload)
                .setHeader(StreamingHeaders.TYPE, type)
                .build();

        final Sinks.EmitResult emitResult = this.messageSink.tryEmitNext(message);

        if (emitResult.isSuccess()) {
            log.info("The message {} was successfully delivered to the Eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The message {} couldn't be delivered to the Eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }
        log.debug("Message: {}", message);
    }
}
