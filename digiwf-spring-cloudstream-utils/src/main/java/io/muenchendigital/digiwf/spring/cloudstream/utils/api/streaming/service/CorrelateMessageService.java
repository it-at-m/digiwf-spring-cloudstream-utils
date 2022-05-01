package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.service;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.dto.CorrelateMessageDto;
import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.Map;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders.DIGIWF_MESSAGE_NAME;
import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.infrastructure.StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorrelateMessageService {

    private static final String CORRELATEMESSAGEV_01 = "correlatemessagev01";

    private final Sinks.Many<Message<CorrelateMessageDto>> correlateMessageSink;

    /**
     * Sends a message to the bus that can be correlated by process instance ID and message name.
     *
     * @param messageHeaders   The incoming messages' header.
     * @param payloadVariables Data to send back.
     */
    public boolean sendCorrelateMessage(final MessageHeaders messageHeaders, final Map<String, Object> payloadVariables) {
        final CorrelateMessageDto.CorrelateMessageDtoBuilder correlateMessageTOV01Builder = CorrelateMessageDto.builder()
                .messageName((String) messageHeaders.get(DIGIWF_MESSAGE_NAME))
                .processInstanceId((String) messageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID))
                .payloadVariables(payloadVariables);

        final Message<CorrelateMessageDto> message = MessageBuilder
                .withPayload(correlateMessageTOV01Builder.build())
                .setHeader(StreamingHeaders.TYPE, CORRELATEMESSAGEV_01)
                .build();

        // TODO: If spring.cloud.function.definition=sendCorrelateMessage is not set, messaging doesnt work, but no error is thrown.
        // Same for spring.cloud.stream.bindings.sendCorrelateMessage-out-0.destination=
        final Sinks.EmitResult emitResult = this.correlateMessageSink.tryEmitNext(message);

        if (emitResult.isSuccess()) {
            log.info("The correlating message {} was successfully delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        } else {
            log.error("The correlating message {} couldn't be delivered to the eventbus.", message.getHeaders().get(MessageHeaders.ID));
        }
        log.debug("Message: {}", message);
        return emitResult.isSuccess();
    }

}