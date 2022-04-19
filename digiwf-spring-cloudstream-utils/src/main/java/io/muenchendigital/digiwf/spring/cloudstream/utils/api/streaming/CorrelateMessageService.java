package io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming;

import io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.dto.CorrelateMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Sinks;

import java.util.Map;

import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.StreamingHeaders.DIGIWF_MESSAGE_NAME;
import static io.muenchendigital.digiwf.spring.cloudstream.utils.api.streaming.StreamingHeaders.DIGIWF_PROCESS_INSTANCE_ID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CorrelateMessageService {

    private static final String CORRELATEMESSAGEV_01 = "correlatemessagev01";

    private final Sinks.Many<Message<CorrelateMessageDto>> correlateMessageSink;
    private final PayloadSender payloadSender;

    /**
     * Sends a message to the bus that can be correlated by process instance ID and message name.
     *
     * @param messageHeaders   The incoming messages' header.
     * @param payloadVariables Data to send back.
     */
    public void sendCorrelateMessage(final MessageHeaders messageHeaders, final Map<String, Object> payloadVariables) {
        final CorrelateMessageDto.CorrelateMessageDtoBuilder correlateMessageTOV01Builder = CorrelateMessageDto.builder()
                .messageName((String) messageHeaders.get(DIGIWF_MESSAGE_NAME))
                .processInstanceId((String) messageHeaders.get(DIGIWF_PROCESS_INSTANCE_ID))
                .payloadVariables(payloadVariables);

        payloadSender.sendPayload(correlateMessageTOV01Builder.build(), CORRELATEMESSAGEV_01);
    }

}